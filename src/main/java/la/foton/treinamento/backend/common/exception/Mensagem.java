package la.foton.treinamento.backend.common.exception;

public enum Mensagem {
	//conta
	SALDO_INSUFICIENTE("SALDO INSUFICIENTE"),
	CONTA_NAO_ENCONTRADA("Conta Não encontrada"),
	
	//cliente
	SITUACAO_CLIENTE_PENDENTE("Situação do cliente está pendente");

	private String texto;
	
	private Mensagem(String texto) {
		this.texto = texto;
	}
	
	public String getTexto() {
		return texto;
	}

}