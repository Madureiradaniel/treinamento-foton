package la.foton.treinamento.backend.entity;

import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;

public class ContaPoupanca extends Conta {
	private Integer diaDoAniversario;

	public ContaPoupanca() {

		this.tipo = TipoDaConta.POUPANCA;

	}

	@Override
	public void debita(Double valor) throws NegocioException {
		if (saldo < valor) {
			throw new NegocioException(Mensagem.SALDO_INSUFICIENTE);
		}

		saldo = valor - valor;
	}

	public Integer getDiaDoAniversario() {
		return diaDoAniversario;
	}

	public void setDiaDoAniversario(Integer diaDoAniversario) {
		this.diaDoAniversario = diaDoAniversario;
	}

}
