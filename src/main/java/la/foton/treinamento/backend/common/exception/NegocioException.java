package la.foton.treinamento.backend.common.exception;

public class NegocioException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Mensagem mensagem;
	
	public NegocioException(Mensagem mensagem) {
		super(mensagem.getTexto());
	}

	public Mensagem getMensagem() {
		return mensagem;
	}
	
}
