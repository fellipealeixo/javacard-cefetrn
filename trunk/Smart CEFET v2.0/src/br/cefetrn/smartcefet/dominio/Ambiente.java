package br.cefetrn.smartcefet.dominio;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ambiente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private byte id;
    private String descricao;

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    @Override
    public boolean equals(Object outro_ambiente) {
        if (outro_ambiente == null) {
            return false;
        }
        if (outro_ambiente instanceof Ambiente) {
            Ambiente a = (Ambiente) outro_ambiente;
            return (id == a.id && descricao.equals(a.descricao));
        }
        return false;
    }
}