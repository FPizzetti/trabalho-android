package api.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClienteResponse {
	private String identificacao;
	private boolean isRestrito;

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public boolean isRestrito() {
		return isRestrito;
	}

	public void setRestrito(boolean isRestrito) {
		this.isRestrito = isRestrito;
	}

}
