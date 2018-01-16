package app.servlets.perfil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.controllers.FuncionarioController;
import br.ufpr.funcionario.Funcionario;
import br.ufpr.utils.Hash;
import br.ufpr.utils.MensagemUtil;

@WebServlet("/GerenciarConta")
public class GerenciarConta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FuncionarioController funcionarioController;

	public GerenciarConta() {
		super();
		funcionarioController = new FuncionarioController();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

		if (id > 0) {
			Funcionario f = funcionarioController.findByID(id);
			f.setIsAtivo(false);
			funcionarioController.editar(f);
		}
		if (!response.isCommitted())
			response.sendRedirect("logout");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");

		HttpSession session = request.getSession();
		Funcionario f = (Funcionario) session.getAttribute("funcionario");
		MensagemUtil mensagem;
		if ("alterarSenha".equals(action)) {
			String senhaAtual = Hash.getMD5(request.getParameter("senhaAtual"));
			String senhaNova = Hash.getMD5(request.getParameter("novaSenha"));
			String confirmacao = Hash.getMD5(request
					.getParameter("confirmacaoSenha"));

			if (!senhaNova.equals(confirmacao)) {
				mensagem = new MensagemUtil(false, "Confirmação não confere");
			} else if (!f.getSenha().equals(senhaAtual)) {
				mensagem = new MensagemUtil(false, "Senha Atual não confere");
			} else {
				f.setSenha(senhaNova);
				funcionarioController.editar(f);
				mensagem = new MensagemUtil(true, "Senha Alterada com Sucesso");
			}
			request.setAttribute("mensagem", mensagem);
			request.getRequestDispatcher("/profile.jsp").include(request,
					response);
			return;
		} else if ("alterarEmail".equals(action)) {

			String email = request.getParameter("emailReq");

			if (funcionarioController.findByEmail(email) == null) {
				f.setEmail(email);
				funcionarioController.editar(f);
				mensagem = new MensagemUtil(true, "Email alterado com Sucesso");
				session.setAttribute("funcionario", f);
			} else {
				mensagem = new MensagemUtil(false,
						"Email já está sendo utilizado");
			}

			request.setAttribute("mensagemEmail", mensagem);
			request.getRequestDispatcher("/profile.jsp").include(request,
					response);
			return;
		}
		if (!response.isCommitted())
			response.sendRedirect("profile");

	}

}
