package br.cefetrn.smartproject.smartinterface;

//SMART INTERFACE 1.3: UTILIZANDO JAVA 6 - SMART CARD I/O

import javax.smartcardio.CardException;
import javax.swing.JOptionPane;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Exibe uma mensagem, informando que o programa está em execução  
    	JOptionPane.showMessageDialog(null, "Smart Interface 1.3 em excução", "Smart Interface 1.3", 1);
    	
		SmartInterface smartInterface =  new SmartInterface();
		
			try {
				smartInterface.executar();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CardException e) {
				// TODO Auto-generated catch block
				System.out.println("Um erro ocorreu na tentativa de comunicação com o cartão: "+e.getMessage());
				e.printStackTrace();
			}
		
	}	
}
