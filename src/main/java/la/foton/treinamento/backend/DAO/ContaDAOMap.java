package la.foton.treinamento.backend.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;

import la.foton.treinamento.backend.entity.Conta;

@Singleton
public class ContaDAOMap implements ContaDAO {

	private Map<Integer, Conta> contas = new HashMap<>();
	// Map <k , v>

	@Override
	public Integer geraNumero() {
		return contas.size() + 1;
	}

	@Override
	public void insere(Conta conta) {
		contas.put(conta.getNumero(), conta);
	}

	@Override
	public Conta consultaPorNumero(Integer numero) {

		if (contas.containsKey(numero)) {
			return contas.get(numero);
		}

		return null;
	}

	@Override
	public Boolean salvar(Conta conta) {
		if (contas.containsKey(conta.getNumero())) {
			contas.put(conta.getNumero(), conta);
			return true;
		}
		return false;

	}

	@Override
	public List<Conta> consultaTodas() {
		return new ArrayList<>(contas.values());
	}

}
