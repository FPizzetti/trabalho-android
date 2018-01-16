package app.servlets.instituicao;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.controllers.InstituicaoController;
import br.ufpr.instituicao.Instituicao;

@WebServlet("/instituicoes")
public class ListarInstituicoes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InstituicaoController instituicaoController;

	public ListarInstituicoes() {
		super();
		instituicaoController = new InstituicaoController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");

		if (id != null && !id.isEmpty()) {
			request.setAttribute("instituicao",
					instituicaoController.findByID(Integer.parseInt(id)));
		}
		String inicio = request.getParameter("s") == null ? "0" : request
				.getParameter("s");
		String quantidade = request.getParameter("q") == null ? "10" : request
				.getParameter("q");
		String query = request.getParameter("pesquisa");
		List<Instituicao> instituicoes = listar(query == null ? "" : query,
				Integer.parseInt(inicio), Integer.parseInt(quantidade));
		request.setAttribute("instituicoes", instituicoes);

		if (!response.isCommitted())
			request.getRequestDispatcher("instituicoes.jsp").include(request,
					response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private List<Instituicao> listar(String query, int inicio, int qtd) {
		return instituicaoController.listar(query, inicio, qtd);
	}

}
