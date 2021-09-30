import java.io.*;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary {
    /**
     *Hàm insertFromCommandline() có chức năng nhập liệu.
     * trên màn hình.
     */
    public void insertFromCommandLine() throws IOException {
        String file = "E:\\Java\\Dictonary_Assignment\\src\\main\\java\\output.txt";
        FileWriter fs = new FileWriter(file);
        Scanner scanner = new Scanner(System.in);
        String target = "";
        String explain = "";
        int i = 0;
        do {
            i++;
            System.out.printf("Nhập dữ liệu: \n");
            System.out.printf("%d\n Englis: ", i);
            target = scanner.nextLine();
            System.out.printf("Vietnamese: ");
            explain = scanner.nextLine();
            if(!target.equals("") && !explain.equals("")) {
                Word word = new Word();
                word.setWord_target(target);
                word.setWord_explain(explain);
                arrWorld.add(word);
            }
        } while (!target.equals("") || !explain.equals(""));

        for (Word w : arrWorld) {
            System.out.printf("%20s:%20s\n", w.getWord_target(), w.getWord_explain());
            String s = w.getWord_target() + "  " + w.getWord_explain() + "\n";
            fs.write(s);
        }

        fs.close();
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
        System.out.printf("%-30s%-50s\n","English","Vietnamese");
        System.out.print("Enter in word: ");
        String search_word = scanner.nextLine();
        while(!search_word.equals("")) {
            boolean find = false;
            for (Word w : arrWorld) {
                if (w.getWord_target().substring(0,search_word.length()).equals(search_word)) {
                    System.out.printf("%-30s:%-50s\n", w.getWord_target(), w.getWord_explain());
                }
                continue;
            }
            System.out.print("Enter in word: ");
            search_word = scanner.nextLine();
        }
    }

    /**
     * hàm dictionarySetWord() có chức năng sửa các từ.
     */
    public void dictionarySetWord() throws IOException {
        insertFromFile();
        Scanner scanner = new Scanner(System.in);
        String target = "";
        String explain = "";
        int i = 0;
        boolean setUp = false;

        do {
            setUp = false;
            i++;
            System.out.printf("Sửa dữ liệu: \n");
            System.out.printf("Tìm dữ liệu: \n");
//            System.out.printf("%d\n Englis: ", i);
            dictionaryLookup();
            target = scanner.nextLine();
            System.out.printf("Nhập dữ liệu cần sửa: \n");
            System.out.printf("%d\n Englis: ", i);
            target = scanner.nextLine();
            System.out.printf("Vietnamese: ");
            explain = scanner.nextLine();
            if(!target.equals("") && !explain.equals("")) {
                setUp = true;
                for (Word w : arrWorld) {
                    if (w.getWord_target().equals(target)) {
                        Word word = new Word();
                        word.setWord_target(target);
                        word.setWord_explain(explain);
                        w.setWord(word);
                        System.out.println(w.getWord_target() + ": " + w.getWord_explain());
                    }
                }
            }
            target = "";
            explain = "";
        } while (setUp);

        String file = "E:\\Java\\Dictonary_Assignment\\src\\main\\java\\dictionaries.txt";
        FileWriter fs = new FileWriter(file);

        for (Word w : arrWorld) {
            System.out.printf("%-20s:%-50s\n", w.getWord_target(), w.getWord_explain());
            String s = w.getWord_target() + "  " + w.getWord_explain() + "\n";
            System.out.println(s);
            fs.write(s);
        }

        fs.close();
    }
}