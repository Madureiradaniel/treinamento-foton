package la.foton.treinamento.backend.common.exception;

import java.util.Arrays;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NegocioExceptionMapper implements ExceptionMapper<NegocioException> {

	@Override
	public Response toResponse(NegocioException e) {

		Erro erro = new Erro(e.getMessage(), e.getMensagem().toString());

		return Response//
				.status(Response.Status.BAD_REQUEST)//
				.entity(Arrays.asList(erro))//
				.build();//
	}

}
