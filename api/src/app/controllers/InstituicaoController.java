package app.controllers;

import java.util.List;

import br.ufpr.instituicao.Instituicao;
import br.ufpr.instituicao.dao.InstituicaoDAO;
import br.ufpr.instituicao.dao.InstituicaoDAOImpl;

public class InstituicaoController {

	private InstituicaoDAO instituicaoDAO;

	public InstituicaoController() {
		instituicaoDAO = new InstituicaoDAOImpl();
	}

	public void cadastrar(Instituicao instituicao) {
		instituicaoDAO.salvar(instituicao);
	}

	public void editar(Instituicao instituicao) {
		instituicaoDAO.editar(instituicao);
	}

	public List<Instituicao> listar(String pesquisa, int inicio, int qtd) {
		return instituicaoDAO.pesquisar(pesquisa, inicio, qtd);
	}

	public Instituicao findByID(Integer id) {
		return instituicaoDAO.procurarPorId(id);
	}

	public Instituicao findByCnpj(String cnpj) {
		return instituicaoDAO.findByCnpj(cnpj);
	}

}
