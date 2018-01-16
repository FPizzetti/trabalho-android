package api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import api.controllers.ApiController;
import api.model.ClienteResponse;
import br.ufpr.cliente.Cliente;

@Path("/clientes")
public class ApiREST {

	private ApiController apiController;

	public ApiREST() {
		apiController = new ApiController();
	}

	@GET
	@Path("/{identificacao}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public ClienteResponse getMsg(
			@PathParam("identificacao") String identificacao) {
		return apiController.getClienteStatus(identificacao);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response inserirDevedor(Cliente cliente) {
		apiController.inserirDevedor(cliente);
		return Response.status(Status.CREATED).build();
	}

	@DELETE
	@Path("/{identificacao}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response removerDevedor(
			@PathParam("identificacao") String identificacao) {
		apiController.removerDevedor(identificacao);
		return Response.status(Status.OK).build();
	}
}