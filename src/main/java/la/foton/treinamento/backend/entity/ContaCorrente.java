package la.foton.treinamento.backend.entity;

public class ContaCorrente extends Conta{
	private Double limeteDeChequeEspecial;
	
	@Override
	public void debita(Double valor) throws Exception {
		if(this.saldo < valor) {
			throw new Exception("Saldo Insufiente!");
		}
		
		this.saldo = valor - valor;
	}
}
