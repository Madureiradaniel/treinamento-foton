package la.foton.treinamento.backend.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import la.foton.treinamento.backend.DAO.ContaDAO;
import la.foton.treinamento.backend.DAO.ContaDAOMap;
import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;
import la.foton.treinamento.backend.entity.Cliente;
import la.foton.treinamento.backend.entity.Conta;
import la.foton.treinamento.backend.entity.ContaCorrente;
import la.foton.treinamento.backend.entity.EstadoDaConta;
import la.foton.treinamento.backend.entity.SituacaoDoCliente;
import la.foton.treinamento.backend.entity.TipoDaConta;
import la.foton.treinamento.backend.service.ContaService;

public class ContaServiceTest {

	private ContaService service;
	private Cliente titular;

	@Before
	public void setUp() {
		titular = new Cliente("47576469115", "Daniel Madureira");
	}

	@Test
	public void deveAbrirUmaContaCorrente() {
		try {
			titular.ativa();
			Conta conta = service.abreConta(titular, TipoDaConta.CORRENTE);

			Conta contaConsultada = service.ConsultaPorNumero(conta.getNumero());

			assertNotNull(contaConsultada);
			assertEquals(conta.getSaldo(), contaConsultada.getSaldo());
			assertEquals(500.00, ((ContaCorrente) conta).getLimeteDeChequeEspecial(), 0.001);

		} catch (NegocioException e) {
			fail(e.getMensagem().getTexto());
		}
	}

	@Test
	public void naoDeveAbrirContaComOTitularPendente() {
		try {

			Conta conta = service.abreConta(titular, TipoDaConta.POUPANCA);
			fail();

		} catch (NegocioException e) {

			assertEquals(titular.getSituacao(), SituacaoDoCliente.PENDENTE);
		}

	}

	@Test
	public void deveInativarUmaConta() {

		try {
			// abrindo a conta
			titular.ativa();
			Conta conta = service.abreConta(titular, TipoDaConta.CORRENTE);

			// inativando a conta
			service.encerraConta(conta);

			// checando
			assertEquals(conta.getEstado(), EstadoDaConta.INATIVADA);

		} catch (NegocioException e) {
			fail(e.getMensagem().getTexto());

		}

	}

	@Test
	public void naoDeveInativarUmaContaComSaldo() {
		Conta conta = null;
		try {
			// abrindo a conta
			titular.ativa();
			conta = service.abreConta(titular, TipoDaConta.CORRENTE);
			// acao
			conta.credita(100.00);
			service.encerraConta(conta);
			fail();
		} catch (NegocioException e) {
			assertEquals(600.00, conta.getSaldo(), 0.01);
		}
	}

	@Test
	public void naoDeveInativarUmaContaInativada() {
		Conta conta = null;
		try {
			// abrindo a conta
			titular.ativa();
			conta = service.abreConta(titular, TipoDaConta.CORRENTE);
			// acao
			conta.inativa();
			service.encerraConta(conta);
			fail();
		} catch (NegocioException e) {
			assertEquals(conta.getEstado(), EstadoDaConta.INATIVADA);
		}
	}

	@Test
	public void naoDeveInativarUmaContaComSaldoNegativo() {
		Conta conta = null;
		try {
			// abrindo a conta
			titular.ativa();
			conta = service.abreConta(titular, TipoDaConta.CORRENTE);
			// acao
			conta.debita(200.00);
			service.encerraConta(conta);
			fail();
		} catch (NegocioException e) {
			assertEquals(300.00, conta.getSaldo(), 0.01);
		}
	}

	@Test
	public void deveDebitarUmvalorContaCorrente() {
		
		Conta conta = null;
		try {
			titular.ativa();
			conta = service.abreConta(titular, TipoDaConta.CORRENTE);

			service.debita(conta,499.99);
			assertEquals(0.01, conta.getSaldo(), 0.001);

			Conta conta2 = service.ConsultaPorNumero(conta.getNumero());

			assertEquals(conta.getSaldo(), conta2.getSaldo());
			assertEquals(conta.getNumero(), conta2.getNumero());

		} catch (NegocioException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deveTransferirUmValorEntreContas() {
		
		Conta origem = null;
		Conta destino = null;
		titular.ativa();
		try {
			origem = service.abreConta(titular, TipoDaConta.CORRENTE);
			destino = service.abreConta(titular, TipoDaConta.POUPANCA);
			
			service.transfere(origem, destino, 499.99);
			
			assertEquals(0.01,service.ConsultaPorNumero(origem.getNumero()).getSaldo(),0.001 );
			assertEquals(499.99, service.ConsultaPorNumero(destino.getNumero()).getSaldo(),0.001);
			
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			
		}
		
		
	}
	
	@Test
	public void naoDeveTransferirUmValorEntreContasSemSaldo() {
		
		Conta origem = null;
		Conta destino = null;
		titular.ativa();
		try {
			origem = service.abreConta(titular, TipoDaConta.POUPANCA);
			destino = service.abreConta(titular, TipoDaConta.CORRENTE);
			
			
			service.transfere(origem, destino, 0.01);
			fail();	
			
		} catch (NegocioException e) {
			assertEquals(e.getMessage(), Mensagem.SALDO_INSUFICIENTE.getTexto());
		}
		
		
	}


}
