package la.foton.treinamento.backend.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Alternative;

import la.foton.treinamento.backend.entity.Conta;

@Alternative
@Singleton
@Startup
public class ContaDAOMap implements ContaDAO{
	
	//Map<Key, value>
	private Map<Integer, Conta> contas = new HashMap<>();
	
	/*private static ContaDAOMap instancia;
	
	private ContaDAOMap() {
	}
	
	public static ContaDAOMap get() {
		if (instancia == null) {
			instancia = new ContaDAOMap();
		}
		
		return instancia;
	}*/
	
	@Override
	public Integer geraNumero() {
		return contas.size() + 1;
	}
	
	@Override
	public void insere(Conta conta) {
		contas.put(conta.getNumero(), conta);
	}

	
	@Override
	public void atualiza(Conta conta) {
		if(contas.containsKey(conta.getNumero())) {
			contas.put(conta.getNumero(), conta);
		}		
	}
	

	@Override
	public Conta consultaPorNumero(Integer numero) {
		
		if(contas.containsKey(numero)) {
			return contas.get(numero);
		}
		
		return null;
	}

	@Override
	public List<Conta> consultaTodas() {
		return new ArrayList<>(contas.values());
	}
	
}
