import key.generator.KeyGenerator;

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
        BigInteger[] KeyPairA = generator.generateKeyPair();
        BigInteger y2 = generator.generateY2(KeyPairB[1], KeyPairA[0], 't');
        System.out.println('t');
        System.out.println(y2);
        System.out.println(generator.decrypt(KeyPairA[1], y2, KeyPairB[0]));

        BigInteger test = new BigInteger(HEX_STRING.getHexString(), 16);
        System.out.println(test);
        do {
            input = menu();
            switch (input) {
                case 1 -> {
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
                case 2:

                    , 3, default -> System.out.println("ungültige Auswahl");
            }


        } while (input != 4);
    }

    private static int menu() {
        scanner = new Scanner(System.in);
        System.out.println("Bitte wählen Sie eine Option aus:");
        System.out.print(" 1. Schlüsselgenerierung |");
        System.out.print(" 2. Verschlüsselung |");
        System.out.print(" 3. Entschlüsselung |");
        System.out.println(" 4. schliessen");
        return scanner.nextInt();
    }

}
