package la.foton.treinamento.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Cliente {
	
	@Id
	@Column
	private String cpf;
	
	@Column(nullable = false)
	private String nome;

	@Column
	@Enumerated(EnumType.ORDINAL)
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
