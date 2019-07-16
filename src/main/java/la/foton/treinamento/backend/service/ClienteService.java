package la.foton.treinamento.backend.service;

import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;
import la.foton.treinamento.backend.entity.Cliente;
import la.foton.treinamento.backend.entity.SituacaoDoCliente;

public class ClienteService {

	private static ClienteService instancia;
	
	private ClienteService() {
	}
	
	public static ClienteService get() {
		if (instancia == null) {
			instancia = new ClienteService();
		}
		return instancia;
	}
	
	public void verificaSituacao(Cliente cliente) throws NegocioException {
		if (SituacaoDoCliente.PENDENTE.equals(cliente.getSituacao())) {
			throw new NegocioException(Mensagem.SITUACAO_CLIENTE_PENDENTE);
		}

	}

}
