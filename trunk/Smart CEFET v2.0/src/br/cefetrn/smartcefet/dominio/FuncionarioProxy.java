package br.cefetrn.smartcefet.dominio;

/*import br.cefetrn.smartcefet.apdu.AdicionarAmbienteCApdu;
import br.cefetrn.smartcefet.apdu.AtivarCartaoCApdu;
import br.cefetrn.smartcefet.apdu.GetAmbientesCApdu;
import br.cefetrn.smartcefet.apdu.GetCredencialCApdu;
import br.cefetrn.smartcefet.apdu.GetMatriculaCApdu;
import br.cefetrn.smartcefet.apdu.IsAtivoCApdu;
import br.cefetrn.smartcefet.apdu.RegistrarPontoCApdu;
import br.cefetrn.smartcefet.apdu.RegistrarPontoRApdu;
import br.cefetrn.smartcefet.apdu.RemoverAmbienteCApdu;
import br.cefetrn.smartcefet.apdu.SetCredencialCApdu;
import br.cefetrn.smartcefet.apdu.SetMatriculaCApdu;
import br.cefetrn.smartcefet.apdu.SetSenhaCApdu;
import br.cefetrn.smartcefet.apdu.VerificarAcessoAmbienteCApdu;
import br.cefetrn.smartcefet.apdu.VerificarSenhaFuncionarioCApdu;*/
import br.cefetrn.smartcefet.persistencia.sgbd.Persistencia;
import br.cefetrn.smartproject.gpcomm.GpCommCard;
import br.cefetrn.smartproject.gpcomm.GpCommException;
import br.cefetrn.smartproject.gpcomm.Util;
import br.cefetrn.smartproject.gpcomm.apdu.RApdu;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

/**
 * @author Crístian Deives <cristiandeives@gmail.com>
 */
public class FuncionarioProxy {
    /*public static final byte[] AID = new byte[] {(byte) 0x6c, (byte) 0x69,
            (byte) 0x67, (byte) 0x69, (byte) 0x61};
    public static final short SW_CAMPO_NAO_CORRESPONDENTE = (short) 0x6403;
    public static final short SW_CARTAO_INATIVO = (short) 0x6406;
    public static final short SW_OK = (short) 0x9000;   
    
    private static final Logger log =
            Logger.getLogger(FuncionarioProxy.class.getName());
    private GpCommCard cartao;
    
    public FuncionarioProxy(GpCommCard cartao) {
        if (cartao == null) {
            throw new NullPointerException("cartao não pode ser null");
        }
        this.cartao = cartao;
    }
    
    public boolean isInstalado() throws GpCommException {
        RApdu resposta = cartao.gpSelect(AID);
        boolean ok = false;
        switch (resposta.getSw()) {
            case SW_OK:
                ok = true;
                break;
            default:
                log.warning("SW inválido: " +
                        Util.toString(resposta.getSw()));
                break;
        }
        return ok;
    }
    
    public String getMatricula() throws GpCommException {
        RApdu resposta = cartao.execute(new GetMatriculaCApdu());
        if (resposta.getSw() == SW_OK) {
            return new String(resposta.getData());
        }
        else {
            throw new GpCommException("SW inválido: " +
                    Util.toString(resposta.getSw()));
        }
    }
    
    public void setMatricula(String outra_matricula) throws GpCommException {
        RApdu resposta = cartao.execute(new SetMatriculaCApdu(outra_matricula));
        if (resposta.getSw() != SW_OK){
            throw new GpCommException("SW inválido: " +
                    Util.toString(resposta.getSw()));
        }
    }
    
    public boolean verificarSenha(String outra_senha) throws GpCommException {
        RApdu resposta = cartao.execute(
                new VerificarSenhaFuncionarioCApdu(outra_senha));
        boolean ok = false;
        switch (resposta.getSw()) {
            case SW_OK:
                ok = true;
                break;
            case SW_CAMPO_NAO_CORRESPONDENTE:
                // nada
                break;
            default:
                log.warning("SW inválido: " +
                        Util.toString(resposta.getSw()));
                break;
        }
        return ok;
    }
    
    public void setSenha(String outra_senha) throws GpCommException {
        RApdu resposta = cartao.execute(new SetSenhaCApdu(outra_senha));
        if (resposta.getSw() != SW_OK){
            throw new GpCommException("SW inválido: " +
                    Util.toString(resposta.getSw()));
        }
    }
    
    public List<Ambiente> getAmbientes() throws GpCommException,
            PersistenceException {
        RApdu resposta = cartao.execute(new GetAmbientesCApdu());
        if (resposta.getSw() == SW_OK) {
            Persistencia p = Persistencia.getInstance();
            List<Ambiente> ambientes = new ArrayList<Ambiente>();
            for (byte b : resposta.getData()) {
                ambientes.add(p.find(Ambiente.class, b));
            }
            return ambientes;
        }
        else {
            throw new GpCommException("SW inválido: " +
                    Util.toString(resposta.getSw()));
        }
    }
    
    public boolean temAcessoAmbiente(Ambiente outro_ambiente)
            throws GpCommException {
        RApdu resposta = cartao.execute(
                new VerificarAcessoAmbienteCApdu(outro_ambiente));
        boolean ok = false;
        switch (resposta.getSw()) {
            case SW_OK:
                ok = true;
                break;
            case SW_CAMPO_NAO_CORRESPONDENTE:
                // nada
                break;
            default:
                log.warning("SW inválido: " +
                        Util.toString(resposta.getSw()));
                break;
        }
        return ok;
    }
    
    public void adicionarAmbiente(Ambiente outro_ambiente)
            throws GpCommException {
        RApdu resposta = cartao.execute(
                new AdicionarAmbienteCApdu(outro_ambiente));
        if (resposta.getSw() != SW_OK){
            throw new GpCommException("SW inválido: " +
                    Util.toString(resposta.getSw()));
        }
    }
    
    public void removerAmbiente(Ambiente outro_ambiente)
            throws GpCommException {
        RApdu resposta = cartao.execute(
                new RemoverAmbienteCApdu(outro_ambiente));
        if (resposta.getSw() != SW_OK){
            throw new GpCommException("SW inválido: " +
                    Util.toString(resposta.getSw()));
        }
    }
    
    public Credencial getCredencial() throws GpCommException {
        RApdu resposta = cartao.execute(new GetCredencialCApdu());
        if (resposta.getSw() == SW_OK){
            byte credencial = resposta.getData()[0];
            for (Credencial c : Credencial.values()) {
                if (c.ordinal() == credencial) {
                    return c;
                }
            }
            return null;
        }
        else{
            throw new GpCommException("SW inválido: " +
                    Util.toString(resposta.getSw()));
        }
    }
    
    public void setCredencial(Credencial outra_credencial) throws GpCommException {
        RApdu resposta = cartao.execute(new SetCredencialCApdu(outra_credencial));
        if (resposta.getSw() != SW_OK){
            throw new GpCommException("SW inválido: " +
                    Util.toString(resposta.getSw()));
        }
    }
    
    public boolean isAtivo() throws GpCommException {
       RApdu resposta = cartao.execute(new IsAtivoCApdu(new Date()));
       boolean ok = false;
       switch (resposta.getSw()) {
           case SW_OK:
               ok = true;
               break;
           case SW_CARTAO_INATIVO:
               // nada
               break;
           default:
               log.warning("SW inválido: " +
                    Util.toString(resposta.getSw()));
               break;
       }
       return ok;
    }
    
    public void ativarCartao() throws GpCommException{
        RApdu resposta = cartao.execute(new AtivarCartaoCApdu(new Date()));
        boolean ok = false;
        switch (resposta.getSw()){
            case SW_OK:
                ok = true;
                break;
            case SW_CARTAO_INATIVO:
                //?
                break;
            default:
                log.warning("SW inválido: " +
                    Util.toString(resposta.getSw()));
                
        }
    }
    
    public TipoPonto registrarPonto() throws GpCommException {
        RApdu resposta = cartao.execute(new RegistrarPontoCApdu());
        RegistrarPontoRApdu resposta_registrar_ponto =
                new RegistrarPontoRApdu(resposta);
        if (resposta_registrar_ponto.isEntrada()) {
            return TipoPonto.ENTRADA;
        }
        else if (resposta_registrar_ponto.isSaida()) {
            return TipoPonto.SAIDA;
        }
        else {
            log.warning("SW inválido: " +
                    Util.toString(resposta.getSw()));
            return null;
        }
    }*/
}