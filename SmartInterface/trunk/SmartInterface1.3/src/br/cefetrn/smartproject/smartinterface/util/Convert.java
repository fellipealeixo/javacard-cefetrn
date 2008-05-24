package br.cefetrn.smartproject.smartinterface.util;


public class Convert {
    public static byte[] fromHexaToByteArray(String hexadecimal) {
        String[] octets = hexadecimal.split(":");
        byte[] result = new byte[octets.length];
        for (int i = 0; i < octets.length; i++) {
            result[i] = (byte) Integer.parseInt(octets[i], 16);
        }
        return result;
    }
    
    public static String fromByteArrayToHexa(byte[] array) {
    	return fromByteArrayToHexa(array, 0,
    			((array == null) ? 0 : (array.length)));
    }
    
	public static String fromByteArrayToHexa(byte[] array, int from, int to) {
		if (array == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(to - from);
    	for (int i = from; i < to; i++) {
    		String hexa = Integer.toHexString(array[i] & 0xFF).toUpperCase();
    		if (hexa.length() == 1) {
    			sb.append('0');
    		}
    		sb.append(hexa);
    		if (i < to - 1) {
    			sb.append(":");
    		}
    	}
    	return sb.toString();
    }
}
