package br.cefetrn.smartproject.smartinterface.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Realiza c�lculos criptogr�ficos.
 */
public class Criptografia {
    /** Algoritmo de criptografia 3DES. */
    public static final String ALGORITMO_3DES = "DESede";
    /** Algoritmo de criptografia 3DES, no modo CBC, sem padding. */
    public static final String _3DES_CBC = "DESede/CBC/NoPadding";
    /** Algoritmo de criptografia 3DES, no modo ECB, sem padding. */
    public static final String _3DES_ECB = "DESede/ECB/NoPadding";
    
    /**
     * Criptografa um array de bytes a partir de uma chave e um vetor de
     * inicializa��o, utilizando o algoritmo 3DES.
     * @param modo O modo de criptografia. Os valores poss�veis s�o {@link #CBC}
     * e {@link #ECB}.
     * @param chave A chave que ser� utilizada para a criptografia. A chave
     * deve ser um array de tamanho igual a 24. Caso esse tamanho seja menor,
     * os bytes do in�cio do array ser�o acrescentados novamente ao fim dele,
     * quantas vezes forem necess�rias, at� o tamanho chegar a exatamente 24.
     * Caso o tamanho do array informado seja maior ou igual a 24, ele n�o
     * ser� modificado, por�m, s� ser�o utilizados os 24 primeiros bytes.
     * @param valor O valor a ser criptografado. O tamanho desse array de bytes
     * deve ser um m�ltiplo de 8. Caso contr�rio, os bytes do in�cio do array
     * ser�o acrescentados novamente ao fim dele, quantas vezes forem
     * necess�rias, at� o tamanho chegar ao menor m�ltiplo de 8 que seja maior
     * que o tamanho inicial.
     * @param iv O vetor de inicializa��o. Se <code>iv</code> for igual a
     * <code>null</code>, considera-se que n�o ser� usado um Vetor de
     * Inicializa��o.
     * @return O array <code>valor</code> criptografado com a chave
     * <code>chave</code>, utilizando o algoritmo 3DES, ou <code>null</code> se
     * houver algum erro (o que N�O � esperado). Esse array ter� o mesmo tamanho
     * que o tamanho do valor informado (m�ltiplo de 8).
     * @throws NullPointerException Se <code>chave</code> ou <code>valor</code>
     * for nulo.
     */
    public static byte[] tripleDES(String modo, byte[] chave, byte[] valor,
            IvParameterSpec iv) throws NullPointerException {
        if (chave == null) {
            throw new NullPointerException("Chave n�o pode ser nula");
        }
        if (valor == null) {
            throw new NullPointerException("Valor n�o pode ser nulo");
        }

        int tamanho_chave = chave.length;
        int tamanho_valor = valor.length;

        // A chave DEVE ter 24 bytes
        while (tamanho_chave < 24) {
            int tamanho_novo = tamanho_chave >= 12 ? 24 : tamanho_chave * 2;
            byte[] nova_chave = new byte[tamanho_novo];
            System.arraycopy(chave, 0, nova_chave, 0, tamanho_chave);
            System.arraycopy(chave, 0, nova_chave, tamanho_chave,
                    tamanho_novo - tamanho_chave);
            chave = nova_chave;
            tamanho_chave = chave.length;
        }

        // FIXME Se o valor a ser criptografado tiver tamanho < 4, d� erro!!!
        // O tamanho do valor DEVE ser m�ltiplo de 8
        if (tamanho_valor % 8 != 0) {
            byte[] novo_valor = new byte[((tamanho_valor / 8) + 1) * 8];
            System.arraycopy(valor, 0, novo_valor, 0, tamanho_valor);
            System.arraycopy(valor, 0, novo_valor, tamanho_valor,
                    novo_valor.length - tamanho_valor);
            valor = novo_valor;
            tamanho_valor = valor.length;
        }
        
        try {
            SecretKey key = SecretKeyFactory.getInstance(ALGORITMO_3DES).
                    generateSecret(new DESedeKeySpec(chave));
            Cipher cifra = Cipher.getInstance(modo);
            
            if (iv == null) {
                cifra.init(Cipher.ENCRYPT_MODE, key);
            }
            else {
                cifra.init(Cipher.ENCRYPT_MODE, key, iv);
            }
            
            return cifra.doFinal(valor);
        }
        catch (InvalidKeyException e) {
            System.err.println("A chave informada n�o cont�m 24 bytes: " +
                    e.getMessage());
        }
        catch (NoSuchAlgorithmException e) {
            System.err.println("O algoritmo informado n�o � v�lido: " +
                    e.getMessage());
        }
        catch (InvalidKeySpecException e) {
            System.err.println("Especifica��o de chave inv�lida: " +
                    e.getMessage());
        }
        catch (NoSuchPaddingException e) {
            System.err.println("M�todo de padding inv�lido: " +
                    e.getMessage());
        }
        catch (IllegalBlockSizeException e) {
            System.err.println("O tamanho do valor informado n�o � m�ltiplo " +
                    "de 8: " + e.getMessage());
        }
        catch (BadPaddingException e) {
            System.err.println("Padding inv�lido: " + e.getMessage());
        }
        catch (InvalidAlgorithmParameterException e) {
            System.err.println("IV inv�lido: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Criptografa um array de bytes a partir de uma chave, utilizando o
     * algoritmo 3DES.
     * @param modo O modo de criptografia. Os valores poss�veis s�o {@link #CBC}
     * e {@link #ECB}.
     * @param chave A chave que ser� utilizada para a criptografia. A chave
     * deve ser um array de tamanho igual a 24. Caso esse tamanho seja menor,
     * os bytes do in�cio do array ser�o acrescentados novamente ao fim dele,
     * quantas vezes forem necess�rias, at� o tamanho chegar a exatamente 24.
     * Caso o tamanho do array informado seja maior ou igual a 24, ele n�o
     * ser� modificado, por�m, s� ser�o utilizados os 24 primeiros bytes.
     * @param valor O valor a ser criptografado. O tamanho desse array de bytes
     * deve ser um m�ltiplo de 8. Caso contr�rio, os bytes do in�cio do array
     * ser�o acrescentados novamente ao fim dele, quantas vezes forem
     * necess�rias, at� o tamanho chegar ao menor m�ltiplo de 8 que seja maior
     * que o tamanho inicial.
     * @return O array <code>valor</code> criptografado com a chave
     * <code>chave</code>, utilizando o algoritmo 3DES, ou <code>null</code> se
     * houver algum erro (o que N�O � esperado). Esse array ter� o mesmo tamanho
     * que o tamanho do valor informado (m�ltiplo de 8).
     * @throws NullPointerException Se <code>chave</code> ou <code>valor</code>
     * for nulo.
     */
    public static byte[] tripleDES(String modo, byte[] chave, byte[] valor)
            throws NullPointerException {
        return tripleDES(modo, chave, valor, null);
    }
}
