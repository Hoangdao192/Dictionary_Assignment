import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary {
    /**
     *Hàm insertFromCommandline() có chức năng nhập liệu.
     * Hàm showAllWords() có chức năng hiển thị kết quả danh sách dữ liệu từ điển
     * trên màn hình.
     */
    public void insertFromCommandLine() throws FileNotFoundException {
        String file_input = "E:\\Java\\Dictonary_Assignment\\src\\main\\java\\input.txt";
        String file_output = "E:\\Java\\Dictonary_Assignment\\src\\main\\java\\output.txt";
        FileInputStream input = new FileInputStream(file_input);
        Scanner scan_in = new Scanner(input);
        FileInputStream output = new FileInputStream(file_output);
        Scanner scan_out = new Scanner(output);
        while (scan_in.hasNext()) {
            Word word = new Word();
            String a = scan_in.nextLine();
            String b = scan_out.nextLine();
            word.setWord_target(a);
            word.setWord_explain(b);
            arrWorld.add(word);
        }
        scan_in.close();
        scan_out.close();
    }

    public void showAllWorlds(){
        System.out.printf("Number%20s%20s\n","English","Vietnamese");
        for(int i = 0; i < arrWorld.size(); i++) {
            System.out.printf("%5d%20s%20s\n",i,arrWorld.get(i).getWord_target(),arrWorld.get(i).getWord_explain());
        }
    }
}
