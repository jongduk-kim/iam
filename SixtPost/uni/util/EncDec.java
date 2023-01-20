package uni.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncDec {
	private static final String SECURITY_KEY = "trionsoft";
	private static SecretKey getKey(String strKey) throws 	InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
	byte[] desKeyData = strKey.getBytes();

	DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

	return keyFactory.generateSecret(desKeySpec);
}

	public static String setEncrypt(String data) throws 	InvalidKeyException, 
															NoSuchAlgorithmException, 
															NoSuchPaddingException, 
															InvalidKeySpecException, 
															UnsupportedEncodingException, 
															IllegalBlockSizeException, 
															BadPaddingException {//FF
		return ((encrypt(data, SECURITY_KEY)).replaceAll("[+]", "^"));
	}

	public static String encrypt(String data, String strKey) throws 	NoSuchAlgorithmException, 
																	NoSuchPaddingException, 
																	InvalidKeyException, 
																	InvalidKeySpecException, 
																	UnsupportedEncodingException, 
																	IllegalBlockSizeException, 
																	BadPaddingException {
		if(data == null || data.length() == 0) {
			return "";
		}
	
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, getKey(strKey));
	
		byte[] inputBytes = data.getBytes("KSC5601");
		byte[] outputBytes = cipher.doFinal(inputBytes);
	
		BASE64Encoder encoder = new BASE64Encoder();
	
		return encoder.encode(outputBytes);
	}

	public static String getDecrypt(String data) throws 	InvalidKeyException, 
															NoSuchAlgorithmException, 
															NoSuchPaddingException, 
															InvalidKeySpecException, 
															IllegalBlockSizeException, 
															BadPaddingException, 
															IOException {
		data = ((data.replaceAll("\\^", "+")).replaceAll("\n", "")).replaceAll("\r", "");
		
		return decrypt(data, SECURITY_KEY);
	}

	public static String decrypt(String data, String strKey) throws 	NoSuchAlgorithmException, 
																		NoSuchPaddingException, 
																		InvalidKeyException, 
																		InvalidKeySpecException, 
																		IOException, 
																		IllegalBlockSizeException, 
																		BadPaddingException {
		if(data == null || data.length() == 0) {
			return "";
		}

		data = data.replaceAll(" ", "");
	
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, getKey(strKey));
	
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] inputBytes = decoder.decodeBuffer(data);
		byte[] outputBytes = cipher.doFinal(inputBytes);
	
		return new String(outputBytes, "KSC5601");
	}   
}
