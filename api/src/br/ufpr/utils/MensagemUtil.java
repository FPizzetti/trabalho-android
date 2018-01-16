package br.ufpr.utils;

public class MensagemUtil {

	private boolean status;
	private String descricao;

	public MensagemUtil(boolean status, String descricao) {
		this.status = status;
		this.descricao = descricao;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
