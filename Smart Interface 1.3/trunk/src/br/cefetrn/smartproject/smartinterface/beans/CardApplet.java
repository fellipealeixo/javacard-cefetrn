package br.cefetrn.smartproject.smartinterface.beans;

public class CardApplet {
	
	private String id;
	private String estado;
	
	public CardApplet(String id, String estado){
		this.id = id;
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
