package br.ufpr.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

@SuppressWarnings("unchecked")
public class GenericDao<T> implements Dao<T>, Serializable {
	private static final long serialVersionUID = 1L;
	protected Class<T> classe;

	public GenericDao() {
		this(null);
	}

	public GenericDao(Class<T> classe) {
		this.classe = classe;
	}

	public void salvar(T obj) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			boolean committed = false;
			try {
				session.persist(obj);
				session.getTransaction().commit();
				committed = true;
			} finally {
				if (!committed)
					session.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void remover(T obj) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			boolean committed = false;
			try {
				obj = (T) session.merge(obj);
				session.refresh(obj);
				session.delete(obj);
				session.getTransaction().commit();
				committed = true;
			} finally {
				if (!committed)
					session.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public List<T> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		Query query = session.createQuery("SELECT a from "
				+ classe.getSimpleName() + " a order by a.id");

		List<T> result = query.list();
		session.getTransaction().commit();
		session.close();
		return result;
	}

	@Override
	public T editar(T obj) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			boolean committed = false;
			try {
				obj = (T) session.merge(obj);
				session.getTransaction().commit();
				committed = true;
			} finally {
				if (!committed)
					session.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return obj;
	}

	@Override
	public T procurarPorId(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		T result = (T) session.load(classe, id);
		session.getTransaction().commit();
		// session.close();
		return result;
	}

	@Override
	public T procurarPorId(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		T result = (T) session.load(classe, id);
		session.getTransaction().commit();
		session.close();
		return result;
	}

}
