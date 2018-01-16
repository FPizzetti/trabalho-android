package br.ufpr.operacao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ufpr.cliente.Cliente;
import br.ufpr.funcionario.Funcionario;
import br.ufpr.instituicao.Instituicao;

@Entity
@Table(name = "tbOperacao")
public class Operacao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataOperacao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Cliente cliente;

	private Boolean isRestricao;

	@Column(length = 300)
	private String descricao;

	@ManyToOne
	@JoinColumn
	private Instituicao instituicao;

	@ManyToOne
	@JoinColumn
	private Funcionario funcionario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataOperacao() {
		return dataOperacao;
	}

	public void setDataOperacao(Date dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Boolean isRestricao() {
		return isRestricao;
	}

	public void setIsRestricao(Boolean isRestricao) {
		this.isRestricao = isRestricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Boolean getIsRestricao() {
		return isRestricao;
	}

}
