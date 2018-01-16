package app.controllers;

import java.util.Date;

import br.ufpr.funcionario.Funcionario;
import br.ufpr.funcionario.dao.FuncionarioDAO;
import br.ufpr.funcionario.dao.FuncionarioDAOImpl;
import br.ufpr.utils.Hash;
import br.ufpr.utils.SessionIDGenerator;

public class LoginController {

	private FuncionarioDAO funcionarioDAO;
	private SessionIDGenerator sessionIDGenerator;

	public LoginController() {
		funcionarioDAO = new FuncionarioDAOImpl();
		sessionIDGenerator = new SessionIDGenerator();
	}

	public Funcionario autenticar(String email, String senha) {

		Funcionario funcionario = funcionarioDAO.autenticar(email,
				Hash.getMD5(senha));

		if (funcionario != null) {
			String token = sessionIDGenerator.nextSessionId();
			funcionario.setToken(token + funcionario.getId());
			funcionarioDAO.editar(funcionario);
		}

		return funcionario;
	}

	public Funcionario getByToken(String token) {
		return funcionarioDAO.getByToken(token);
	}

	public void logout(String email) {
		Funcionario f = funcionarioDAO.getByEmail(email);
		f.setToken(null);
		f.setUltimoAcesso(new Date());
		funcionarioDAO.editar(f);
	}

}
