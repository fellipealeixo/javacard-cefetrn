package br.cefetrn.smartcefet.visao;

import br.cefetrn.smartcefet.dominio.Ambiente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

public class AmbienteListModel extends DefaultListModel {
    public AmbienteListModel(List<Ambiente> ambientes) {
        if (ambientes != null) {
            for (Ambiente a : ambientes) {
                addElement(a);
            }
        }
    }
    
    public List<Ambiente> getAmbientes() {
        int total_ambientes = size();
        List<Ambiente> ambientes = new ArrayList<Ambiente>();
        for (int i = 0; i < total_ambientes; i++) {
            ambientes.add((Ambiente) getElementAt(i));
        }
        return ambientes;
    }
}