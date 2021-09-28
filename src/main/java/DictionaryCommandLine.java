import java.io.FileNotFoundException;

public class DictionaryCommandLine extends DictionaryManagement {
    /**
     * hàm dictionaryAdvanced() có chức năng gọi hàm insertFromFile(), showAllWords() và dictionaryLookup()
     */
    public void dictionaryAdvanced() throws FileNotFoundException {
        insertFromFile();
        showAllWorlds();
        dictionaryLookup();
    }
}
