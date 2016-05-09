package logic;

public class Login {
	
	public static boolean login(String username, String pass){
			
		int response = 0;
		
		String[] paramName = { "name", "password"};
		String[] paramVal = { username, pass };
		
		try {
			response = Communication.POST("playerService/login", paramName, paramVal);
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
