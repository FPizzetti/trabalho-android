package app.servlets.dashboard;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.controllers.ClienteController;
import app.controllers.OperacaoController;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteController clienteController;
	private OperacaoController operacaoController;

	public Dashboard() {
		super();
		clienteController = new ClienteController();
		operacaoController = new OperacaoController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("clientesRestritos",
				clienteController.getTotalClientesRestritos());
		request.setAttribute("totalClientes",
				clienteController.getTotalClientes());
		request.setAttribute("clientesNaoRestritos",
				clienteController.getTotalClientesNaoRestritos());
		request.setAttribute("operacoes", operacaoController.listar());

		if (!response.isCommitted())
			request.getRequestDispatcher("/dashboard.jsp").include(request,
					response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
