package la.foton.treinamento.backend.ws;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import la.foton.treinamento.backend.common.exception.NegocioException;
import la.foton.treinamento.backend.entity.Cliente;
import la.foton.treinamento.backend.service.ClienteService;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
public class ClienteWS {

	@EJB
	private ClienteService service;

	@GET
	@Path("/{cpf}")
	public Response consultaPorCPF(@PathParam("cpf") String cpf) throws NegocioException {

		Cliente cliente = service.consultaPorCpf(cpf);
		return Response.ok().entity(cliente).build();
	}

	@POST
	@Path("/{cpf}/cadastra")
	public Response cadastraCliente(@PathParam("cpf") String cpf, @QueryParam("nome") String nome)
			throws NegocioException {

		service.cadastraCliente(cpf, nome);

		URI uri = UriBuilder.fromPath("cliente/{cpf}").build(cpf);

		return Response.created(uri).type(MediaType.APPLICATION_JSON_TYPE).build();
	}

	@PUT
	@Path("/{cpf}/ativa")
	public Response ativaCliente(@PathParam("cpf") String cpf) throws NegocioException {
		service.ativaCliente(cpf);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{cpf}/remove")
	public Response removeCliente(@PathParam("cpf") String cpf) throws NegocioException {
		
		service.removeCliente(cpf);
		
		return Response.noContent().build();
	}
	
	@GET
	@Path("/todos")
	public Response consultaTodos() throws NegocioException {
		
		List<Cliente> clientes = service.consultaTodos();
		
		return Response.ok().entity(clientes) .build();
	}

}
