package br.cefetrn.smartcefet.visao;

import javax.swing.JComboBox;

public class CredencialComboBox extends JComboBox {
    public CredencialComboBox() {
    	//FIXME não meu - olhar
        setModel(new CredencialComboBoxModel());
      //FIXME não meu - olhar
        setRenderer(new CredencialListCellRenderer());
    }
}