package logic;

public class Register {
	
public static Boolean register(String username, String pass){
					
		int response = 0;
		
		String[] paramName = { "name", "password", "chips" };
		String[] paramVal = { username, pass, "2500" };
		
		try {
			response = Communication.POST("playerService/register", paramName, paramVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(response == 200){
			System.out.println("HELLOOOOO1" + response);
			return true;
		}
			
		else{
			System.out.println("HELLOOOOO2" + response);
			return false;
		}
	}
	
}
