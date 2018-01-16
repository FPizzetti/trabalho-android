package app.servlets.funcionario;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.controllers.FuncionarioController;
import br.ufpr.funcionario.Funcionario;
import br.ufpr.utils.Hash;

@WebServlet("/GerenciarFuncionarios")
public class GerenciarFuncionarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FuncionarioController funcionarioController;

	public GerenciarFuncionarios() {
		super();
		funcionarioController = new FuncionarioController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");

		if (id != null) {
			Funcionario f = funcionarioController
					.findByID(Integer.parseInt(id));
			if (f != null) {
				f.setSenha(Hash.getMD5(f.getEmail()));
				funcionarioController.editar(f);
			}
		}

		if (!response.isCommitted())
			response.sendRedirect("funcionarios?alterada=true");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		salvar(request);

		if (!response.isCommitted())
			response.sendRedirect("funcionarios");
	}

	private void salvar(HttpServletRequest request) {
		Funcionario f = prepareFuncionario(request);
		if (f.getId() != null) {
			funcionarioController.editar(f);
		} else {
			funcionarioController.cadastrar(f);
		}
	}

	private Funcionario prepareFuncionario(HttpServletRequest request) {
		Funcionario f = new Funcionario();
		int id = Integer.parseInt(request.getParameter("id").isEmpty() ? "0"
				: request.getParameter("id"));
		if (id > 0) {
			f.setId(id);
			f.setIsAtivo(request.getParameter("status").equals("ativo"));
			f.setSenha(request.getParameter("senha"));
		} else {
			f.setIsAtivo(true);
			f.setSenha(Hash.getMD5(request.getParameter("email")));
		}
		f.setEmail(request.getParameter("email"));
		f.setNome(request.getParameter("nome"));

		f.setIsAdmin(false);
		return f;
	}

}
