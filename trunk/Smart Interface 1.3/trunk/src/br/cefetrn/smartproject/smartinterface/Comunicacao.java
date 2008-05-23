package br.cefetrn.smartproject.smartinterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.crypto.spec.IvParameterSpec;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.CommandAPDU;

//import opencard.core.service.CardRequest;
//import opencard.core.service.CardServiceException;
//import opencard.core.service.SmartCard;
//import opencard.core.terminal.CardTerminalException;
//import opencard.core.terminal.CommandAPDU;
//import opencard.core.terminal.ResponseAPDU;
//import opencard.opt.util.PassThruCardService;
import br.cefetrn.smartproject.smartinterface.CAP;
import br.cefetrn.smartproject.smartinterface.util.Criptografia;

public class Comunicacao {
	public static final byte KAUTHENC = 1;
	public static final byte KMAC = 2;
	public static final byte KKEK = 3;
	public static final byte SEM_SEGURANCA = 0;
	public static final byte COM_MAC = 1;
	public static final byte COM_MAC_CRIPTOGRAFIA = 3;
	public static final byte CARDMANAGER = (byte) 0x80;
	public static final byte APPLETS = (byte) 0x40;
	public static final byte LOADFILES = (byte) 0x20;
	public static final byte GEMPLUS_AI = (byte) 0x80;
	public static final byte PRIMEIRO = (byte) 0x00;
	public static final byte PROXIMO = (byte) 0x01;
	public static final int STATUS_APPLET_BLOCKED = 0x6283;
	public static final int STATUS_APPLET_NF = 0x6A82;
	public static final int STATUS_CONDITIONS_NS = 0x6985;
	public static final int STATUS_CRYPTOGRAM_NV = 0x6300;
	public static final int STATUS_FUNCTION_NOT_SUPPORTED = 0x6A81;
	public static final int STATUS_INCORRECT_DATA = 0x6A80;
	public static final int STATUS_INCORRECT_LENGTH = 0x6700;
	public static final int STATUS_INCORRECT_P1_P2 = 0x6A86;
	public static final int STATUS_INSUFFICIENT_MEMORY = 0x6A84;
	public static final int STATUS_MEMORY_FAILURE = 0x6581;
	public static final int STATUS_MORE_DATA_AVAILABLE = 0x6310;
	public static final int STATUS_NO_MORE_SELECTED_APPLETS = 0x6999;
	public static final int STATUS_NO_PRECISE_DIAGNOSIS = 0x6F00;
	public static final int STATUS_OK = 0x9000;
	public static final int STATUS_REFERENCE_DATA_NF = 0x6A88;
	public static final int STATUS_SECURITY_STATUS_NS = 0x6982;
	public static final int STATUS_UNKNOWN_INSTRUCTION_CODE = 0x6D00;
	public static final String LIFE_CYCLE_OPREADY = "OP_READY";
	public static final String LIFE_CYCLE_INITIALIZED = "INITIALIZED";
	public static final String LIFE_CYCLE_SECURED = "SECURED";
	public static final String LIFE_CYCLE_LOCKED = "LOCKED";

	//private SmartCard cartao;
	
	//Minhas Variáveis com SmartCardIO
	
	List<CardTerminal> leitoras = null;
	private Card cartao6 = null;
	CardChannel canal;
	
	//Variaveis de Cristian
	

	private static final Logger log = Logger.getLogger(Comunicacao.class.getName());
	//private PassThruCardService servico;
	
	
	private byte[] batchIdentifier;
	private byte[] cardChallenge;
	private byte[] cardCryptogram;
	private byte[] fabricationData;
	private byte[] hostCryptogram;
	private byte[] keyAUTHENC;
	private byte[] keyKEK;
	private byte[] keyMAC;
	private byte[] lsbAIDCardManager;
	private byte[] mac;
	private byte[] serialNumber;
	private byte[] sessionKeyAUTHENC;
	private byte[] sessionKeyKEK;
	private byte[] sessionKeyMAC;
	private byte keySetVersion;
	private byte keySetIndex;
	private byte modo_seguranca = SEM_SEGURANCA;
	private byte[] hostChallenge = new byte[] { (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00 };

	public Comunicacao(Card cartao) throws ClassNotFoundException, NullPointerException {
		
		this.cartao6 = cartao;
		this.canal = cartao6.getBasicChannel();
		
	}
	
	
	
	
	
	// *INITIALIZE UPDATE*
	public ResponseAPDU initializeUpdate(byte[] KMC)throws  IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write((byte) 0x80);
		baos.write((byte) 0x50);
		baos.write((byte) 0x00);
		baos.write((byte) 0x00);
		baos.write((byte) 0x08);
		baos.write(hostChallenge);
		baos.write((byte) 0x1C);
		
		//Minha Implementação
		CommandAPDU comando = new CommandAPDU(baos.toByteArray());
		ResponseAPDU resposta = null;
		byte[] dados = null;
		
		try {
			resposta = canal.transmit(comando);
			dados = resposta.getBytes();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//byte[] dados = resposta.data();
		

		lsbAIDCardManager = new byte[2];
		System.arraycopy(dados, 0, lsbAIDCardManager, 0, 2);
		fabricationData = new byte[2];
		System.arraycopy(dados, 2, fabricationData, 0, 2);
		serialNumber = new byte[4];
		System.arraycopy(dados, 4, serialNumber, 0, 4);
		batchIdentifier = new byte[2];
		System.arraycopy(dados, 8, batchIdentifier, 0, 2);
		keySetVersion = dados[10];
		keySetIndex = dados[11];
		cardChallenge = new byte[8];
		System.arraycopy(dados, 12, cardChallenge, 0, 8);
		cardCryptogram = new byte[8];
		System.arraycopy(dados, 20, cardCryptogram, 0, 8);

		byte[] ddAUTHENC = generateDiversificationData(KAUTHENC);
		byte[] ddMAC = generateDiversificationData(KMAC);
		byte[] ddKEK = generateDiversificationData(KKEK);

		keyAUTHENC = generateKey(ddAUTHENC, KMC);
		keyMAC = generateKey(ddMAC, KMC);
		keyKEK = generateKey(ddKEK, KMC);

		sessionKeyAUTHENC = generateSessionKey(keyAUTHENC);
		sessionKeyMAC = generateSessionKey(keyMAC);
		sessionKeyKEK = generateSessionKey(keyKEK);

		return resposta;
	}

	// *EXTERNAL AUTHENTICATE*
	public ResponseAPDU externalAuthenticate() throws CardException, IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write((byte) 0x84);
		baos.write((byte) 0x82);
		baos.write((byte) modo_seguranca);
		baos.write((byte) 0x00);
		baos.write((byte) 0x10);
		hostCryptogram = generateHostCryptogram();
		mac = generateMAC();
		baos.write(hostCryptogram);
		baos.write(mac);

		CommandAPDU comando = new CommandAPDU(baos.toByteArray());
		ResponseAPDU resposta = this.canal.transmit(comando);
		return resposta;
	}

	private byte[] generateDiversificationData(byte tipo_chave)
			throws IllegalArgumentException {
		switch (tipo_chave) {
		case KAUTHENC:
		case KMAC:
		case KKEK:
			byte[] dd = new byte[16];
			System.arraycopy(lsbAIDCardManager, 0, dd, 0, 2);
			System.arraycopy(serialNumber, 0, dd, 2, 4);
			dd[6] = (byte) 0xF0;
			dd[7] = tipo_chave;
			System.arraycopy(lsbAIDCardManager, 0, dd, 8, 2);
			System.arraycopy(serialNumber, 0, dd, 10, 4);
			dd[14] = (byte) 0x0F;
			dd[15] = tipo_chave;
			return dd;
		default:
			throw new IllegalArgumentException(String.valueOf(tipo_chave));
		}
	}

	private byte[] generateKey(byte[] dd, byte[] KMC) {
		byte[] kLeft;
		byte[] kRight;
		byte[] ddLeft = new byte[8];
		byte[] ddRight = new byte[8];
		byte[] key = new byte[16];
		System.arraycopy(dd, 0, ddLeft, 0, 8);
		System.arraycopy(dd, 8, ddRight, 0, 8);
		kLeft = Criptografia.tripleDES(Criptografia._3DES_ECB, KMC, ddLeft);
		kRight = Criptografia.tripleDES(Criptografia._3DES_ECB, KMC, ddRight);
		System.arraycopy(kLeft, 0, key, 0, 8);
		System.arraycopy(kRight, 0, key, 8, 8);
		return key;
	}

	private byte[] generateSessionKey(byte[] chave) {
		byte[] leftDiversifier = new byte[8];
		byte[] rightDiversifier = new byte[8];
		System.arraycopy(cardChallenge, 4, leftDiversifier, 0, 4);
		System.arraycopy(hostChallenge, 0, leftDiversifier, 4, 4);
		System.arraycopy(cardChallenge, 0, rightDiversifier, 0, 4);
		System.arraycopy(hostChallenge, 4, rightDiversifier, 4, 4);
		byte[] sessionKeyLeft = Criptografia.tripleDES(Criptografia._3DES_ECB,
				chave, leftDiversifier);
		byte[] sessionKeyRight = Criptografia.tripleDES(Criptografia._3DES_ECB,
				chave, rightDiversifier);
		byte[] sessionKey = new byte[16];
		System.arraycopy(sessionKeyLeft, 0, sessionKey, 0, 8);
		System.arraycopy(sessionKeyRight, 0, sessionKey, 8, 8);
		return sessionKey;
	}

	private byte[] generateHostCryptogram() {
		byte[] inputToMAC = new byte[24];
		System.arraycopy(cardChallenge, 0, inputToMAC, 0, 8);
		System.arraycopy(hostChallenge, 0, inputToMAC, 8, 8);
		inputToMAC[16] = (byte) 0x80;
		Arrays.fill(inputToMAC, 17, 24, (byte) 0x00);
		IvParameterSpec iv = new IvParameterSpec(new byte[] { (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00 });
		byte[] saida = Criptografia.tripleDES(Criptografia._3DES_CBC,
				sessionKeyAUTHENC, inputToMAC, iv);
		// PENDING Por que os 8 últimos bytes?
		byte[] host = new byte[8];
		System.arraycopy(saida, 16, host, 0, 8);
		return host;
	}

	private byte[] generateMAC() {
		byte[] inputToMAC = new byte[] { (byte) 0x84, (byte) 0x82,
				modo_seguranca, (byte) 0x00, (byte) 0x10,
				/* enchendo lingüiça => */(byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, /* <= */(byte) 0x80, (byte) 0x00,
				(byte) 0x00 };
		System.arraycopy(hostCryptogram, 0, inputToMAC, 5, 8);
		IvParameterSpec iv = new IvParameterSpec(new byte[] { (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00 });
		byte[] saida = Criptografia.tripleDES(Criptografia._3DES_CBC,
				sessionKeyMAC, inputToMAC, iv);
		// PENDING Por que os 8 últimos bytes?
		byte[] mac = new byte[8];
		System.arraycopy(saida, 8, mac, 0, 8);
		return mac;
	}

	// *GET STATUS*
	/*
	 * se o tamanho do aid = 0 ou for de 5 a 16 bytes, tag = 4F se o tamanho for
	 * de 1, tag = 9F70
	 */
	public ResponseAPDU getStatus(int p1, int p2, byte[] aid) throws IOException, CardException {
		int tamanho_aid = (aid == null) ? 0 : aid.length;
		boolean aidOuNull = true;
		if (tamanho_aid == 1) {
			aidOuNull = false;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write((byte) 0x80);
		baos.write((byte) 0xF2);
		baos.write((byte) p1);
		baos.write((byte) p2);
		if (aidOuNull) {
			baos.write((byte) 2 + (tamanho_aid));
			baos.write((byte) 0x4F);
			baos.write((byte) tamanho_aid);
			if (tamanho_aid > 0) {
				baos.write(aid);
			}
		} else {
			baos.write((byte) 3 + (tamanho_aid));
			baos.write((byte) 0x9F);
			baos.write((byte) 0x70);
			baos.write((byte) 0x01);
			baos.write((byte) aid[0]);
		}
		baos.write((byte) 0x00);

		CommandAPDU comando = new CommandAPDU(baos.toByteArray());
		ResponseAPDU resposta = canal.transmit(comando);
		return resposta;

	}

	// *INSTALL FOR LOAD*
	public ResponseAPDU install4load(byte[] aid) throws IOException, CardException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write((byte) 0x80);
		baos.write((byte) 0xE6);
		baos.write((byte) 0x02);
		baos.write((byte) 0x00);
		baos.write((byte) (aid.length + 5));
		baos.write((byte) aid.length);
		baos.write(aid);
		baos.write((byte) 0x00);
		baos.write((byte) 0x00);
		baos.write((byte) 0x00);
		baos.write((byte) 0x00);
		baos.write((byte) 0x01);

		CommandAPDU comando = new CommandAPDU(baos.toByteArray());
		ResponseAPDU resposta = canal.transmit(comando);
		return resposta;

	}

	// *LOAD*
	public ResponseAPDU load(CAP cap) throws IOException, CardException {
		final int N_BYTES = 239;
		byte[] dados = cap.dados();
		byte[] buffer = new byte[N_BYTES + 5];
		byte p2 = 0;
		buffer[0] = (byte) 0x80;
		buffer[1] = (byte) 0xE8;
		buffer[2] = (byte) 0x00;
		int j = 0;
		while (dados.length - j >= N_BYTES) {
			buffer[3] = p2;
			buffer[4] = (byte) N_BYTES;
			System.arraycopy(dados, j, buffer, 5, N_BYTES);

			CommandAPDU comando = new CommandAPDU(buffer);
			ResponseAPDU resposta = canal.transmit(comando);
			if (resposta.getSW() != 0x9000) {
				return resposta;
			}
			j += N_BYTES;
			p2++;
		}
		int restou = dados.length - j;
		buffer = new byte[restou + 6];
		buffer[0] = (byte) 0x80;
		buffer[1] = (byte) 0xE8;
		buffer[2] = (byte) 0x80;
		buffer[3] = p2;
		buffer[4] = (byte) restou;
		System.arraycopy(dados, j, buffer, 5, restou);
		buffer[buffer.length - 1] = (byte) 0x01;

		CommandAPDU comando = new CommandAPDU(buffer);
		ResponseAPDU resposta = canal.transmit(comando);
		return resposta;
	}

	// *INSTALL FOR INSTALL*
	public ResponseAPDU install4install(byte[] aid_loadfile, byte[] aid_applet)throws IOException, CardException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// CLA
		baos.write((byte) 0x80);
		// INS
		baos.write((byte) 0xE6);
		// P1
		baos.write((byte) 0x0C);
		// P2
		baos.write((byte) 0x00);
		// LC
		baos.write(9 + aid_loadfile.length + 2 * aid_applet.length);
		// Length of Load File AID
		baos.write((byte) aid_loadfile.length);
		// Load File AID
		baos.write(aid_loadfile);
		// Length of AID within Load File
		baos.write((byte) aid_applet.length);
		// AID within Load File
		baos.write(aid_applet);
		// Length of application instance AID
		baos.write((byte) aid_applet.length);
		// Application instance identifier (AID)
		baos.write(aid_applet);
		// Length of application privileges
		baos.write((byte) 0x01); // e poderia ser outro valor???
		// Application privileges
		baos.write((byte) 0x00);
		// Length of install parameter field
		baos.write((byte) 0x02);
		// Install parameter field
		baos.write((byte) 0x9F);
		baos.write((byte) 0x00);
		// Length of Install Token
		baos.write((byte) 0x00);
		// LE
		baos.write((byte) 0x00);

		CommandAPDU comando = new CommandAPDU(baos.toByteArray());
		ResponseAPDU resposta = canal.transmit(comando);
		return resposta;
	}
	
	public ResponseAPDU pinChangeUnblock(byte p2, byte[] pin) throws CardException {
		byte[] pin_criptografado = Criptografia.tripleDES(Criptografia._3DES_CBC, sessionKeyKEK, pin);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write((byte) 0x80);
		baos.write((byte) 0x24);
		baos.write((byte) 0x00);
		baos.write(p2);
		baos.write((byte) pin_criptografado.length);
		try {
			baos.write(pin_criptografado);
		}
		catch (IOException e) {
			// ByteArrayOutputStream.close will never throw this exception
		}
		CommandAPDU cmd = new CommandAPDU(baos.toByteArray());
		System.out.println(cmd);
		return canal.transmit(cmd);
	}

	/**
	 * Exclui uma aplicação ou arquivo carregado no cartão.
	 * 	
	 * @param aid
	 * 		The AID of the application (or the Load File) tobe deleted.
	 * @return The response APDU of this command, containing the status and the
	 *         response data.
	 * @throws CardException
	 */
	public ResponseAPDU delete(byte[] aid) throws CardException {
		log.entering(getClass().getName(), "delete");
		byte tamanhoAid = (byte) aid.length;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// CLA
		baos.write((byte) 0x80);
		// INS
		baos.write((byte) 0xE4);
		// P1
		baos.write((byte) 0x00);
		// P2
		baos.write((byte) 0x00);
		// LC (T + L + V)
		baos.write((byte) (2 + tamanhoAid));
		// Tag
		baos.write((byte) 0x4F);
		// Length
		baos.write(tamanhoAid);
		// Value (AID)
		try {
			baos.write(aid);
		} catch (IOException e) {
			// ByteArrayOutputStream will never throw this exception
		}
		// LE
		baos.write((byte) 0x01);
		CommandAPDU comando = new CommandAPDU(baos.toByteArray());
		log.fine("Sending the DELETE command to the card");
		ResponseAPDU resposta = canal.transmit(comando);
		log.exiting(getClass().getName(), "delete");
		return resposta;
	}

	// *SELECT*
	public ResponseAPDU select(byte[] aid) throws IOException, CardException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write((byte) 0x00);
		baos.write((byte) 0xA4);
		baos.write((byte) 0x04);
		baos.write((byte) 0x00);
		baos.write((byte) aid.length);
		baos.write(aid);
		baos.write((byte) 0x00);

		CommandAPDU comando = new CommandAPDU(baos.toByteArray());
		ResponseAPDU resposta =canal.transmit(comando);
		return resposta;
	}

	//* EXCE  * 
	public ResponseAPDU exec(byte[] apdu) throws CardException {
		CommandAPDU comando = new CommandAPDU(apdu);
		ResponseAPDU resposta = canal.transmit(comando);
		return resposta;
	}
}
