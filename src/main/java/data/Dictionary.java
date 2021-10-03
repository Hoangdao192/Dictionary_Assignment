package data;

import java.util.ArrayList;

public class Dictionary {
    ArrayList<Word> arrWord = new ArrayList<Word>(14000);

    /**
     * find indext of string : target.
     */
    private int findIndext(int start, int end, String target) {
        if (end < start) return arrWord.size();
        int mid = start + (end - start)/2;
        int check = target.compareTo(arrWord.get(mid).getWord_target());
        if (check > 0) {
            return findIndext(mid + 1, end, target);
        } else if (check < 0) {
            return findIndext(start, mid - 1, target);
        } else {
            return mid;
        }
    }

    public int findIndext(String target) {
        return findIndext(0, arrWord.size()-1, target);
    }

    /**
     * search area of string : target.
     */
    int searchWord(int start, int end, String target) {
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
        int left = id - 1;
        int right = id + 1;
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
        for(int i = left; i <= right; i++) {
            arr.add(arrWord.get(i));
        }
        return arr;
    }
}
