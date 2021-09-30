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
        String search_word = "";
        search_word = scanner.nextLine();
        while(true) {
            if (search_word.equals("")) {
                break;
            }
            for (int i = 0; i < arrWorld.size(); i++) {
                Word w = arrWorld.get(i);
                if(w.getWord_target().length() < search_word.length()) {
                    continue;
                }
                if (w.getWord_target().substring(0,search_word.length()).equals(search_word)) {
                    System.out.printf("%-30s:%-50s\n", w.getWord_target(), w.getWord_explain());
                }
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

        do {
            i++;
            System.out.printf("Sửa dữ liệu: \n");
            System.out.printf("Tìm phạm vi dữ liệu (từ khóa): \n");
            dictionaryLookup();
            System.out.printf("Nhập dữ liệu cần sửa: \n");
            System.out.printf("%d\n English: ", i);
            String targetF = scanner.nextLine();
            if (targetF.equals("")) {
                break;
            }

            System.out.printf("Nhập dữ liệu mới: \n");
            System.out.printf("%d\n English: ", i);
            target = scanner.nextLine();
            if (target.equals("")) {
                break;
            }

            System.out.printf("Vietnamese: ");
            explain = scanner.nextLine();
            if (explain.equals("")) {
                break;
            }

            int num = 0;
            boolean checkSet = false;
            for (Word w : arrWorld) {
                num++;
                if (w.getWord_target().equals(targetF)) {
                    checkSet = true;
                    Word word = new Word();
                    word.setWord_target(target);
                    word.setWord_explain(explain);
                    w.setWord(word);
                    System.out.println(w.getWord_target() + ": " + w.getWord_explain());
                }
                if (num == arrWorld.size() && !checkSet) {
                    System.out.println("The world is invalid");
                }
            }
            target = "";
            explain = "";
        } while (true);

        String file = "E:\\Java\\Dictonary_Assignment\\src\\main\\java\\dictionaries.txt";
        FileWriter fs = new FileWriter(file);

        for (Word w : arrWorld) {
            System.out.printf("%-20s:%-50s\n", w.getWord_target(), w.getWord_explain());
            String s = w.getWord_target() + "  " + w.getWord_explain() + "\n";
            fs.write(s);
        }

        fs.close();
    }
}