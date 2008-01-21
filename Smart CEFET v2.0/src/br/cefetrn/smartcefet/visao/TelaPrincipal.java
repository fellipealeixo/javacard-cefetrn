package br.cefetrn.smartcefet.visao;


import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import br.cefetrn.smartcefet.persistencia.sgbd.Persistencia;

/**
 * @author Crístian Deives <cristiandeives@gmail.com>
 */
public class TelaPrincipal extends JFrame {
    private JMenu mFuncionalidades;
    private JMenuBar mbMenu;
    private JMenuItem miAcessoAmbiente;
    private JMenuItem miRegistroPonto;
    private JMenuItem miSistemaAcademico;
    private JMenuItem miConfigurarCartao;
    
    public TelaPrincipal() {
        criarComponentes();
        posicionarComponentes();
        criarListeners();
        configurarJanela();
    }
    
    private void criarComponentes() {
        mbMenu = new JMenuBar();
        mFuncionalidades = new JMenu("Funcionalidades");
        mFuncionalidades.setMnemonic('F');
        miAcessoAmbiente = new JMenuItem("Autorizar acesso a ambiente");
        miAcessoAmbiente.setMnemonic('A');
        miAcessoAmbiente.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F1, 0));
        miRegistroPonto = new JMenuItem("Registrar ponto");
        miRegistroPonto.setMnemonic('P');
        miRegistroPonto.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F2, 0));
        miSistemaAcademico = new JMenuItem("Autenticar no Sistema Acadêmico");
        miSistemaAcademico.setMnemonic('S');
        miSistemaAcademico.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F3, 0));
        miSistemaAcademico.setEnabled(false);
        miConfigurarCartao = new JMenuItem("Configurar cartão");
        miConfigurarCartao.setMnemonic('C');
        miConfigurarCartao.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F4, 0));
    }
    
    private void posicionarComponentes() {
        mFuncionalidades.add(miAcessoAmbiente);
        mFuncionalidades.add(miRegistroPonto);
        mFuncionalidades.add(miSistemaAcademico);
        mFuncionalidades.addSeparator();
        mFuncionalidades.add(miConfigurarCartao);
        mbMenu.add(mFuncionalidades);
    }
       
    private void criarListeners() {
        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Object fonte = evt.getSource();
                if (fonte == miAcessoAmbiente) {
                    JDialog d = null;
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    try {
                        //FIXME alterar contrutor
                        d = new TelaAcessoAmbiente();
                    }
                    finally {
                        setCursor(Cursor.getPredefinedCursor(
                                Cursor.DEFAULT_CURSOR));
                    }
                    d.setVisible(true);
                }
                else if (fonte == miRegistroPonto) {
                    JDialog d = null;
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    try {
                        d = new RegistroPontoGUI(TelaPrincipal.this);
                    }
                    finally {
                        setCursor(Cursor.getPredefinedCursor(
                                Cursor.DEFAULT_CURSOR));                      
                    }
                    d.setVisible(true);
                }
                else if (fonte == miConfigurarCartao) {
                    JDialog d = null;
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    try {
                        d = new ConfigurarCartao(TelaPrincipal.this);
                    }
                    finally {
                        setCursor(Cursor.getPredefinedCursor(
                                Cursor.DEFAULT_CURSOR));
                    }
                    d.setVisible(true);
                }
            }
        };
        WindowListener window = new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                Persistencia.fechar();
            }
        };
        
        miAcessoAmbiente.addActionListener(action);
        miRegistroPonto.addActionListener(action);
        miSistemaAcademico.addActionListener(action);
        miConfigurarCartao.addActionListener(action);
        addWindowListener(window);
    }
    
    private void configurarJanela() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setJMenuBar(mbMenu);
        setSize(640, 480);
        setTitle("SmartCefet");
        setLocationByPlatform(true);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        
        }   
        
    }
    
    public static void main(String[] args) {
        JFrame f = new TelaPrincipal();
        f.setVisible(true);
    }
}