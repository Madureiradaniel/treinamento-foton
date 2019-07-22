package la.foton.treinamento.backend.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;
import la.foton.treinamento.backend.entity.Cliente;
import la.foton.treinamento.backend.entity.Conta;
import la.foton.treinamento.backend.entity.ContaCorrente;
import la.foton.treinamento.backend.entity.EstadoDaConta;
import la.foton.treinamento.backend.entity.TipoDaConta;

public class ContaServiceTest {
	
	private ContaService service;
	
	private Cliente titular;
	private Cliente titular2;
	
	@Before
	public void setUp() {
		titular = new Cliente("784561321595", "Matheus Barbosa");
	}
	
	@Test
	public void deveAbrirUmaContaCorrente() {
		try {
			titular.ativa();
			
			Conta conta = service.abreConta(titular, TipoDaConta.CORRENTE);
						
			Conta contaConsultada = service.consultaPorNumero(conta.getNumero());
			
			assertNotNull(contaConsultada);
			assertEquals(conta.getSaldo(), contaConsultada.getSaldo());
			assertEquals(500.00, ((ContaCorrente) conta).getLimiteDeChequeEspecial(), 0.0);
			
			
		} catch (NegocioException e) {
			fail(e.getMensagem().getTexto());
		}
	}
	
	@Test
	public void naoDeveAbrirUmaContaCorrenteParaUmTitularPendente() {
		try {
			service.abreConta(titular, TipoDaConta.CORRENTE);
			
			fail();
			
		} catch (NegocioException e) {
			assertEquals(Mensagem.SITUACAO_CLIENTE_PENDENTE, e.getMensagem());
		}		
	}
	
	@Test
	public void deveEncerrarUmaContaAtivaESemSaldo() {
		try {
			
			titular.ativa();
			
			Conta conta = service.abreConta(titular, TipoDaConta.CORRENTE);;
			Conta contaConsultada = service.consultaPorNumero(conta.getNumero());
						
			conta.ativa();
			
			service.encerraConta(conta.getNumero());
			
			assertEquals(conta.getEstado(), EstadoDaConta.ENCERRADA);
			assertEquals(conta.getEstado(), contaConsultada.getEstado());
			
			
		} catch (NegocioException e) {
			fail(e.getMensagem().getTexto());
		}
	}
	
	@Test
	public void naoDeveEncerrarUmaContaAtivaEComSaldo() {
		
		try {
			
			titular.ativa();
			
			Conta conta = service.abreConta(titular, TipoDaConta.CORRENTE);
			
			conta.ativa();
			
			service.deposita(100.0, conta);
			service.encerraConta(conta.getNumero());
			
			fail();
			
		} catch (NegocioException e) {
			assertEquals(Mensagem.CONTA_NAO_PODE_SER_ENCERRADA, e.getMensagem());
		}
	}
	
	@Test
	public void deveDepositarValoresEmConta() {
		try {
			
			titular.ativa();
			
			Conta conta = service.abreConta(titular, TipoDaConta.CORRENTE);
			Conta contaConsultada = service.consultaPorNumero(conta.getNumero());
			conta.ativa();
			
			service.deposita(100.0, conta);
			
			
			
			assertEquals(conta.getSaldo(), contaConsultada.getSaldo());
			assertEquals(600.0, conta.getSaldo(), 0.0);
			
			
		} catch (NegocioException e) {
			fail(e.getMensagem().getTexto());
		}
	}
	
	@Test
	public void naoDeveDepositarValoresEmContaComEstadoEncerrada() {
		try {
			
			titular.ativa();
			
			Conta conta = service.abreConta(titular, TipoDaConta.CORRENTE);
			conta.ativa();
			
			service.encerraConta(conta.getNumero());
			service.deposita(100.0, conta);
			
			fail();
			
		} catch (NegocioException e) {
			assertEquals(Mensagem.CONTA_JA_ENCERRADA, e.getMensagem());
		}
	}
	
	@Test
	public void deveSacarUmValorDaContaQuePossuiSaldo(){
		try {
			
			titular.ativa();
			
			Conta conta = service.abreConta(titular, TipoDaConta.CORRENTE);
			Conta contaConsultada = service.consultaPorNumero(conta.getNumero());
			conta.ativa();
			
			service.deposita(100.0, conta);
			service.saca(100.0, conta);
			
			
			
			assertEquals(conta.getSaldo(), contaConsultada.getSaldo());
			assertEquals(500.0, conta.getSaldo(), 0.0);
			
			
		} catch (NegocioException e) {
			fail(e.getMensagem().getTexto());
		}
	}
	
	@Test
	public void naoDeveSacarUmValorDaContaQueNaoPossuiSaldo(){
		try {
			
			titular.ativa();
			
			Conta conta = service.abreConta(titular, TipoDaConta.CORRENTE);
			conta.ativa();
			
			service.deposita(100.0, conta);
			service.saca(610.0, conta);
			
			fail();
			
		} catch (NegocioException e) {
			assertEquals(Mensagem.SALDO_INSUFICIENTE, e.getMensagem());
		}
	}
	
	@Test 
	public void deveTranferirUmValorEntreContas() {
		try {
			
			titular2 = new Cliente("98765432114", "Andresson Souza");
			
			titular.ativa();
			titular2.ativa();
			
			Conta conta = service.abreConta(titular, TipoDaConta.CORRENTE);
			Conta conta2 = service.abreConta(titular2, TipoDaConta.POUPANCA);
			Conta contaConsultada = service.consultaPorNumero(conta.getNumero());
			Conta contaConsultada2 = service.consultaPorNumero(conta2.getNumero());
			conta.ativa();
			conta2.ativa();
			
			service.deposita(100.0, conta);
			service.transfere(conta, 100.0, conta2);
			
			assertEquals(conta.getSaldo(), contaConsultada.getSaldo());
			assertEquals(conta2.getSaldo(), contaConsultada2.getSaldo());
			assertEquals(500.0, conta.getSaldo(), 0.0);
			assertEquals(100.0, conta2.getSaldo(), 0.0);
			
			
		} catch (NegocioException e) {
			fail(e.getMensagem().getTexto());
		}
				
	}
	
	
}
