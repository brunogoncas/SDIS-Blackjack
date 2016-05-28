package logic;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Login {
	
	public static boolean login(String username, String pass) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException{
			
		int response = 0;
		
		String passSha256 = Communication.sha256(pass);
		String token = issueToken(username);
		String[] paramName = { "name", "password", "token"};
		String[] paramVal = { username, passSha256, token};
		
		try {
			response = Communication.POST("playerService/login", paramName, paramVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		if(response == 200){
			System.out.println("HELLOOOOO1" + response);
			Globals.token = token;
			return true;
		}
			
		else{
			System.out.println("HELLOOOOO2" + response);
			return false;
		}

	}
	
	//https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey
    private static String issueToken(String username) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
    	
    	MessagesEncrypter messagesEncrypter = new MessagesEncrypter();
    	
    	String token = null;
    			
		try {
			token = messagesEncrypter.encrypt("blackjack_SDIS_" + username + "_" + new java.util.Date(),12);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		
    	return token;
    }
	
}