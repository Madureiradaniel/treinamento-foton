package la.foton.treinamento.backend.entity;

public class Cliente {
	private String cpf;
	private String nome;
	private SituacaoDoCliente situacao;
	
	public Cliente(){
		situacao = SituacaoDoCliente.PENDENTE;
	}
	
	public void ativa() {
		situacao = SituacaoDoCliente.ATIVO;
	}
}
