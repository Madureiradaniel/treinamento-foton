package la.foton.treinamento.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;

@Entity
@Table(name = "ContaCorrente")
public class ContaCorrente extends Conta{
	
	@Column(name = "limiteDeChequeEspecial")
	private Double limiteDeChequeEspecial;
	
	public ContaCorrente(){
		this.limiteDeChequeEspecial = 0.0;
		this.tipo = TipoDaConta.CORRENTE;
	}
	
	public Double getLimiteDeChequeEspecial() {
		return limiteDeChequeEspecial;
	}

	public void setLimiteDeChequeEspecial(Double limiteDeChequeEspecial) {
		this.limiteDeChequeEspecial = limiteDeChequeEspecial;
	}
	

	@Override
	public Double getSaldo() {
		return this.saldo + limiteDeChequeEspecial;
	}
	
	@Override
	public void debita(Double valor) throws NegocioException {
		if(this.saldo + getLimiteDeChequeEspecial() < valor) {
			throw new NegocioException(Mensagem.SALDO_INSUFICIENTE);
		}
		
		this.saldo = saldo - valor;
	}
	
	
}
