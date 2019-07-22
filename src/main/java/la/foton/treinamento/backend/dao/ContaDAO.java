package la.foton.treinamento.backend.dao;

import java.util.List;

import la.foton.treinamento.backend.entity.Conta;

public interface ContaDAO {
	public Integer geraNumero();
	
	void insere(Conta conta);
	
	void atualiza(Conta conta);

	Conta consultaPorNumero(Integer numero);
	
	List<Conta> consultaTodas();
}
