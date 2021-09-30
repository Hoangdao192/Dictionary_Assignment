import java.io.FileNotFoundException;

public class DictionaryCommandLine extends DictionaryManagement {

    /**
     * Hàm showAllWords() có chức năng hiển thị kết quả danh sách dữ liệu từ điển trên màn hình.
     */
    public void showAllWorlds(){
        System.out.printf("Number %-20s%-50s\n","English","Vietnamese");
        for(int i = 0; i < arrWorld.size(); i++) {
            System.out.printf("%-7d%-20s%-50s\n",i,arrWorld.get(i).getWord_target(),arrWorld.get(i).getWord_explain());
        }
    }

    /**
     * Hàm dictionaryBasic() có chức năng gọi hàm insertFromCommandline() và showAllWords()
     * hàm dictionaryAdvanced() có chức năng gọi hàm insertFromFile(), showAllWords() và dictionaryLookup()
     */
    public void dictionnaryBasic() {
        try {
            insertFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            insertFromCommandLine();
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
        }
    }

    public void dictionaryAdvanced() throws FileNotFoundException {
        insertFromFile();
        showAllWorlds();
        dictionaryLookup();
    }

    /**
     * hàm dictionarySearcher() có chức năng tìm kiếm các từ.
     */
    public void dictionarySearcher() {
        try {
            insertFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        dictionaryLookup();
    }

}
