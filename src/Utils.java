import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by dongfu on 17-9-8.
 */
public class Utils {
    /**
     * 将输入的预定信息或者取消预订信息转换为Item类的实例对象
     * @param string 输入的预定信息或者取消预订信息
     * @return 转换后的Item类的实例对象
     */
    public static Item bookMessageToItem(String string) {
        String[] strings = string.split(" ");
        String user;
        Calendar date = new GregorianCalendar();
        int start;
        int end;
        char field;
        boolean cancel = false;
        Item item = null;

        if (strings.length == 4 || strings.length == 5) {
            // U002 2017-08-01 19:00~22:00 A C
            // U002 2017-08-01 19:00~22:00 A

            // user
            user = strings[0];

            //date
            String[] tempDate = strings[1].split("-");
            if (tempDate.length != 3) {
                return null;
            }
            try {
                date.set(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]) - 1, Integer.parseInt(tempDate[2]));
            } catch (Exception e) {
                return null;
            }

            //time
            String[] tempTime = strings[2].split("~");
            if (tempTime.length != 2) {
                return null;
            }

            start = getTime(tempTime[0]);
            end = getTime(tempTime[1]);
            if (start < 0 || end < 0 || start >= end) {
                return null;
            }

            //field
            field = strings[3].charAt(0);
            if (strings[3].length() != 1 || field < 'A' || field > 'D') {
                return null;
            }

            //cancel
            if (strings.length == 5) {
                if (strings[4].equals("C")) {
                    cancel = true;
                } else {
                    return null;
                }
            }

            item = new Item(user, date, start, end, field, cancel);
        }

        return item;
    }

    /**
     * 给定一个“xx：xx”格式的时间，判断是否为正确的9点到22点的整点格式，若是返回整点数字，否则返回-1
     * @param string “xx：xx”格式的时间
     * @return 整点时间，或者-1
     */
    private static int getTime(String string) {
        int time = 0;
        String[] strings = string.split(":");
        if (strings.length != 2 || !strings[1].equals("00")) {
            return -1;
        }
        try{
            time = Integer.parseInt(strings[0]);
        } catch (Exception e) {
            return -1;
        }
        if (time >=9 && time <=22) {
            return time;
        }
        return -1;
    }
}
