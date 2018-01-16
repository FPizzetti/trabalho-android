package app.servlets.cliente;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.controllers.ClienteController;
import br.ufpr.cliente.Cliente;

@WebServlet("/clienteIdentificacaoValidator")
public class ClienteIdentificacaoValidator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteController clienteController;

	public ClienteIdentificacaoValidator() {
		super();
		clienteController = new ClienteController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("funcionario") != null) {
			Cliente c = clienteController.findByIdentificacao(request
					.getParameter("identificacao"));

			boolean text = c == null
					|| c.getId().intValue() == Integer.parseInt(request
							.getParameter("id"));
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(text ? "true" : "false");
		} else {
			response.sendRedirect("login.jsp");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
