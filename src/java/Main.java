import key.generator.KeyGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
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

                System.out.println("Schlüssel generiert, sk.txt private key, pk.txt public key");
            }
            case 3 -> { //Verschlüsselung
                String publicKey = IOHelper.readFile("pk.txt");
                String toEncrypt = IOHelper.readFile("text.txt");

                BigInteger key = new BigInteger(publicKey);

                try (FileWriter wr = new FileWriter("chiffre.txt")) {
                    //verschlüsselung des textes
                    for (Character s : toEncrypt.toCharArray()) {
                        BigInteger[] keyPair = generator.generateKeyPair();
                        BigInteger encrypted = generator.generateY2(key, keyPair[0], s);
                        wr.write("(" + keyPair[1].toString() + "," + encrypted.toString() + ");");
                    }
                    wr.flush();
                } catch (IOException ee) {
                    System.out.println("Fehler beim speichern der Datei");
                    throw new RuntimeException(ee);
                }
                System.out.println("erfolgreich verschlüsselt");
            }

            case 4 -> { //Entschlüsselung
                List<String> encrypted;
                String privateKeyString = IOHelper.readFile("sk.txt");
                String raw = IOHelper.readFile("chiffre.txt"); //lesen des verschlüsselten Text
                List<List<String>> characters = new ArrayList<>();


                //splitten der (Y1,Y2);(Y1,Y2); zu separaten (Y1,Y2)
                encrypted = Arrays.stream(raw.substring(1, raw.length() - 1)
                        .split(";"))
                    .toList();

                ArrayList<Character> ausgabe = new ArrayList<>();
                //splitten der (Y1,Y2) zu werten Y1 und Y2 , entfernung der Klammern
                for (String values : encrypted) {
                    characters.add(Arrays.stream(values.replaceAll("\\(", "").
                        replaceAll("\\)", "").split(",")).toList());
                }

                BigInteger privateKey = new BigInteger(privateKeyString);

                //entschlüsselung jedes chars
                for (List<String> val : characters) {
                    char decrypted =
                        generator.decrypt(new BigInteger(val.get(0)), new BigInteger(val.get(1)),
                            privateKey);
                    //System.out.println(decrypted);
                    ausgabe.add(decrypted);
                }

                StringBuilder output = new StringBuilder();
                for (Character character : ausgabe) {
                    output.append(character);
                }

                IOHelper.writeFile(output.toString(), "text-d.txt");
                System.out.println("Output: " + output);
                System.out.println("Erfolgreich entschlüsselt");
            }
            case 5 -> System.out.println("Auf wiedersehen");

            default -> System.out.println("Ungültige Auswahl");
            }
        }
        while (input != 5);

    }

    private static int menu() {
        //menü Konzept aus der ersten Programmieraufgabe übernommen
        scanner = new Scanner(System.in);
        System.out.print("\n");
        System.out.println("Bitte wählen Sie eine Option aus:");
        System.out.print(" 2. Schlüsselgenerierung |");
        System.out.print(" 3. Verschlüsselung |");
        System.out.print(" 4. Entschlüsselung |");
        System.out.println(" 5. schliessen");
        return scanner.nextInt();
    }
}
