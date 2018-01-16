package br.ufpr.funcionario.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.ufpr.dao.GenericDao;
import br.ufpr.dao.HibernateUtil;
import br.ufpr.funcionario.Funcionario;

public class FuncionarioDAOImpl extends GenericDao<Funcionario> implements
		FuncionarioDAO {

	private static final long serialVersionUID = 1L;

	public FuncionarioDAOImpl() {
		super(Funcionario.class);
	}

	@Override
	public Funcionario autenticar(String email, String senha) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Query query = (Query) s
				.createQuery("FROM Funcionario WHERE email = :email AND senha = :senha");
		query.setParameter("email", email);
		query.setParameter("senha", senha);
		Funcionario f = (Funcionario) query.uniqueResult();
		s.getTransaction().commit();
		return f;
	}

	@Override
	public Funcionario getByToken(String token) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Query query = (Query) s
				.createQuery("FROM Funcionario WHERE token = :token");
		query.setParameter("token", token);
		Funcionario f = (Funcionario) query.uniqueResult();
		s.getTransaction().commit();
		return f;
	}

	@Override
	public Funcionario getByEmail(String email) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Query query = (Query) s
				.createQuery("FROM Funcionario WHERE email = :email");
		query.setParameter("email", email);
		Funcionario f = (Funcionario) query.uniqueResult();
		s.getTransaction().commit();
		return f;
	}

	@Override
	public List<Funcionario> pesquisar(String pesquisa, int inicio, int qtd) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Query query = (Query) s
				.createQuery("FROM Funcionario WHERE nome LIKE CONCAT('%', :pesquisa, '%') OR email LIKE CONCAT('%', :pesquisa, '%')");
		query.setParameter("pesquisa", pesquisa);
		query.setFirstResult(inicio);
		query.setMaxResults(qtd);
		List<Funcionario> funcionarios = query.list();
		s.getTransaction().commit();
		return funcionarios;
	}
}
