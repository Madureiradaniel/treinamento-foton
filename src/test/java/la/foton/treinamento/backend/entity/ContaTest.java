package la.foton.treinamento.backend.entity;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;

public class ContaTest {

	private Conta conta;

	@Before
	public void setUp() {
		conta = new ContaCorrente();
		conta.credita(100.0);
	}

	@After
	public void tearDown() {

	}

	@Test
	public void deveCreditarUmValorNaConta() {
		conta.credita(10.0);
		assertEquals(110.0, conta.getSaldo(), 0.0);
	}

	@Test
	public void deveDebitarUmValorNaContaQuePossuiSaldoSuficiente() {
		try {
			conta.debita(15.0);
		} catch (NegocioException e) {
			fail();
		}

		assertEquals(85.0, conta.getSaldo(), 0.0);
	}
	
	@Test
	public void naoDeveDebitarValorEmContaQueNaoPossuiSaldo() {
		try {
			conta.debita(100.01);
			fail();
		} catch (NegocioException e) {
			assertEquals(Mensagem.SALDO_INSUFICIENTE, e.getMensagem());
		}
				
	}
	
	@Test
	public void deveTranferirUmValorEntreContas() {
		Conta contaDeCredito = new ContaPoupanca();
		try {
			conta.transfere(99.99, contaDeCredito);
			assertEquals(0.01, conta.getSaldo(), 0.1);
			assertEquals(99.99, contaDeCredito.getSaldo(), 0.0);
		} catch (NegocioException e) {
			fail(e.getMensagem().getTexto());
		}		
		
		
	}
	
	@Test
	public void naoDeveTranferirUmValorDeContaCorrenteParaUmaContaDestinoQueNaoPossuiSaldo() {
		
	}
	
	
}
