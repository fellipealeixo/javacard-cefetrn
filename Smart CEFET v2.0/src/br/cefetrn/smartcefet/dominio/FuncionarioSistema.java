package br.cefetrn.smartcefet.dominio;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Crístian Deives <cristiandeives@gmail.com>
 */
@Entity
@Table(name = "FUNCIONARIO")
public class FuncionarioSistema implements Serializable {
    @Id
    private String matricula;
    private boolean valido;
    private String nome;
    private String senha;
    private boolean ativo;
    
    /**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getMatricula() {
        return matricula;
    }
    
    public void setMatricula(String outra_matricula) {
        matricula = outra_matricula;
    }
    
    public boolean isValido() {
        return valido;
    }
    
    public void setValido(boolean eh_valido) {
        valido = eh_valido;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String outro_nome) {
        nome = outro_nome;
    }

	/**
	 * @return the ativo
	 */
	public boolean isAtivo() {
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}