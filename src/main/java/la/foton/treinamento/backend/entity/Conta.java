package la.foton.treinamento.backend.entity;

import la.foton.treinamento.backend.common.exception.NegocioException;

public abstract class Conta {
	private Integer numero;
	private Integer agencia;
	protected Double saldo;
	private Cliente titular;
	protected TipoDaConta tipo;
	private EstadoDaConta estado;

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
