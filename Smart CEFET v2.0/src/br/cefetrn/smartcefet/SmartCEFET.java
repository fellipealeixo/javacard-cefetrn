package br.cefetrn.smartcefet;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import br.cefetrn.smartcefet.dominio.Ambiente;
import br.cefetrn.smartcefet.dominio.FuncionarioSistema;
import br.cefetrn.smartcefet.dominio.Permissao;
import br.cefetrn.smartcefet.dominio.Ponto;
import br.cefetrn.smartcefet.excecoes.NenhumTerminalDisponivelException;
import br.cefetrn.smartcefet.negocio.ServicosPersistencia;
import br.cefetrn.smartcefet.negocio.ServicosSmartCard;
import br.cefetrn.smartcefet.persistencia.sgbd.Persistencia;
import br.cefetrn.smartcefet.smartcard.Terminal;
import br.cefetrn.smartproject.gpcomm.GpCommException;

public class SmartCEFET {
	
	private ServicosSmartCard servicosSmartCard;
	private ServicosPersistencia servicosPersistencia;
	
	public SmartCEFET() throws GpCommException{
		servicosSmartCard = new ServicosSmartCard();
		servicosPersistencia = new ServicosPersistencia();
	}
	
	public Terminal recuperarTerminal(int indice) throws GpCommException, NenhumTerminalDisponivelException{
		List<Terminal> terminias = this.servicosSmartCard.recuperarTerminais();
		return terminias.get(indice);
	}
	
	public void fecharComunicacao() throws GpCommException{
		this.servicosSmartCard.fecharComunicacao();
	}
	
	public void atualizarSenhaFuncionario(FuncionarioSistema funcionario){
		servicosPersistencia.atualizarFuncionario(funcionario);
	}
	
	public void atualizarFuncionarioAtivo(FuncionarioSistema funcionario){
		servicosPersistencia.atualizarFuncionarioAtivo(funcionario);
	}
	//FIXME descomentar aqui
	public void inserirEntidade(Object entidade){
		this.servicosPersistencia.inserir(entidade);
	}
	
	public List<Ambiente> recuperarAmbientes(){
		return this.servicosPersistencia.recuperarAmbientes();
	}
	
	public List<FuncionarioSistema> recuperarFuncionariosPorNome(String nome){
		return this.servicosPersistencia.recuperarFuncionariosPorNome(nome);
	}
	
	public FuncionarioSistema recuperarFuncionarioPorNome(String nome){
		return this.servicosPersistencia.recuperarFuncionarioPorNome(nome);
	}
	
	public FuncionarioSistema recuperarFuncionarioPorMatricula(String matricula) throws PersistenceException {
		  	return this.servicosPersistencia.recuperarFuncionarioPorMatricula(matricula);
	}
	
	public Permissao recuperarPermisao(FuncionarioSistema funcionario, Ambiente ambiente){		
		return this.servicosPersistencia.recuperarPermisao(funcionario, ambiente);
	}
	
	public List<Permissao> recuperarPermicoesPorFuncionario(FuncionarioSistema funcionario){
		return this.servicosPersistencia.recuperarPermicoesPorFuncionario(funcionario);
	}
	//FIXME não está funcionanado
	/*public List<Ambiente> recuperarAmbientesNaoPermitidos(FuncionarioSistema funcionario){		
		return this.servicosPersistencia.recuperarAmbientesNaoPermitidos(funcionario);
	}*/
	
	public List<Ambiente> recuperarAmbientesNaoPermitidos(FuncionarioSistema funcionario){
		List<Ambiente> ambientes = this.recuperarAmbientes();
		List<Permissao> permissoes = this.recuperarPermicoesPorFuncionario(funcionario);
		List<Ambiente> ambientesNaoPermitidos = new ArrayList<Ambiente>();
		boolean permitido;
		for(Ambiente ambiente: ambientes){
			permitido= false;
			for(Permissao permissao: permissoes){
				if(permissao.getAmbiente().getId()==ambiente.getId())
						permitido= true;
			}
			if(!permitido)
					ambientesNaoPermitidos.add(ambiente);
		}
		
		return ambientesNaoPermitidos;
	}

	public void excluirPermissao(Permissao permissao) {
		this.servicosPersistencia.excluirPermissao(permissao);
		
	}

	public void inserirPonto(Ponto ponto) {
		this.servicosPersistencia.inserirPonto(ponto);
		
	}
	
	
	
}
