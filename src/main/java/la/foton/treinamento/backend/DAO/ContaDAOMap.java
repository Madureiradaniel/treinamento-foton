package la.foton.treinamento.backend.DAO;

import java.util.HashMap;
import java.util.Map;

import la.foton.treinamento.backend.entity.Conta;

public class ContaDAOMap implements ContaDAO {

	
	private Map<Integer, Conta> contas = new HashMap<>();
	// Map <k , v>

	private static ContaDAOMap instancia;
	
	private ContaDAOMap() {
		
	}
	
	public static ContaDAOMap get() {
		if(instancia == null) {
			instancia  = new ContaDAOMap();
		}
		
		return instancia;
	}
	
	@Override
	public Integer geraNumero() {
		return contas.size()+1;
	}

	@Override
	public void insere(Conta conta) {
		contas.put(conta.getNumero(), conta);
	}

	@Override
	public Conta consultaPorNumero(Integer numero) {
		
		if(contas.containsKey(numero)) {
			return contas.get(numero);
		}
		
		return null;
	}

}
