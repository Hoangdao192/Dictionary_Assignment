import java.util.Scanner;

public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement;

    public DictionaryCommandline() {
        this.dictionaryManagement = new DictionaryManagement();
    }

    public void dictionaryBasic() {
        final int INSERT_WORD = 1;
        final int SHOW_ALL_WORDS = 3;
        final int LOAD_FROM_FILE = 2;
        final int EXIT = 4;

        int input = 0;
        while (input != EXIT) {
            System.out.print("\n\n\n\n\n");

            System.out.println(INSERT_WORD + ". Chèn từ vựng vào từ điển.");
            System.out.println(LOAD_FROM_FILE + ". Nhập từ điển từ file.");
            System.out.println(SHOW_ALL_WORDS + ". Hiển thị tất cả các từ.");
            System.out.println(EXIT + ". Thoát");

            System.out.print("Nhập lệnh: ");
            Scanner sc = new Scanner(System.in);
            input = sc.nextInt();

            switch (input) {
                case INSERT_WORD: this.dictionaryManagement.insertFromCommandline(); break;
                case LOAD_FROM_FILE: {
                    try {
                        this.dictionaryManagement.insertFromFile(ResourcesPath.DICTIONARY_TXT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } break;
                case SHOW_ALL_WORDS: this.dictionaryManagement.showAllWords(); break;
                case EXIT: break;
                default: System.out.println("Không tồn tại lệnh này.");
            }
        }
    }

    public void dictionaryAdvanced() {
        final int INSERT_WORD = 1;
        final int SHOW_ALL_WORDS = 3;
        final int LOAD_FROM_FILE = 2;
        final int WORD_LOOKUP = 4;
        final int EXIT = 5;

        int input = 0;
        while (input != EXIT) {
            System.out.print("\n\n\n\n\n");

            System.out.println(INSERT_WORD + ". Chèn từ vựng vào từ điển.");
            System.out.println(LOAD_FROM_FILE + ". Nhập từ điển từ file.");
            System.out.println(SHOW_ALL_WORDS + ". Hiển thị tất cả các từ.");
            System.out.println(WORD_LOOKUP + ". Tra từ.");
            System.out.println(EXIT + ". Thoát");

            System.out.print("Nhập lệnh: ");
            Scanner sc = new Scanner(System.in);
            input = sc.nextInt();

            switch (input) {
                case INSERT_WORD: this.dictionaryManagement.insertFromCommandline(); break;
                case LOAD_FROM_FILE: {
                    try {
                        this.dictionaryManagement.insertFromFile(ResourcesPath.DICTIONARY_TXT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } break;
                case SHOW_ALL_WORDS: this.dictionaryManagement.showAllWords(); break;
                case WORD_LOOKUP: this.dictionaryManagement.dictionaryLookup(); break;
                case EXIT: break;
                default: System.out.println("Không tồn tại lệnh này.");
            }
        }
    }
}
