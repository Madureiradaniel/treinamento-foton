package la.foton.treinamento.backend.entity;

public class Cliente {

	private String cpf;

	private String nome;

	private SituacaoDoCliente situacao;

	public Cliente() {
		situacao = SituacaoDoCliente.PENDENTE;
	}

	public Cliente(String cpf, String nome) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		situacao = SituacaoDoCliente.PENDENTE;		
	}

	public void ativa() {
		situacao = SituacaoDoCliente.ATIVO;
	}

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	public SituacaoDoCliente getSituacao() {
		return situacao;
	}
}
