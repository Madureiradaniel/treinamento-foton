package la.foton.treinamento.backend.common.exception;

public enum Mensagem {
	
	//Conta
	SALDO_INSUFICIENTE("Saldo Insuficiente!"), //
	CONTA_NAO_ENCONTRADA("Conta não Encontrada!"),//
	CONTA_JA_ENCERRADA("Conta já Encerrada!"),//
	CONTA_NAO_PODE_SER_ENCERRADA("Conta Pode ser Encerrada pois Possuir Saldo!"),//

	//Cliente
	SITUACAO_CLIENTE_PENDENTE("Situação do Cliente está Pendente!"),
	CLIENTE_NAO_ENCONTRADO("Cliente não encontrado"), 
	CLINTE_NAO_PODE_SER_CADASTRADO("Cliente nao pode ser cadastrado, dados inválidos"),
	CLIENTE_JA_CADASTRADO("Cliente já cadastrado"),
	NAO_EXISTEM_CLIENTES("Não existe clientes cadastrados");
	
	private String texto;
	
	private Mensagem(String texto) {
		this.texto = texto;
	}
	
	public String getTexto() {
		
		return this.texto;
	}
	
}
