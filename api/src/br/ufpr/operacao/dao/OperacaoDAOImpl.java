package br.ufpr.operacao.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.ufpr.dao.GenericDao;
import br.ufpr.dao.HibernateUtil;
import br.ufpr.operacao.Operacao;

public class OperacaoDAOImpl extends GenericDao<Operacao> implements
		OperacaoDAO {

	private static final long serialVersionUID = 1L;

	public OperacaoDAOImpl() {
		super(Operacao.class);
	}

	@Override
	public List<Operacao> getUltimasOperacoes() {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Query query = (Query) s.createQuery("FROM Operacao o ORDER BY o.id DESC");
		query.setMaxResults(5);
		List<Operacao> operacoes = query.list();
		s.getTransaction().commit();
		return operacoes;
	}
}
