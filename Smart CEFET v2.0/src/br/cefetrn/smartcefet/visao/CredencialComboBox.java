package br.cefetrn.smartcefet.visao;

import javax.swing.JComboBox;

public class CredencialComboBox extends JComboBox {
    public CredencialComboBox() {
    	//FIXME n�o meu - olhar
        setModel(new CredencialComboBoxModel());
      //FIXME n�o meu - olhar
        setRenderer(new CredencialListCellRenderer());
    }
}