package logic;

import java.security.NoSuchAlgorithmException;

public class Register {
	
public static Boolean register(String username, String pass) throws NoSuchAlgorithmException{
					
		String response = "";
		
		String passSha256 = Communication.sha256(pass);
		String[] paramName = { "name", "password", "chips" };
		String[] paramVal = { username, passSha256, "2500" };
		
		try {
			response = Communication.POST("playerService/register", paramName, paramVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response.toLowerCase().contains("success"))
			return true;
		else
			return false;
	}
	
}
