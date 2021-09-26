import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.*;

public class DictionaryCommandline {
    /**
     * Khoi tao mot interface map de luu tru cap tu voi key la tu trong tieng anh, value la nghia trong tieng viet
     */
    private Map<String, String> Dic = new TreeMap<String, String>();

    /**
     * Phuong thuc nay co tac dung them mot cap tu moi vao trong map bang cach su dung cau truc du lieu put
     * @param word_target
     * @param word_explain
     */
    public void AddDic(String word_target, String word_explain) {
        Dic.put(word_target, word_explain);
    }

    /**
     * Phuong thuc nay co chuc nang tim kiem tu bang cach su dung get tim kiem key va tra va value
     * @param word_target
     * @return
     */
    public String FindWord(String word_target) {
        String word_explain = Dic.get(word_target);
        return word_explain;
    }

    /**
     * Constructor khoi tao tu dien co san bang cach lay du lieu tu 2 file de truyen vao Dic,
     * Su dung mot file luu tru tu tieng anh va mot file luu tru nghia tieng viet tuong ung theo dung thu tu
     */
    public DictionaryCommandline() {
        try {
            File fileeng = new File("E:\\OOP_intellij\\BTL\\src\\word_eng.txt");
            FileReader fileReader1 = new FileReader(fileeng);
            BufferedReader reader1 = new BufferedReader(fileReader1);
            File filevi = new File("E:\\OOP_intellij\\BTL\\src\\word_vi.txt");
            FileReader fileReader2 = new FileReader(filevi);
            BufferedReader reader2 = new BufferedReader(fileReader2);
            String line1=null;
            String line2=null;
            while(true){
                line1 = reader1.readLine();
                line2 = reader2.readLine();
                if(line1==null||line1==null) break;
                else AddDic(line1, line2);
            }
            reader1.close();
            reader2.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        DictionaryCommandline Dic = new DictionaryCommandline();
        int choose = 0;
        Scanner scan = new Scanner(System.in);
        do{
            System.out.println("0. Bam 0 de ket thuc");
            System.out.println("1. Them danh tu vao tu dien");
            System.out.println("2. Tra cuu tu");
            choose = scan.nextInt();
            scan.nextLine();
            if(choose==1) {
                System.out.println("Nhap vao tu tieng anh: ");
                String word_target = scan.nextLine();
                System.out.println("Nhap vao nghia tieng viet: ");
                String word_explain = scan.nextLine();
                Dic.AddDic(word_target, word_explain);
            } else if (choose==2) {
                System.out.printf("%-10s|%-50s|%-20s", "No", "English", "Vietnamese");
                int i = 0;
                while(true){
                    System.out.print("Hay nhap vao tu can tra cuu, nhap 0 de thoat khoi che do tra cuu: ");
                    String word = scan.nextLine();
                    if(word.equals("0")) break;
                    else {
                        i++;
                        System.out.printf("%-10d|%-50s|%-20s", i, word, Dic.FindWord(word));
                    }
                }
            }
        }while(choose != 0);
    }
}
