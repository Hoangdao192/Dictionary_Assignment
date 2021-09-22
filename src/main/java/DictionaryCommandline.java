public class DictionaryCommandline {
    private Dictionary dictionary;
    private DictionaryManagement dictionaryManagement;

    public DictionaryCommandline() {
        this.dictionary = new Dictionary();
        this.dictionaryManagement = new DictionaryManagement();
    }

    public void showAllWords() {
        int numberOfWords = dictionary.getDictionarySize();
        for (int i = 0; i < numberOfWords; ++i) {
            Word currentWord = dictionary.get(i);
            System.out.println(currentWord.getWordTarget() + " " + currentWord.getWordExplain() + "\n");
        }
    }
}
