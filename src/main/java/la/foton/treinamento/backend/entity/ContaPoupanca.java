package la.foton.treinamento.backend.entity;

public class ContaPoupanca extends Conta{
	private Integer diaDoAniversario;

	@Override
	public void debita(Double valor) throws Exception {
		if(saldo < valor) {
			throw new Exception("Saldo Insufiente!");
		}
		
		saldo = valor - valor;
	}
	
	
}
