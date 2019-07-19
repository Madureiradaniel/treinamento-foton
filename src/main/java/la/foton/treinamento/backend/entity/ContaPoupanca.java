package la.foton.treinamento.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;

@Entity
@Table(name = "ContaPoupanca")
public class ContaPoupanca extends Conta {
	
	@Column(name = "diaDoAniversario")
	private Integer diaDoAniversario;

	public ContaPoupanca() {

		this.tipo = TipoDaConta.POUPANCA;

	}

	@Override
	public void debita(Double valor) throws NegocioException {
		if (saldo < valor) {
			throw new NegocioException(Mensagem.SALDO_INSUFICIENTE);
		}

		saldo = saldo - valor;
	}

	public Integer getDiaDoAniversario() {
		return diaDoAniversario;
	}

	public void setDiaDoAniversario(Integer diaDoAniversario) {
		this.diaDoAniversario = diaDoAniversario;
	}
	

}
