package br.ufpr.operacao.dao;

import java.util.List;

import br.ufpr.dao.Dao;
import br.ufpr.operacao.Operacao;

public interface OperacaoDAO extends Dao<Operacao> {

	public List<Operacao> getUltimasOperacoes();
}
