package logic;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import org.json.JSONArray;

public class Main {

	Scanner reader = new Scanner(System.in);
	String usernameLogged=null;
	
	int rPost = 0;
	
	private FindThread findThread;
	
	public void loginMenu() throws IOException {
		while (true) {
			System.out.println("1. Login");
			System.out.println("2. Registar");
			System.out.print("Escolha: ");

			int choice;

			try {
				choice = Integer.parseInt(reader.next());
			} catch (NumberFormatException e) {
				System.out.println("\nInput invalido!");
				System.out.println("");

				continue;
			}

			switch (choice) {
			case 1: {
				try {
					Login();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

			case 2: {
				try {
					Register();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

			default: {
				System.out.println("\nEscolha nao valida!\n");
				break;
			}
			}
		}
	}
	
	public void Login() throws IOException, NoSuchAlgorithmException {
		
		String username = "";
		String password = "";
				
		System.out.println("Username:");
		reader.nextLine();
	    username = reader.nextLine();
	    
		System.out.println("Password:");
		password = reader.nextLine();
		
		usernameLogged = username;
		
		if (!Login.login(username, password))
			System.out.println("Username/Password Erradas");
		else { 
			System.out.println("Bem Vindo");
			mainMenu();
		}
	
	}
	
	public void Register() throws IOException, NoSuchAlgorithmException {
		
		String username = "";
		String password = "";
				
		System.out.println("Username:");
		reader.nextLine();
	    username = reader.nextLine();
	    
		System.out.println("Password:");
		password = reader.nextLine();
		
		if (!Register.register(username, password))
			System.out.println("Erro no Registo");
		else
			System.out.println("Registado");
	}
	
	public void mainMenu() {
		System.out.println("\n\n ===== BLACKJACK ===== ");
		while (true) {
			System.out.println("1. Jogar numa sala existente");
			System.out.println("2. Ver saldo disponivel");
			System.out.println("3. Depositar dinheiro");
			System.out.println("4. Levantar dinheiro");
			System.out.println("5. Ser avisado quando um amigo entrar numa sala");
			System.out.println("6. Sair\n");

			System.out.print("Escolha: ");

			int choice;

			try {
				choice = Integer.parseInt(reader.next());
			} catch (NumberFormatException e) {
				System.out.println("\nInput invalido!");
				System.out.println("");

				continue;
			}

			switch (choice) {
			case 1: {
				ListRooms();
				break;
			}

			case 2: {
				String response = null;
				try {
					response = Communication.GET("playerService/getMoney?name="+usernameLogged);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Tens " + response + " chips.");
				break;
			}
			case 3: {
				int AddChips = 0;
				String input;
				System.out.println("Quanto pretende depositar: ");
				reader.nextLine();
				input = reader.nextLine();
				AddChips = Integer.parseInt(input);
				
				String[] paramName = { "name", "addChips"};
				String[] paramVal = { usernameLogged, Integer.toString(AddChips) };
				
				try {
					rPost = Communication.POST("playerService/addChips", paramName, paramVal);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
			}
			case 4: {
				int RemoveChips = 0;
				String input;
				System.out.println("Quanto pretende levantar: ");
				reader.nextLine();
				input = reader.nextLine();
				RemoveChips = Integer.parseInt(input);
				
				String[] paramName = { "name", "removeChips"};
				String[] paramVal = { usernameLogged, Integer.toString(RemoveChips) };
				
				try {
					rPost = Communication.POST("playerService/removeChips", paramName, paramVal);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
			}
			
			case 5: {
				String username = "";
						
				System.out.println("Username:");
				reader.nextLine();
			    username = reader.nextLine();
				
				findThread = new FindThread(username);
				findThread.start();
				
				mainMenu();
				
				break;
			}
			
			case 6: {
				System.out.println("\nA fechar...");
				
				int response = 0;
				
				String[] paramName = {"token"};
				String[] paramVal = {Globals.token};
				
				try {
					response = Communication.POST("playerService/logout", paramName, paramVal);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(response == 200){
					System.out.println("Successfully logged out...");
				}
					
				else{
					System.out.println("Error while logging out!");
				}
				
				System.exit(0);
				break;
			}

			default: {
				System.out.println("\nEscolha nao valida!\n");
				break;
			}
			}
		}
	}
	
	public void ListRooms() {
		
		String response = null;
		boolean skip=false;
		try {
			response = Communication.GET("roomService/room");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(response);
	
		JSONArray jArray = new JSONArray(response);
		 for(int i = 0; i < jArray.length(); i++){
			 int idroom = jArray.getJSONObject(i).getInt("id");
			 String nameroom = jArray.getJSONObject(i).getString("name");
			 System.out.println(idroom + " -> " + nameroom); 
		  }
	
		
	    while(!skip) {
			int RoomChoose = 0;
			int temp = 0;
			String input;
			System.out.println("Qual a Room pretende escolher? (0-Exit) : ");
			reader.nextLine();
			input = reader.nextLine();
			RoomChoose = Integer.parseInt(input);
			
			if(RoomChoose == 0) {
				mainMenu();
				skip=true;
			}
			else  { //verificar aqui se contem!!!!!!!!!!!!!!!!!!1
				skip=true;
				
				response=null;
				try {
					response = Communication.GET("roomService/room/"+RoomChoose+"/number");
					temp = Integer.parseInt(response);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("NUM: " + temp);
				
				if(temp >= 6){
					System.out.println("There are too many players in the room already!");
					
				}
				
				else{

				String[] paramName3 = { "name", "idRoom" };
				String[] paramVal3 = {usernameLogged, Integer.toString(RoomChoose) };
				
				try {
					rPost = Communication.POST("roomService/room/player", paramName3 , paramVal3);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
					PlayPlayer p = new PlayPlayer(RoomChoose,usernameLogged);
					Thread pLogic = new Thread(p);
					pLogic.setName(String.valueOf(RoomChoose));
					pLogic.start();
					
					try {
						pLogic.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	    }
	}

	public static void main(String[] args) throws IOException {
		System.out.println(" ===== BLACKJACK ===== ");
		Main Menu = new Main();
		Menu.loginMenu();
	}

}
