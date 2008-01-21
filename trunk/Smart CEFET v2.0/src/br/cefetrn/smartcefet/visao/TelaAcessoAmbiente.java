package br.cefetrn.smartcefet.visao;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.UIManager;

import java.awt.Dimension;
import javax.swing.JTextPane;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import br.cefetrn.smartcefet.SmartCEFET;
import br.cefetrn.smartcefet.dominio.Ambiente;
import br.cefetrn.smartcefet.excecoes.NenhumTerminalDisponivelException;
import br.cefetrn.smartcefet.smartcard.Terminal;
//import br.cefetrn.smartcefet.dominio.FuncionarioProxy;
import br.cefetrn.smartproject.gpcomm.GpComm;
import br.cefetrn.smartproject.gpcomm.GpCommCardEvent;
import br.cefetrn.smartproject.gpcomm.GpCommCardListener;
import br.cefetrn.smartproject.gpcomm.GpCommException;
import br.cefetrn.smartproject.gpcomm.GpCommTerminal;
//FIXME Coloquei JDialog por enquanto
public class TelaAcessoAmbiente extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField jTextFieldVisor = null;
	private JPanel jPanelTeclado = null;
	private JButton jButtonEntrar = null;
	private JButton jButton01 = null;
	private JButton jButton02 = null;
	private JButton jButton03 = null;
	private JButton jButton04 = null;
	private JButton jButton05 = null;
	private JButton jButton06 = null;
	private JButton jButton07 = null;
	private JButton jButton08 = null;
	private JButton jButton09 = null;
	private JButton jButton0 = null;
	private JTextField jTextFieldHora = null;
	private JTextField jTextFieldData = null;
	private JButton jButton = null;
	private JButton jButtonCancelar = null;
	private static final Logger log = Logger.getLogger(TelaAcessoAmbiente.class.getName());
	//FIXME Comentei e tem que mudar a forma de acesso, colocando via nesgocio
	//private FuncionarioProxy funcProxy;
	private Ambiente ambiente;
	private SmartCEFET smartCEFET;
	private JFrame parent;
	private StringBuffer senha;
	
	public TelaAcessoAmbiente(JFrame parent) {
		super();		
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
            	
                setVisibleDialog(false);
                dispose();
            }
        });
		initialize();
		confExtras();
		adicionarEventosAoTerminal();
		
	}
	
	/**
	 * This method initializes jTextFieldVisor	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldVisor() {
		if (jTextFieldVisor == null) {
			jTextFieldVisor = new JTextField();
			jTextFieldVisor.setBounds(new Rectangle(14, 32, 298, 44));
			jTextFieldVisor.setEditable(false);
			jTextFieldVisor.setText("AUDIO VISUAL");
			jTextFieldVisor.setHorizontalAlignment(JTextField.CENTER);
			jTextFieldVisor.setFont(new Font("Dialog", Font.BOLD, 14));
			jTextFieldVisor.setBackground(new Color(224, 230, 217));
		}
		return jTextFieldVisor;
	}

	/**
	 * This method initializes jPanelTeclado	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelTeclado() {
		if (jPanelTeclado == null) {
			jPanelTeclado = new JPanel();
			jPanelTeclado.setLayout(null);
			jPanelTeclado.setBounds(new Rectangle(14, 89, 296, 175));
			jPanelTeclado.setBackground(new Color(121, 150, 166));
			jPanelTeclado.add(getJButtonEntrar(), null);
			jPanelTeclado.add(getJButton01(), null);
			jPanelTeclado.add(getJButton02(), null);
			jPanelTeclado.add(getJButton03(), null);
			jPanelTeclado.add(getJButton04(), null);
			jPanelTeclado.add(getJButton05(), null);
			jPanelTeclado.add(getJButton06(), null);
			jPanelTeclado.add(getJButton07(), null);
			jPanelTeclado.add(getJButton08(), null);
			jPanelTeclado.add(getJButton09(), null);
			jPanelTeclado.add(getJButton0(), null);
			jPanelTeclado.add(getJButton(), null);
			jPanelTeclado.add(getJButtonCancelar(), null);
		}
		return jPanelTeclado;
	}

	/**
	 * This method initializes jButtonEntrar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonEntrar() {
		if (jButtonEntrar == null) {
			jButtonEntrar = new JButton();
			jButtonEntrar.setBounds(new Rectangle(187, 120, 100, 30));
			jButtonEntrar.setBackground(new Color(75, 216, 103));
			jButtonEntrar.setFont(new Font("Dialog", Font.BOLD, 14));
			jButtonEntrar.setForeground(Color.white);
			jButtonEntrar.setActionCommand("");
			jButtonEntrar.setText("Enter");
		}
		return jButtonEntrar;
	}

	/**
	 * This method initializes jButton01	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton01() {
		if (jButton01 == null) {
			jButton01 = new JButton();
			jButton01.setText("1");
			jButton01.setFont(new Font("Dialog", Font.BOLD, 14));
			jButton01.setLocation(new Point(120, 15));
			jButton01.setSize(new Dimension(50, 26));
			jButton01.setToolTipText("");
		}
		return jButton01;
	}

	/**
	 * This method initializes jButton02	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton02() {
		if (jButton02 == null) {
			jButton02 = new JButton();
			jButton02.setText("2");
			jButton02.setFont(new Font("Dialog", Font.BOLD, 14));
			jButton02.setBounds(new Rectangle(180, 15, 50, 26));
			jButton02.setToolTipText("");
		}
		return jButton02;
	}

	/**
	 * This method initializes jButton03	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton03() {
		if (jButton03 == null) {
			jButton03 = new JButton();
			jButton03.setText("3");
			jButton03.setFont(new Font("Dialog", Font.BOLD, 14));
			jButton03.setBounds(new Rectangle(240, 15, 50, 26));
			jButton03.setToolTipText("");
		}
		return jButton03;
	}

	/**
	 * This method initializes jButton04	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton04() {
		if (jButton04 == null) {
			jButton04 = new JButton();
			jButton04.setText("4");
			jButton04.setFont(new Font("Dialog", Font.BOLD, 14));
			jButton04.setBounds(new Rectangle(120, 51, 50, 26));
			jButton04.setToolTipText("");
		}
		return jButton04;
	}

	/**
	 * This method initializes jButton05	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton05() {
		if (jButton05 == null) {
			jButton05 = new JButton();
			jButton05.setText("5");
			jButton05.setPreferredSize(new Dimension(52, 26));
			jButton05.setFont(new Font("Dialog", Font.BOLD, 14));
			jButton05.setBounds(new Rectangle(180, 51, 50, 26));
			jButton05.setToolTipText("");
		}
		return jButton05;
	}

	/**
	 * This method initializes jButton06	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton06() {
		if (jButton06 == null) {
			jButton06 = new JButton();
			jButton06.setText("6");
			jButton06.setFont(new Font("Dialog", Font.BOLD, 14));
			jButton06.setBounds(new Rectangle(240, 51, 50, 26));
			jButton06.setToolTipText("");
		}
		return jButton06;
	}

	/**
	 * This method initializes jButton07	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton07() {
		if (jButton07 == null) {
			jButton07 = new JButton();
			jButton07.setText("7");
			jButton07.setFont(new Font("Dialog", Font.BOLD, 14));
			jButton07.setBounds(new Rectangle(120, 87, 50, 26));
			jButton07.setToolTipText("");
		}
		return jButton07;
	}

	/**
	 * This method initializes jButton08	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton08() {
		if (jButton08 == null) {
			jButton08 = new JButton();
			jButton08.setText("8");
			jButton08.setFont(new Font("Dialog", Font.BOLD, 14));
			jButton08.setPreferredSize(new Dimension(49, 26));
			jButton08.setBounds(new Rectangle(180, 87, 50, 26));
			jButton08.setToolTipText("");
		}
		return jButton08;
	}

	/**
	 * This method initializes jButton09	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton09() {
		if (jButton09 == null) {
			jButton09 = new JButton();
			jButton09.setText("9");
			jButton09.setFont(new Font("Dialog", Font.BOLD, 14));
			jButton09.setBounds(new Rectangle(240, 87, 50, 26));
			jButton09.setToolTipText("");
		}
		return jButton09;
	}

	/**
	 * This method initializes jButton0	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("0");
			jButton0.setFont(new Font("Dialog", Font.BOLD, 14));
			jButton0.setBounds(new Rectangle(120, 123, 50, 26));
			jButton0.setToolTipText("");
		}
		return jButton0;
	}
	
	private void confExtras(){
		try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        
        } 
        this.jTextFieldVisor.setBorder(null);
        this.jTextFieldData.setBorder(null);
        this.jTextFieldHora.setBorder(null);
        
	}

	/**
	 * This method initializes jTextFieldHora	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldHora() {
		if (jTextFieldHora == null) {
			jTextFieldHora = new JTextField();
			jTextFieldHora.setBounds(new Rectangle(14, 16, 167, 20));
			jTextFieldHora.setEditable(false);
			jTextFieldHora.setText("13:27");
			jTextFieldHora.setFont(new Font("Dialog", Font.BOLD, 12));
			jTextFieldHora.setBackground(new Color(224, 230, 217));
		}
		return jTextFieldHora;
	}

	/**
	 * This method initializes jTextFieldData	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldData() {
		if (jTextFieldData == null) {
			jTextFieldData = new JTextField();
			jTextFieldData.setBounds(new Rectangle(179, 16, 133, 20));
			jTextFieldData.setEditable(false);
			jTextFieldData.setText("16-jan-08");
			jTextFieldData.setHorizontalAlignment(JTextField.RIGHT);
			jTextFieldData.setFont(new Font("Dialog", Font.BOLD, 12));
			jTextFieldData.setBackground(new Color(224, 230, 217));
		}
		return jTextFieldData;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(13, 15, 90, 60));
			jButton.setIcon(new ImageIcon(getClass().getResource("/img/seta.png")));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButtonCancelar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonCancelar() {
		if (jButtonCancelar == null) {
			jButtonCancelar = new JButton();
			jButtonCancelar.setBounds(new Rectangle(13, 89, 90, 60));
			jButtonCancelar.setIcon(new ImageIcon(getClass().getResource("/img/cancelar.png")));
		}
		return jButtonCancelar;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TelaAcessoAmbiente thisClass = new TelaAcessoAmbiente();

				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public TelaAcessoAmbiente() {
		super();
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){            	
                //setVisibleDialog(false);
                dispose();
            }
        });
		initialize();
		confExtras();
		adicionarEventosAoTerminal();
		
	}	
	
	public void setVisibleDialog( boolean b ){
		parent.setEnabled(!b);
	        setVisible(b);
	    }
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.parent = parent;
		this.senha = new StringBuffer();
		try {
			this.smartCEFET = new SmartCEFET();
		} catch (GpCommException e) {
			// TODO JOPTIonPane
			System.out.println("Erro no GPComm");
			e.printStackTrace();
		}
		this.setSize(328, 310);
		
		System.out.println(this.getDefaultCloseOperation());
		this.setContentPane(getJContentPane());
		this.setTitle("Controle de Acesso a Ambientes");
		this.setVisible(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJTextFieldVisor(), null);
			jContentPane.add(getJPanelTeclado(), null);
			jContentPane.add(getJTextFieldHora(), null);
			jContentPane.add(getJTextFieldData(), null);
		}
		return jContentPane;
	}
	
	private void adicionarEventosAoTerminal(){
		Terminal terminal = null;
		try {
			terminal = this.smartCEFET.recuperarTerminal(0);
			
		} catch (GpCommException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NenhumTerminalDisponivelException e) {
			// TODO colocar JOPtionPane
			System.out.println("nenhum terminal!");
			e.printStackTrace();
		}
		terminal.addCardListener(new GpCommCardListener(){
			@Override
			public void cardInserted(GpCommCardEvent arg0) {
				System.out.println("cartão inserido ");
	            //funcProxy = new FuncionarioProxy(evt.getCard());
	            //FIXME Gambiarra
				jTextFieldVisor.setText("Digite sua senha");
			}
			@Override
			public void cardRemoved(GpCommCardEvent arg0) {
				System.out.println("cartão removido");
				// TODO Auto-generated method stub			
				
			}
			
		});		
	}
	//FIXME Alterar essa implementação
	/*private void verificarAcesso() {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (funcProxy.isInstalado()) {
                if (funcProxy.isAtivo()) {                  
                    if (funcProxy.temAcessoAmbiente(this.ambiente)) {                    	
                    	this.jTextFieldVisor.setText("Digite sua senha e pressione enter");
                    	try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
                    	this.abilitarTecladoNumerico();
                    }
                    else {
                          JOptionPane.showMessageDialog(this, "Você não " +
                                    "tem acesso ao ambiente selecionado.",
                                    "Acesso negado", JOptionPane.ERROR_MESSAGE);
                          //pfSenha.setEnabled(false);
                    }
                    
                   
                }
                else {
                    JOptionPane.showMessageDialog(this, "Para acessar um " +
                            "ambiente, você deve primeiro\nregistrar o ponto.",
                            "Funcionário inativo",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Esse cartão não pode " +
                        "ser utilizado para este fim.", "Cartão inválido",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (GpCommException e) {
            log.log(Level.WARNING, "Erro inesperado", e);
            JOptionPane.showMessageDialog(this, "Ops!", "Erro inesperado",
                    JOptionPane.ERROR_MESSAGE);
        }
        finally {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }*/

	private void abilitarTecladoNumerico() {
		// TODO Auto-generated method stub
		
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
