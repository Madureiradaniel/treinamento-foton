package la.foton.treinamento.backend.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import la.foton.treinamento.backend.entity.Cliente;

public class ClienteDAOImpl implements ClienteDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void insere(Cliente cliente) {
		em.persist(cliente);
	}

	@Override
	public void atualiza(Cliente cliente) {
		em.merge(cliente);
	}

	@Override
	public void remove(Cliente cliente) {
		em.remove(cliente);
	}

	@Override
	public Cliente consultaPorCPF(String cpf) {
		return em.find(Cliente.class, cpf);
	}

	@Override
	public List<Cliente> consultaTodos() {
		
		String sql = "SELECT c FROM Cliente c";
		
		return em.createQuery(sql, Cliente.class).getResultList();
	}

}
