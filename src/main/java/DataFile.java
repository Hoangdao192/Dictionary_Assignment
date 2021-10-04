import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DataFile {

    /**
     * Scan data from file.
     */
    public ArrayList<Word> insertFromFile(String file) {
        ArrayList<Word> arrWord = new ArrayList<Word>(13000);
//        String file = "E:\\Java\\Dictonary_Assignment\\src\\main\\java\\dictionaries.txt";
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
                arrWord.add(word);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(arrWord);
        return arrWord;
    }

    /**
     * Save data to file.
     */
    public void saveToFile(String file, ArrayList<Word> arrWorld) {

        File f = new File(file);
        f.delete();
        boolean newFile;
        try{
            newFile = f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter(file)) {
            for(Word w : arrWorld) {
                fw.write(w.getWord_target() + "*" + w.getWord_explain() + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
