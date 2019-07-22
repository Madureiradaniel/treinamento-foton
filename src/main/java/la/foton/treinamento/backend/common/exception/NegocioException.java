package la.foton.treinamento.backend.common.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class NegocioException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private Mensagem mensagem;
	
	public NegocioException(Mensagem mensagem) {
		super(mensagem.getTexto());
		
		this.mensagem = mensagem;
	}

	public Mensagem getMensagem() {
		return mensagem;
}
	
}
