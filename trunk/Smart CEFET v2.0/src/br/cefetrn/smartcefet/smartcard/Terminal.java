package br.cefetrn.smartcefet.smartcard;

import br.cefetrn.smartproject.gpcomm.GpCommCardListener;
import br.cefetrn.smartproject.gpcomm.GpCommException;
import br.cefetrn.smartproject.gpcomm.GpCommTerminal;

public class Terminal{
	GpCommTerminal gpCommTerminal;

	public Terminal(GpCommTerminal gpCommTerminal) {
		super();
		this.gpCommTerminal = gpCommTerminal;
	}
	
	public boolean isCarataoConectado() throws GpCommException{
		return this.gpCommTerminal.isCardConnected();
	}
	
	/**
	 * @return the gpCommTerminal
	 */
	public GpCommTerminal getGpCommTerminal() {
		return gpCommTerminal;
	}

	/**
	 * @param gpCommTerminal the gpCommTerminal to set
	 */
	public void setGpCommTerminal(GpCommTerminal gpCommTerminal) {
		this.gpCommTerminal = gpCommTerminal;
	}
	
	public void addCardListener(GpCommCardListener cardListener){
		this.gpCommTerminal.addGpCommCardListener(cardListener);
	}
}
