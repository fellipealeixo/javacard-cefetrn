package br.cefetrn.smartcefet.negocio;

import java.util.List;

import br.cefetrn.smartcefet.excecoes.NenhumTerminalDisponivelException;
import br.cefetrn.smartcefet.smartcard.Comunicacao;
import br.cefetrn.smartcefet.smartcard.Terminal;
import br.cefetrn.smartproject.gpcomm.GpCommException;

public class ServicosSmartCard {
	
	private Comunicacao comunicacao;
	
	public ServicosSmartCard() throws GpCommException{
		this.comunicacao = new Comunicacao();
	}
	
	public List<Terminal> recuperarTerminais() throws GpCommException, NenhumTerminalDisponivelException{
		List<Terminal> terminais = (List<Terminal>) this.comunicacao.recuperarTerminais();
		return terminais;
	}

	public void fecharComunicacao() throws GpCommException {
		this.comunicacao.fechar();
		
	}
	
}
