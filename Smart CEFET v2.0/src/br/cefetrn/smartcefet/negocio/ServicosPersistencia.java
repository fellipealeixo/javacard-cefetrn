package br.cefetrn.smartcefet.negocio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.cefetrn.smartcefet.dominio.Ambiente;
import br.cefetrn.smartcefet.dominio.FuncionarioSistema;
import br.cefetrn.smartcefet.dominio.Permissao;
import br.cefetrn.smartcefet.dominio.Ponto;
import br.cefetrn.smartcefet.persistencia.sgbd.Persistencia;

public class ServicosPersistencia {
	private Persistencia persistencia;
	
	public ServicosPersistencia(){
		this.persistencia = Persistencia.getInstance();
	}
	
	public void atualizarFuncionario(FuncionarioSistema funcionario){
   	 	this.persistencia.atualizarSenhaFuncionario(funcionario);
   	 	
	}
	
	public void atualizarFuncionarioAtivo(FuncionarioSistema funcionario){
		this.persistencia.atualizarFuncionarioAtivo(funcionario);
	}
	
	public void inserir(Object entidade){
		this.persistencia.inserir(entidade);
	}
	
	public List<Ambiente> recuperarAmbientes(){
		return this.persistencia.findTodosAmbientes();
	}
	
	public List<FuncionarioSistema> recuperarFuncionariosPorNome(String nome){
		return this.persistencia.findFuncionariosPorNome(nome);
	}
	
	 public FuncionarioSistema recuperarFuncionarioPorMatricula(String matricula) throws PersistenceException {
		  	return this.persistencia.findFuncionarioPorMatricula(matricula);
	 }	
	  
	public Permissao recuperarPermisao(FuncionarioSistema funcionario, Ambiente ambiente){		
		return this.persistencia.recuperarPermisao(funcionario, ambiente);
	}

	public FuncionarioSistema recuperarFuncionarioPorNome(String nome) {
		return this.persistencia.findFuncionarioPorNome(nome);
	}
	
	public List<Permissao> recuperarPermicoesPorFuncionario(FuncionarioSistema funcionario){
		return this.persistencia.recuperarPermicoesPorFuncionario(funcionario);
	}
	
	/*public List<Ambiente> recuperarAmbientesNaoPermitidos(FuncionarioSistema funcionario){		
		return this.persistencia.recuperarAmbientesNaoPermitidos(funcionario);
	}*/

	public void excluirPermissao(Permissao permissao) {
		this.persistencia.excluirPermissao(permissao);
		
	}

	public void inserirPonto(Ponto ponto) {
		this.persistencia.inserirPonto(ponto);
		
	}
	
}
	

