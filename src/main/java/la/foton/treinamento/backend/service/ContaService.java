package la.foton.treinamento.backend.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;
import la.foton.treinamento.backend.dao.ContaDAO;
import la.foton.treinamento.backend.entity.Cliente;
import la.foton.treinamento.backend.entity.Conta;
import la.foton.treinamento.backend.entity.ContaCorrente;
import la.foton.treinamento.backend.entity.ContaPoupanca;
import la.foton.treinamento.backend.entity.EstadoDaConta;
import la.foton.treinamento.backend.entity.TipoDaConta;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContaService {
	
	//private static ContaService instancia;
	
	@Inject
	private ContaDAO dao; // = ContaDAOMap.get();
	
	@EJB
	private ClienteService clienteService;  // = ClienteService.get();
	
	/*private ContaService() {
	}
	
	public static ContaService get() {
		if (instancia == null) {
			instancia = new ContaService();
		}
		
		return instancia;
	}*/
	
	public void verificaEstado(Conta conta) throws NegocioException {
		if(EstadoDaConta.ENCERRADA.equals(conta.getEstado())) {
			throw new NegocioException(Mensagem.CONTA_JA_ENCERRADA);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Conta abreConta(Cliente titular, TipoDaConta tipo) throws NegocioException {
		
		clienteService.verificaSituacao(titular);
		
		Conta conta = criaConta(tipo, titular);
		conta.setNumero(dao.geraNumero());
		conta.ativa();
		
		dao.insere(conta);
		
		return conta;
	}
	
	public Conta consultaPorNumero(Integer numero) throws NegocioException {
		Conta conta = dao.consultaPorNumero(numero);
		
		if(conta == null) {
			throw new NegocioException(Mensagem.CONTA_NAO_ENCONTRADA);
		}
		
		return conta;
	}

	private Conta criaConta(TipoDaConta tipo, Cliente titular) {

		Conta conta = null;
		
		if(TipoDaConta.CORRENTE.equals(tipo)) {
			conta = new ContaCorrente();
			((ContaCorrente) conta).setLimiteDeChequeEspecial(500.0);
		}else {
			conta = new ContaPoupanca();
			((ContaPoupanca) conta).setDiaDoAniversario(1);
		}
		
		conta.setTitular(titular);
		
		return conta;
	}
	
	public void encerraConta(Integer numero) throws NegocioException {
		Conta c = dao.consultaPorNumero(numero);
		
		c.encerra();
		
		dao.atualiza(c);
	}
	
	public void deposita(Double valor, Conta conta) throws NegocioException {
		Conta c = dao.consultaPorNumero(conta.getNumero());
		
		if(c.getEstado() == EstadoDaConta.ATIVA) {
			c.credita(valor);
			dao.atualiza(c);
		}else {
			throw new NegocioException(Mensagem.CONTA_JA_ENCERRADA);
		}
		
	}
	
	public void saca(Double valor, Conta conta) throws NegocioException {
		Conta c = dao.consultaPorNumero(conta.getNumero());
		
		c.debita(valor);
		dao.atualiza(c);
	}
	
	public void transfere(Conta conta, Double valor, Conta contaDestino) throws NegocioException {
		Conta c = dao.consultaPorNumero(conta.getNumero());
		Conta c2 = dao.consultaPorNumero(contaDestino.getNumero());
				
		c.transfere(valor, c2);
		
		dao.atualiza(c);
		dao.atualiza(c2);
	}
	
}
