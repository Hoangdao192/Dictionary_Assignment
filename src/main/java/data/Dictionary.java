package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Dictionary {
    ArrayList<Word> arrWord = new ArrayList<Word>(140000);
    ArrayList<Word> arrWordSup = new ArrayList<Word>(3000);
    ArrayList<Word> arrWordMain = new ArrayList<Word>(140000);
    ArrayList<Word> recentWorld = new ArrayList<Word>(10);


    /**
     * Các hàm xử lý recentWorld.
     */

    /**
     * Hàm recentWorldAdd(Word w) nhận vào giá trị w nếu phù hợp.
     */
    private void recentWordAdd(Word w) {
        boolean check = true;
        for (Word s : recentWorld) {
            if (s.getWord_target().equals(w.getWord_target()) && s.getWord_target().length() == w.getWord_target().length()) {
                System.out.println("false");
                check = false;
                break;
            }
        }
        if (check) {
            System.out.println("true");
            if (recentWorld.size() < 10) {
                recentWorld.add(w);
            } else {
                recentWorld.remove(0);
                recentWorld.add(w);
            }
        }
    }

    /**
     * Show recentWorld on display.
     */
    void showRecentWorld() {
        for(int i = recentWorld.size()-1; i >=0; i--) {
            System.out.print(recentWorld.get(i).getWord_target() + " ");
        }
    }


    /**
     * find indext of string : target.
     */
    private int findIndext(int start, int end, String target, ArrayList<Word> currentWorld) {
        if (end < start) return currentWorld.size();
        int mid = start + (end - start)/2;
        int check = target.compareTo(currentWorld.get(mid).getWord_target());
        if (check > 0) {
            return findIndext(mid + 1, end, target, currentWorld);
        } else if (check < 0) {
            return findIndext(start, mid - 1, target, currentWorld);
        } else {
            return mid;
        }
    }


    public int findIndext(String target, ArrayList<Word> currentWorld) {
        return findIndext(0, currentWorld.size()-1, target, currentWorld);
    }

    /**
     * search area of string : target.
     */
    int searchWord(int start, int end, String target, ArrayList<Word> currentWorld) {
        if (end < start) return currentWorld.size();
        int mid = start + (end - start)/2;
        boolean check = currentWorld.get(mid).getWord_target().startsWith(target);
        int checkSup = target.compareTo(currentWorld.get(mid).getWord_target());
        if (check) {
            return mid;
        } else {
            if (checkSup < 0) {
                return searchWord(start, mid - 1, target, currentWorld);
            } else {
                return searchWord(mid + 1, end, target, currentWorld);
            }
        }
    }

    public ArrayList<Word> searchWord(String target, ArrayList<Word> currentWorld) {
        ArrayList<Word> arr = new ArrayList<Word>(1000);
        int id = searchWord(0, currentWorld.size() - 1, target, currentWorld);
        if (id == currentWorld.size()) return arr;
        int left = id;
        int right = id;
        while (currentWorld.get(left - 1).getWord_target().startsWith(target)) {
            left--;
            if (left == 0) {
                break;
            }
        }
        while (currentWorld.get(right + 1).getWord_target().startsWith(target)) {
            right++;
            if (right == currentWorld.size() - 1) {
                break;
            }
        }
        int count = 0;
        for(int i = left; i <= right; i++) {
            count ++;
            arr.add(currentWorld.get(i));
            if (count == 10) {
                break;
            }
        }
        return arr;
    }

    /**
     * các hàm điều khiển dictionary.
     */
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
     * Hàm searchDictionary(String target) trả về các từ bắt đầu bằng target.
     */
    public void dictionarySearcher(String target) {
        System.out.printf("%-30s%-50s\n", "English", "Vietnamese");
        ArrayList<Word> arr = searchWord(target, arrWord);
        if(arr.size() == 1) {
            recentWordAdd(arr.get(0));
        }
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
            Word w = new Word();
            w.setWord_target(target);
            w.setWord_explain(explain);
            int id = findIndext(target, arrWordSup);
            if (id < arrWordSup.size()) {
                arrWordSup.get(id).setWord(w);
            } else {
                arrWordSup.add(w);
                arrWord.add(w);
                Collections.sort(arrWordSup);
                Collections.sort(arrWord);
            }

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
}
