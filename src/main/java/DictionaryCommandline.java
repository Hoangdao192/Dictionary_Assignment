import java.util.Scanner;

public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement;

    public DictionaryCommandline() {
        this.dictionaryManagement = new DictionaryManagement();
    }

    public void dictionaryBasic() {
        final int INSERT_WORD = 1;
        final int SHOW_ALL_WORDS = 2;
        final int EXIT = 3;
        int input = 0;
        while (input != EXIT) {
            System.out.println("1. Chèn từ vựng vào từ điển.");
            System.out.println("2. Hiển thị tất cả các từ.");
            System.out.println("3. Thoát.");

            System.out.print("Nhập lệnh: ");
            Scanner sc = new Scanner(System.in);
            input = sc.nextInt();

            System.out.print("\n\n\n\n\n");

            switch (input) {
                case 1: this.dictionaryManagement.insertFromCommandline(); break;
                case 2: this.dictionaryManagement.showAllWords(); break;
                case 3: break;
                default: System.out.println("Không tồn tại lệnh này.");
            }
        }
    }
}
