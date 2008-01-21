package br.cefetrn.smartcefet.dominio;

import br.cefetrn.smartcefet.dominio.Ambiente;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LogDeAmbientes {
    private Map<Ambiente, Integer> ambientes;
    public static final Integer ADICIONADO = 1;
    public static final Integer REMOVIDO = -1;
    
    public LogDeAmbientes() {
        ambientes = new HashMap<Ambiente, Integer>();
    }
    
    private void colocarAmbiente(Ambiente a, int valor) {
        Integer i = ambientes.get(a);
        Integer novo_i = (i == null) ? valor : i + valor;
        ambientes.put(a, novo_i);
    }
    
    private Set<Ambiente> lerAmbientes(Integer estado) {
        Set<Ambiente> adicionados = new HashSet<Ambiente>();
        for (Map.Entry<Ambiente, Integer> e : ambientes.entrySet()) {
            if (e.getValue() == estado) {
                adicionados.add(e.getKey());
            }
        }
        return adicionados;
    }
    
    public void adicionar(Ambiente a) {
        colocarAmbiente(a, ADICIONADO);
    }
    
    public void remover(Ambiente a) {
        colocarAmbiente(a, REMOVIDO);
    }
    
    public Set<Ambiente> getAdicionados() {
        return lerAmbientes(ADICIONADO);
    }
    
    public Set<Ambiente> getRemovidos() {
        return lerAmbientes(REMOVIDO);
    }
}