import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Created by dongfu on 17-9-8.
 */
public class FieldManageSystem {
    public static void main(String... args) {
        Calendar calendar = new GregorianCalendar();
//        Calendar c1 = new GregorianCalendar();
//        c1.set(2017,8,8);
//        calendar.set(2017,8,8);
//        System.out.println(calendar.get(Calendar.YEAR) == 2017);
//        System.out.println(c1.compareTo(calendar) == 0);
//        System.out.println(calendar.getTime());
//        System.out.println(calendar.get(Calendar.DATE));
//
//        ArrayList<String> list = new ArrayList<>();
//        String str = "hello";
//        String b = str;
//        list.add(str);
//        list.add(b);
//        list.size();
//        Scanner scanner = new Scanner(System.in);
//        if (scanner.nextLine().equals("")) {
//            System.out.println("hehehe");
//        }

//        for (int i = 0; i< 5; i++) {
//            String str = new String(i + "");
//            System.out.println(str);
//        }

        FieldManage fieldManage = new FieldManage();
        fieldManage.manage();
    }


}
