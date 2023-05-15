import key.generator.KeyGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

import static key.generator.HexString.HEX_STRING;

public class main {
    public static Scanner scanner;
    public static int input;


    public static void main(String[] args) {
        KeyGenerator generator = new KeyGenerator();

        BigInteger test = new BigInteger(HEX_STRING.getHexString(),16);
        System.out.println(test);
        do {
            input = menu();
            switch(input){
                case 1:

                    BigInteger[] array = generator.generateY1();

                    try (FileWriter wr = new FileWriter("pk.txt");
                         FileWriter wr2 = new FileWriter("sk.txt")) {
                        wr.write(array[0].toString());
                        wr2.write(array[1].toString());
                        System.out.println("öffentlicher und privater Schlüssel erfolgreich gespeichert");
                    } catch (IOException ee) {
                        System.out.println("Fehler beim Speichern der Datei");
                        throw new RuntimeException(ee);
                    }
                    break;

                case 2:


                case 3:

                default:
                    System.out.println("ungültige Auswahl");

            }



        } while (input!=4);


        //}

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
