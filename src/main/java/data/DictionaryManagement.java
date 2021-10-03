package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary {

    ArrayList<Word> arrWordSup = new ArrayList<Word>(3000);
    ArrayList<Word> arrWordMain = new ArrayList<Word>(14000);

    public void start() {
        String fileMain = "E:\\Java\\Dictonary_Assignment\\src\\main\\java\\dictionaries.txt";
        String fileSup = "E:\\Java\\Dictonary_Assignment\\src\\main\\java\\output.txt";
        DataFile data = new DataFile();
        arrWordMain = data.insertFromFile(fileMain);
        arrWordSup = data.insertFromFile(fileSup);
        for (Word w : arrWordMain) {
            arrWord.add(w);
        }
        for (Word w : arrWordSup) {
            arrWord.add(w);
        }
        Collections.sort(arrWord);
    }

    /**
     * hàm dictionaryLookup() có chức năng tra cứu từ điển bằng dòng lệnh.
     */
    public void dictionaryLookup() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%-30s%-50s\n", "English", "Vietnamese");
        System.out.print("Enter in word: ");
        String search_word = "";
        search_word = scanner.nextLine();
        while (true) {
            if (search_word.equals("")) {
                break;
            }
            int id = findIndext(search_word);
            if(id == arrWord.size()) {
                System.out.println("The world not fonud! You can set world is a new world!");
                break;
            }
            Word w = arrWord.get(id);
            System.out.printf("%-30s:%-50s\n", w.getWord_target(), w.getWord_explain());
            System.out.print("Enter in word: ");
            search_word = scanner.nextLine();
        }
    }

    /**
     * Hàm searchDictionary(String target) trả về các từ bắt đầu bằng target.
     */
    public void dictionarySearcher(String target) {
        System.out.printf("%-30s%-50s\n", "English", "Vietnamese");
        ArrayList<Word> arr = searchWord(target);
        for (Word w : arr) {
            System.out.printf("%-30s:%-50s\n", w.getWord_target(), w.getWord_explain());
        }
    }

    /**
     * Hàm insertFromCommandline() sửa từ hoặc thêm từ vào kho dữ liệu.
     */
    public void  insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        int num = 0;
        while(true) {
            System.out.printf("Enter the new word (or word you can set) %d: \n", num);
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
            int id = findIndext(target);
            Word w = new Word();
            w.setWord_target(target);
            w.setWord_explain(explain);
            arrWord.get(id).setWord(w);
            id = findIndextSup(target);
            arrWordSup.get(id).setWord(w);
        }
    }

    /**
     * hàm end() lưu lại các từ vựng vào lại file.
     */
    public void end() {
        String fileMain = "E:\\Java\\Dictonary_Assignment\\src\\main\\java\\dictionaries.txt";
        String fileSup = "E:\\Java\\Dictonary_Assignment\\src\\main\\java\\output.txt";
        DataFile data = new DataFile();
        data.saveToFile(fileMain, arrWordMain);
        data.saveToFile(fileSup, arrWordSup);
    }

    /**
     *thiết kế phần tìm kiếm cho arrWordSup.
     */
    private int findIndextSup(int start, int end, String target) {
        if (end < start) return arrWordSup.size();
        int mid = start + (end - start)/2;
        int check = target.compareTo(arrWordSup.get(mid).getWord_target());
        if (check > 0) {
            return findIndextSup(mid + 1, end, target);
        } else if (check < 0) {
            return findIndextSup(start, mid - 1, target);
        } else {
            return mid;
        }
    }

    private int findIndextSup(String target) {
        return findIndextSup(0, arrWordSup.size() - 1, target);
    }
}

