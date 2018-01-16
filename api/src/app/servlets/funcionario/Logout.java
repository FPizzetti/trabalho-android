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

@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginController loginController;

	public Logout() {
		super();
		loginController = new LoginController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		Funcionario f = (Funcionario) session.getAttribute("funcionario");

		if (session != null && f != null) {

			loginController.logout(f.getEmail());

			session.invalidate();

			response.addCookie(createCookie());

			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/login.jsp");
			rd.include(request, response);
		} else {
			response.sendRedirect("login.jsp");
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	private Cookie createCookie() {
		Cookie c = new Cookie("token", null);
		c.setMaxAge(0);
		return c;
	}

}
