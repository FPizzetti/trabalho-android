package br.ufpr.instituicao.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.ufpr.dao.GenericDao;
import br.ufpr.dao.HibernateUtil;
import br.ufpr.instituicao.Instituicao;

public class InstituicaoDAOImpl extends GenericDao<Instituicao> implements
		InstituicaoDAO {

	private static final long serialVersionUID = 1L;

	public InstituicaoDAOImpl() {
		super(Instituicao.class);
	}

	@Override
	public List<Instituicao> pesquisar(String pesquisa, int inicio, int qtd) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Query query = (Query) s
				.createQuery("FROM Instituicao WHERE nome LIKE CONCAT('%', :pesquisa, '%') OR cnpj LIKE CONCAT('%', :pesquisa, '%')");
		query.setParameter("pesquisa", pesquisa);
		query.setFirstResult(inicio);
		query.setMaxResults(qtd);
		List<Instituicao> instituicoes = query.list();
		s.getTransaction().commit();
		return instituicoes;
	}

	@Override
	public Instituicao findByCnpj(String cnpj) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Query query = (Query) s
				.createQuery("FROM Instituicao WHERE cnpj = :cnpj");
		query.setParameter("cnpj", cnpj);
		Instituicao instituicao = (Instituicao) query.uniqueResult();
		s.getTransaction().commit();
		return instituicao;
	}

}
