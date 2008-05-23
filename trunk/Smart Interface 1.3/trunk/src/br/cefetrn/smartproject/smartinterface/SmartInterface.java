package br.cefetrn.smartproject.smartinterface;

// SMART INTERFACE 1.3: UTILIZANDO JAVA 6 - SMART CARD I/O

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.JOptionPane;

import br.cefetrn.smartproject.smartinterface.Guis.GuiAutenticacaoCartao;
import br.cefetrn.smartproject.smartinterface.beans.Arquivo;
import br.cefetrn.smartproject.smartinterface.beans.CardApplet;
import br.cefetrn.smartproject.smartinterface.beans.CardManager;
import br.cefetrn.smartproject.smartinterface.util.Convert;
import br.cefetrn.smartproject.smartinterface.Comunicacao;


public class SmartInterface {
	
	//FIXME Apaguei as variaveis de Crístian, qualquer coisa, versao 1.2.
    
    private Comunicacao comunicacao;
    private List<CardTerminal> leitoras;
    
    //FIXME Construtor vazio - Discutir Isso
    public SmartInterface(){
    	
    }
    
    public void executar()throws ClassNotFoundException, CardException{
    	
    	//Recupera as leitoras conectadas no sistema
    	this.leitoras = this.getLeitoras();
    	//Recupera a primeira leitora da lista FIXME mudar essa implemetação
    	CardTerminal terminal = leitoras.get(0);    	
    	//aguarda a inserção de um cartão por tempo indeterminado
    	terminal.waitForCardPresent(0);    	
    	//TODO Criar janela p/ que o usuário informe o protocolo do seu cartão
    	//Conecta ao cartão presente no terminal
		Card card = terminal.connect("T=0");
        comunicacao = new Comunicacao(card);
        
        GuiAutenticacaoCartao guiAutenticacaoCartao = new GuiAutenticacaoCartao(this);            
        guiAutenticacaoCartao.setVisible(true);
        
        //Mantem o programa em execução
        //FIXME Encontrar outra maneira de fazer isso
        while (true) {
                	
        }
    }
    
    /**
	 * Transforma dados em bytes em dados em hexadecimal
	 * @param buffer
	 * @return
	 */

    private static String printBytesHexa(byte[] buffer){
		StringBuffer stringBuffer = new StringBuffer();
		//formata a string de saída
		for(int i = 0; i<buffer.length;++i){
			int maisSiginificativo = ((buffer[i]>>4)& 0xf)<<4;
			int menosSignificativo = buffer[i]&0xf;
			if(maisSiginificativo == 0){
				//stringBuffer.append(&apos;0&apos;);
			}
			stringBuffer.append(Integer.toHexString(maisSiginificativo | maisSiginificativo)+" ");
		}
		return stringBuffer.toString().toUpperCase();
	}
	
    /**
     * Imprime a lista de leitora configuradas no sistema
     * @param terminais
     */
	private static void imprimeListaLeitoras(List<CardTerminal> terminais){
		for(Iterator iter = terminais.iterator(); iter.hasNext();){
			System.out.println("Leitora: "+iter.next());
		}
			
	}
	
	/**
	 * Recebe uma String, especificando uma leitora configurada no sistema, e a retona
	 * @param leitora
	 * @return terminal
	 * @throws CardException
	 */
	
	private static CardTerminal getLeitora( String leitora) throws CardException{
		TerminalFactory factory = TerminalFactory.getDefault();
		//Obtém a lista de leitoras configuradas no ambiente
		CardTerminal terminal = factory.terminals().getTerminal(leitora);
		return terminal;
	}
	
	/**
	 * Recupera uma lista de leitoras configuradas no sistema e as retorna
	 * @return terminais
	 * @throws CardException
	 */
	private List<CardTerminal> getLeitoras() throws CardException{
		TerminalFactory factory = TerminalFactory.getDefault();
		//Obtém a lista de leitoras configuradas no ambiente
		List<CardTerminal> terminais = factory.terminals().list();
		//factory.terminals().getTerminal("Gemplus");
		return terminais;
	}
    
   
    
    public boolean autenticar(String umaSenha) throws IOException, CardException {
    	
    	boolean autenticou = false;
    	
    	String auth = new String();
        
            byte[] kmc = umaSenha.getBytes();
            ResponseAPDU respostaIU = comunicacao.initializeUpdate(kmc);
            int statusIU = respostaIU.getSW();
            byte[] dataIU = respostaIU.getData();
            
            if (statusIU == Comunicacao.STATUS_OK) {
            	
            	System.out.println("************************************************");
            	System.out.println("Two last bytes of the Card Manager AID: " +	Convert.fromByteArrayToHexa(dataIU, 0, 2));
            	System.out.println("IC Fabrication Data: " + Convert.fromByteArrayToHexa(dataIU, 2, 4));
            	System.out.println("IC Serial Number: " + Convert.fromByteArrayToHexa(dataIU, 4, 8));
            	System.out.println("IC Batch Identifier: " + Convert.fromByteArrayToHexa(dataIU, 8, 10));
            	System.out.println("Key set version: " + Integer.toHexString(dataIU[10]));
        		System.out.println("Key index: " + Integer.toHexString(dataIU[11]));
        		System.out.println("Card Random: " + Convert.fromByteArrayToHexa(dataIU, 12, 20));
        		System.out.println("Card Cryptogram: " + Convert.fromByteArrayToHexa(dataIU, 20, 28));
                
        		ResponseAPDU respostaEA = comunicacao.externalAuthenticate();
                int statusEa = respostaEA.getSW();
               
                if (statusEa == Comunicacao.STATUS_OK) {
                	auth = "Autenticação realizada com sucesso!";
                	autenticou = true;
                }
                
                else {
                	auth = "Falha na autenticação: ";
                	autenticou = false;
                    switch (statusEa) {
                        case Comunicacao.STATUS_CRYPTOGRAM_NV:
                            auth+="Cryptogram not verified";
                            break;
                        case Comunicacao.STATUS_INCORRECT_LENGTH:
                        	auth+="Incorrect length";
                            break;
                        case Comunicacao.STATUS_SECURITY_STATUS_NS:
                        	auth+="MAC not verified";
                            break;
                        case Comunicacao.STATUS_CONDITIONS_NS:
                        	auth+="Command conditions not satisfied";
                            break;
                        case Comunicacao.STATUS_INCORRECT_P1_P2:
                            auth+="Incorrect P1/P2";
                            break;
                        default:
                        	auth+="Erro Desconhecido";
                    }
                    auth+=" (" + Integer.toHexString(statusEa) +
                            ")";
                }
            }
            //statusIu != Comunicacao.STATUS_OK
            else {
                System.out.print("Authentication failed: ");
                switch (statusIU) {
                    case Comunicacao.STATUS_CRYPTOGRAM_NV:
                        System.out.print("Cryptogram not verified");
                        break;
                    case Comunicacao.STATUS_INCORRECT_LENGTH:
                        System.out.print("Incorrect length");
                        break;
                    case Comunicacao.STATUS_CONDITIONS_NS:
                        System.out.print("Conditions of use not satisfied");
                        break;
                    case Comunicacao.STATUS_FUNCTION_NOT_SUPPORTED:
                        System.out.println("Function not supported");
                        break;
                    case Comunicacao.STATUS_INCORRECT_P1_P2:
                        System.out.print("Incorrect P1/P2");
                        break;
                    case Comunicacao.STATUS_REFERENCE_DATA_NF:
                        System.out.print("Referenced data not found");
                        break;
                    default:
                        System.out.print("Unknown error");
                }
                System.out.println(" (" + Integer.toHexString(statusIU) + ")");
            }
        
        return autenticou;
    }
    
    //FIXME Apaguei o doDelete, qualquer probelma, consultar a versão 1.2
    
    public boolean excluirApplet(String umID) throws IOException, CardException {
        
    	if(umID==null || umID.equals("")){
    		 System.out.println("-- Nenhum applet selecionado. Selecione o applet que deseja excluir");    		 
    		 return false;
    	}
    	
        byte[] aid = Convert.fromHexaToByteArray(umID);
        ResponseAPDU response = comunicacao.delete(aid);
        byte[] data = response.getData();
        int status = response.getSW();
        if (data != null && data[0] == (byte) 0x00 && status == Comunicacao.STATUS_OK) {
            System.out.println("Applet successfully deleted!");
            return true;
        }
        else {
            System.out.print("Applet could not be deleted: ");
            switch (status) {
               case Comunicacao.STATUS_MEMORY_FAILURE:
                   System.out.print("Memory failure");
                   break;
               case Comunicacao.STATUS_SECURITY_STATUS_NS:
                   System.out.print("Security status not satisfied");
                   break;
               case Comunicacao.STATUS_CONDITIONS_NS:
                   System.out.print("Applet in use; could not " +
                           "be deleted");
                   break;
               case Comunicacao.STATUS_INCORRECT_DATA:
                   System.out.print("Incorrect data");
                   break;
               case Comunicacao.STATUS_INCORRECT_P1_P2:
                   System.out.print("Incorrect P1/P2");
                   break;
               case Comunicacao.STATUS_REFERENCE_DATA_NF:
                   System.out.print("Reference data not found");
                   break;
               default:
                   System.out.print("Unknown error");
           }
           System.out.println(" (" + Integer.toHexString(status) + ")");
           return false;
       }
    }
    
    //TODO Acertar a implementação desse método, incluisve a chamada dele apartir fe GuiGerenciamentoCartao
    /**
     * Executa um APDU
     * @param unsParametros
     * 						APDU a ser executado
     * @throws IOException
     * @throws CardException
     */
    private void executarAPDU(String[] unsParametros) throws IOException, CardException {
    	if (unsParametros.length > 0) {
    		ResponseAPDU response = comunicacao.exec( Convert.fromHexaToByteArray(unsParametros[0]));
    		System.out.println("Response data: " + Convert.fromByteArrayToHexa(response.getData()));
    		System.out.println("Response status: " + Integer.toHexString(response.getSW()));
        }
        else {
            System.out.println("Usage: EXEC <APDU command>");
        }
   }
    
    //FIXME Apaguei os métodos doExecfile e parseComand, qualque problema ver na versao 1.2
   
   //TODO Cria janela com esses dados, a janela deverá aparecer qdo o usuário clicar em help 
    private void ajuda() throws IOException {
    	System.out.println("Usage: java " + getClass().getName() +
                " <COMMAND> {PARAMETERS}");
        System.out.println("<COMMAND> := AUTH | DELETE | EXEC | EXECFILE | " +
                "EXIT | HELP | INSTALL | LIST | SELECT");
        System.out.println();
        System.out.println("AUTH: Performs authentication on the card");
        System.out.println("PARAMETER: The authentication key (KMC)");
        System.out.println();
        System.out.println("DELETE: Deletes an Applet/Load File");
        System.out.println("PARAMETER: The AID of the Applet/Load File to " +
                "be deleted, coded in hexadecimal numbers separated by \":\" " +
                "(i.e. A0:00:00:00:62:03:01)");
        System.out.println();
        System.out.println("EXEC: Executes an APDU command.");
        System.out.println("PARAMETER: The APDU command, coded in " +
                "hexadecimal numbers separated by a colon (i.e. " +
                "A0:00:00:00:62:03:01)");
        System.out.println();
        System.out.println("EXECFILE: Executes each line of a file as " +
        		"several SmartShell commands");
        System.out.println("PARAMETER: The APDUs file path");
        System.out.println();
        System.out.println("EXIT: Exits this program");
        System.out.println();
        System.out.println("HELP: Shows this text");
        System.out.println();
        System.out.println("INSTALL: Install an Applet/Load File into the " +
                "card. If the Load File contains more than one Applet, each " +
                "one will be installed.");
        System.out.println("PARAMETER: The CAP file path");
        System.out.println();
        System.out.println("LIST: Lists information about the Card Manager, " +
                "Applets or Load Files installed into the card.");
        System.out.println("PARAMETER 1: The component to be listed. Valid " +
                "values are: \"CARDMANAGER\", \"APPLETS\" or \"LOADFILES\". " +
                "Only one of theses components can be listed at a time.");
        System.out.println("PARAMETER 2 (optional): An AID to narrow the " +
                "search. If this parameter is specified, the listing will " +
                "bring only the files that match that AID. Currently, only " +
                "one APDU can be returned, even if the search requires " +
                "multiple responses APDU");
        System.out.println();
        System.out.println("SELECT: Selects an Applet");
        System.out.println("PARAMETER: The AID of the Applet to be selected, " +
                "coded in hexadecimal numbers separated by a colon (i.e. " +
                "A0:00:00:00:62:03:01)");
        System.out.println();
    }
    
    /**
     * instala um/uns applet(s) e arquivos do pacote no cartão
     * @param umCapPath
     * 		Caminho absoluto de onde está o arquivo .CAP
     * @throws IOException
     * @throws CardException
     */
    public void instalarApplet(String umCapPath) throws IOException, CardException {
    	
            CAP cap = new CAP(umCapPath);
            byte[] capAid = cap.getAid();
            ResponseAPDU responseI4L = comunicacao.install4load(capAid);
            int statusI4L = responseI4L.getSW();
            if (statusI4L == Comunicacao.STATUS_OK) {
                ResponseAPDU responseLoad = comunicacao.load(cap);
                int statusLoad = responseLoad.getSW();
                
                if (statusLoad == Comunicacao.STATUS_OK) {
                    
                	if (cap.hasApplet()) {
                        for (byte[] appletAid : cap.getAppletAids()) {
                            ResponseAPDU responseI4I = comunicacao.install4install(
                                    capAid, appletAid);
                            int statusI4I = responseI4I.getSW();
                            if (statusI4I == Comunicacao.STATUS_OK) {
                                System.out.println("-- Applet " +
                                        Convert.fromByteArrayToHexa(appletAid) +
                                        " successfully installed!");
                                //return true;
                            }
                            else {
                                System.out.print("-- Applet " +
                                        Convert.fromByteArrayToHexa(appletAid) +
                                        " could not be installed: " +
                                        statusInstall(statusI4I) + " (" +
                                        Integer.toHexString(statusI4I) + ")");
                                //return false;
                            }
                        }
                    }
                	
                    System.out.println("-- Load File " +
                            Convert.fromByteArrayToHexa(capAid) +
                            " successfully installed!");
                    //return true;
                    
                }
                
                else {
                    System.out.println("-- Applet/Load File could not be " +
                            "loaded: " + statusLoad(statusLoad) + "( " +
                            Integer.toHexString(statusLoad) + ")");
                    //return false;
                }
            }
            else {
                System.out.print("-- Load File " + Convert.fromByteArrayToHexa(
                        capAid) + " could not be installed: " +
                        statusInstall(statusI4L) + " (" + Integer.toHexString(
                        statusI4L) + ")");
                //return false;
            }
    }    
    
    private static String statusLoad(int status) {
        String errorMessage;
        switch (status) {
            case Comunicacao.STATUS_MEMORY_FAILURE:
                errorMessage = "Memory failure";
                break;
            case Comunicacao.STATUS_INCORRECT_LENGTH:
                errorMessage = "Incorrect length";
                break;
            case Comunicacao.STATUS_CONDITIONS_NS:
                errorMessage = "Command conditions not satisfied";
                break;
            case Comunicacao.STATUS_INCORRECT_DATA:
                errorMessage = "Incorrect data in the data field";
                break;
            case Comunicacao.STATUS_INSUFFICIENT_MEMORY:
                errorMessage = "Insufficient memory";
                break;
            case Comunicacao.STATUS_INCORRECT_P1_P2:
                errorMessage = "Incorrect P1/P2";
                break;
            case Comunicacao.STATUS_UNKNOWN_INSTRUCTION_CODE:
                errorMessage = "Instruction not supported";
                break;
            default:
                errorMessage = "Unknown error";
        }
        return errorMessage;
    }

    private static String statusInstall(int status) {
        String errorMessage;
        switch (status) {
            case Comunicacao.STATUS_MEMORY_FAILURE:
                errorMessage = "Memory failure";
                break;
            case Comunicacao.STATUS_CONDITIONS_NS:
                errorMessage = "Conditions of use not satisfied";
                break;
            case Comunicacao.STATUS_INCORRECT_DATA:
                errorMessage = "Incorrect data";
                break;
            case Comunicacao.STATUS_INSUFFICIENT_MEMORY:
                errorMessage = "Memory full";
                break;
            case Comunicacao.STATUS_INCORRECT_P1_P2:
                errorMessage = "Incorrect P1/P2";
                break;
            case Comunicacao.STATUS_UNKNOWN_INSTRUCTION_CODE:
                errorMessage = "Instruction not supported";
                break;
            case Comunicacao.STATUS_NO_PRECISE_DIAGNOSIS:
                errorMessage = "No precise diagnosis";
                break;
            default:
                errorMessage = "Unknown error";
        }
        return errorMessage;
    }

    //FIXME Apaguei o doList, qualquer problema ver na verão 1.2
    
    public ArrayList<CardApplet> listarApplets() throws IOException, CardException{
    	
    	byte listedComponents = 0;
    	listedComponents = Comunicacao.APPLETS;
    	byte[] aid = null;
    	
    	ResponseAPDU response = comunicacao.getStatus(listedComponents, Comunicacao.PRIMEIRO, aid);
		int status = response.getSW();
		byte[] data = response.getData();
		ArrayList<CardApplet> applets = new ArrayList<CardApplet>();
		
		if (status == Comunicacao.STATUS_OK) {
			byte lengthAid;
			for (int i = 0; i < data.length; i += lengthAid + 3) {
				lengthAid = data[i];
				String id =  Convert.fromByteArrayToHexa(data, i + 1, lengthAid + i + 1);
				String estado = appletLifeCycle(data[lengthAid + i + 1]);
				applets.add(new CardApplet(id, estado));
			}
		}
		
		else if (status == Comunicacao.STATUS_REFERENCE_DATA_NF) {
			System.out.println("No application matches search criteria");
		}
		
		else {
			System.out.print("Listing failed: ");
			switch (status) {
				case Comunicacao.STATUS_MORE_DATA_AVAILABLE:
					System.out.print("More data available");
					break;
				case Comunicacao.STATUS_INCORRECT_LENGTH:
					System.out.print("Incorrect length parameter");
					break;
				case Comunicacao.STATUS_INCORRECT_DATA:
					System.out.print("Incorrect data: unknown criteria " +
							"or incorrect AID length");
					break;
				case Comunicacao.STATUS_INCORRECT_P1_P2:
					System.out.print("Incorrect P1/P2");
					break;
				default:
					System.out.print("Unknown error");
			}
			System.out.println(" (" + Integer.toHexString(status) + ")");
		}
		
		return applets;
	}
    
    public ArrayList<Arquivo> listarArquivos() throws IOException, CardException{
    	
    	byte listedComponents = 0;
    	
    	listedComponents = Comunicacao.LOADFILES;
    	byte[] aid = null;
    	ResponseAPDU response = comunicacao.getStatus(listedComponents, Comunicacao.PRIMEIRO, aid);
		int status = response.getSW();
		byte[] data = response.getData();
		
		ArrayList<Arquivo> arquivos = new ArrayList<Arquivo>();
		
		if (status == Comunicacao.STATUS_OK) {
			byte lengthAid;
			for (int i = 0; i < data.length; i += lengthAid + 3) {
				lengthAid = data[i];
				String id = Convert.fromByteArrayToHexa(data, i + 1, lengthAid + i + 1);
				String estado ="LOADED";
				arquivos.add(new Arquivo(id, estado));
				
			}	
		}
		
		else if (status == Comunicacao.STATUS_REFERENCE_DATA_NF) {
			System.out.println("No application matches search criteria");
		}
		
		else {
			System.out.print("Listing failed: ");
			switch (status) {
				case Comunicacao.STATUS_MORE_DATA_AVAILABLE:
					System.out.print("More data available");
					break;
				case Comunicacao.STATUS_INCORRECT_LENGTH:
					System.out.print("Incorrect length parameter");
					break;
				case Comunicacao.STATUS_INCORRECT_DATA:
					System.out.print("Incorrect data: unknown criteria " +
							"or incorrect AID length");
					break;
				case Comunicacao.STATUS_INCORRECT_P1_P2:
					System.out.print("Incorrect P1/P2");
					break;
				default:
					System.out.print("Unknown error");
			}
			System.out.println(" (" + Integer.toHexString(status) + ")");
		}
		
		return arquivos;
	}
    
    public CardManager listarCardManager() throws IOException, CardException{
    	
    	byte listedComponents = 0;
    	listedComponents = Comunicacao.CARDMANAGER;
    	byte[] aid = null;
    	ResponseAPDU response = comunicacao.getStatus(listedComponents,Comunicacao.PRIMEIRO, aid);
		int status = response.getSW();
		byte[] data = response.getData();
		
		CardManager cardManager = null;
		
		if (status == Comunicacao.STATUS_OK) {
			byte lengthAid;
			lengthAid = data[0];			
			String id = Convert.fromByteArrayToHexa(data, 1,lengthAid + 1);//ID
			String estado = cardManagerLifeCycle(data[lengthAid + 1]);//Card Manager life cycle state
			String privilegio = Integer.toHexString(data[lengthAid + 2] & 0xFF);//Applet privileges
			
			cardManager = new CardManager(id, estado, privilegio);
			
		}
            else if (status == Comunicacao.STATUS_REFERENCE_DATA_NF) {
    			System.out.println("No application matches search criteria");
    		}
    		else {
    			System.out.print("Listing failed: ");
    			switch (status) {
    				case Comunicacao.STATUS_MORE_DATA_AVAILABLE:
    					System.out.print("More data available");
    					break;
    				case Comunicacao.STATUS_INCORRECT_LENGTH:
    					System.out.print("Incorrect length parameter");
    					break;
    				case Comunicacao.STATUS_INCORRECT_DATA:
    					System.out.print("Incorrect data: unknown criteria " +
    							"or incorrect AID length");
    					break;
    				case Comunicacao.STATUS_INCORRECT_P1_P2:
    					System.out.print("Incorrect P1/P2");
    					break;
					default:
						System.out.print("Unknown error");
    			}
    			System.out.println(" (" + Integer.toHexString(status) + ")");
    		}
    	
    	return cardManager;
    }
    
    public boolean excluirArquivo(String id) throws IOException, CardException {
        
        byte[] aid = Convert.fromHexaToByteArray(id);
        ResponseAPDU response = comunicacao.delete(aid);
        byte[] data = response.getData();
        int status = response.getSW();
        if (data != null && data[0] == (byte) 0x00 && status == Comunicacao.STATUS_OK) {
            System.out.println("-- Load File successfully deleted!");
            return true;
        }
        else {
            System.out.print("-- Load File could not be deleted: ");
            switch (status) {
               case Comunicacao.STATUS_MEMORY_FAILURE:
                   System.out.print("Memory failure");
                   break;
               case Comunicacao.STATUS_SECURITY_STATUS_NS:
                   System.out.print("Security status not satisfied");
                   break;
               case Comunicacao.STATUS_CONDITIONS_NS:
                   System.out.print("-- Load File in use; could not " +
                           "be deleted");
                   break;
               case Comunicacao.STATUS_INCORRECT_DATA:
                   System.out.print("-- Incorrect data");
                   break;
               case Comunicacao.STATUS_INCORRECT_P1_P2:
                   System.out.print("-- Incorrect P1/P2");
                   break;
               case Comunicacao.STATUS_REFERENCE_DATA_NF:
                   System.out.print("-- Reference data not found");
                   break;
               default:
                   System.out.print("-- Unknown error");
           }
           System.out.println(" (" + Integer.toHexString(status) + ")");
           return false;
       }
   }    
    
    private static String appletLifeCycle(byte state) {
    	String result;
		switch (state) {
			case 0x03:
				result = "INSTALLED";
				break;
			case 0x07:
				result = "SELECTABLE";
				break;
			case 0x0F:
				result = "PERSONALIZED";
				break;
			case 0x7F:
				result = "BLOCKED";
				break;
			case (byte) 0xFF:
				result = "LOCKED";
				break;
			default:
				throw new IllegalArgumentException("Invalid life cycle code");
		}
		return result;
	}
    
    public boolean selecionarApplet(String id) throws CardException  {
    	
    	if(id==null){
    		System.out.println("-- Nenhum applet selecionado. Clique sobre um applet na tabela");
    		return false;
    	}
    	
    	//if (id.length() > 0) {
    		
    		byte[] aid = Convert.fromHexaToByteArray(id);
    		System.out.println("Convert.fromHexaToByteArray(id);");
    		ResponseAPDU response = null;
			try {
				response = comunicacao.select(aid);
			
			} catch (IOException e) {
				System.out.println("CATCH 02");
				e.printStackTrace();
			}
    		
    		int status = response.getSW();
    		
    		if (status == Comunicacao.STATUS_OK) {
    			System.out.println("Applet successfully selected!");
    			return true;
    		}
    		
    		else {
    			System.out.print("Applet could not be selected: ");
    			switch (status) {
    				case Comunicacao.STATUS_APPLET_BLOCKED:
    					System.out.print("Selected applet blocked, or the " +
    							"Card Manager applet is selected and it is " +
    							"locked");
    					break;
    				case Comunicacao.STATUS_INCORRECT_LENGTH:
    					System.out.print("Incorrect AID length");
    					break;
    				case Comunicacao.STATUS_NO_MORE_SELECTED_APPLETS:
    					System.out.print("Select failed and no more " +
    							"applets are selected");
    					break;
    				case Comunicacao.STATUS_FUNCTION_NOT_SUPPORTED:
    					System.out.print("Function not supported");
    					break;
    				case Comunicacao.STATUS_APPLET_NF:
    					System.out.print("File not found in the selected " +
    							"application");
    					break;
    				case Comunicacao.STATUS_INCORRECT_P1_P2:
    					System.out.print("Incorrect P1/P2 paramter");
    					break;
    				default:
    					System.out.print("Unknown error");
    				break;
    			}
    			System.out.println(" (" + Integer.toHexString(status) + ")");
    			return false;
    		}
    	//}
    	/*else {
        	System.out.println("Usage: SELECT <AID>");
        }*/
    }
    
    //********************************************************************************************
	private void doSelect(String[] params) throws IOException, CardException {
    	if (params.length > 0) {
    		byte[] aid = Convert.fromHexaToByteArray(params[0]);
    		ResponseAPDU response = comunicacao.select(aid);
    		int status = response.getSW();
    		if (status == Comunicacao.STATUS_OK) {
    			System.out.println("Applet successfully selected!");
    		}
    		else {
    			System.out.print("Applet could not be selected: ");
    			switch (status) {
    				case Comunicacao.STATUS_APPLET_BLOCKED:
    					System.out.print("Selected applet blocked, or the " +
    							"Card Manager applet is selected and it is " +
    							"locked");
    					break;
    				case Comunicacao.STATUS_INCORRECT_LENGTH:
    					System.out.print("Incorrect AID length");
    					break;
    				case Comunicacao.STATUS_NO_MORE_SELECTED_APPLETS:
    					System.out.print("Select failed and no more " +
    							"applets are selected");
    					break;
    				case Comunicacao.STATUS_FUNCTION_NOT_SUPPORTED:
    					System.out.print("Function not supported");
    					break;
    				case Comunicacao.STATUS_APPLET_NF:
    					System.out.print("File not found in the selected " +
    							"application");
    					break;
    				case Comunicacao.STATUS_INCORRECT_P1_P2:
    					System.out.print("Incorrect P1/P2 paramter");
    					break;
    				default:
    					System.out.print("Unknown error");
    				break;
    			}
    			System.out.println(" (" + Integer.toHexString(status) + ")");
    		}
    	}
    	else {
        	System.out.println("Usage: SELECT <AID>");
        }
    }
    
    private static String cardManagerLifeCycle(int code) {
    	String result;
    	switch (code) {
    		case 0x01:
    			result = Comunicacao.LIFE_CYCLE_OPREADY;
    			break;
    		case 0x07:
    			result = Comunicacao.LIFE_CYCLE_INITIALIZED;
    			break;
    		case 0x0F:
    			result = Comunicacao.LIFE_CYCLE_SECURED;
    			break;
    		case 0x7F:
    			result = Comunicacao.LIFE_CYCLE_LOCKED;
    			break;
			default:
				throw new IllegalArgumentException("Invalid life cycle code");
    	}
    	return result;
    }
}