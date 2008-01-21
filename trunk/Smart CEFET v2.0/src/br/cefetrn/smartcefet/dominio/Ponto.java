package br.cefetrn.smartcefet.dominio;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Gleison
 *
 */
@Entity
public class Ponto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date data_ponto;
    @ManyToOne
    @JoinColumn(name = "matricula_funcionario")
    private FuncionarioSistema funcionario;
    private TipoPonto tipo;
    
    public Ponto(){
    	
    }
    
    public Ponto(Date data_ponto, FuncionarioSistema funcionario, TipoPonto tipo) {
		super();
		this.data_ponto = data_ponto;
		this.funcionario = funcionario;
		this.tipo = tipo;
	}

	public long getId() {
        return id;
    }
    
    public void setId(long outro_id) {
        id = outro_id;
    }
    
    public Date getData() {
        return (data_ponto == null) ? null : (Date) data_ponto.clone();
    }
    
    public void setData(Date outra_data) {
        data_ponto = (outra_data == null) ? null : (Date) outra_data.clone();
    }
    
    public FuncionarioSistema getFuncionario() {
        return funcionario;
    }
    
    public void setFuncionario(FuncionarioSistema outro_funcionario) {
        funcionario = outro_funcionario;
    }
    
    public TipoPonto getTipoPonto() {
        return tipo;
    }
    
    public void setTipoPonto(TipoPonto outro_tipo) {
        tipo = outro_tipo;
    }
}