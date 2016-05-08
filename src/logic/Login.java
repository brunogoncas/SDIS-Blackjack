package logic;

public class Login {
	
	public static boolean login(String username, String pass){
			
		String response = "";
		
		String[] paramName = { "name", "password"};
		String[] paramVal = { username, pass };
		
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
