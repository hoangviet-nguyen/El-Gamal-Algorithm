import key.generator.KeyGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {
    public static Scanner scanner;
    public static int input;


    public static void main(String[] args) {
        KeyGenerator generator = new KeyGenerator(); //Init der Klasse für unsere Berechnungen

        do {
            input = menu();
            switch (input) {
            case 2 -> { //Schlüsselgenerierung
                BigInteger[] KeyPairB = generator.generateKeyPair();

                IOHelper.writeFile(KeyPairB[0].toString(), "sk.txt");
                IOHelper.writeFile(KeyPairB[1].toString(), "pk.txt");
            }
            case 3 -> { //Verschlüsselung
                String publicKey = IOHelper.readFile("pk.txt");
                String toEncrypt = IOHelper.readFile("text.txt");

                BigInteger key = new BigInteger(publicKey);

                try (FileWriter wr = new FileWriter("chiffre.txt")) {
                    //verschlüsselung des textes
                    for (Character s : toEncrypt.toCharArray()) {
                        BigInteger[] keyPair = generator.generateKeyPair();
                        BigInteger verschlüsselt = generator.generateY2(key, keyPair[0], s);
                        wr.write("(" + keyPair[1].toString() + "," + verschlüsselt.toString() + ");");
                    }
                    wr.flush();
                } catch (IOException ee) {
                    System.out.println("Fehler beim speichern der Datei");
                    throw new RuntimeException(ee);
                }
                System.out.println("erfolgreich verschlüsselt");
            }

            case 4 -> { //Entschlüsselung
                Scanner reader;
                List<String> encrypted;
                String privateKeyString = IOHelper.readFile("sk.txt");
                String raw = IOHelper.readFile("chiffre.txt"); //lesen des verschlüsselten Text
                List<List<String>> characters = new ArrayList<>();

                encrypted = Arrays.stream(raw.substring(1, raw.length() - 1)
                        .split(";")) //splitten der (Y1,Y2);(Y1,Y2); zu separaten (Y1,Y2)
                    .toList();

                for (String values : encrypted) {  //splitten der (Y1,Y2) zu werten Y1 und Y2 , entfernung der Klammern
                    characters.add(Arrays.stream(values.replaceAll("\\(", "").
                        replaceAll("\\)", "").split(",")).toList());


                    ArrayList<Character> ausgabe = new ArrayList<>();
                    BigInteger privateKey = new BigInteger(privateKeyString);

                    for (List<String> values_ : characters) { //entschlüsselung jedes chars
                        char decrypted =
                            generator.decrypt(new BigInteger(values_.get(0)), new BigInteger(values_.get(1)),
                                privateKey);
                        System.out.println(decrypted);
                        ausgabe.add(decrypted);
                    }
                    String output = "";
                    for (Character character : ausgabe) {
                        output += character;
                    }

                    IOHelper.writeFile(output, "text-d.txt");
                    System.out.println("output: " + output);
                    System.out.println("erfolgreich entschlüsselt");
                }
            }
            case 5 -> System.out.println("Auf wiedersehen");

            default -> System.out.println("ungültige Auswahl");
            }
        }
        while (input != 5);

    }

    private static int menu() {
        //menü Konzept aus der ersten Programmieraufgabe übernommen
        scanner = new Scanner(System.in);
        System.out.println("Bitte wählen Sie eine Option aus:");
        System.out.print(" 2. Schlüsselgenerierung |");
        System.out.print(" 3. Verschlüsselung |");
        System.out.print(" 4. Entschlüsselung |");
        System.out.println(" 5. schliessen");
        return scanner.nextInt();
    }
}
