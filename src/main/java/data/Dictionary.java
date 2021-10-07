package data;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Dictionary {
    public static final String MAIN_DICTIONARY = "src/main/resources/data/English-Vietnamese.txt";
    public static final String RECENT_WORD = "src/main/resources/data/recent-word.txt";
    ArrayList<Word> arrWord = new ArrayList<Word>(140000);

    /**
     * Các hàm xử lý recentWorld.
     */

    /**
     * Hàm recentWorldAdd(Word w) nhận vào giá trị w nếu phù hợp.
     */
    void recentWordAdd(Word w) {
        boolean check = true;
        for (Word s : arrWord) {
            if (s.getWord_target().equals(w.getWord_target()) && s.getWord_target().length() == w.getWord_target().length()) {
                System.out.println("false");
                check = false;
                break;
            }
        }
        if (check) {
            System.out.println("true");
            if (arrWord.size() < 10) {
                arrWord.add(w);
            } else {
                arrWord.remove(0);
                arrWord.add(w);
            }
        }
    }

    /**
     * Show recentWorld on display.
     */
    void showRecentWorld() {
        for(int i = arrWord.size()-1; i >=0; i--) {
            System.out.print(arrWord.get(i).getWord_target() + " ");
        }
        System.out.println();
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


    public int findIndext(String target) {
        return findIndext(0, arrWord.size()-1, target, arrWord);
    }

    /**
     * search area of string : target.
     */
    private int searchWord(int start, int end, String target) {
        if (end < start) return arrWord.size();
        int mid = start + (end - start)/2;
        boolean check = arrWord.get(mid).getWord_target().startsWith(target);
        int checkSup = target.compareTo(arrWord.get(mid).getWord_target());
        if (check) {
            return mid;
        } else {
            if (checkSup < 0) {
                return searchWord(start, mid - 1, target);
            } else {
                return searchWord(mid + 1, end, target);
            }
        }
    }

    public ArrayList<Word> searchWord(String target) {
        ArrayList<Word> arr = new ArrayList<Word>(1000);
        int id = searchWord(0, arrWord.size() - 1, target);
        if (id == arrWord.size()) return arr;
        int left = id;
        int right = id;
        while (arrWord.get(left - 1).getWord_target().startsWith(target)) {
            left--;
            if (left == 0) {
                break;
            }
        }
        while (arrWord.get(right + 1).getWord_target().startsWith(target)) {
            right++;
            if (right == arrWord.size() - 1) {
                break;
            }
        }
        int count = 0;
        for(int i = left; i <= right; i++) {
            count ++;
            arr.add(arrWord.get(i));
            if (count == 10) {
                break;
            }
        }
        return arr;
    }

    /**
     * Hàm searchDictionary(String target) trả về các từ bắt đầu bằng target.
     */
    public void dictionarySearcher(String target) {
        System.out.printf("%-30s%-50s\n", "English", "Vietnamese");
        ArrayList<Word> arr = searchWord(target);
        if(arr.size() == 1) {
            recentWordAdd(arr.get(0));
        }
        for (Word w : arr) {
            System.out.printf("%-30s:%-50s\n", w.getWord_target(), w.getWord_explain());
        }
    }

    /**
     * các hàm điều khiển dictionary.
     */
    public void setArrWorld(ArrayList<Word> arr) {
        arrWord = arr;
    }

    public ArrayList<Word> getArrWord() {
        return arrWord;
    }

    public void insertFromFile(String file) {
        //  String file = "E:\\Java\\Dictonary_Assignment\\src\\main\\java\\dictionaries.txt";
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
    }

    public void saveToFile(String file) {
        File f = new File(file);
        f.delete();
        boolean newFile;
        try{
            newFile = f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter(file)) {
            for(Word w : arrWord) {
                fw.write(w.getWord_target() + "*" + w.getWord_explain() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

