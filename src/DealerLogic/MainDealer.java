package DealerLogic;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Observable;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import logic.Communication;

public class MainDealer extends Observable{
	
	Scanner reader = new Scanner(System.in);
	//globals
	private static String name="",NameRoom="";

	static int rPost = 0;
	static String rGet = "";
	
	public void mainMenu() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		while (true) {
			System.out.println("1. Criar uma sala de jogo");
			System.out.println("2. Sair\n");

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
								
				System.out.println("Qual é o seu nome? ");
				reader.nextLine();
				name = reader.nextLine();
			    
				System.out.println("Qual é o nome da sala que pretende criar? ");
				NameRoom = reader.nextLine();
				
				String[] paramName = { "roomname", "dealername", "password"};
				String[] paramVal = { NameRoom ,name, ""};
				
				try {
					rPost = Communication.POST("roomService/room", paramName, paramVal);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				PlayerDealer p = new PlayerDealer(name,NameRoom);
				Thread pLogic = new Thread(p);
				pLogic.setName(String.valueOf(NameRoom));
				pLogic.start();
				
				try {
					pLogic.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			case 2: {
				System.out.println("\nA fechar...");
				System.exit(0);
				break;
			}
			default: {
				System.out.println("\nEscolha não valida!\n");
				break;
			}
			}
		}
	}
	
	public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		System.out.println(" ===================== ");
		System.out.println(" ====== DEALING ====== ");
		System.out.println(" ===================== ");
		MainDealer Menu = new MainDealer();
		Menu.mainMenu();
	}
}
