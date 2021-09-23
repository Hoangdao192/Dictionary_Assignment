import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary;

    public DictionaryManagement() {
        this.dictionary = new Dictionary();
    }

    public void insertFromCommandline() {
        String wordEnglish;
        String explainVietnamese;

        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập từ tiếng Anh: ");
        wordEnglish = sc.nextLine();
        System.out.print("Nhập nghĩa tiếng Việt: ");
        explainVietnamese = sc.nextLine();

        this.dictionary.add(wordEnglish, explainVietnamese);

        //  Viet ra file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Dictionary.txt", true));
            writer.write(wordEnglish);
            writer.write("\t");
            writer.write(explainVietnamese);
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Cannot find Dictionary.txt");
        }
    }

    public void showAllWords() {
        int numberOfWords = this.dictionary.getDictionarySize();

        // Định dạng phần đề mục
        String headingFormat = String.format("%-7s %c %-30s %c %s", "No", '|', "English", '|', "Vietnamese");
        System.out.println(headingFormat);

        for (int i = 0; i < numberOfWords; ++i) {
            Word currentWord = dictionary.get(i);
            //  Định dạng các dòng
            String printOutFormat = String.format("%-7d %c %-30s %c %s", i, '|', currentWord.getWordTarget(), '|', currentWord.getWordExplain());
            System.out.println(printOutFormat);
        }
    }
}
