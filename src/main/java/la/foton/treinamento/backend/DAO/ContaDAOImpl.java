package la.foton.treinamento.backend.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import la.foton.treinamento.backend.entity.Conta;

public class ContaDAOImpl implements ContaDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Integer geraNumero() {
		String sql = "SELECT MAX(c.numero) FROM Conta c";
		
		TypedQuery<Integer> query = em.createQuery(sql, Integer.class);
		
		Integer numero = query.getSingleResult();
		
		return numero != null ? numero + 1 : 1;
	}

	@Override
	public void insere(Conta conta) {
		em.persist(conta);
	}

//	@Override
//	public void atualiza(Conta conta) {
//		em.merge(conta);
//	}

	@Override
	public Conta consultaPorNumero(Integer numero) {
		return em.find(Conta.class, numero);
	}

	@Override
	public List<Conta> consultaTodas() {
		
		String sql ="SELECT c FROM Conta c";
		
		return em.createQuery(sql, Conta.class).getResultList();
	}

	@Override
	public Boolean salvar(Conta conta) {
		// TODO Auto-generated method stub
		return null;
	}

}
