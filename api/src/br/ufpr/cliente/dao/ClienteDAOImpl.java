package br.ufpr.cliente.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.ufpr.cliente.Cliente;
import br.ufpr.dao.GenericDao;
import br.ufpr.dao.HibernateUtil;

public class ClienteDAOImpl extends GenericDao<Cliente> implements ClienteDAO {

	private static final long serialVersionUID = 1L;

	public ClienteDAOImpl() {
		super(Cliente.class);
	}

	@Override
	public Cliente findByIdentificacao(String identificacao) {
		Session s = HibernateUtil.getSessionFactory().openSession();

		s.beginTransaction();
		Query query = (Query) s
				.createQuery("FROM Cliente WHERE identificacao = :identificacao");
		query.setParameter("identificacao", identificacao);
		Cliente c = (Cliente) query.uniqueResult();
		s.getTransaction().commit();
		return c;
	}

	@Override
	public Cliente findByEmail(String email) {
		Session s = HibernateUtil.getSessionFactory().openSession();

		s.beginTransaction();
		Query query = (Query) s
				.createQuery("FROM Cliente WHERE email = :email");
		query.setParameter("email", email);
		Cliente c = (Cliente) query.uniqueResult();
		s.getTransaction().commit();
		return c;
	}

	@Override
	public List<Cliente> pesquisar(String pesquisa, int inicio, int quantidade) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Query query = (Query) s
				.createQuery("FROM Cliente WHERE nome LIKE CONCAT('%', :pesquisa, '%') OR identificacao LIKE CONCAT('%', :pesquisa, '%') OR rg LIKE CONCAT('%', :pesquisa, '%')");
		query.setParameter("pesquisa", pesquisa);
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		List<Cliente> clientes = query.list();
		s.getTransaction().commit();
		return clientes;
	}

	@Override
	public int getTotalClientesRestritos() {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Query query = (Query) s
				.createQuery("SELECT COUNT(*) FROM Cliente WHERE restrito = 1");
		int count = ((Long) query.uniqueResult()).intValue();
		s.getTransaction().commit();
		return count;
	}

	@Override
	public int getTotalClientes() {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Query query = (Query) s.createQuery("SELECT COUNT(*) FROM Cliente");
		int count = ((Long) query.uniqueResult()).intValue();
		s.getTransaction().commit();
		return count;
	}

	@Override
	public int getTotalClientesNaoRestritos() {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Query query = (Query) s
				.createQuery("SELECT COUNT(*) FROM Cliente WHERE restrito = 0");
		int count = ((Long) query.uniqueResult()).intValue();
		s.getTransaction().commit();
		return count;
	}
}
