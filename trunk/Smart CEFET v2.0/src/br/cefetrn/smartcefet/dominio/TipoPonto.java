package br.cefetrn.smartcefet.dominio;

public enum TipoPonto {
    ENTRADA("Entrada"),
    SAIDA("Sa�da");
    
    private String descricao;
    
    private TipoPonto(String descricao) {
        this.descricao = descricao;
    }
    
    public String toString() {
        return descricao;
    }
}