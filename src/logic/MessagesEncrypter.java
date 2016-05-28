package logic;

import java.util.Scanner;

//https://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-plat
public class MessagesEncrypter {
	public static void main(String[] args) {
	 while(true){
	 @SuppressWarnings("resource")
	Scanner reader = new Scanner(System.in);  // Reading from System.in
	 System.out.println("Enter a String: ");
	 String piriri = reader.nextLine(); // Scans the next token of the input as an int.
     String enc = MessagesEncrypter.encrypt( piriri,12);
     String dec = MessagesEncrypter.decrypt(enc,12);
     
     System.out.println("ENC: " + enc + "DEC " + dec);
	 }
 }

	public static String decrypt(String enc, int offset) {
        return encrypt(enc, 26-offset);
    }
 
    public static String encrypt(String enc, int offset) {
        offset = offset % 26 + 26;
        StringBuilder encoded = new StringBuilder();
        for (char i : enc.toCharArray()) {
            if (Character.isLetter(i)) {
                if (Character.isUpperCase(i)) {
                    encoded.append((char) ('A' + (i - 'A' + offset) % 26 ));
                } else {
                    encoded.append((char) ('a' + (i - 'a' + offset) % 26 ));
                }
            } else {
                encoded.append(i);
            }
        }
        return encoded.toString();
    }

}
