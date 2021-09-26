import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(ResourcesPath.DICTIONARY_TXT, true));
            writer.write(wordEnglish);
            writer.write("\t");
            writer.write(explainVietnamese);
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Cannot find Dictionary.txt");
        }
    }

    public void insertFromFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            if (line.length() == 0 || !((line.charAt(0) >= 'A' && line.charAt(0) <= 'Z')
                    || (line.charAt(0) >= 'a' && line.charAt(0) <= 'z'))) {
                continue;
            }

            // Tách 2 từ
            String[] arr = line.split("\t");
            String wordTarget = arr[0];
            String wordExplain = arr[1];
            this.dictionary.add(wordTarget, wordExplain);
        }
    }

    public void dictionaryLookup() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập từ: ");
        String lookupWord = sc.nextLine();

        String explainWord = dictionary.getThisWordExplain(lookupWord);
        if (!explainWord.equals("NULL")) {
            System.out.println("Nghĩa tiếng Việt: " + explainWord);
        }
    }

    public void showAllWords() {
        int numberOfWords = this.dictionary.size();

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
