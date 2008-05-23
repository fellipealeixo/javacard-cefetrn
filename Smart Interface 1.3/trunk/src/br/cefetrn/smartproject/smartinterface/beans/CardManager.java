package br.cefetrn.smartproject.smartinterface.beans;

public class CardManager {
	
	private String id;
	private String estado;
	private String privilegio;
	
	public CardManager(String id, String estado, String privilegio){
		this.id = id;
		this.estado = estado;
		this.privilegio = privilegio;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPrivilegio() {
		return this.privilegio;
	}

	public void setPrivilegio(String privilegio) {
		this.privilegio = privilegio;
	}
	
}
