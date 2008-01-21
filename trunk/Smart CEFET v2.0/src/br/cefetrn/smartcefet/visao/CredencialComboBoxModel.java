package br.cefetrn.smartcefet.visao;

import br.cefetrn.smartcefet.dominio.Credencial;
import javax.swing.DefaultComboBoxModel;
//FIXME olhar classe credencial
public class CredencialComboBoxModel extends DefaultComboBoxModel {
    public CredencialComboBoxModel() {
        for (Credencial c : Credencial.values()) {
            addElement(c);
        }
    }
}