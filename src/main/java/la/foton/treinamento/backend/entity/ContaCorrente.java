package la.foton.treinamento.backend.entity;

import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;

public class ContaCorrente extends Conta{
	
	private Double limeteDeChequeEspecial;
	
	@Override
	public Double getSaldo() {
		
		return saldo + limeteDeChequeEspecial;
	}
	
	public ContaCorrente() {
	this.tipo = TipoDaConta.CORRENTE;
		this.limeteDeChequeEspecial = 0.00;
	}
	
	@Override
	public void debita(Double valor) throws NegocioException {
		if(saldo + limeteDeChequeEspecial < valor) {
			throw new NegocioException(Mensagem.SALDO_INSUFICIENTE);
		}
		
		saldo -= valor;
	}

	public Double getLimeteDeChequeEspecial() {
		return limeteDeChequeEspecial;
	}

	public void setLimeteDeChequeEspecial(Double limeteDeChequeEspecial) {
		this.limeteDeChequeEspecial = limeteDeChequeEspecial;
	}
	
	
}
