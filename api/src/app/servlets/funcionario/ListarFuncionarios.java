package app.servlets.funcionario;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.controllers.FuncionarioController;
import br.ufpr.funcionario.Funcionario;

@WebServlet("/funcionarios")
public class ListarFuncionarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FuncionarioController funcionarioController;

	public ListarFuncionarios() {
		super();
		funcionarioController = new FuncionarioController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");

		if (id != null && !id.isEmpty()) {
			request.setAttribute("f",
					funcionarioController.findByID(Integer.parseInt(id)));
		}

		String query = request.getParameter("pesquisa");
		String inicio = request.getParameter("s") == null ? "0" : request
				.getParameter("s");
		String quantidade = request.getParameter("q") == null ? "10" : request
				.getParameter("q");
		List<Funcionario> funcionarios = listar(query == null ? "" : query,
				Integer.parseInt(inicio), Integer.parseInt(quantidade));
		request.setAttribute("funcionarios", funcionarios);
		if (!response.isCommitted())
			request.getRequestDispatcher("funcionarios.jsp").include(request,
					response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private List<Funcionario> listar(String query, int inicio, int qtd) {
		return funcionarioController.pesquisar(query, inicio, qtd);
	}

}
