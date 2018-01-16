package br.ufpr.cliente.dao;

import java.util.List;

import br.ufpr.cliente.Cliente;
import br.ufpr.dao.Dao;

public interface ClienteDAO extends Dao<Cliente> {

	public Cliente findByIdentificacao(String identificacao);

	public Cliente findByEmail(String email);

	public List<Cliente> pesquisar(String query, int inicio, int quantidade);

	public int getTotalClientesRestritos();

	public int getTotalClientes();

	public int getTotalClientesNaoRestritos();
}
