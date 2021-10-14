package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class RecentWord extends Dictionary{
    final int MAX_WORD = 18;
    public void add(Word word) {
        int i = 0;
        while (i < arrWord.size()) {
            if (arrWord.get(i).getWord_target().equals(word.getWord_target())) {
                arrWord.remove(i);
            }
            else {
                ++i;
            }
        }
        this.arrWord.add(0, word);
        while (this.arrWord.size() > MAX_WORD) {
            this.arrWord.remove(arrWord.size() - 1);
        }
    }

    public void loadFromFile(String file) {
        try (FileInputStream fs = new FileInputStream(file)) {
            Scanner scanner = new Scanner(fs);
            while (scanner.hasNextLine()) {
                Word word = new Word();
                String a = scanner.nextLine();
                String[] arr = a.split("\\*");
                int i = 0;
                for (String s : arr) {
                    if (i == 0) {
                        word.setWord_target(s);
                    } else {
                        word.setWord_explain(s);
                    }
                    i++;
                }
                String[] m = word.getWord_explain().split("/");
                String sound = "";
                if(m.length > 1) {
                    sound = m[1];
                }
                word.setSound(sound);
                arrWord.add(word);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Word getWord(String wordTarget) {
        for (int i = 0; i < arrWord.size(); ++i) {
            if (wordTarget.equals(arrWord.get(i).getWord_target())) {
                return arrWord.get(i);
            }
        }
        return null;
    }
}
