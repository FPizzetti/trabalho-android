package app.controllers;

import java.util.Date;
import java.util.List;

import br.ufpr.cliente.Cliente;
import br.ufpr.cliente.dao.ClienteDAO;
import br.ufpr.cliente.dao.ClienteDAOImpl;
import br.ufpr.funcionario.Funcionario;
import br.ufpr.instituicao.Instituicao;
import br.ufpr.operacao.Operacao;
import br.ufpr.operacao.dao.OperacaoDAO;
import br.ufpr.operacao.dao.OperacaoDAOImpl;

public class ClienteController {

	private ClienteDAO clienteDAO;
	private OperacaoDAO operacaoDAO;

	public ClienteController() {
		clienteDAO = new ClienteDAOImpl();
		operacaoDAO = new OperacaoDAOImpl();
	}

	public void salvar(Cliente cliente, Funcionario funcionario,
			Instituicao instituicao) {

		if (cliente.getId() == null)
			clienteDAO.salvar(cliente);
		else
			clienteDAO.editar(cliente);

		registrarOperacao(
				cliente,
				funcionario,
				instituicao,
				"Cadastro do Cliente como "
						+ (cliente.getRestrito() != null
								&& cliente.getRestrito() ? "Restrito"
								: "Não Restrito pela Instituição: "
										+ instituicao.getNome()));
	}

	public void alterarStatus(Cliente cliente) {
		cliente.setRestrito(!cliente.getRestrito());
		clienteDAO.editar(cliente);
	}

	public List<Cliente> pesquisar(String pesquisa, int inicio, int qtd) {
		return clienteDAO.pesquisar(pesquisa, inicio, qtd);
	}

	public Cliente findByEmail(String email) {
		return clienteDAO.findByEmail(email);
	}

	public Cliente findByIdentificacao(String identificacao) {
		return clienteDAO.findByIdentificacao(identificacao);
	}

	public Cliente findByID(Integer id) {
		return clienteDAO.procurarPorId(id);
	}

	public int getTotalClientesRestritos() {
		return clienteDAO.getTotalClientesRestritos();
	}

	public int getTotalClientes() {
		return clienteDAO.getTotalClientes();
	}

	public int getTotalClientesNaoRestritos() {
		return clienteDAO.getTotalClientesNaoRestritos();
	}

	private void registrarOperacao(Cliente cliente, Funcionario funcionario,
			Instituicao instituicao, String descricao) {
		Operacao operacao = new Operacao();
		operacao.setCliente(cliente);
		operacao.setDataOperacao(new Date());
		operacao.setFuncionario(funcionario);
		operacao.setInstituicao(instituicao);
		operacao.setIsRestricao(cliente.getRestrito());
		operacao.setDescricao(descricao);

		operacaoDAO.salvar(operacao);
	}

}
