package br.cefetrn.smartcefet.visao;

import br.cefetrn.smartcefet.dominio.Ambiente;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * @author Crístian Deives <cristiandeives@gmail.com>
 */
public class AmbienteListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        Object new_value = value;
        if (value instanceof Ambiente) {
            Ambiente a = (Ambiente) value;
            new_value = a.getDescricao();
        }
        return super.getListCellRendererComponent(list, new_value, index,
                isSelected, cellHasFocus);
    }
}