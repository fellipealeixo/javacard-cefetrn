package br.cefetrn.smartproject.smartinterface.beans;

public class Arquivo {
	
	private String id;
	private String estado;
	
	public Arquivo(String id, String estado){
		this.id = id;
		this.estado = estado;
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
	
	
}
