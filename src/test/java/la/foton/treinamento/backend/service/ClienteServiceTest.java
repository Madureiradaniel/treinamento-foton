package la.foton.treinamento.backend.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;
import la.foton.treinamento.backend.entity.Cliente;

public class ClienteServiceTest {

	ClienteService service;

	Cliente cliente;

	@Before
	public void setUp() {
		cliente = new Cliente("78945612358", "Matheus Barbosa");
	}

	@Test
	public void deveVerificarQueClienteNaoEstaPendente() {
		cliente.ativa();

		try {
			service.verificaSituacao(cliente);
		} catch (NegocioException e) {
			fail();
		}
	}

	@Test
	public void naoDeveAceitarQueClienteEstejaPendente() {
		try {
			service.verificaSituacao(cliente);

			fail();
		} catch (NegocioException e) {
			assertEquals(Mensagem.SITUACAO_CLIENTE_PENDENTE, e.getMensagem());
		}
	}

}
