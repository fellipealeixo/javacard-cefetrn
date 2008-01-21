package br.cefetrn.smartcefet.visao;

import javax.swing.SwingUtilities;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;


import javax.swing.JLabel;


import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPasswordField;

import br.cefetrn.smartcefet.SmartCEFET;
import br.cefetrn.smartcefet.dominio.Ambiente;
import br.cefetrn.smartcefet.dominio.FuncionarioSistema;
import br.cefetrn.smartcefet.dominio.Permissao;
import br.cefetrn.smartcefet.dominio.Ponto;
import br.cefetrn.smartcefet.dominio.TipoPonto;
import br.cefetrn.smartcefet.excecoes.NenhumTerminalDisponivelException;


import br.cefetrn.smartcefet.smartcard.Terminal;

import br.cefetrn.smartproject.gpcomm.GpCommCardEvent;
import br.cefetrn.smartproject.gpcomm.GpCommCardListener;
import br.cefetrn.smartproject.gpcomm.GpCommException;

//FIXME coloquei JDialog por enquanto
public class RegistroPontoGUI extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabelHora = null;
	private JLabel jLabelData = null;
	private JLabel jLabelFuncionario = null;
	private JTextField jTextFieldFuncionario = null;
	private JLabel jLabelSenha = null;
	private JLabel jLabelRegistroDePonto = null;
	private JPasswordField jPasswordField = null;
	private SmartCEFET smartCEFET;
	private FuncionarioSistema funcionario;  //  @jve:decl-index=0:

	/**
	 * This method initializes jTextFieldFuncionario	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldFuncionario() {
		if (jTextFieldFuncionario == null) {
			jTextFieldFuncionario = new JTextField();
			jTextFieldFuncionario.setBounds(new Rectangle(131, 137, 293, 28));
			jTextFieldFuncionario.setText("");
			jTextFieldFuncionario.setEditable(false);
			jTextFieldFuncionario.setFont(new Font("Dialog", Font.PLAIN, 18));
		}
		return jTextFieldFuncionario;
	}

	/**
	 * This method initializes jPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getJPasswordField() {
		if (jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setBounds(new Rectangle(131, 183, 293, 28));
			jPasswordField.setEnabled(true);
			jPasswordField.setFont(new Font("Dialog", Font.PLAIN, 16));
			jPasswordField.setEditable(false);
			jPasswordField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					registrarPonto(); // TODO Auto-generated Event stub actionPerformed()
				}
			});
			jPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					//FIXME muganga dos usuarios
					if (e.getKeyCode() == KeyEvent.VK_F1) {
						recuperarDadosFuncionario("Fellipe Aleixo");

					} else if (e.getKeyCode() == KeyEvent.VK_F2) {
						recuperarDadosFuncionario("Gleison Diolino");
					} else if (e.getKeyCode() == KeyEvent.VK_F3) {
						recuperarDadosFuncionario("Lierbet Medeiros");
					}
				}
			});
		}
		return jPasswordField;
	}
	
	private void registrarPonto(){
		String senha = this.jPasswordField.getText();
		System.out.println("senha ->: "+senha);
		Ponto ponto;
		
		String formato = "dd/MM/yy \t HH:mm:ss ";
        SimpleDateFormat formatador = new SimpleDateFormat(formato);
        //""
		
		if(senha.equals(this.funcionario.getSenha())){
			if(!this.funcionario.isAtivo()){			
				Date data = new Date();
				ponto = new Ponto(data, this.funcionario, TipoPonto.ENTRADA);
				this.funcionario.setAtivo(true);
				this.smartCEFET.atualizarFuncionarioAtivo(funcionario);
				this.smartCEFET.inserirPonto(ponto);
				JOptionPane.showMessageDialog(this, "Entrada registrada em "+formatador.format(data),
						"Ponto Registrado",
						JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
			else{
				Date data = new Date();
				ponto = new Ponto(data, this.funcionario, TipoPonto.SAIDA);
				this.funcionario.setAtivo(false);
				this.smartCEFET.atualizarFuncionarioAtivo(funcionario);
				this.smartCEFET.inserirPonto(ponto);
				JOptionPane.showMessageDialog(this, "Saída registrada em "+formatador.format(data),
						"Ponto Registrado",
						JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
			
		}
		else{
			JOptionPane.showMessageDialog(this, "Senha Incorreta!",
					"Erro",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void recuperarDadosFuncionario(String nome/* FIXME Gambiarra */) {
		this.funcionario = this.smartCEFET.recuperarFuncionarioPorNome(nome);
		this.jTextFieldFuncionario.setText(this.funcionario.getNome());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				RegistroPontoGUI thisClass = new RegistroPontoGUI(null);
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	//FIXME Contrutor RegistroPontoGUI
	public RegistroPontoGUI(JFrame parent) {
		super(parent, true);
		initialize();
		temporizador();
		adicionarEventosAoTerminal();
		
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                dispose();
                
            }
        });
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
				RegistroPontoGUI.this.jPasswordField.setEnabled(true);
				RegistroPontoGUI.this.jPasswordField.setEditable(true);
				RegistroPontoGUI.this.jPasswordField.requestFocusInWindow();
		
			}
			@Override
			public void cardRemoved(GpCommCardEvent arg0) {
				System.out.println("cartão removido");
				// TODO Auto-generated method stub
				RegistroPontoGUI.this.jPasswordField.setEditable(false);
				RegistroPontoGUI.this.jPasswordField.setEnabled(false);
				RegistroPontoGUI.this.jPasswordField.setText("");
				RegistroPontoGUI.this.jTextFieldFuncionario.setText("");
				
			}
			
		});
		 
		
	}

	
	private void temporizador(){
        ActionListener action = new ActionListener() {
        	String formatoHora = "HH:mm:ss";
            SimpleDateFormat simpleDateFormatHora = new SimpleDateFormat(formatoHora);
            String formatoData = "dd/MM/yy";
            SimpleDateFormat simpleDateFormatData = new SimpleDateFormat(formatoData);           
            
            
            public void actionPerformed(ActionEvent e) {
                String hora = simpleDateFormatHora.format(Calendar.getInstance().getTime());
                RegistroPontoGUI.this.jLabelHora.setText(hora);
                String data = simpleDateFormatData.format(Calendar.getInstance().getTime());
                RegistroPontoGUI.this.jLabelData.setText(data);
            }
        };
        Timer timer = new Timer(1000, action);        
        timer.start();
    }
	
	
	
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
					.println("Problema com o GPComm ao tentar fechar comunicaçãos");
			e.printStackTrace();
		}
		super.dispose();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		try {
			this.smartCEFET = new SmartCEFET();
		} catch (GpCommException e) {
			// TODO JOPTIonPane
			System.out.println("Erro no GPComm");
			e.printStackTrace();
		}
		this.setSize(444, 295);
		this.setContentPane(getJContentPane());
		this.setTitle("CEFET/RN");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabelRegistroDePonto = new JLabel();
			jLabelRegistroDePonto.setBounds(new Rectangle(2, 16, 435, 48));
			jLabelRegistroDePonto.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelRegistroDePonto.setFont(new Font("Dialog", Font.BOLD, 24));
			jLabelRegistroDePonto.setBackground(new Color(192, 192, 188));
			jLabelRegistroDePonto.setText("Registro de Ponto");
			jLabelSenha = new JLabel();
			jLabelSenha.setBounds(new Rectangle(66, 183, 60, 28));
			jLabelSenha.setText("Senha");
			jLabelSenha.setFont(new Font("Dialog", Font.BOLD, 18));
			jLabelFuncionario = new JLabel();
			jLabelFuncionario.setBounds(new Rectangle(17, 137, 109, 28));
			jLabelFuncionario.setFont(new Font("Dialog", Font.BOLD, 18));
			jLabelFuncionario.setText("Funcionário");
			jLabelData = new JLabel();
			jLabelData.setBounds(new Rectangle(316, 76, 108, 44));
			jLabelData.setText("18-jan-08");
			jLabelData.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelData.setFont(new Font("Dialog", Font.BOLD, 24));
			jLabelHora = new JLabel();
			jLabelHora.setBounds(new Rectangle(17, 76, 105, 44));
			jLabelHora.setFont(new Font("Dialog", Font.BOLD, 24));
			jLabelHora.setText("20:53:17");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setFont(new Font("Dialog", Font.PLAIN, 12));
			jContentPane.add(jLabelHora, null);
			jContentPane.add(jLabelData, null);
			jContentPane.add(jLabelFuncionario, null);
			jContentPane.add(getJTextFieldFuncionario(), null);
			jContentPane.add(jLabelSenha, null);
			jContentPane.add(jLabelRegistroDePonto, null);
			jContentPane.add(getJPasswordField(), null);
		}
		return jContentPane;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
