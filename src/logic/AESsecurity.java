package logic;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class AESsecurity {

	/**
	 * gets the AES encryption key. In your actual programs, this should be
	 * safely stored.
	 * 
	 * @return
	 */
	public static SecretKey getSecretEncryptionKey() throws Exception {

		KeyGenerator generator = KeyGenerator.getInstance("AES");

		generator.init(128); // The AES key size in number of bits

		SecretKey secKey = generator.generateKey();

		return secKey;
	}

	/**
	 * Encrypts plainText in AES using the secret key
	 * 
	 * @param plainText
	 * @param secKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptText(String plainText, SecretKey secKey) throws Exception {

		// AES defaults to AES/ECB/PKCS5Padding in Java 7

		Cipher aesCipher = Cipher.getInstance("AES");

		aesCipher.init(Cipher.ENCRYPT_MODE, secKey);

		byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());

		return byteCipherText;

	}

	/**
	 * Decrypts encrypted byte array using the key used for encryption.
	 * 
	 * @param byteCipherText
	 * @param secKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptText(byte[] byteCipherText, SecretKey secKey) throws Exception {

		// AES defaults to AES/ECB/PKCS5Padding in Java 7
		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.DECRYPT_MODE, secKey);

		byte[] bytePlainText = aesCipher.doFinal(byteCipherText);

		return new String(bytePlainText);
	}

	/**
	 * Convert a binary byte array into readable hex form
	 * 
	 * @param hash
	 * @return
	 */
	static String bytesToHex(byte[] hash) {
		return DatatypeConverter.printHexBinary(hash);
	}
	
	public static byte[] encryptMessage(String message) {
	     /*
	    Encrypt and decrypt string
	    https://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-plat
	    */
	    try {
	        String key = "Bar12345Bar12345"; // 128 bit key
	        // Create key and cipher
	        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	        // encrypt the text
	        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	        return cipher.doFinal(message.getBytes());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return null;
	}
	
	public static String dencryptMessage(byte[] encrypted) {
	    try {
	        String key = "Bar12345Bar12345"; // 128 bit key
	        // Create key and cipher
	        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES");

	        // decrypt the text
	        cipher.init(Cipher.DECRYPT_MODE, aesKey);
	        return new String(cipher.doFinal(encrypted));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return null;
	}
	
	//MAIN DISTO
	/*String plainText = "Hello World";
    byte[] cipherText = null;
    String decryptedText = null;
    SecretKey secKey = null;
	try {
		secKey = AESsecurity.getSecretEncryptionKey();
		cipherText = AESsecurity.encryptText(plainText, secKey);
		decryptedText = AESsecurity.decryptText(cipherText, secKey);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   ;

    System.out.println("Original Text:" + plainText);
    System.out.println("AES Key (Hex Form):"+ AESsecurity.bytesToHex(secKey.getEncoded()));
    System.out.println("Encrypted Text (Hex Form):"+ AESsecurity.bytesToHex(cipherText));
    System.out.println("Descrypted Text:"+decryptedText);*/

}
