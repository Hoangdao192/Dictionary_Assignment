import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Play {
    public static void main(String[] ags) throws FileNotFoundException {
        DictionaryCommandLine dictionary = new DictionaryCommandLine();
        boolean check = true;
        Scanner scanner = new Scanner(System.in);
        while(check) {
            System.out.printf("" +
                    "0. Exit\n" +
                    "1. View dictionary and Find word\n" +
                    "2. Find word\n" +
                    "3. Set word\n");
            int select = 1;
            select = scanner.nextInt();
            switch (select) {
                case 1 :
                    dictionary.dictionaryAdvanced();
                    break;
                case 2:
                    dictionary.dictionarySearcher();
                    break;
                case 3:
                    try {
                        dictionary.dictionarySetWord();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    check = false;
                    break;
            }
        }
    }
}
