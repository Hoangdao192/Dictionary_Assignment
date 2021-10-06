package data;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DictionaryCommandLine {

    Dictionary dictionary = new Dictionary();
    Dictionary dictionaryMain = new Dictionary();
    Dictionary dictionarySup = new Dictionary();
    Dictionary dictionaryRecent = new Dictionary();

    public void start() {
        String fileMain = "E:\\Java\\program\\Dictonary_Assignment\\src\\main\\java\\dictionaries.txt";
        String fileSup = "E:\\Java\\program\\Dictonary_Assignment\\src\\main\\java\\Sup.txt";
        String fileRecent = "E:\\Java\\program\\Dictonary_Assignment\\src\\main\\java\\Recent.txt";
        DataFile data = new DataFile();
        dictionaryMain.setArrWorld(data.insertFromFile(fileMain));
        dictionarySup.setArrWorld(data.insertFromFile(fileSup));
        dictionaryRecent.setArrWorld(data.insertFromFile(fileRecent));
        ArrayList<Word> arr = new ArrayList<Word>();
        for(Word w: dictionaryMain.getArrWorld()) {
            arr.add(w);
        }
        for(Word w: dictionarySup.getArrWorld()) {
            arr.add(w);
        }
        Collections.sort(arr);
        dictionary.setArrWorld(arr);
    }

    public void end() {
        String fileMain = "E:\\Java\\program\\Dictonary_Assignment\\src\\main\\java\\dictionaries.txt";
        String fileSup = "E:\\Java\\program\\Dictonary_Assignment\\src\\main\\java\\Sup.txt";
        String fileRecent = "E:\\Java\\program\\Dictonary_Assignment\\src\\main\\java\\Recent.txt";
        DataFile data = new DataFile();
        data.saveToFile(fileMain, dictionaryMain.getArrWorld());
        data.saveToFile(fileSup, dictionarySup.getArrWorld());
        data.saveToFile(fileRecent, dictionaryRecent.getArrWorld());
    }

    /**
     * Hàm showAllWords() có chức năng hiển thị kết quả danh sách dữ liệu từ điển trên màn hình.
     */
    public void showAllWorlds(){
        ArrayList<Word> arrWord = dictionary.arrWord;
        System.out.printf("Number %-20s%-50s\n","English","Vietnamese");
        for(int i = 0; i < arrWord.size(); i++) {
            System.out.printf("%-7d%-20s%-50s\n",i, arrWord.get(i).getWord_target(), arrWord.get(i).getWord_explain());
        }
    }

    public void showNewWorlds(){
        ArrayList<Word> arrWord = dictionarySup.arrWord;
        System.out.printf("Number %-20s%-50s\n","English","Vietnamese");
        for(int i = 0; i < arrWord.size(); i++) {
            System.out.printf("%-7d%-20s%-50s\n",i, arrWord.get(i).getWord_target(), arrWord.get(i).getWord_explain());
        }
    }

    public void dictionaryAdvanced() throws FileNotFoundException {
        showAllWorlds();
        String target = "---";
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Enter world you want: ");
            target = scanner.nextLine();
            if(target.equals("")) {
                break;
            }
            dictionary.dictionarySearcher(target);
        }
    }

    /**
     * hàm dictionarySearcher() có chức năng tìm kiếm các từ.
     */
    public void dictionarySearcher() {
        String target = "---";
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Enter charaters you want: ");
            target = scanner.nextLine();
            if(target.equals("")) {
                break;
            }
            System.out.printf("%-30s%-50s\n", "English", "Vietnamese");
            ArrayList<Word> arr = dictionary.searchWord(target);
            if(arr.size() == 1) {
                dictionaryRecent.recentWordAdd(arr.get(0));
            }
            for (Word w : arr) {
                System.out.printf("%-30s:%-50s\n", w.getWord_target(), w.getWord_explain());
            }
        }
    }

    /**
     *
     */
    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        int num = 0;
        while(true) {
            System.out.printf("Enter the new word (or word you can set) %d: \n", num++);
            System.out.print("English: ");
            String target = scanner.nextLine();
            if (target.equals("")) {
                break;
            }
            System.out.print("Vietnamese: ");
            String explain = scanner.nextLine();
            if (explain.equals("")) {
                break;
            }
            Word w = new Word();
            w.setWord_target(target);
            w.setWord_explain(explain);
            int id = dictionarySup.findIndext(target);
            if (id < dictionarySup.getArrWorld().size()) {
                dictionarySup.getArrWorld().get(id).setWord(w);
            } else {
                dictionarySup.getArrWorld().add(w);
                Collections.sort(dictionarySup.getArrWorld());
            }
            id = dictionary.findIndext(target);
            if(id == dictionary.getArrWorld().size()) {
                dictionary.getArrWorld().add(w);
                Collections.sort(dictionary.getArrWorld());
            }
        }
    }

    /**
     *
     */
    public void showRecentWorld() {
        dictionaryRecent.showRecentWorld();
    }
}