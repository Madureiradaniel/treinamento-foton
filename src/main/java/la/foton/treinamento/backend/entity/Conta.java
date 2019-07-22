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

import la.foton.treinamento.backend.common.exception.Mensagem;
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
	@Enumerated(EnumType.ORDINAL)
	protected EstadoDaConta estado;
	
	@Column
	@Convert(converter = TipoDaContaConverter.class)
	protected TipoDaConta tipo;
	
	@PrePersist
	public void prePersist() {
		this.agencia = 1234;
	}
	
	public Conta() {
		// this.agencia = 1234;
		this.saldo = 0.0;
	}
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Double getSaldo() {
		return saldo;
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

	public void credita(Double valor) {
		saldo += valor;
	}
	
	public abstract void debita(Double valor) throws NegocioException;
	
	public void transfere(Double valor, Conta contaDestino) throws NegocioException {
		this.debita(valor);		
		
		contaDestino.credita(valor);
	}
	
	public void ativa() {
		this.estado = EstadoDaConta.ATIVA;
	}
	
	public void encerra() throws NegocioException {
		if(EstadoDaConta.ENCERRADA.equals(estado)) {
			throw new NegocioException(Mensagem.CONTA_JA_ENCERRADA);		
		}
		
		if(this.saldo > 0) {
			throw new NegocioException(Mensagem.CONTA_NAO_PODE_SER_ENCERRADA);
		}
		
		this.estado = EstadoDaConta.ENCERRADA;
		
		/*if (this.getEstado() == EstadoDaConta.ATIVA) {
			
			if(this.getSaldo() == 0.00) {
				this.estado = EstadoDaConta.ENCERRADA;
			}else {
				throw new NegocioException(Mensagem.CONTA_POSSUI_SALDO_E_NAO_PODE_SER_ENCERRADA);
			}
						
		}else {
			throw new NegocioException(Mensagem.CONTA_JA_ENCERRADA);
		}*/
		
		
	}
	
	public void encerrada() {
		estado = EstadoDaConta.ENCERRADA;
	}
	
}
