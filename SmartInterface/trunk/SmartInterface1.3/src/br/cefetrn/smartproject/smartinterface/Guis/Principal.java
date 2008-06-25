package br.cefetrn.smartproject.smartinterface.Guis;

import java.awt.BorderLayout;

import javax.smartcardio.CardException;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import java.awt.Rectangle;
import javax.swing.JTextPane;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.cefetrn.smartproject.smartinterface.beans.Arquivo;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JSplitPane;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanelPCSC = null;
	private JLabel jLabelLeitora = null;
	private JComboBox jComboBoxLeitoras = null;
	private JLabel jLabelCartaoPresente = null;
	private JTextField jTextFieldCartaoPresente = null;
	private JLabel jLabelProtocolo = null;
	private JTextField jTextFieldProtocolo = null;
	private JLabel jLabelATR = null;
	private JTextField jTextFieldATR = null;
	private JPanel jPanelApplets = null;
	private JScrollPane jScrollPaneApplets = null;
	private JTable jTableApplets = null;  //  @jve:decl-index=0:visual-constraint="10,522"
	private JButton jButtonConectar = null;
	private JButton jButtonSelecionarApplet = null;
	private JButton jButtonExcluirApplet = null;
	private JPanel jPanelArquivos = null;
	private JScrollPane jScrollPaneArquivos = null;
	private JTable jTableArquivos = null;
	private JButton jButtonExcluirArquivo = null;
	private JPanel jPanelComandos = null;
	private JLabel jLabelCLA = null;
	private JLabel jLabelINS = null;
	private JLabel jLabelP1 = null;
	private JLabel jLabelP2 = null;
	private JLabel jLabelLC = null;
	private JLabel jLabelLDados = null;
	private JLabel jLabelLE = null;
	private JTextField jTextFieldCLA = null;
	private JTextField jTextFieldINS = null;
	private JTextField jTextFieldP1 = null;
	private JTextField jTextFieldP2 = null;
	private JTextField jTextFieldLC = null;
	private JTextField jTextFieldDados = null;
	private JTextField jTextFieldLE = null;
	private JButton jButtonEnviarAPDU = null;
	private JTextArea jTextAreaResposta = null;
	private JLabel jLabelRespostas = null;
	private JLabel jLabelAppletSelecionado = null;
	private JTextField jTextFieldAppletSelecionadoID = null;
	private JMenuBar jJMenuBar = null;
	private JToolBar jJToolBarBar = null;
	/**
	 * This is the default constructor
	 */
	public Principal() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {		
		this.setSize(632, 503);
		this.setJMenuBar(getJJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.setarTableModelApplets();
		this.setarTableModelArquivos();
	}

	private void setarTableModelArquivos() {
		 jTableArquivos.setModel(new javax.swing.table.DefaultTableModel(
	                new Object [][] {
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null},
	                    {null, null, null}
	                },
	                new String [] {
	                    "", "ID", "Estado"
	                }
	            ) {
	                Class[] types = new Class [] {
	                    java.lang.String.class, java.lang.String.class, java.lang.String.class
	                };
	                boolean[] canEdit = new boolean [] {
	                    false, false, false
	                };

	                public Class getColumnClass(int columnIndex) {
	                    return types [columnIndex];
	                }

	                public boolean isCellEditable(int rowIndex, int columnIndex) {
	                    return canEdit [columnIndex];
	                }
	            });		
		this.listarArquivos();
	}

	private void setarTableModelApplets() {
        jTableApplets.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                new String [] {
                    "", "ID", "Estado"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class
                };
                boolean[] canEdit = new boolean [] {
                    false, false, false
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
        this.listarApplets();
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
			jContentPane.add(getJTabbedPane(), null);
			jContentPane.add(getJJToolBarBar(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(11, 37, 600, 397));
			jTabbedPane.setName("");
			jTabbedPane.setToolTipText("");
			jTabbedPane.addTab("PC/SC", null, getJPanelPCSC(), "PC/SC");
			jTabbedPane.addTab("Applets", null, getJPanelApplets(), null);
			jTabbedPane.addTab("Arquivos", null, getJPanelArquivos(), null);
			jTabbedPane.addTab("APDUs", null, getJPanelComandos(), "APDUs");
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanelPCSC	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelPCSC() {
		if (jPanelPCSC == null) {
			jLabelATR = new JLabel();
			jLabelATR.setBounds(new Rectangle(89, 165, 66, 20));
			jLabelATR.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelATR.setText("Cadeia ATR");
			jLabelProtocolo = new JLabel();
			jLabelProtocolo.setBounds(new Rectangle(15, 130, 140, 20));
			jLabelProtocolo.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelProtocolo.setText("Protocolo de Transporte");
			jLabelCartaoPresente = new JLabel();
			jLabelCartaoPresente.setBounds(new Rectangle(61, 95, 94, 20));
			jLabelCartaoPresente.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelCartaoPresente.setText("Cartao presente");
			jLabelLeitora = new JLabel();
			jLabelLeitora.setBounds(new Rectangle(107, 60, 48, 20));
			jLabelLeitora.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelLeitora.setText("Leitora");
			jPanelPCSC = new JPanel();
			jPanelPCSC.setLayout(null);
			jPanelPCSC.setToolTipText("PC/SC");
			jPanelPCSC.setName("PC/SC");
			jPanelPCSC.add(jLabelLeitora, null);
			jPanelPCSC.add(getJComboBoxLeitoras(), null);
			jPanelPCSC.add(jLabelCartaoPresente, null);
			jPanelPCSC.add(getJTextFieldCartaoPresente(), null);
			jPanelPCSC.add(jLabelProtocolo, null);
			jPanelPCSC.add(getJTextFieldProtocolo(), null);
			jPanelPCSC.add(jLabelATR, null);
			jPanelPCSC.add(getJTextFieldATR(), null);
			jPanelPCSC.add(getJButtonConectar(), null);
		}
		return jPanelPCSC;
	}

	/**
	 * This method initializes jComboBoxLeitoras	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxLeitoras() {
		if (jComboBoxLeitoras == null) {
			jComboBoxLeitoras = new JComboBox();
			jComboBoxLeitoras.setBounds(new Rectangle(160, 60, 330, 20));
			jComboBoxLeitoras.setToolTipText("Leitoras");
		}
		return jComboBoxLeitoras;
	}

	/**
	 * This method initializes jTextFieldCartaoPresente	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldCartaoPresente() {
		if (jTextFieldCartaoPresente == null) {
			jTextFieldCartaoPresente = new JTextField();
			jTextFieldCartaoPresente.setBounds(new Rectangle(160, 95, 28, 20));
			jTextFieldCartaoPresente.setText("Não");
			jTextFieldCartaoPresente.setHorizontalAlignment(JTextField.LEFT);
			jTextFieldCartaoPresente.setEditable(false);
		}
		return jTextFieldCartaoPresente;
	}

	/**
	 * This method initializes jTextFieldProtocolo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldProtocolo() {
		if (jTextFieldProtocolo == null) {
			jTextFieldProtocolo = new JTextField();
			jTextFieldProtocolo.setBounds(new Rectangle(160, 130, 31, 20));
			jTextFieldProtocolo.setEditable(false);
			jTextFieldProtocolo.setText("T = 0");
		}
		return jTextFieldProtocolo;
	}

	/**
	 * This method initializes jTextFieldATR	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldATR() {
		if (jTextFieldATR == null) {
			jTextFieldATR = new JTextField();
			jTextFieldATR.setBounds(new Rectangle(160, 165, 330, 20));
			jTextFieldATR.setEditable(false);
			jTextFieldATR.setText("T = 0");
		}
		return jTextFieldATR;
	}

	/**
	 * This method initializes jPanelApplets	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelApplets() {
		if (jPanelApplets == null) {
			jPanelApplets = new JPanel();
			jPanelApplets.setLayout(null);
			jPanelApplets.add(getJScrollPaneApplets(), null);
			jPanelApplets.add(getJButtonSelecionarApplet(), null);
			jPanelApplets.add(getJButtonExcluirApplet(), null);
		}
		return jPanelApplets;
	}

	/**
	 * This method initializes jScrollPaneApplets	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneApplets() {
		if (jScrollPaneApplets == null) {
			jScrollPaneApplets = new JScrollPane();
			jScrollPaneApplets.setBounds(new Rectangle(15, 15, 570, 303));
			jScrollPaneApplets.setViewportView(getJTableApplets());
		}
		return jScrollPaneApplets;
	}

	/**
	 * This method initializes jTableApplets	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTableApplets() {
		if (jTableApplets == null) {
			jTableApplets = new JTable();
			jTableApplets.setShowGrid(true);			
		}
		return jTableApplets;
	}
	
	private void configurarTabelas(JTable tabela){
        //coluna ordem
        tabela.getColumnModel().getColumn(0).setMaxWidth(40);
        tabela.getColumnModel().getColumn(0).setMinWidth(40);
       
        //coluna ID
        tabela.getColumnModel().getColumn(1).setMinWidth(290);
        
        //Coluna estado
        tabela.getColumnModel().getColumn(2).setMinWidth(110);
        tabela.getColumnModel().getColumn(2).setMaxWidth(120);       
    }
	
	 public void listarApplets(){
	    	ArrayList<String> textos = new ArrayList<String>();
	    	
	    	textos.add("gleison");
	    	textos.add("Claudia");
	    	textos.add("CEFET");
						
			
			int i = 1;
			for(String texto : textos){
				DefaultTableModel tabelaAppletsModel = (DefaultTableModel)this.jTableApplets.getModel();
				
				String id = texto.substring(0, 3);
				String estado = texto;
				
				tabelaAppletsModel.insertRow(i-1, new String[]{""+i, id, estado});
				++i;
			}
	    	
	  }
	 
	 public void listarArquivos(){
	    	ArrayList<String> textos = new ArrayList<String>();
	    	
	    	textos.add("Arquivo1");
	    	textos.add("Arquivo50");
	    	textos.add("Arquivo99");
						
			
			int i = 1;
			for(String texto : textos){
				DefaultTableModel tabelaArquivosModel = (DefaultTableModel)this.jTableArquivos.getModel();
				
				String id = texto.substring(0, 3);
				String estado = texto;
				
				tabelaArquivosModel.insertRow(i-1, new String[]{""+i, id, estado});
				++i;
			}
	    	
	  }

	/**
	 * This method initializes jButtonConectar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonConectar() {
		if (jButtonConectar == null) {
			jButtonConectar = new JButton();
			jButtonConectar.setBounds(new Rectangle(495, 60, 90, 20));
			jButtonConectar.setText("Conectar");
		}
		return jButtonConectar;
	}

	/**
	 * This method initializes jButtonSelecionarApplet	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonSelecionarApplet() {
		if (jButtonSelecionarApplet == null) {
			jButtonSelecionarApplet = new JButton();
			jButtonSelecionarApplet.setBounds(new Rectangle(380, 330, 100, 30));
			jButtonSelecionarApplet.setText("Selecionar");
		}
		return jButtonSelecionarApplet;
	}

	/**
	 * This method initializes jButtonExcluirApplet	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonExcluirApplet() {
		if (jButtonExcluirApplet == null) {
			jButtonExcluirApplet = new JButton();
			jButtonExcluirApplet.setBounds(new Rectangle(485, 329, 100, 30));
			jButtonExcluirApplet.setText("Excluir");
		}
		return jButtonExcluirApplet;
	}

	/**
	 * This method initializes jPanelArquivos	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelArquivos() {
		if (jPanelArquivos == null) {
			jPanelArquivos = new JPanel();
			jPanelArquivos.setLayout(null);
			jPanelArquivos.setName("Arquivos");
			jPanelArquivos.add(getJScrollPaneArquivos(), null);
			jPanelArquivos.add(getJButtonExcluirArquivo(), null);
		}
		return jPanelArquivos;
	}

	/**
	 * This method initializes jScrollPaneArquivos	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneArquivos() {
		if (jScrollPaneArquivos == null) {
			jScrollPaneArquivos = new JScrollPane();
			jScrollPaneArquivos.setBounds(new Rectangle(15, 15, 570, 303));
			jScrollPaneArquivos.setViewportView(getJTableArquivos());
		}
		return jScrollPaneArquivos;
	}

	/**
	 * This method initializes jTableArquivos	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTableArquivos() {
		if (jTableArquivos == null) {
			jTableArquivos = new JTable();
			jTableArquivos.setShowGrid(true);
		}
		return jTableArquivos;
	}

	/**
	 * This method initializes jButtonExcluirArquivo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonExcluirArquivo() {
		if (jButtonExcluirArquivo == null) {
			jButtonExcluirArquivo = new JButton();
			jButtonExcluirArquivo.setBounds(new Rectangle(485, 327, 100, 30));
			jButtonExcluirArquivo.setText("Excluir");
		}
		return jButtonExcluirArquivo;
	}

	/**
	 * This method initializes jPanelComandos	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelComandos() {
		if (jPanelComandos == null) {
			jLabelAppletSelecionado = new JLabel();
			jLabelAppletSelecionado.setBounds(new Rectangle(15, 26, 132, 20));
			jLabelAppletSelecionado.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelAppletSelecionado.setText("Applet Selecionado (ID)");
			jLabelRespostas = new JLabel();
			jLabelRespostas.setBounds(new Rectangle(15, 151, 59, 16));
			jLabelRespostas.setText("Resposta");
			jLabelLE = new JLabel();
			jLabelLE.setBounds(new Rectangle(538, 72, 17, 20));
			jLabelLE.setToolTipText("LE");
			jLabelLE.setHorizontalAlignment(SwingConstants.LEFT);
			jLabelLE.setText("LE");
			jLabelLE.setName("LE");
			jLabelLDados = new JLabel();
			jLabelLDados.setBounds(new Rectangle(295, 72, 38, 20));
			jLabelLDados.setToolTipText("Dados");
			jLabelLDados.setHorizontalAlignment(SwingConstants.LEFT);
			jLabelLDados.setText("Dados");
			jLabelLDados.setName("Dados");
			jLabelLC = new JLabel();
			jLabelLC.setBounds(new Rectangle(245, 72, 19, 20));
			jLabelLC.setText("LC");
			jLabelLC.setName("LC");
			jLabelLC.setToolTipText("LC");
			jLabelLC.setHorizontalAlignment(SwingConstants.LEFT);
			jLabelP2 = new JLabel();
			jLabelP2.setBounds(new Rectangle(180, 72, 18, 20));
			jLabelP2.setText("P2");
			jLabelP2.setHorizontalAlignment(SwingConstants.LEFT);
			jLabelP1 = new JLabel();
			jLabelP1.setBounds(new Rectangle(115, 72, 18, 20));
			jLabelP1.setText("P1");
			jLabelP1.setHorizontalAlignment(SwingConstants.LEFT);
			jLabelINS = new JLabel();
			jLabelINS.setBounds(new Rectangle(65, 72, 32, 20));
			jLabelINS.setHorizontalAlignment(SwingConstants.LEFT);
			jLabelINS.setText("INS");
			jLabelCLA = new JLabel();
			jLabelCLA.setBounds(new Rectangle(15, 72, 25, 20));
			jLabelCLA.setHorizontalAlignment(SwingConstants.LEFT);
			jLabelCLA.setText("CLA");
			jPanelComandos = new JPanel();
			jPanelComandos.setLayout(null);
			jPanelComandos.setName("APDUs");
			jPanelComandos.setToolTipText("APDUs");
			jPanelComandos.add(jLabelCLA, null);
			jPanelComandos.add(jLabelINS, null);
			jPanelComandos.add(jLabelP1, null);
			jPanelComandos.add(jLabelP2, null);
			jPanelComandos.add(jLabelLC, null);
			jPanelComandos.add(jLabelLDados, null);
			jPanelComandos.add(jLabelLE, null);
			jPanelComandos.add(getJTextFieldCLA(), null);
			jPanelComandos.add(getJTextFieldINS(), null);
			jPanelComandos.add(getJTextFieldP1(), null);
			jPanelComandos.add(getJTextFieldP2(), null);
			jPanelComandos.add(getJTextFieldLC(), null);
			jPanelComandos.add(getJTextFieldDados(), null);
			jPanelComandos.add(getJTextFieldLE(), null);
			jPanelComandos.add(getJButtonEnviarAPDU(), null);
			jPanelComandos.add(getJTextAreaResposta(), null);
			jPanelComandos.add(jLabelRespostas, null);
			jPanelComandos.add(jLabelAppletSelecionado, null);
			jPanelComandos.add(getJTextFieldAppletSelecionadoID(), null);
		}
		return jPanelComandos;
	}

	/**
	 * This method initializes jTextFieldCLA	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldCLA() {
		if (jTextFieldCLA == null) {
			jTextFieldCLA = new JTextField();
			jTextFieldCLA.setBounds(new Rectangle(15, 95, 45, 20));
		}
		return jTextFieldCLA;
	}

	/**
	 * This method initializes jTextFieldINS	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldINS() {
		if (jTextFieldINS == null) {
			jTextFieldINS = new JTextField();
			jTextFieldINS.setBounds(new Rectangle(65, 95, 45, 20));
		}
		return jTextFieldINS;
	}

	/**
	 * This method initializes jTextFieldP1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldP1() {
		if (jTextFieldP1 == null) {
			jTextFieldP1 = new JTextField();
			jTextFieldP1.setBounds(new Rectangle(115, 95, 60, 20));
		}
		return jTextFieldP1;
	}

	/**
	 * This method initializes jTextFieldP2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldP2() {
		if (jTextFieldP2 == null) {
			jTextFieldP2 = new JTextField();
			jTextFieldP2.setBounds(new Rectangle(180, 95, 60, 20));
		}
		return jTextFieldP2;
	}

	/**
	 * This method initializes jTextFieldLC	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldLC() {
		if (jTextFieldLC == null) {
			jTextFieldLC = new JTextField();
			jTextFieldLC.setBounds(new Rectangle(245, 95, 45, 20));
		}
		return jTextFieldLC;
	}

	/**
	 * This method initializes jTextFieldDados	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldDados() {
		if (jTextFieldDados == null) {
			jTextFieldDados = new JTextField();
			jTextFieldDados.setBounds(new Rectangle(295, 95, 238, 20));
		}
		return jTextFieldDados;
	}

	/**
	 * This method initializes jTextFieldLE	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldLE() {
		if (jTextFieldLE == null) {
			jTextFieldLE = new JTextField();
			jTextFieldLE.setBounds(new Rectangle(538, 95, 45, 20));
		}
		return jTextFieldLE;
	}

	/**
	 * This method initializes jButtonEnviarAPDU	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonEnviarAPDU() {
		if (jButtonEnviarAPDU == null) {
			jButtonEnviarAPDU = new JButton();
			jButtonEnviarAPDU.setBounds(new Rectangle(483, 121, 100, 30));
			jButtonEnviarAPDU.setText("Enviar");
		}
		return jButtonEnviarAPDU;
	}

	/**
	 * This method initializes jTextAreaResposta	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaResposta() {
		if (jTextAreaResposta == null) {
			jTextAreaResposta = new JTextArea();
			jTextAreaResposta.setBounds(new Rectangle(14, 171, 573, 190));
			jTextAreaResposta.setEditable(false);
		}
		return jTextAreaResposta;
	}

	/**
	 * This method initializes jTextFieldAppletSelecionadoID	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldAppletSelecionadoID() {
		if (jTextFieldAppletSelecionadoID == null) {
			jTextFieldAppletSelecionadoID = new JTextField();
			jTextFieldAppletSelecionadoID.setBounds(new Rectangle(150, 26, 213, 20));
			jTextFieldAppletSelecionadoID.setEditable(false);
		}
		return jTextFieldAppletSelecionadoID;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.setPreferredSize(new Dimension(50, 20));
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jJToolBarBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setBounds(new Rectangle(1, 1, 622, 20));
		}
		return jJToolBarBar;
	}
	

}  //  @jve:decl-index=0:visual-constraint="10,10"
