package la.foton.treinamento.backend.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWS {

	@GET
	@Path("/{nome}")
	public Response hello(@PathParam("nome") String nome) {
		
		return Response.ok().entity("Hello ->" + nome).build();
	}
	
}
