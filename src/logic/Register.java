package logic;

public class Register {
	
public static Boolean register(String username, String pass){
					
		String response = "";
		
		String[] paramName = { "name", "password", "chips" };
		String[] paramVal = { username, pass, "2500" };
		
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
