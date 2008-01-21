package br.cefetrn.smartcefet.visao;

import br.cefetrn.smartcefet.dominio.FuncionarioSistema;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class FuncionarioSistemaTableModel extends DefaultTableModel {
    public FuncionarioSistemaTableModel() {
        addColumn("Matrícula");
        addColumn("Nome");
    }
    
    public void setFuncionarios(List<FuncionarioSistema> funcionarios) {
        limpar();
        if (funcionarios != null) {
            for (FuncionarioSistema f : funcionarios) {
                adicionar(f);
            }
        }
    }
    
    public void setFuncionario(FuncionarioSistema funcionario) {
        limpar();
        adicionar(funcionario);
    }
    
    public FuncionarioSistema getFuncionario(int linha) {
        FuncionarioSistema f = new FuncionarioSistema();
        f.setMatricula((String) getValueAt(linha, 0));
        f.setNome((String) getValueAt(linha, 1));
        return f;
    }
    
    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;
    }
    
    private void limpar() {
        for (int i = 0; i < getRowCount(); i++) {
            removeRow(i);
        }
    }
    
    private void adicionar(FuncionarioSistema f) {
        if (f != null) {
            addRow(new Object[] {f.getMatricula(), f.getNome()});
        }
    }
}