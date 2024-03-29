/*
 * ConfigurarCartao.java
 *
 * Created on January 18, 2007, 11:28 AM
 */

package br.cefetrn.smartcefet.visao;

import br.cefetrn.smartcefet.SmartCEFET;
import br.cefetrn.smartcefet.dominio.Ambiente;
import br.cefetrn.smartcefet.dominio.Credencial;
import br.cefetrn.smartcefet.dominio.FuncionarioProxy;
import br.cefetrn.smartcefet.dominio.FuncionarioSistema;
import br.cefetrn.smartcefet.dominio.LogDeAmbientes;
import br.cefetrn.smartcefet.dominio.Permissao;
import br.cefetrn.smartcefet.excecoes.NenhumTerminalDisponivelException;
import br.cefetrn.smartcefet.smartcard.Terminal;
import br.cefetrn.smartproject.gpcomm.GpComm;
import br.cefetrn.smartproject.gpcomm.GpCommCardEvent;
import br.cefetrn.smartproject.gpcomm.GpCommCardListener;
import br.cefetrn.smartproject.gpcomm.GpCommException;
import br.cefetrn.smartproject.gpcomm.GpCommTerminal;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.smartcardio.CardException;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 * 
 * @author cd1
 */
public class ConfigurarCartao extends javax.swing.JDialog {
	private static Logger log = Logger.getLogger(RegistroPontoGUI.class
			.getName());

	// FIXME comentei, mudar implementa��o
	// private FuncionarioProxy funcProxy;
	// FIXME comentei, mudar implementa��o
	// private GpComm gpcomm;
	private LogDeAmbientes logAmbientes;
	private FuncionarioSistema funcionario;
	private SmartCEFET smartCEFET;
	private boolean componentesIniciados;
	private Terminal terminal;
	private DefaultListModel listModelP;
	private DefaultListModel listModelNP;

	/**
	 * @return the funcionario
	 */
	public FuncionarioSistema getFuncionario() {
		return funcionario;
	}

	/**
	 * @param funcionario
	 *            the funcionario to set
	 */
	public void setFuncionario(FuncionarioSistema funcionario) {
		this.funcionario = funcionario;
		this.recuperarDadosFuncionario(this.funcionario.getNome());
	}

	/** Creates new form ConfigurarCartao */
	public ConfigurarCartao(java.awt.Frame parent) {
		super(parent, true);
		try {
			this.smartCEFET = new SmartCEFET();
		} catch (GpCommException e) {
			System.out.println("Erro no GPComm");
			e.printStackTrace();
		}
		this.adicionarEventosAoTerminal();
		getRootPane().setDefaultButton(bOk);
		logAmbientes = new LogDeAmbientes();
		initComponents();
		this.listModelNP = (DefaultListModel) liLocaisNaoPermitidos.getModel();
		this.listModelP = (DefaultListModel) liLocaisPermitidos.getModel();
		
		
		this.componentesIniciados = true;
		try {
			if (!this.terminal.isCarataoConectado()) {
				this.desabilitarComponentes();
			}

		} catch (GpCommException e) {
			System.out.println("problema com o gpcomm");
			e.printStackTrace();
		}
	}

	private void adicionarEventosAoTerminal() {
		this.terminal = null;
		try {
			this.terminal = this.smartCEFET.recuperarTerminal(0);
		} catch (GpCommException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NenhumTerminalDisponivelException e) {
			// TODO colocar JOPtionPane
			System.out.println("nenhum terminal!");
			e.printStackTrace();
		}
		terminal.addCardListener(new GpCommCardListener() {
			@Override
			public void cardInserted(GpCommCardEvent arg0) {
				System.out.println("cart�o inserido ->");
				if (!componentesIniciados) {
					initComponents();
				}
				abilitarComponentes();
				// FIXME comentei, alterar implementa��o
				// funcProxy = new FuncionarioProxy(evt.getCard());
				// FIXME -->
				// recuperarDadosFuncionario();
				// funcProxy = new FuncionarioProxy(evt.getCard());
				// FIXME Gambiarra

			}

			@Override
			public void cardRemoved(GpCommCardEvent arg0) {
				System.out.println("cart�o removido->");
				if (!componentesIniciados) {
					initComponents();
				}
				desabilitarComponentes();
				// TODO Auto-generated method stub
				// FIXME comentei, alterar implementa��o
				// funcProxy = null;

			}

		});
	}

	public void setMatricula(String matricula) {
		tfMatricula.setText(matricula);
	}

	@Override
	public void dispose() {
		// FIXME Verificar isso - Fechar GPCOMM
		/*
		 * if (gpcomm != null) { try { gpcomm.close(); } catch (GpCommException
		 * e) { log.log(Level.WARNING, "Houve um erro ao finalizar o GP Comm",
		 * e); } }
		 */

		try {
			this.smartCEFET.fecharComunicacao();
		} catch (GpCommException e) {
			System.out
					.println("Problema com o GPComm ao tentar fechar comunica��os");
			e.printStackTrace();
		}

		super.dispose();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code
	// ">//GEN-BEGIN:initComponents
	private void initComponents() {

		lMatricula = new javax.swing.JLabel();
		tfMatricula = new javax.swing.JTextField();
		bPesquisarFuncionario = new javax.swing.JButton();
		lCredencial = new javax.swing.JLabel();
		// FIXME ver isso
		cobCredencial = new CredencialComboBox();
		lLocaisNaoPermitidos = new javax.swing.JLabel();
		spLocaisNaoPermitidos = new javax.swing.JScrollPane();
		liLocaisNaoPermitidos = new JList(new AmbienteListModel(this.smartCEFET
				.recuperarAmbientes()));

		lLocaisPermitidos = new javax.swing.JLabel();
		spLocaisPermitidos = new javax.swing.JScrollPane();
		liLocaisPermitidos = new javax.swing.JList();
		bAdicionarUm = new javax.swing.JButton();
		bAdicionarTodos = new javax.swing.JButton();
		bRemoverTodos = new javax.swing.JButton();
		bRemoverUm = new javax.swing.JButton();
		lSenha1 = new javax.swing.JLabel();
		pfSenha1 = new javax.swing.JPasswordField();
		lSenha2 = new javax.swing.JLabel();
		pfSenha2 = new javax.swing.JPasswordField();
		separador = new javax.swing.JSeparator();
		bOk = new javax.swing.JButton();
		bFechar = new javax.swing.JButton();

		FormListener formListener = new FormListener();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Configurar cart\u00e3o");
		setLocationByPlatform(true);

		lMatricula.setText("Matr\u00edcula:");

		tfMatricula.setEditable(false);

		bPesquisarFuncionario.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/img/find.png")));
		bPesquisarFuncionario.setText("Pesquisar...");
		bPesquisarFuncionario.addActionListener(formListener);

		lCredencial.setText("Credencial:");

		lLocaisNaoPermitidos
				.setFont(lLocaisNaoPermitidos.getFont().deriveFont(
						lLocaisNaoPermitidos.getFont().getStyle()
								| java.awt.Font.BOLD));
		lLocaisNaoPermitidos
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lLocaisNaoPermitidos.setText("Locais n\u00e3o-permitidos");

		liLocaisNaoPermitidos.setCellRenderer(new AmbienteListCellRenderer());
		spLocaisNaoPermitidos.setViewportView(liLocaisNaoPermitidos);

		lLocaisPermitidos.setFont(lLocaisPermitidos.getFont().deriveFont(
				lLocaisPermitidos.getFont().getStyle() | java.awt.Font.BOLD));
		lLocaisPermitidos
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lLocaisPermitidos.setText("Locais permitidos");

		liLocaisPermitidos.setModel(new AmbienteListModel(null));
		// FIXME ver isso - nao meu
		liLocaisPermitidos.setCellRenderer(new AmbienteListCellRenderer());
		spLocaisPermitidos.setViewportView(liLocaisPermitidos);

		bAdicionarUm.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/img/1rightarrow.png")));
		bAdicionarUm.addActionListener(formListener);

		bAdicionarTodos.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/img/2rightarrow.png")));
		bAdicionarTodos.addActionListener(formListener);

		bRemoverTodos.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/img/2leftarrow.png")));
		bRemoverTodos.addActionListener(formListener);

		bRemoverUm.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/img/1leftarrow.png")));
		bRemoverUm.addActionListener(formListener);

		lSenha1.setText("Senha:");
		// FIXME Mutreta do usuarios
		pfSenha1.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F1) {
					recuperarDadosFuncionario("Fellipe Aleixo");

				} else if (e.getKeyCode() == KeyEvent.VK_F2) {
					recuperarDadosFuncionario("Gleison Diolino");
				} else if (e.getKeyCode() == KeyEvent.VK_F3) {
					recuperarDadosFuncionario("Lierbet Medeiros");
				}
			}
		});
		lSenha2.setText("Confirmar senha:");

		bOk.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/img/ok.png")));
		bOk.setText("Confirmar");
		bOk.addActionListener(formListener);

		bFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/img/exit.png")));
		bFechar.setText("Fechar");
		bFechar.addActionListener(formListener);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																separador,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																443,
																Short.MAX_VALUE)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								lCredencial)
																						.addComponent(
																								lMatricula))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												tfMatricula,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												234,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												bPesquisarFuncionario))
																						.addComponent(
																								cobCredencial,
																								0,
																								363,
																								Short.MAX_VALUE)))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								lLocaisNaoPermitidos,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								184,
																								Short.MAX_VALUE)
																						.addComponent(
																								spLocaisNaoPermitidos,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								184,
																								Short.MAX_VALUE))
																		.addGap(
																				12,
																				12,
																				12)
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								layout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.TRAILING)
																										.addGroup(
																												layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(
																																bAdicionarTodos)
																														.addComponent(
																																bRemoverTodos))
																										.addComponent(
																												bAdicionarUm))
																						.addComponent(
																								bRemoverUm))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								lLocaisPermitidos,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								185,
																								Short.MAX_VALUE)
																						.addComponent(
																								spLocaisPermitidos,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								185,
																								Short.MAX_VALUE)))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				lSenha1)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				pfSenha1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				130,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lSenha2)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				pfSenha2,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				130,
																				Short.MAX_VALUE))
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																layout
																		.createSequentialGroup()
																		.addComponent(
																				bOk)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				bFechar)))
										.addContainerGap()));

		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { bFechar, bOk });

		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { bAdicionarTodos, bAdicionarUm,
						bRemoverTodos, bRemoverUm });

		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lMatricula)
														.addComponent(
																bPesquisarFuncionario)
														.addComponent(
																tfMatricula,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lCredencial)
														.addComponent(
																cobCredencial,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(27, 27, 27)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lLocaisNaoPermitidos)
														.addComponent(
																lLocaisPermitidos))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																spLocaisPermitidos,
																0, 0,
																Short.MAX_VALUE)
														.addComponent(
																spLocaisNaoPermitidos,
																0, 0,
																Short.MAX_VALUE)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				bAdicionarUm)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				bAdicionarTodos)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				bRemoverTodos)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				bRemoverUm)))
										.addGap(27, 27, 27)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(lSenha1)
														.addComponent(
																pfSenha1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																pfSenha2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(lSenha2))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												separador,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(bFechar)
														.addComponent(bOk))
										.addContainerGap()));

		pack();
	}

	// Code for dispatching events from components to event handlers.

	private class FormListener implements java.awt.event.ActionListener {
		FormListener() {
		}

		public void actionPerformed(java.awt.event.ActionEvent evt) {
			if (evt.getSource() == bPesquisarFuncionario) {
				ConfigurarCartao.this.bPesquisarFuncionarioActionPerformed(evt);
			} else if (evt.getSource() == bAdicionarUm) {
				ConfigurarCartao.this.bAdicionarUmActionPerformed(evt);
			} else if (evt.getSource() == bAdicionarTodos) {
				ConfigurarCartao.this.bAdicionarTodosActionPerformed(evt);
			} else if (evt.getSource() == bRemoverTodos) {
				ConfigurarCartao.this.bRemoverTodosActionPerformed(evt);
			} else if (evt.getSource() == bRemoverUm) {
				ConfigurarCartao.this.bRemoverUmActionPerformed(evt);
			} else if (evt.getSource() == bOk) {
				ConfigurarCartao.this.bOkActionPerformed(evt);
			} else if (evt.getSource() == bFechar) {
				ConfigurarCartao.this.bFecharActionPerformed(evt);
			}
		}
	}// </editor-fold>//GEN-END:initComponents

	private void bPesquisarFuncionarioActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bPesquisarFuncionarioActionPerformed
		// FIXME n�o meu
		JDialog d = new PesquisarFuncionario(this);
		d.setVisible(true);
	}// GEN-LAST:event_bPesquisarFuncionarioActionPerformed

	private void bOkActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bOkActionPerformed
		configurarCartao();
	}// GEN-LAST:event_bOkActionPerformed

	private void bFecharActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bFecharActionPerformed
		dispose();
	}// GEN-LAST:event_bFecharActionPerformed

	private void bRemoverUmActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bRemoverUmActionPerformed
		Object[] ambientes_object = liLocaisPermitidos.getSelectedValues();
		System.out.println(ambientes_object.length);
		if (ambientes_object.length == 0) {
			// FIXME ver o q est� acontecendo, executa duas vezes com um s�
			// clique
			/*
			 * JOptionPane.showMessageDialog(this, "Voc� deve selecionar pelo
			 * menos " + "um ambiente\npara remov�-lo da lista de locais
			 * permitidos.", "Selecione um ambiente",
			 * JOptionPane.WARNING_MESSAGE);
			 */
		} else {
			removerLocaisPermitidos(deObjectParaAmbiente(ambientes_object));
		}
	}// GEN-LAST:event_bRemoverUmActionPerformed

	private void bRemoverTodosActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bRemoverTodosActionPerformed
		AmbienteListModel modelo_np = (AmbienteListModel) liLocaisPermitidos
				.getModel();
		List<Ambiente> ambientes = modelo_np.getAmbientes();
		if (ambientes.isEmpty()) {
			JOptionPane.showMessageDialog(this, "N�o h� nenhum ambiente a ser "
					+ "adicionado\n� lista de locais permitidos.",
					"Nenhum ambiente " + "dispon�vel",
					JOptionPane.WARNING_MESSAGE);
		} else {
			removerLocaisPermitidos(ambientes);
		}
	}// GEN-LAST:event_bRemoverTodosActionPerformed

	private void bAdicionarTodosActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bAdicionarTodosActionPerformed
		AmbienteListModel modelo_np = (AmbienteListModel) liLocaisNaoPermitidos
				.getModel();
		List<Ambiente> ambientes = modelo_np.getAmbientes();
		if (ambientes.isEmpty()) {
			JOptionPane.showMessageDialog(this, "N�o h� nenhum ambiente a ser "
					+ "adicionado � lista de locais permitidos.",
					"Nenhum ambiente " + "dispon�vel",
					JOptionPane.WARNING_MESSAGE);
		} else {
			adicionarLocaisPermitidos(ambientes, true);
		}
	}// GEN-LAST:event_bAdicionarTodosActionPerformed

	private void bAdicionarUmActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bAdicionarUmActionPerformed
		Object[] ambientes_object = liLocaisNaoPermitidos.getSelectedValues();
		System.out.println(ambientes_object.length);
		if (ambientes_object.length == 0) {
			// FIXME ver o que est� acontecendo de errado
			/*
			 * JOptionPane.showMessageDialog(this, "Voc� deve selecionar pelo
			 * menos " + "um ambiente\npara adicion�-lo � lista de locais
			 * permitidos.", "Selecione um ambiente",
			 * JOptionPane.WARNING_MESSAGE);
			 */
		} else {
			adicionarLocaisPermitidos(deObjectParaAmbiente(ambientes_object),
					true);
		}
	}// GEN-LAST:event_bAdicionarUmActionPerformed

	private void adicionarLocaisPermitidos(List<Ambiente> locais_np,
			boolean logar) {
		DefaultListModel modelo_np = (DefaultListModel) liLocaisNaoPermitidos
				.getModel();
		DefaultListModel modelo_p = (DefaultListModel) liLocaisPermitidos
				.getModel();
		for (Ambiente a : locais_np) {
			modelo_np.removeElement(a);
			modelo_p.addElement(a);
			if (logar) {
				logAmbientes.adicionar(a);
			}
		}
	}

	private void removerLocaisPermitidos(List<Ambiente> locais_p) {
		DefaultListModel modelo_np = (DefaultListModel) liLocaisNaoPermitidos
				.getModel();
		DefaultListModel modelo_p = (DefaultListModel) liLocaisPermitidos
				.getModel();
		for (Ambiente a : locais_p) {
			modelo_p.removeElement(a);
			modelo_np.addElement(a);
			logAmbientes.remover(a);
		}
	}

	private static List<Ambiente> deObjectParaAmbiente(Object[] ambientes_object) {
		List<Ambiente> ambientes = new ArrayList<Ambiente>(
				ambientes_object.length);
		for (Object o : ambientes_object) {
			ambientes.add((Ambiente) o);
		}
		return ambientes;
	}

	private void configurarCartao() {
		try {
			String matricula = tfMatricula.getText();
			if ("".equals(matricula)) {
				JOptionPane
						.showMessageDialog(
								this,
								"Voc� deve selecionar o "
										+ "funcion�rio que ser� o propriet�rio do cart�o.",
								"Selecione um funcion�rio",
								JOptionPane.WARNING_MESSAGE);
				return;
			}
			String senha1 = String.valueOf(pfSenha1.getPassword());
			String senha2 = String.valueOf(pfSenha2.getPassword());
			if ("".equals(senha1)) {
				JOptionPane.showMessageDialog(this, "Digite uma senha.",
						"Campo vazio", JOptionPane.WARNING_MESSAGE);
				pfSenha1.requestFocusInWindow();
				return;
			}
			if ("".equals(senha2)) {
				JOptionPane.showMessageDialog(this, "Digite uma senha.",
						"Campo vazio", JOptionPane.WARNING_MESSAGE);
				pfSenha2.requestFocusInWindow();
				return;
			}
			if (!senha1.equals(senha2)) {
				JOptionPane.showMessageDialog(this, "As senhas digitadas "
						+ "n�o conferem.", "Senhas diferentes",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			// FIXME COMENTEI AQUI
			/*
			 * if (funcProxy.isInstalado()) { funcProxy.setMatricula(matricula);
			 * funcProxy.setCredencial( (Credencial)
			 * cobCredencial.getSelectedItem()); for (Ambiente a :
			 * logAmbientes.getAdicionados()) { funcProxy.adicionarAmbiente(a); }
			 * for (Ambiente a : logAmbientes.getRemovidos()) {
			 * funcProxy.removerAmbiente(a); } funcProxy.setSenha(senha2);
			 * JOptionPane.showMessageDialog(this, "Cart�o configurado com " +
			 * "sucesso!", "Cart�o configurado",
			 * JOptionPane.INFORMATION_MESSAGE); } else {
			 * JOptionPane.showMessageDialog(this, "Esse cart�o n�o pode " +
			 * "ser utilizado para este fim.", "Cart�o inv�lido",
			 * JOptionPane.ERROR_MESSAGE); }
			 */
			this.finalizarConfiguracao();
		} catch (/* GpComm */Exception e) {
			log.log(Level.WARNING, "Erro inesperado", e);
			JOptionPane.showMessageDialog(ConfigurarCartao.this, "Ops!",
					"Erro inesperado", JOptionPane.ERROR_MESSAGE);
		} finally {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	private void finalizarConfiguracao() {
		this.funcionario.setSenha(this.pfSenha1.getText());
		this.smartCEFET.atualizarSenhaFuncionario(this.funcionario);
		
		Permissao permissao;

		for (int i = 0; i < this.listModelP.getSize(); ++i) {
			if (this.smartCEFET.recuperarPermisao(this.funcionario,
					(Ambiente) listModelP.getElementAt(i)) == null) {
				permissao = new Permissao(this.funcionario, (Ambiente) listModelP
						.getElementAt(i));
				this.smartCEFET.inserirEntidade(permissao);
			}
		}
		
		permissao = null;
		
		for (int i = 0; i < this.listModelNP.getSize(); ++i) {
			permissao = this.smartCEFET.recuperarPermisao(this.funcionario,
					(Ambiente) listModelNP.getElementAt(i));
			if ( permissao != null) {
				this.smartCEFET.excluirPermissao(permissao);
			}
		}

		JOptionPane.showMessageDialog(ConfigurarCartao.this,
				"Cart�o connfigurado com sucesso!", "Cart�o configurado",
				JOptionPane.INFORMATION_MESSAGE);

		dispose();
	}

	private void recuperarDadosFuncionario(String nome/* FIXME Gambiarra */) {
		// FIXME Comentei aqui --Gambiarra maior do mundo
		/*
		 * try { setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); if
		 * (funcProxy.isInstalado()) {
		 * tfMatricula.setText(funcProxy.getMatricula());
		 * cobCredencial.setSelectedItem(funcProxy.getCredencial());
		 * adicionarLocaisPermitidos(funcProxy.getAmbientes(), false); } } catch
		 * (GpCommException e) { JOptionPane.showMessageDialog(this, "Houve um
		 * erro ao recuperar " + "os dados do cart�o.", "Erro de comunica��o",
		 * JOptionPane.ERROR_MESSAGE); log.log(Level.SEVERE, "Erro ao recuperar
		 * os dados do cart�o.", e); } finally {
		 * setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); }
		 */
		this.funcionario = this.smartCEFET.recuperarFuncionarioPorNome(nome);
		this.setMatricula(this.funcionario.getMatricula());
		liLocaisNaoPermitidos.removeAll();

		this.listModelP.removeAllElements();
		for (Permissao p : this.smartCEFET.recuperarPermicoesPorFuncionario(this.funcionario)) {
			this.listModelP.addElement(p.getAmbiente());
		}
		this.listModelNP.removeAllElements();
		for(Ambiente a: this.smartCEFET.recuperarAmbientesNaoPermitidos(funcionario)){
			listModelNP.addElement(a);
		}

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				ConfigurarCartao dialog = new ConfigurarCartao(
						new javax.swing.JFrame());
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	private void desabilitarComponentes() {
		bAdicionarTodos.setEnabled(false);
		bAdicionarUm.setEnabled(false);
		bOk.setEnabled(false);
		bPesquisarFuncionario.setEnabled(false);
		bRemoverTodos.setEnabled(false);
		bRemoverUm.setEnabled(false);
		cobCredencial.setEnabled(false);
		pfSenha1.setEnabled(false);
		pfSenha2.setEnabled(false);
		tfMatricula.setText("");
		tfMatricula.setEnabled(false);
	}

	private void abilitarComponentes() {
		bAdicionarTodos.setEnabled(true);
		bAdicionarUm.setEnabled(true);
		bOk.setEnabled(true);
		bPesquisarFuncionario.setEnabled(true);
		bRemoverTodos.setEnabled(true);
		bRemoverUm.setEnabled(true);
		cobCredencial.setEnabled(true);
		pfSenha1.setEnabled(true);
		pfSenha1.requestFocusInWindow();
		pfSenha2.setEnabled(true);
		tfMatricula.setEnabled(true);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton bAdicionarTodos;
	private javax.swing.JButton bAdicionarUm;
	private javax.swing.JButton bFechar;
	private javax.swing.JButton bOk;
	private javax.swing.JButton bPesquisarFuncionario;
	private javax.swing.JButton bRemoverTodos;
	private javax.swing.JButton bRemoverUm;
	private javax.swing.JComboBox cobCredencial;
	private javax.swing.JLabel lCredencial;
	private javax.swing.JLabel lLocaisNaoPermitidos;
	private javax.swing.JLabel lLocaisPermitidos;
	private javax.swing.JLabel lMatricula;
	private javax.swing.JLabel lSenha1;
	private javax.swing.JLabel lSenha2;
	private javax.swing.JList liLocaisNaoPermitidos;
	private javax.swing.JList liLocaisPermitidos;
	private javax.swing.JPasswordField pfSenha1;
	private javax.swing.JPasswordField pfSenha2;
	private javax.swing.JSeparator separador;
	private javax.swing.JScrollPane spLocaisNaoPermitidos;
	private javax.swing.JScrollPane spLocaisPermitidos;
	private javax.swing.JTextField tfMatricula;
	// End of variables declaration//GEN-END:variables
	

}
