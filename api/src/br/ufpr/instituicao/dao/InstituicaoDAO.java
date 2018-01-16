package br.ufpr.instituicao.dao;

import java.util.List;

import br.ufpr.dao.Dao;
import br.ufpr.instituicao.Instituicao;

public interface InstituicaoDAO extends Dao<Instituicao> {

	public List<Instituicao> pesquisar(String pesquisa, int inicio, int qtd);

	public Instituicao findByCnpj(String cnpj);

}
