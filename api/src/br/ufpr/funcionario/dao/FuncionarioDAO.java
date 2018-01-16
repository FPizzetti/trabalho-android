package br.ufpr.funcionario.dao;

import java.util.List;

import br.ufpr.dao.Dao;
import br.ufpr.funcionario.Funcionario;

public interface FuncionarioDAO extends Dao<Funcionario> {
	public Funcionario autenticar(String email, String senha);

	public Funcionario getByToken(String token);

	public Funcionario getByEmail(String email);

	public List<Funcionario> pesquisar(String pesquisa, int inicio, int qtd);
}
