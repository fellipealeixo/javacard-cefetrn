package br.cefetrn.smartcefet.visao;

import br.cefetrn.smartcefet.dominio.Credencial;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class CredencialListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        Object new_value = value;
        if (value instanceof Credencial) {
            String nome_credencial = value.toString();
            new_value = Character.toUpperCase(nome_credencial.charAt(0)) +
                    nome_credencial.substring(1).toLowerCase();
        }
        return super.getListCellRendererComponent(list, new_value, index, isSelected,
                cellHasFocus);
    }
}