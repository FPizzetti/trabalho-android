package app.servlets.cliente;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.controllers.ClienteController;
import app.controllers.InstituicaoController;
import br.ufpr.cliente.Cliente;
import br.ufpr.funcionario.Funcionario;
import br.ufpr.instituicao.Instituicao;

@WebServlet("/GerenciarClientes")
public class GerenciarClientes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteController clienteController;
	private InstituicaoController instituicaoController;

	public GerenciarClientes() {
		super();
		clienteController = new ClienteController();
		instituicaoController = new InstituicaoController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("salvar".equals(action)) {
			salvar(request);
		} else if ("inativar".equals(action)) {
			alterarStatus(request);
		}
		response.sendRedirect("clientes");
	}

	private void salvar(HttpServletRequest request) {
		Cliente c = prepareCliente(request);
		Funcionario f = (Funcionario) request.getSession().getAttribute(
				"funcionario");
		Instituicao i = instituicaoController.findByID(Integer.parseInt(request
				.getParameter("instituicao")));
		clienteController.salvar(c, f, i);
	}

	private void alterarStatus(HttpServletRequest request) {

		int id = Integer.parseInt(request.getParameter("id"));
		Cliente c = clienteController.findByID(id);

		clienteController.alterarStatus(c);
	}

	private Cliente prepareCliente(HttpServletRequest request) {
		Cliente c = new Cliente();

		int id = Integer.parseInt(request.getParameter("id").isEmpty() ? "0"
				: request.getParameter("id"));
		if (id > 0) {
			c.setId(Integer.parseInt(request.getParameter("id")));
		}
		c.setIdentificacao(request.getParameter("identificacao").replaceAll(
				"[^\\d]", ""));
		c.setEmail(request.getParameter("email"));
		c.setNome(request.getParameter("nome"));
		c.setRg(request.getParameter("rg"));
		c.setRestrito(request.getParameter("status").equals("restrito"));
		return c;
	}

}
