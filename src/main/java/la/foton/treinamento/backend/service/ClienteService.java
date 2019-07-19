package la.foton.treinamento.backend.service;

import javax.ejb.Stateless;

import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;
import la.foton.treinamento.backend.entity.Cliente;
import la.foton.treinamento.backend.entity.SituacaoDoCliente;

@Stateless
public class ClienteService {
	
	public void verificaSituacao(Cliente cliente) throws NegocioException {
		if (SituacaoDoCliente.PENDENTE.equals(cliente.getSituacao())) {
			throw new NegocioException(Mensagem.SITUACAO_CLIENTE_PENDENTE);
		}

	}

}
