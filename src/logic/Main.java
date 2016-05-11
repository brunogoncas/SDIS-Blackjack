package logic;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {

	Scanner reader = new Scanner(System.in);
	String usernameLogged=null;
	
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
			System.out.println("5. Sair\n");

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
				
				int response = 0;
				try {
					response = Communication.POST("playerService/addChips", paramName, paramVal);
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
				
				int response = 0;
				try {
					response = Communication.POST("playerService/removeChips", paramName, paramVal);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
			}
			case 5: {
				System.out.println("\nA fechar...");
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
		
		final JSONObject obj = new JSONObject(response);
	    final JSONArray geodata = obj.getJSONArray("geodata");
	    final int n = geodata.length();
	    for (int i = 0; i < n; ++i) {
	      final JSONObject room = geodata.getJSONObject(i);
	      System.out.println(room.getInt("id") + " - " + room.getString("name"));
	    }
	    
	    while(!skip) {
			int RoomChoose = 0;
			String input;
			System.out.println("Qual a Room pretende escolher? (0-Exit) : ");
			reader.nextLine();
			input = reader.nextLine();
			RoomChoose = Integer.parseInt(input);
			
			if(RoomChoose == 0) {
				mainMenu();
				skip=true;
			}
			else if ( geodata.toString().contains("\"id\":\""+RoomChoose+"\"")) {
				skip=true;
				PlayPlayer.Player();
			}
			else{
				System.out.println("Escolha uma sala v�lida! ");
			}
	    }
	}

	public static void main(String[] args) throws IOException {
		System.out.println(" ===== BLACKJACK ===== ");
		Main Menu = new Main();
		Menu.loginMenu();
	}

}
