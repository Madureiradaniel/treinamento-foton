package la.foton.treinamento.backend.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import la.foton.treinamento.backend.DAO.ContaDAO;
import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;
import la.foton.treinamento.backend.entity.Cliente;
import la.foton.treinamento.backend.entity.Conta;
import la.foton.treinamento.backend.entity.ContaCorrente;
import la.foton.treinamento.backend.entity.ContaPoupanca;
import la.foton.treinamento.backend.entity.EstadoDaConta;
import la.foton.treinamento.backend.entity.TipoDaConta;

@Stateless
public class ContaService {

	
	@Inject
	private ContaDAO dao;

	@EJB
	private ClienteService clienteService;

	private Conta criaConta(TipoDaConta tipo, Cliente titular) {
		Conta conta = null;
		if (TipoDaConta.CORRENTE.equals(tipo)) {
			conta = new ContaCorrente();
			((ContaCorrente) conta).setLimeteDeChequeEspecial(500.00);
		} else {
			conta = new ContaPoupanca();
			((ContaPoupanca) conta).setDiaDoAniversario(1);
		}
		conta.setTitular(titular);
		return conta;
	}

	public Conta ConsultaPorNumero(Integer numero) throws NegocioException {
		Conta conta = dao.consultaPorNumero(numero);
		if (conta == null) {
			throw new NegocioException(Mensagem.CONTA_NAO_ENCONTRADA);
		}
		return conta;
	}

	public Conta abreConta(Cliente titular, TipoDaConta tipo) throws NegocioException {

		clienteService.verificaSituacao(titular);

		Conta conta = criaConta(tipo, titular);

		conta.setNumero(dao.geraNumero());

		conta.ativa();

		dao.insere(conta);

		return conta;

	}

	public void credita(Conta conta, Double valor) {
		conta.credita(valor);
		dao.salvar(conta);
	}

	public void debita(Conta conta, Double valor) throws NegocioException {
		conta.debita(valor);
		dao.salvar(conta);
	}

	public void transfere(Conta origem, Conta destino, Double valor) throws NegocioException {

		debita(origem, valor);
		credita(destino,valor);

		dao.salvar(origem);
		dao.salvar(destino);

	}

	public void encerraConta(Conta conta) throws NegocioException {

		if (TipoDaConta.CORRENTE.equals(conta.getTipo())) {
			if ((conta.getSaldo() - ((ContaCorrente) conta).getLimeteDeChequeEspecial()) != 0) {
				throw new NegocioException(Mensagem.EXISTE_SALDO_NA_CONTA);
			}

		} else {
			if (conta.getSaldo() != 0) {
				throw new NegocioException(Mensagem.EXISTE_SALDO_NA_CONTA);
			}
		}
		if (conta.getEstado() != EstadoDaConta.ATIVO) {
			throw new NegocioException(Mensagem.CONTA_JA_INATIVA);
		}

		conta.inativa();
		dao.salvar(conta);

	}

}
