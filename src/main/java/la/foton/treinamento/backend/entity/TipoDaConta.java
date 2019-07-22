package la.foton.treinamento.backend.entity;

public enum TipoDaConta {
	
	CORRENTE(1), POUPANCA(2);
	
	private Integer chave;
	
	private TipoDaConta(Integer chave) {
		this.chave = chave;
	}
	
	public Integer getChave() {
		return chave;
	}
}
