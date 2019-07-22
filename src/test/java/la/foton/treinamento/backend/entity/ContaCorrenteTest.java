package la.foton.treinamento.backend.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;

public class ContaCorrenteTest {
	
	private ContaCorrente conta;
	
	@Before
	public void setUp() {
		conta = new ContaCorrente();
		conta.credita(500.0);
		conta.setLimiteDeChequeEspecial(100.0);
	}
	
	@Test
	public void deveDebitarValorNaContaCorrenteQuePossuiSaldoJuntoComLimite() {
		try {
			conta.debita(599.99);
			assertEquals(0.01, conta.getSaldo(), 0.01);
		} catch (NegocioException e) {
			fail();
		}
	}
	
	@Test
	public void naoDeveDebitarValorNaContaQueExtrapolaOSaldoMaisOLimite() {
		try {
			conta.debita(600.01);
			fail();
		} catch (NegocioException e) {
			assertEquals(Mensagem.SALDO_INSUFICIENTE, e.getMensagem());
		}
	}
}
