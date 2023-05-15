import key.generator.KeyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

import static key.generator.HexString.HEX_STRING;

public class main {
    public static Scanner scanner;
    public static int input;


    public static void main(String[] args) {
        KeyGenerator generator = new KeyGenerator();


        BigInteger test = new BigInteger(HEX_STRING.getHexString(), 16);
        System.out.println(test);
        do {
            input = menu();
            switch (input) {
                case 2 -> {
                    BigInteger[] KeyPairB = generator.generateKeyPair();
                    try (FileWriter wr = new FileWriter("sk.txt");
                         FileWriter wr2 = new FileWriter("pk.txt")) {

                        wr.write(KeyPairB[0].toString()); // private
                        wr2.write(KeyPairB[1].toString()); // public
                        System.out.println("öffentlicher und privater Schlüssel erfolgreich gespeichert");

                    } catch (IOException ee) {
                        System.out.println("Fehler beim Speichern der Datei");
                        throw new RuntimeException(ee);
                    }
                }
                case 3 -> {
                    String publicKey = "";
                    Scanner reader;
                    String toEncrypt = "";

                    try {
                        File textFile = new File("pk.txt");
                        reader = new Scanner(textFile);
                        while (reader.hasNextLine()) {
                            publicKey = reader.nextLine();
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("fehler beim Lesen der Datei");
                        throw new RuntimeException(e);
                    }

                    try {
                        File textFile = new File("text.txt");
                        reader = new Scanner(textFile);
                        while (reader.hasNextLine()) {
                            toEncrypt = reader.nextLine();
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("fehler beim Lesen der Datei");
                        throw new RuntimeException(e);
                    }
                    System.out.println(toEncrypt);
                    BigInteger key = new BigInteger(publicKey);


                    try (FileWriter wr = new FileWriter("chiffre.txt")) {
                        for (Character s : toEncrypt.toCharArray()) {
                            BigInteger[] keyPair = generator.generateKeyPair();
                            BigInteger verschlüsselt = generator.generateY2(key, keyPair[0], s);
                            wr.write("(" + keyPair[1].toString() + "," + verschlüsselt.toString() + ");");
                        }
                        System.out.println("erfolgreich verschlüsselt");

                    } catch (IOException ee) {
                        System.out.println("Fehler beim Speichern der Datei");
                        throw new RuntimeException(ee);
                    }


                }
                case 4 -> {
                }
                default -> System.out.println("ungültige Auswahl");
            }


        } while (input != 5);
    }

    private static int menu() {
        scanner = new Scanner(System.in);
        System.out.println("Bitte wählen Sie eine Option aus:");
        System.out.print(" 2. Schlüsselgenerierung |");
        System.out.print(" 3. Verschlüsselung |");
        System.out.print(" 4. Entschlüsselung |");
        System.out.println(" 5. schliessen");
        return scanner.nextInt();
    }

}
