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
		conta.credita(500.00);
		conta.setLimeteDeChequeEspecial(100.00);
	}

	@Test
	public void deveDebitarValornaContaCorrenteQuePOssuiSaldoComLimite() {

		try {
			conta.debita(599.99);
			assertEquals(0.01, conta.getSaldo(), 0.01);
		} catch (Exception e) {
			fail();

		}

	}

	@Test
	public void naoDeveDebitarValorNaContaQueExtrapolaOSaldoMaisOLimite() {
		try {
			conta.debita(600.01);
			fail();
		} catch (NegocioException e) {
			// checar uma exceção

			assertEquals(Mensagem.SALDO_INSUFICIENTE.getTexto(), e.getMessage());

		}

	}
}
