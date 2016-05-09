package logic;

import java.security.NoSuchAlgorithmException;

public class Login {
	
	public static boolean login(String username, String pass) throws NoSuchAlgorithmException{
			
		int response = 0;
		
		String passSha256 = Communication.sha256(pass);
		String[] paramName = { "name", "password"};
		String[] paramVal = { username, passSha256 };
		
		try {
			response = Communication.POST("playerService/login", paramName, paramVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(response == 200){
			System.out.println("HELLOOOOO1" + response);
			//String usernameAndPassword = "username" + ":" + "pass";
			//String userCredentials = "Authorization";
			//String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString( usernameAndPassword.getBytes() );
	
			return true;
		}
			
		else{
			System.out.println("HELLOOOOO2" + response);
			return false;
		}

	}
	
}
