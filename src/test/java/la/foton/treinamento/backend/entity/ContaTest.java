package la.foton.treinamento.backend.entity;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ContaTest {

	private Conta conta;

	@Before
	public void setUp() {
		new Conta();
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
		} catch (Exception e) {
			fail();
		}

		assertEquals(85.0, conta.getSaldo(), 0.0);
	}
	
	@Test
	public void naoDeveDebitarValorEmContaQueNaoPossuiSaldo() {
		try {
			conta.debita(100.01);
			fail();
		} catch (Exception e) {
			assertEquals("Saldo Insuficiente!", e.getMessage());
		}
		
		
		
	}
}
