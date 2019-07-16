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
		conta = new ContaPoupanca();
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
		} catch (NegocioException e) {
			assertEquals(Mensagem.SALDO_INSUFICIENTE, e.getMensagem().getTexto());
		}

	}

	@Test
	public void deveTransferirUmValor() {
		
		Conta contaDestino;

		contaDestino = new ContaPoupanca();
		try {
			conta.tranfere(99.99, contaDestino);
			assertEquals(0.01,conta.getSaldo(),0.001);
			assertEquals(99.99,contaDestino.getSaldo(),0.001);
		} catch (NegocioException e) {
			fail();
			e.printStackTrace();
		}
	}
}
