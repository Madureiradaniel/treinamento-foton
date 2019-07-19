package la.foton.treinamento.backend.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import la.foton.treinamento.backend.common.exception.NegocioException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Conta {
	@Id
	private Integer numero;
	
	@Column
	private Integer agencia;
	
	@Column
	protected Double saldo;
	
	@ManyToOne
	@JoinColumn(name = "cpf")
	private Cliente titular;
	
	@Column
	@Convert(converter = TipoDaConta.class)
	protected TipoDaConta tipo;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private EstadoDaConta estado;
	
	@PrePersist
	public void prePersist() {
		this.agencia = 1234;
	}

	public Conta() {
		this.agencia = 1234;
		this.saldo = 0.0;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void credita(Double valor) {
		saldo = saldo + valor;
	}

	public abstract void debita(Double valor) throws NegocioException;

	public void tranfere(Double valor, Conta contaDestino) throws NegocioException {
		this.debita(valor);

		contaDestino.credita(valor);
	}

	public Cliente getTitular() {
		return titular;
	}

	public EstadoDaConta getEstado() {
		return estado;
	}

	public void setTitular(Cliente titular) {
		this.titular = titular;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public void ativa() {
		this.estado = EstadoDaConta.ATIVO;
	}
	
	public void inativa() {
		this.estado = EstadoDaConta.INATIVADA;		
	}
	
	public TipoDaConta getTipo() {
		return tipo;
	}

}
