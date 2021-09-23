import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
    ArrayList<Word> words;

    // Khoi tao
    public void readFromFile() throws FileNotFoundException{
        File file = new File("Dictionary.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            if (line.length() == 0 || !((line.charAt(0) >= 'A' && line.charAt(0) <= 'Z')
                    || (line.charAt(0) >= 'a' && line.charAt(0) <= 'z'))) {
                continue;
            }

            // Tach 2 tu
            String[] arr = line.split("\\p{Punct}");
            String wordTarget = arr[0];
            String wordExplain = arr[1];
            Word newWord = new Word(wordTarget, wordExplain);
            this.words.add(newWord);
        }
    }

    public Dictionary() {
        this.words = new ArrayList<Word>();
        try {
            this.readFromFile();
        } catch (FileNotFoundException e) {
            System.out.println(e.getStackTrace().toString());
        }
    }

    public void add(String wordTarget, String wordExplain) {
        Word newWord = new Word(wordTarget, wordExplain);
        this.words.add(newWord);
    }

    public String getThisWordExplain(String wordTarget) {
        for (int i = 0; i < this.words.size(); ++i) {
            Word currentWord = this.words.get(i);
            if (wordTarget.equals(currentWord.getWordTarget())) {
                return currentWord.getWordExplain();
            }
        }

        return "NULL";
    }

    public int getDictionarySize() {
        return this.words.size();
    }

    public Word get(int position) {
        if (position >= this.words.size()) {
            Word error = new Word("NULL", "NULL");
            return error;
        }

        return this.words.get(position);
    }
}
