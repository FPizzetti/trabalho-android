package app.servlets.instituicao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufpr.instituicao.Instituicao;

import app.controllers.InstituicaoController;

@WebServlet("/instituicaoCnpjValidator")
public class InstituicaoCnpjValidator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InstituicaoController instituicaoController;

	public InstituicaoCnpjValidator() {
		super();
		instituicaoController = new InstituicaoController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("funcionario") != null) {
			Instituicao i = instituicaoController.findByCnpj(request
					.getParameter("cnpj"));
			boolean text = i == null
					|| i.getId().intValue() == Integer.parseInt("id");
			System.out.println(text);
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
