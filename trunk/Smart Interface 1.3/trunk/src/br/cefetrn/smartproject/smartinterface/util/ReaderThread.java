/*
 * ReaderThread.java
 * 
 * Created on 25/09/2007, 01:32:22
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.cefetrn.smartproject.smartinterface.util;

import java.io.IOException;
import java.io.PipedInputStream;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Gleison
 */
public class ReaderThread extends Thread {
            
    PipedInputStream pi;
    JTextArea textArea = null; 
            
    public ReaderThread(PipedInputStream pi, JTextArea textArea) {
        this.pi = pi;
        this.textArea = textArea;
    }
    
    public void run() {
        final byte[] buf = new byte[1024];
        try {
            while (true) {
                final int len = pi.read(buf);
                if (len == -1) {
                    break;
                }
                
                SwingUtilities.invokeLater(new Runnable() {
                
                     public void run() {
                       textArea.append("\n"+new String(buf, 0, len));
                        // Mantém a última linha sempre visível
                        textArea.setCaretPosition(textArea.getDocument().getLength());
                       
                    }
                 });
             }
         } catch (IOException e) {
             
           }
    }
}
