package br.cefetrn.smartcefet.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author Gleison
 *
 */
@Entity
public class Permissao {
		
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)    
	private long idPermissao;
	@ManyToOne
    @JoinColumn(name="matricula_funcionario")
	private FuncionarioSistema funcionario;
	@ManyToOne
    @JoinColumn(name="id_ambiente")
	private Ambiente ambiente;
	
	public Permissao(FuncionarioSistema funcinario, Ambiente ambiente) {
		super();
		this.funcionario = funcinario;
		this.ambiente = ambiente;
	}
	
	public Permissao(){
		
	}

	/**
	 * @return the idPermissao
	 */
	public long getIdPermissao() {
		return idPermissao;
	}

	/**
	 * @param idPermissao the idPermissao to set
	 */
	public void setIdPermissao(long idPermissao) {
		this.idPermissao = idPermissao;
	}

	/**
	 * @return the funcinario
	 */
	public FuncionarioSistema getFuncinario() {
		return funcionario;
	}

	/**
	 * @param funcinario the funcinario to set
	 */
	public void setFuncinario(FuncionarioSistema funcinario) {
		this.funcionario = funcinario;
	}

	/**
	 * @return the ambiente
	 */
	public Ambiente getAmbiente() {
		return ambiente;
	}

	/**
	 * @param ambiente the ambiente to set
	 */
	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}
	
	
	
}
