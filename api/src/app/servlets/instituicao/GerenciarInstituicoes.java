package app.servlets.instituicao;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.controllers.InstituicaoController;
import br.ufpr.instituicao.Instituicao;

@WebServlet("/GerenciarInstituicoes")
public class GerenciarInstituicoes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InstituicaoController instituicaoController;

	public GerenciarInstituicoes() {
		super();
		instituicaoController = new InstituicaoController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		salvar(request);

		if (!response.isCommitted())
			response.sendRedirect("instituicoes");
	}

	private void salvar(HttpServletRequest request) {
		Instituicao i = prepareInstituicao(request);
		if (i.getId() != null) {
			instituicaoController.editar(i);
		} else {
			instituicaoController.cadastrar(i);
		}
	}

	private Instituicao prepareInstituicao(HttpServletRequest request) {
		Instituicao i = new Instituicao();
		int id = Integer.parseInt(request.getParameter("id").isEmpty() ? "0"
				: request.getParameter("id"));
		if (id > 0) {
			i.setId(id);
		} else {
			i.setDataCadastro(new Date());
		}
		i.setCnpj(request.getParameter("cnpj").replaceAll("[^\\d]", ""));
		i.setNome(request.getParameter("nome"));
		i.setIsAtiva("ativa".equals(request.getParameter("ativa")));

		return i;
	}
}
