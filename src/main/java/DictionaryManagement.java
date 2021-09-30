import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary {
    /**
     * Hàm showAllWords() có chức năng hiển thị kết quả danh sách dữ liệu từ điển
     * trên màn hình.
     */
    public void showAllWorlds(){
        System.out.printf("Number%20s%20s\n","English","Vietnamese");
        for(int i = 0; i < arrWorld.size(); i++) {
            System.out.printf("%5d%20s%20s\n",i,arrWorld.get(i).getWord_target(),arrWorld.get(i).getWord_explain());
        }
    }

    /**
     * Hàm insertFromFile() nhập dữ liệu từ điển từ tệp dictionaries.txt.
     */
    public void insertFromFile() throws FileNotFoundException {
        String file = "E:\\Java\\Dictonary_Assignment\\src\\main\\java\\dictionaries.txt";
        FileInputStream fs = new FileInputStream(file);
        Scanner scanner = new Scanner(fs);
        while (scanner.hasNextLine()) {
            Word word = new Word();
            String a = scanner.next();
            String b = scanner.nextLine();
            word.setWord_target(a);
            word.setWord_explain(b);
            arrWorld.add(word);
        }
        scanner.close();
    }

    /**
     * hàm dictionaryLookup() có chức năng tra cứu từ điển bằng dòng lệnh.
     */
    public void dictionaryLookup() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%20s%20s\n","English","Vietnamese");
        System.out.print("Enter in word: ");
        while(scanner.hasNext()) {
            String search_word = scanner.nextLine();
            for (Word w : arrWorld) {
                if (w.getWord_target().equals(search_word)) {
                    System.out.printf("%20s:%20s\n", w.getWord_target(), w.getWord_explain());
                }
            }
            System.out.print("Enter in word: ");
        }
    }
}
