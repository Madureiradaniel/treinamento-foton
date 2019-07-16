package la.foton.treinamento.backend.service;

import la.foton.treinamento.backend.DAO.ContaDAO;
import la.foton.treinamento.backend.DAO.ContaDAOMap;
import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;
import la.foton.treinamento.backend.entity.Cliente;
import la.foton.treinamento.backend.entity.Conta;
import la.foton.treinamento.backend.entity.ContaCorrente;
import la.foton.treinamento.backend.entity.ContaPoupanca;
import la.foton.treinamento.backend.entity.TipoDaConta;

public class ContaService {

	private static ContaService instancia;

	private ContaDAO dao = ContaDAOMap.get();

	private ClienteService clienteService = ClienteService.get();

	private ContaService() {

	}

	public Conta ConsultaPorNumero(Integer numero) throws NegocioException {
		Conta conta = dao.consultaPorNumero(numero);
		if(conta == null) {
			throw new NegocioException(Mensagem.CONTA_NAO_ENCONTRADA);
		}
		return conta;
	}

	public static ContaService get() {
		if (instancia == null) {
			instancia = new ContaService();
		}

		return instancia;
	}

	public Conta abreConta(Cliente titular, TipoDaConta tipo) throws NegocioException {

		clienteService.verificaSituacao(titular);

		Conta conta = criaConta(tipo, titular);

		conta.setNumero(dao.geraNumero());

		conta.ativa();

		dao.insere(conta);

		return conta;

	}

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

}
