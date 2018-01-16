package app.servlets.funcionario;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.controllers.LoginController;
import br.ufpr.funcionario.Funcionario;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginController loginController;

	public Login() {
		super();
		loginController = new LoginController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		Funcionario funcionario = loginController.autenticar(email, senha);
		if (funcionario != null && funcionario.getIsAtivo()) {
			boolean permanecerConectado = request
					.getParameter("permanecerConectado") != null;
			if (permanecerConectado) {
				response.addCookie(createCookie(funcionario.getToken()));
			}
			HttpSession session = request.getSession();
			session.setAttribute("funcionario", funcionario);
			response.sendRedirect("dashboard");
		} else if (funcionario != null && !funcionario.getIsAtivo()) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/login.jsp");
			request.setAttribute("erro", "Esta conta foi inativada");
			rd.include(request, response);
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/login.jsp");
			request.setAttribute("erro", "Login ou senha Incorretos");
			rd.include(request, response);
		}
	}

	private Cookie createCookie(String token) {
		Cookie c = new Cookie("token", token);
		c.setMaxAge(60 * 60 * 24 * 7);
		return c;
	}

}
