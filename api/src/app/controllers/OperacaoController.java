package app.controllers;

import java.util.List;

import br.ufpr.operacao.Operacao;
import br.ufpr.operacao.dao.OperacaoDAO;
import br.ufpr.operacao.dao.OperacaoDAOImpl;

public class OperacaoController {

	private OperacaoDAO operacaoDAO;

	public OperacaoController() {
		operacaoDAO = new OperacaoDAOImpl();
	}

	public List<Operacao> listar() {
		return operacaoDAO.getUltimasOperacoes();
	}
}
