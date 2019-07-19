package la.foton.treinamento.backend.DAO;

import java.util.List;

import la.foton.treinamento.backend.entity.Conta;

public interface ContaDAO {

	Integer geraNumero();
	
	void insere(Conta conta);

	Conta consultaPorNumero(Integer numero);
	
	Boolean salvar(Conta conta);
	
	List<Conta> consultaTodas();
	
}
