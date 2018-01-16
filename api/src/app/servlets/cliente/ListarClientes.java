package app.servlets.cliente;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.controllers.ClienteController;
import app.controllers.InstituicaoController;
import br.ufpr.cliente.Cliente;
import br.ufpr.instituicao.Instituicao;

@WebServlet("/clientes")
public class ListarClientes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteController clienteController;
	private InstituicaoController instituicaoController;

	public ListarClientes() {
		super();
		clienteController = new ClienteController();
		instituicaoController = new InstituicaoController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("cliente");

		if (id != null && !id.isEmpty()) {
			request.setAttribute("cliente",
					clienteController.findByID(Integer.parseInt(id)));
		}
		String inicio = request.getParameter("s") == null ? "0" : request
				.getParameter("s");
		String quantidade = request.getParameter("q") == null ? "10" : request
				.getParameter("q");

		String query = request.getParameter("pesquisa");
		List<Cliente> clientes = listar(query == null ? "" : query,
				Integer.parseInt(inicio), Integer.parseInt(quantidade));
		request.setAttribute("clientes", clientes);

		List<Instituicao> instituicoes = instituicaoController.listar("", 0,
				100);
		request.setAttribute("instituicoes", instituicoes);

		if (!response.isCommitted())
			request.getRequestDispatcher("clientes.jsp").include(request,
					response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private List<Cliente> listar(String query, int inicio, int qtd) {
		return clienteController.pesquisar(query, inicio, qtd);
	}

}
