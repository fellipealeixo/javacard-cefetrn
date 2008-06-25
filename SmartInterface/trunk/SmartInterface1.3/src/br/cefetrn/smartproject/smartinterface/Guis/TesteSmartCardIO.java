package br.cefetrn.smartproject.smartinterface.Guis;

import java.util.Iterator;
import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

public class TesteSmartCardIO {

	/**
	 * @param args
	 * @throws CardException 
	 */
	public static void main(String[] args) throws CardException {
		List<CardTerminal> leitoras = null;
		Card card = null;	
		//Lista as leitoras configuradas e conecta o smart card na primeira da lista
		try {
			leitoras = getLeitoras();
			imprimeListaLeitoras(leitoras);
			card = conectaSmartCard(leitoras.get(0));
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//comando APDU ISO GET CHALLENGE para obter um número aleatório
		byte[] comando = {(byte)0x00, (byte)0x84, (byte)0x00, (byte)0x00, (byte)0x08};
		
		//obtém um canal de comunicação com o smart card
		CardChannel canal = card.getBasicChannel();
		
		System.out.println("ANTES de transmit");
		//envia o camando APDU e armazena a resposta
		ResponseAPDU resposta = canal.transmit(new CommandAPDU(comando));
		System.out.println("depois de transmit");
		
		System.out.println("Comando APDU: "+printBytesHexa(comando));
		System.out.println("RespostaAPDU: "+printBytesHexa(resposta.getBytes()));
		

	}
	
	/**
	 * 
	 * @param terminal
	 * @return
	 * @throws CardException
	 */
	
	private static Card conectaSmartCard(CardTerminal terminal) throws CardException{
		Card card = null;
		System.out.println("Conectando ao cartão inteligente...");
		//verifica se o smart card está inserido
		if(!terminal.isCardPresent()){
			System.out.println("Insira um smart card...");
		}
		else{
			try{
				card = terminal.connect("T=1");						
			}catch (Exception e) {				
				card = terminal.connect("T=0");							
			}
		}
		
		System.out.println("Protocolo de Transporte: "+card.getProtocol());
		
		if(card!=null){
			System.out.println("Conectado: "+card);
			System.out.println("ATR: "+printBytesHexa(card.getATR().getBytes()));
		}
		return card;
	}
	
	/**
	 * imprime na saída padrão 
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
	
	private static void imprimeListaLeitoras(List<CardTerminal> terminais){
		for(Iterator iter = terminais.iterator(); iter.hasNext();){
                        CardTerminal leitora = (CardTerminal)iter.next();
			System.out.println("Leitora: "+leitora.getName());			
		}
			
	}
	
	private static List<CardTerminal> getLeitoras() throws CardException{
		TerminalFactory factory = TerminalFactory.getDefault();
		//Obtém a lista de leitoras configuradas no ambiente
		List<CardTerminal> terminais = factory.terminals().list();
		return terminais;
	}
	

}
