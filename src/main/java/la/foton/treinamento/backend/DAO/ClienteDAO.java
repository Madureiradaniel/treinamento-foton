package la.foton.treinamento.backend.DAO;

import java.util.List;

import la.foton.treinamento.backend.entity.Cliente;

public interface ClienteDAO {
	
	void insere(Cliente cliente);
	
	void atualiza(Cliente cliente);
	
	void remove(Cliente cliente);
	
	Cliente consultaPorCPF(String cpf);
	
	List<Cliente> consultaTodos();
}
