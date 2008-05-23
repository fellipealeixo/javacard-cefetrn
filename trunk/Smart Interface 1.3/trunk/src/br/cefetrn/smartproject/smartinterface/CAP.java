package br.cefetrn.smartproject.smartinterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CAP {
    public static final byte ACC_APPLET = 0x04;
    
    public static final int TAG_HEADER = 1;
    public static final int TAG_DIRECTORY = 2;
    public static final int TAG_APPLET = 3;
    public static final int TAG_IMPORT = 4;
    public static final int TAG_CONSTANTPOOL = 5;
    public static final int TAG_CLASS = 6;
    public static final int TAG_METHOD = 7;
    public static final int TAG_STATICFIELD = 8;
    public static final int TAG_REFERENCELOCATION = 9;
    public static final int TAG_EXPORT = 10;
    public static final int TAG_DESCRIPTOR = 11;
    
    //capzinhos legais :)
    private JarFile cap;
    private byte[] header;
    private byte[] directory;
    private byte[] applet;
    private byte[] import_;
    private byte[] constant_pool;
    private byte[] class_;
    private byte[] method;
    private byte[] static_field;
    private byte[] reference_location;
    private byte[] export;
    private byte[] descriptor;
    
    public CAP(String f) throws IOException {
        cap = new JarFile(f);
        gerarComponentes();
    }

    private void gerarComponentes() throws IOException {
        Enumeration<JarEntry> capzinhos = cap.entries();
        while (capzinhos.hasMoreElements()) {
            InputStream in = cap.getInputStream(capzinhos.nextElement());
            byte tag = (byte) in.read();
            switch (tag) {
                case TAG_HEADER:
                    header = new byte[in.available() + 1];
                    header[0] = tag;
                    for (int i = 1; i < header.length; i++) {
                        header[i] = (byte) in.read();
                    }
                    break;
                case TAG_DIRECTORY:
                    directory = new byte[in.available() + 1];
                    directory[0] = tag;
                    for (int i = 1; i < directory.length; i++) {
                        directory[i] = (byte) in.read();
                    }
                    break;
                case TAG_APPLET:
                    applet = new byte[in.available() + 1];
                    applet[0] = tag;
                    for (int i = 1; i < applet.length; i++) {
                        applet[i] = (byte) in.read();
                    }
                    break;
                case TAG_IMPORT:
                    import_ = new byte[in.available() + 1];
                    import_[0] = tag;
                    for (int i = 1; i < import_.length; i++) {
                        import_[i] = (byte) in.read();
                    }
                    break;
                case TAG_CONSTANTPOOL:
                    constant_pool = new byte[in.available() + 1];
                    constant_pool[0] = tag;
                    for (int i = 1; i < constant_pool.length; i++) {
                        constant_pool[i] = (byte) in.read();
                    }
                    break;
                case TAG_CLASS:
                    class_ = new byte[in.available() + 1];
                    class_[0] = tag;
                    for (int i = 1; i < class_.length; i++) {
                        class_[i] = (byte) in.read();
                    }
                    break;
                case TAG_METHOD:
                    method = new byte[in.available() + 1];
                    method[0] = tag;
                    for (int i = 1; i < method.length; i++) {
                        method[i] = (byte) in.read();
                    }
                    break;
                case TAG_STATICFIELD:
                    static_field = new byte[in.available() + 1];
                    static_field[0] = tag;
                    for (int i = 1; i < static_field.length; i++) {
                        static_field[i] = (byte) in.read();
                    }
                    break;
                case TAG_REFERENCELOCATION:
                    reference_location = new byte[in.available() + 1];
                    reference_location[0] = tag;
                    for (int i = 1; i < reference_location.length; i++) {
                        reference_location[i] = (byte) in.read();
                    }
                    break;
                case TAG_EXPORT:
                    export = new byte[in.available() + 1];
                    export[0] = tag;
                    for (int i = 1; i < export.length; i++) {
                        export[i] = (byte) in.read();
                    }
                    break;
                case TAG_DESCRIPTOR:
                    descriptor = new byte[in.available() + 1];
                    descriptor[0] = tag;
                    for (int i = 1; i < descriptor.length; i++) {
                        descriptor[i] = (byte) in.read();
                    }
                    break;
            }
            in.close();
        }
    }
    
    public short getTamanhoTotal() {
        int tamanho = header.length + directory.length + import_.length +
                class_.length + method.length + static_field.length +
                constant_pool.length + reference_location.length +
                descriptor.length;
        
        // Componentes opcionais
        if (export != null) {
            tamanho += export.length;
        }
        if (applet != null) {
            tamanho += applet.length;
        }
        
        return (short) tamanho;
    }

    public byte[] dados() {
        byte[] b = null;
        int i = 0;
        short t = getTamanhoTotal();
        if (t < 128) {
            b = new byte[t + 2]; // 0xC4 + <tamanho>
            b[i++] = (byte) 0xC4;
            b[i++] = (byte) t;
        }
        else if (t < 256) {
            b = new byte[t + 3]; // 0xC4 + 0x81 + <tamanho>
            b[i++] = (byte) 0xC4;
            b[i++] = (byte) 0x81;
            b[i++] = (byte) t;
        }
        else if (t < 65536) {
            b = new byte[t + 4]; // 0xC4 + 0x82 + <B2_tamanho> + <B1_tamanho>
            b[i++] = (byte) 0xC4;
            b[i++] = (byte) 0x82;
            b[i++] = (byte) (t >>> 010);
            b[i++] = (byte) (t & 0xFF);
        }
        else if (t < 16777216) {
            // 0xC4 + 0x83 + <B3_tamanho> + <B2_tamanho> + <B1_tamanho>
            b = new byte[t + 5];
            b[i++] = (byte) 0xC4;
            b[i++] = (byte) 0x83;
            b[i++] = (byte) (t >>> 020);
            b[i++] = (byte) (t >>> 010);
            b[i++] = (byte) (t & 0xFF);
        }
        // HEADER
        System.arraycopy(header, 0, b, i, header.length);
        i += header.length;
        // DIRECTORY
        System.arraycopy(directory, 0, b, i, directory.length);
        i += directory.length;
        // IMPORT
        System.arraycopy(import_, 0, b, i, import_.length);
        i += import_.length;
        // APPLET (opcional)
        if (applet != null) {
            System.arraycopy(applet, 0, b, i, applet.length);
            i += applet.length;
        }
        // CLASS
        System.arraycopy(class_, 0, b, i, class_.length);
        i += class_.length;
        // METHOD
        System.arraycopy(method, 0, b, i, method.length);
        i += method.length;
        // STATIC FIELD
        System.arraycopy(static_field, 0, b, i, static_field.length);
        i += static_field.length;
        // EXPORT (opcional)
        if (export != null) {
            System.arraycopy(export, 0, b, i, export.length);
            i += export.length;
        }
        // CONSTANT POOL
        System.arraycopy(constant_pool, 0, b, i, constant_pool.length);
        i += constant_pool.length;
        // REFERENCE LOCATION
        System.arraycopy(reference_location, 0, b, i,
                reference_location.length);
        i += reference_location.length;
        // DESCRIPTOR
        System.arraycopy(descriptor, 0, b, i, descriptor.length);
        i += descriptor.length;
        return b;
    }

    public byte[] getAid() {
        final int HEADER_AIDLENGTH = 12;
        byte lengthAid = header[HEADER_AIDLENGTH];
        byte[] aid = new byte[lengthAid];
        System.arraycopy(header, HEADER_AIDLENGTH + 1, aid, 0, lengthAid);
        return aid;
    }

    public boolean hasApplet() {
        final int HEADER_FLAG = 9;
        byte headerFlag = header[HEADER_FLAG];
        return ((headerFlag & ACC_APPLET) == ACC_APPLET);
        // return (applet != null);
    }

    public byte[][] getAppletAids() {
        if (hasApplet()) {
            final int INSTALL_METHOD_OFFSET_SIZE = 2;
            final int APPLET_COUNT = 3;
            byte appletCount = applet[APPLET_COUNT];
            byte[][] aids = new byte[appletCount][];
            int i = APPLET_COUNT + 1;
            for (int a = 0; a < appletCount; a++) {
                int aidLength = applet[i];
                aids[a] = new byte[aidLength];
                System.arraycopy(applet, i + 1, aids[a], 0, aidLength);
                i += aidLength + INSTALL_METHOD_OFFSET_SIZE;
            }
            return aids;
        }
        return null;
    }
}
