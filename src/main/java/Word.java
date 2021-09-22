public class Word {
    private String wordTarget;
    private String wordExplain;

    public Word(String wordTarget, String wordExplain) {
        this.wordExplain = wordExplain;
        this.wordTarget = wordTarget;
    }

    public String getWordTarget() {
        return this.wordTarget;
    }

    public String getWordExplain() {
        return this.wordExplain;
    }
}