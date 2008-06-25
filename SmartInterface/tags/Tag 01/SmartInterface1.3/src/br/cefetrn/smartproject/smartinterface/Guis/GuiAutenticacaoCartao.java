package br.cefetrn.smartproject.smartinterface.Guis;

import java.io.IOException;
import java.util.List;

import javax.smartcardio.CardException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import br.cefetrn.smartproject.smartinterface.SmartInterface;

/*
 * GuiAutenticacaoCartao.java
 *
 * Created on 25 de Setembro de 2007, 12:52
 */



/**
 *
 * @author  Gleison
 */
public class GuiAutenticacaoCartao extends javax.swing.JFrame {
    
	// Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAutenticar;
    private javax.swing.JLabel jLabelAtenticar;
    private javax.swing.JLabel jLabelCartao;
    private javax.swing.JLabel jLabelSenha;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenuAjuda;
    private javax.swing.JMenu jMenuArquivo;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuEditar;
    private javax.swing.JMenuItem jMenuItemColar;
    private javax.swing.JMenuItem jMenuItemComandos;
    private javax.swing.JMenuItem jMenuItemCopiar;
    private javax.swing.JMenuItem jMenuItemOpcoes;
    private javax.swing.JMenuItem jMenuItemRecortar;
    private javax.swing.JMenuItem jMenuItemSair;
    private javax.swing.JMenuItem jMenuItemSobre;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JPasswordField jPasswordFieldSenha;
    private javax.swing.JToolBar jToolBar1;
   
    private SmartInterface smartInterface;
    
	
    /** Creates new form GuiAutenticacaoCartao */
    public GuiAutenticacaoCartao(SmartInterface smartInteface) {
    	
    	this.smartInterface = smartInteface;
    	
    	this.configurarLookAndFeel();
        this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("imagens/javaCard.jpg")).getImage());
        this.initComponents();
        this.setContentPane(jPanelPrincipal);       
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
   
    private void initComponents() {

        jPanelPrincipal = new javax.swing.JPanel();
        jLabelCartao = new javax.swing.JLabel();
        jLabelAtenticar = new javax.swing.JLabel();
        jLabelSenha = new javax.swing.JLabel();
        jPasswordFieldSenha = new javax.swing.JPasswordField();
        jButtonAutenticar = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuArquivo = new javax.swing.JMenu();
        jMenuItemSair = new javax.swing.JMenuItem();
        jMenuEditar = new javax.swing.JMenu();
        jMenuItemCopiar = new javax.swing.JMenuItem();
        jMenuItemColar = new javax.swing.JMenuItem();
        jMenuItemRecortar = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemOpcoes = new javax.swing.JMenuItem();
        jMenuAjuda = new javax.swing.JMenu();
        jMenuItemComandos = new javax.swing.JMenuItem();
        jMenuItemSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Smart Interface :: Iniciar");

        jLabelCartao.setFont(new java.awt.Font("Arial", 1, 18));
        jLabelCartao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelCartao.setText("Cart�o Conectado");

        jLabelAtenticar.setFont(new java.awt.Font("Arial", 1, 18));
        jLabelAtenticar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAtenticar.setText("Autentique-se");

        jLabelSenha.setFont(new java.awt.Font("Arial", 1, 14));
        jLabelSenha.setText("Senha:");

        jPasswordFieldSenha.setFont(new java.awt.Font("Arial", 0, 14));

        jButtonAutenticar.setFont(new java.awt.Font("Arial", 0, 14));
        jButtonAutenticar.setText("Autenticar-se");
        jButtonAutenticar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					eventoBotaoAutenticar(evt);
				} catch (CardException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        jToolBar1.setRollover(true);

        javax.swing.GroupLayout jPanelPrincipalLayout = new javax.swing.GroupLayout(jPanelPrincipal);
        jPanelPrincipal.setLayout(jPanelPrincipalLayout);
        jPanelPrincipalLayout.setHorizontalGroup(
            jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelCartao, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAtenticar, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabelSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrincipalLayout.createSequentialGroup()
                .addContainerGap(163, Short.MAX_VALUE)
                .addComponent(jButtonAutenticar)
                .addGap(126, 126, 126))
        );
        jPanelPrincipalLayout.setVerticalGroup(
            jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelCartao, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelAtenticar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSenha)
                    .addComponent(jPasswordFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonAutenticar)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jMenuArquivo.setText("Arquivo");
        jMenuArquivo.setFont(new java.awt.Font("Arial", 0, 12));

        jMenuItemSair.setFont(new java.awt.Font("Arial", 0, 12));
        jMenuItemSair.setText("Sair");
        jMenuItemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventoMenuItemSair(evt);
            }
        });
        jMenuArquivo.add(jMenuItemSair);

        jMenuBar.add(jMenuArquivo);

        jMenuEditar.setText("Editar");
        jMenuEditar.setFont(new java.awt.Font("Arial", 0, 12));

        jMenuItemCopiar.setFont(new java.awt.Font("Arial", 0, 12));
        jMenuItemCopiar.setText("Copiar");
        jMenuItemCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventoMenuItemCopiar(evt);
            }
        });
        jMenuEditar.add(jMenuItemCopiar);

        jMenuItemColar.setFont(new java.awt.Font("Arial", 0, 12));
        jMenuItemColar.setText("Colar");
        jMenuItemColar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventoMenuItemColar(evt);
            }
        });
        jMenuEditar.add(jMenuItemColar);

        jMenuItemRecortar.setFont(new java.awt.Font("Arial", 0, 12));
        jMenuItemRecortar.setText("Recortar");
        jMenuItemRecortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventoMenuItemRecortar(evt);
            }
        });
        jMenuEditar.add(jMenuItemRecortar);

        jMenuBar.add(jMenuEditar);

        jMenu1.setText("Ferramentas");
        jMenu1.setFont(new java.awt.Font("Arial", 0, 12));

        jMenuItemOpcoes.setFont(new java.awt.Font("Arial", 0, 12));
        jMenuItemOpcoes.setText("Op\u00e7oes");
        jMenuItemOpcoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventoMenuItemOpcoes(evt);
            }
        });
        jMenu1.add(jMenuItemOpcoes);

        jMenuBar.add(jMenu1);

        jMenuAjuda.setText("Ajuda");
        jMenuAjuda.setFont(new java.awt.Font("Arial", 0, 12));

        jMenuItemComandos.setFont(new java.awt.Font("Arial", 0, 12));
        jMenuItemComandos.setText("Comandos");
        jMenuAjuda.add(jMenuItemComandos);

        jMenuItemSobre.setFont(new java.awt.Font("Arial", 0, 12));
        jMenuItemSobre.setText("Sobre");
        jMenuItemSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventoMenuItemSobre(evt);
            }
        });
        jMenuAjuda.add(jMenuItemSobre);

        jMenuBar.add(jMenuAjuda);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
  
    private void configurarLookAndFeel() {
       try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
      
    }
    
	private void eventoBotaoAutenticar(java.awt.event.ActionEvent evt) throws CardException {//GEN-FIRST:event_eventoBotaoAutenticar
		String senha = this.jPasswordFieldSenha.getText();
		this.autenticar(senha);
	}

	private void eventoMenuItemSobre(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventoMenuItemSobre
    
	}

	private void eventoMenuItemOpcoes(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventoMenuItemOpcoes
    
	}
	
	private void eventoMenuItemRecortar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventoMenuItemRecortar
    
	}

	private void eventoMenuItemColar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventoMenuItemColar
    
	}

	private void eventoMenuItemCopiar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventoMenuItemCopiar
    
	}

	private void eventoMenuItemSair(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventoMenuItemSair
    
	}
    
    /**
     * @param args the command line arguments
     * @throws CardException 
     */
    /*public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiAutenticacaoCartao().setVisible(true);
            }
        });
    }*/
    
    private void autenticar(String senha) throws CardException{
    	boolean autenticou = false;
    	try {
			autenticou = this.smartInterface.autenticar(senha);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(autenticou){
			GuiGerenciamentoCartao guiGerenciamentoCartao= new GuiGerenciamentoCartao(this.smartInterface);
			this.dispose();
			guiGerenciamentoCartao.setVisible(true);
		}
		
    }
    
}