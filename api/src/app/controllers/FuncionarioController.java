package app.controllers;

import java.util.List;

import br.ufpr.funcionario.Funcionario;
import br.ufpr.funcionario.dao.FuncionarioDAO;
import br.ufpr.funcionario.dao.FuncionarioDAOImpl;

public class FuncionarioController {

	private FuncionarioDAO funcionarioDAO;

	public FuncionarioController() {
		funcionarioDAO = new FuncionarioDAOImpl();
	}

	public void cadastrar(Funcionario funcionario) {
		funcionarioDAO.salvar(funcionario);
	}

	public void editar(Funcionario funcionario) {
		funcionarioDAO.editar(funcionario);
	}

	public List<Funcionario> pesquisar(String pesquisa, int inicio, int qtd) {
		return funcionarioDAO.pesquisar(pesquisa, inicio, qtd);
	}

	public Funcionario findByID(Integer id) {
		return funcionarioDAO.procurarPorId(id);
	}

	public Funcionario findByEmail(String email) {
		return funcionarioDAO.getByEmail(email);
	}

}
