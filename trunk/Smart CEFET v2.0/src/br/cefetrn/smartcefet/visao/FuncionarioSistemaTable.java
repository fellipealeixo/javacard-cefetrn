package br.cefetrn.smartcefet.visao;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class FuncionarioSistemaTable extends JTable {
    public FuncionarioSistemaTable() {
        super(new FuncionarioSistemaTableModel());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}