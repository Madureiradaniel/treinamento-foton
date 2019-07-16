package la.foton.treinamento.backend.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import la.foton.treinamento.backend.common.exception.NegocioException;
import la.foton.treinamento.backend.entity.Cliente;
import la.foton.treinamento.backend.entity.Conta;
import la.foton.treinamento.backend.entity.ContaCorrente;
import la.foton.treinamento.backend.entity.SituacaoDoCliente;
import la.foton.treinamento.backend.entity.TipoDaConta;
import la.foton.treinamento.backend.service.ContaService;

public class ContaServiceTest {

	private ContaService service = ContaService.get();
	private Cliente titular;

	@Before
	public void setUp() {
		titular = new Cliente("47576469115", "Daniel Madureira");
		titular.ativa();
	}

	@Test
	public void deveAbrirUmaContaCorrente() {
		try {

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
	public void naoDeveAbrirContaComOCaraPendente() {
		try {
			
			Conta conta = service.abreConta(titular, TipoDaConta.POUPANCA);
			assertEquals(conta.getTitular().getSituacao(),SituacaoDoCliente.ATIVO);
			
		} catch (NegocioException e) {
			
			fail(e.getMensagem().getTexto());
		}
		
	}

}
