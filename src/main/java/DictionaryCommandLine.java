import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandLine {

    DictionaryManagement dictionaryManagement = new DictionaryManagement();

    public void start() {
        dictionaryManagement.start();
    }

    public void end() {
        dictionaryManagement.end();
    }

    /**
     * Hàm showAllWords() có chức năng hiển thị kết quả danh sách dữ liệu từ điển trên màn hình.
     */
    public void showAllWorlds(){
        ArrayList<Word> arrWord = dictionaryManagement.arrWord;
        System.out.printf("Number %-20s%-50s\n","English","Vietnamese");
        for(int i = 0; i < arrWord.size(); i++) {
            System.out.printf("%-7d%-20s%-50s\n",i, arrWord.get(i).getWord_target(), arrWord.get(i).getWord_explain());
        }
    }


    public void dictionaryAdvanced() throws FileNotFoundException {
        showAllWorlds();
        dictionaryManagement.dictionaryLookup();
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
            dictionaryManagement.dictionarySearcher(target);
        }
    }

    /**
     *
     */
    public void insertFromCommandline() {
        dictionaryManagement.insertFromCommandline();
    }
}