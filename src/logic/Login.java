package logic;

import java.security.NoSuchAlgorithmException;

public class Login {
	
	public static boolean login(String username, String pass) throws NoSuchAlgorithmException{
			
		String response = "";
		String passSha256 = Communication.sha256(pass);
		String[] paramName = { "name", "password"};
		String[] paramVal = { username, passSha256 };
		
		try {
			response = Communication.POST("playerService/login", paramName, paramVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response.toLowerCase().contains("success"))
			return true;
		else
			return false;
		
	}
	
}
