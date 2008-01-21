package br.cefetrn.smartcefet.smartcard;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import br.cefetrn.smartcefet.excecoes.NenhumTerminalDisponivelException;
import br.cefetrn.smartproject.gpcomm.GpComm;
import br.cefetrn.smartproject.gpcomm.GpCommException;
import br.cefetrn.smartproject.gpcomm.GpCommTerminal;

public class Comunicacao {
	GpComm gpcomm;
	
	public Comunicacao() throws GpCommException{
		init();
	}
	
	 public void init() throws GpCommException {
		    this.gpcomm = null;
	        gpcomm = new GpComm();       
	 }
	 
	 public Collection<Terminal> recuperarTerminais() throws GpCommException, NenhumTerminalDisponivelException{
		 Collection<GpCommTerminal> GpCommTerminais = gpcomm.getAvailableTerminals();
		 if(GpCommTerminais.isEmpty()){
			 throw new NenhumTerminalDisponivelException("Não há nrnhum teminal conectado ao computador");
		 }
		 Terminal terminal;
		 Collection<Terminal> terminais = new ArrayList<Terminal>();
		 for(GpCommTerminal gpcommTerminal: GpCommTerminais){
			 terminal = new Terminal(gpcommTerminal);
			 terminais.add(terminal);
		 }
		 return terminais;
	 }
	 
	 public void fechar() throws GpCommException{
		 this.gpcomm.close();
	 }
}
