import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class DictionaryManagement {
    public void insertFromCommandline() {
        String wordEnglish;
        String explainVietnamese;

        Scanner sc = new Scanner(System.in);
        wordEnglish = sc.nextLine();
        explainVietnamese = sc.nextLine();

        //  Viet ra file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Dictionary.txt", true));
            writer.write(wordEnglish);
            writer.write(".");
            writer.write(explainVietnamese);
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Cannot find Dictionary.txt");
        }
    }
}
