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
	private Double limeteDeChequeEspecial;

	public ContaCorrente() {
		this.tipo = TipoDaConta.CORRENTE;
		this.limeteDeChequeEspecial = 0.00;
	}
	
	@Override
	public Double getSaldo() {
		
		return saldo + limeteDeChequeEspecial;
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
