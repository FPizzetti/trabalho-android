package api.controllers;

import java.util.Date;

import api.model.ClienteResponse;
import br.ufpr.cliente.Cliente;
import br.ufpr.cliente.dao.ClienteDAO;
import br.ufpr.cliente.dao.ClienteDAOImpl;
import br.ufpr.funcionario.Funcionario;
import br.ufpr.instituicao.Instituicao;
import br.ufpr.operacao.Operacao;
import br.ufpr.operacao.dao.OperacaoDAO;
import br.ufpr.operacao.dao.OperacaoDAOImpl;

public class ApiController {
	private ClienteDAO clienteDAO;
	private OperacaoDAO operacaoDAO;

	public ApiController() {
		clienteDAO = new ClienteDAOImpl();
		operacaoDAO = new OperacaoDAOImpl();
	}

	public void inserirDevedor(Cliente cliente) {
		Cliente c;

		if ((c = clienteDAO.findByIdentificacao(cliente.getIdentificacao())) != null) {
			c.setRestrito(true);
			clienteDAO.editar(c);
			registrarOperacao(c, null, null, "Cadastro do Cliente como Restrito pelo SistemaVSF - Cheque Especial");
		} else {
			cliente.setId(null);
			cliente.setRestrito(true);
			cliente.setEmail(null);
			clienteDAO.salvar(cliente);
			registrarOperacao(cliente, null, null,
					"Cadastro do Cliente como Restrito pelo SistemaVSF - Cheque Especial");
		}

	}

	public void removerDevedor(String identificacao) {
		Cliente c;
		if ((c = clienteDAO.findByIdentificacao(identificacao)) != null) {
			c.setRestrito(false);
			clienteDAO.editar(c);
			registrarOperacao(c, null, null, "Cadastro do Cliente como Não Restrito pelo SistemaVSF");
		}
	}

	public ClienteResponse getClienteStatus(String identificacao) {
		Cliente c = clienteDAO.findByIdentificacao(identificacao);

		ClienteResponse clienteResponse = new ClienteResponse();
		clienteResponse.setIdentificacao(identificacao);
		clienteResponse.setRestrito(getStatus(c));

		return clienteResponse;
	}

	private boolean getStatus(Cliente c) {
		return c != null && c.getRestrito();
	}

	private void registrarOperacao(Cliente cliente, Funcionario funcionario, Instituicao instituicao,
			String descricao) {
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