package br.ufpr.dao;

import java.util.List;

public interface Dao<T> {
	public void salvar(T obj);

	public T editar(T obj);

	public void remover(T obj);

	public T procurarPorId(int id);

	public T procurarPorId(Long id);

	public List<T> list();

}