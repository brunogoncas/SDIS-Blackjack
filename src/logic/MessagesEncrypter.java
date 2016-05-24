package logic;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

//https://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-plat
public class MessagesEncrypter {
	
	static String secretKey = "XMzDdG4D03CKm2IxIWQw7g==";
	 public static String encrypt(String text) {
         byte[] raw;
         String encryptedString;
         SecretKeySpec skeySpec;
         byte[] encryptText = text.getBytes();
         Cipher cipher;
         try {
             raw = Base64.decodeBase64(secretKey);
             skeySpec = new SecretKeySpec(raw, "AES");
             cipher = Cipher.getInstance("AES");
             cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
             encryptedString = Base64.encodeBase64String(cipher.doFinal(encryptText));
         } 
         catch (Exception e) {
             e.printStackTrace();
             return "Error";
         }
         return encryptedString;
     }

     public static String decrypt(String text) {
         Cipher cipher;
         String encryptedString;
         byte[] encryptText = null;
         byte[] raw;
         SecretKeySpec skeySpec;
         try {
             raw = Base64.decodeBase64(secretKey);
             skeySpec = new SecretKeySpec(raw, "AES");
             encryptText = Base64.decodeBase64(text);
             cipher = Cipher.getInstance("AES");
             cipher.init(Cipher.DECRYPT_MODE, skeySpec);
             encryptedString = new String(cipher.doFinal(encryptText));
         } catch (Exception e) {
             e.printStackTrace();
             return "Error";
         }
         return encryptedString;
     }

	
}
