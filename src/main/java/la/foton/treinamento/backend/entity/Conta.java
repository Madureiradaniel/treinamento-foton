package la.foton.treinamento.backend.entity;

public abstract class Conta {
	private Integer numero;
	private Integer agencia;
	protected Double saldo;
	private Cliente titular;
	private TipoDaConta tipo;
	
	
	public Conta() {
		this.saldo = 0.0;
	}
	
	public Double getSaldo() {
		return saldo;
	}

	public void credita(Double valor) {
		saldo = saldo + valor;
	}
	
	public abstract void debita(Double valor) throws Exception;
	
	public void tranfere(Double valor, Conta contaDestino) throws Exception {
		this.debita(valor);		
		
		contaDestino.credita(valor);
	}
	
	
}
