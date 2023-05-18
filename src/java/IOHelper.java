import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOHelper {

    /**
     * reads a file and returns the content as a String
     * @param file the file to read
     */

    public static String readFile(String file) {
        String text = "";
        try {
            Scanner reader = null;
            File textFile = new File(file);
            reader = new Scanner(textFile);
            while (reader.hasNextLine()) {
                text = reader.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fehler beim Lesen der Datei");
            throw new RuntimeException(e);
        }

        if (text.isEmpty()) {
            System.out.println("Die Textdatei ist leer.");
            return "";
        }
        return text;
    }

    /**
     * writes a String to a file
     *
     * @param text String to write
     * @param file filename to write to
     */
    public static void writeFile(String text, String file) {
        try {
            java.io.FileWriter myWriter = new java.io.FileWriter(file);
            myWriter.write(text);
            myWriter.close();
        } catch (Exception e) {
            System.out.println("Fehler beim Schreiben der Datei");
            throw new RuntimeException(e);
        }
    }
}