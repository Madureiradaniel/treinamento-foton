package la.foton.treinamento.backend.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import la.foton.treinamento.backend.common.exception.Mensagem;
import la.foton.treinamento.backend.common.exception.NegocioException;
import la.foton.treinamento.backend.dao.ClienteDAO;
import la.foton.treinamento.backend.entity.Cliente;
import la.foton.treinamento.backend.entity.SituacaoDoCliente;

@Stateless
public class ClienteService {

	@Inject
	private ClienteDAO dao;

	/*
	 * private static ClienteService instancia;
	 * 
	 * private ClienteService() { }
	 * 
	 * public static ClienteService get() { if(instancia == null) { instancia = new
	 * ClienteService(); }
	 * 
	 * return instancia; }
	 */

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void verificaSituacao(Cliente cliente) throws NegocioException {
		if (SituacaoDoCliente.PENDENTE.equals(cliente.getSituacao())) {
			throw new NegocioException(Mensagem.SITUACAO_CLIENTE_PENDENTE);
		}
	}

	public Cliente consultaPorCpf(String cpf) throws NegocioException {

		Cliente cliente = dao.consultaPorCPF(cpf);

		if (cliente == null) {
			throw new NegocioException(Mensagem.CLIENTE_NAO_ENCONTRADO);
		}

		return cliente;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cadastraCliente(String cpf, String nome) throws NegocioException {

		if (cpf == null || cpf.isEmpty() || nome == null || nome.isEmpty()) {
			throw new NegocioException(Mensagem.CLINTE_NAO_PODE_SER_CADASTRADO);
		}
		if (dao.consultaPorCPF(cpf) != null) {
			throw new NegocioException(Mensagem.CLIENTE_JA_CADASTRADO);
		}

		dao.insere(new Cliente(cpf, nome));

	}

	public void ativaCliente(String cpf) throws NegocioException {
		Cliente cliente = this.consultaPorCpf(cpf);
		cliente.ativa();
		dao.atualiza(cliente);
	}

	public void removeCliente(String cpf) throws NegocioException {

		Cliente cliente = this.consultaPorCpf(cpf);
		dao.remove(cliente);
	}

	public List<Cliente> consultaTodos() throws NegocioException {

		List<Cliente> clientes = dao.consultaTodos();
		
		if (clientes.isEmpty()) {
			throw new NegocioException(Mensagem.NAO_EXISTEM_CLIENTES);
		}
		return clientes;
	}

}
