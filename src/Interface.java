import java.util.Scanner;

public class Interface {
    Scanner reader = new Scanner(System.in);

    public Interface() {
        System.out.println(" ===== BLACKJACK LOGIN ===== ");
        loginMenu();
    }

    public void loginMenu() {
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

                    break;
                }

                case 2: {

                    break;
                }

                default: {
                    System.out.println("\nEscolha nao valida!\n");
                    break;
                }
            }
        }
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

}
