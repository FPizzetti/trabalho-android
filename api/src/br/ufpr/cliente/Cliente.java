package br.ufpr.cliente;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbCliente")
@XmlRootElement
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 50)
	private String nome;
	@Column(length = 20)
	private String rg;
	@Column(unique = true)
	private String email;
	@Column(length = 14, unique = true, nullable = false)
	private String identificacao;
	private Boolean restrito;

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getRestrito() {
		return restrito;
	}

	public void setRestrito(Boolean restrito) {
		this.restrito = restrito;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", rg=" + rg
				+ ", email=" + email + ", identificacao=" + identificacao
				+ ", restrito=" + restrito + ", dataCadastro=" + dataCadastro
				+ "]";
	}

}
