package logic;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	Scanner reader = new Scanner(System.in);
	
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
				Login();
				break;
			}

			case 2: {
				Register();
				break;
			}

			default: {
				System.out.println("\nEscolha nao valida!\n");
				break;
			}
			}
		}
	}
	
	public void Login() throws IOException {
		
		String username = "";
		String password = "";
				
		System.out.println("Username:");
		reader.nextLine();
	    username = reader.nextLine();
	    
		System.out.println("Password:");
		password = reader.nextLine();
				
		if (!Login.login(username, password))
			System.out.println("Username/Password Erradas");
		else { 
			System.out.println("Bem Vindo");
			mainMenu();
		}
	
	}
	
	public void Register() throws IOException {
		
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
			System.out.println("2. Criar uma nova sala para jogar");
			System.out.println("3. Ver saldo disponivel");
			System.out.println("4. Sair\n");

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

				break;
			}

			case 2: {

				break;
			}

			case 3: {

				break;
			}

			case 4: {
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

	public static void main(String[] args) throws IOException {
		System.out.println(" ===== BLACKJACK ===== ");
		Main Menu = new Main();
		Menu.loginMenu();
	}

}
