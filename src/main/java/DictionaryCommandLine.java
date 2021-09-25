public class DictionaryCommandLine extends DictionaryManagement {
    /**
     * Hàm dictionaryBasic() có chức năng gọi hàm insertFromCommandline() và showAllWords()
     */
    public void dictionnaryBasic(){
        try {
            insertFromCommandLine();
        }
        catch (Exception e) {
            System.out.println(e.getCause().toString());
        }
        showAllWorlds();
    }
}
