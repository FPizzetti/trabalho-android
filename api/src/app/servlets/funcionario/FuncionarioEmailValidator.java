package app.servlets.funcionario;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.controllers.FuncionarioController;
import br.ufpr.funcionario.Funcionario;

@WebServlet("/funcionarioEmailValidator")
public class FuncionarioEmailValidator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FuncionarioController funcionarioController;

	public FuncionarioEmailValidator() {
		super();
		funcionarioController = new FuncionarioController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("funcionario") != null) {
			Funcionario f = funcionarioController.findByEmail(request
					.getParameter("email"));
			boolean text = f == null
					|| f.getId().intValue() == Integer.parseInt(request
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
