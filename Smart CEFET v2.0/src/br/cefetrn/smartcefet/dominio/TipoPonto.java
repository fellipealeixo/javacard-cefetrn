package br.cefetrn.smartcefet.dominio;

public enum TipoPonto {
    ENTRADA("Entrada"),
    SAIDA("Saída");
    
    private String descricao;
    
    private TipoPonto(String descricao) {
        this.descricao = descricao;
    }
    
    public String toString() {
        return descricao;
    }
}