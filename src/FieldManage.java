import sun.reflect.generics.tree.Tree;

import java.util.*;

/**
 * Created by dongfu on 17-9-8.
 */
public class FieldManage {
    // 用来记录ABCD四个场地的实时预定情况
    List<List<Item>> list = new ArrayList<>();

    // 用来记录取消的预定的情况
    List<List<Item>> cancelList = new ArrayList<>();

    /** 初始化list和cancelList中的四个元素，分别代表四个场地的预定情况和取消预订记录
     */
    public FieldManage() {
        for (int i = 0; i < 4; i++) {
            List<Item> temp1 = new ArrayList<>();
            List<Item> temp2 = new ArrayList<>();
            list.add(temp1);
            cancelList.add(temp2);
        }
    }

    public void manage() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            // Error: the booking is invalid!
            // Success: the booking is accepted!
            // Error: the booking conflicts with existing bookings!
            // Error: the booking being cancelled does not exist!

            String bookMessage = scanner.nextLine();
            if (bookMessage.equals("")) {
                // 如果输入空行，则输出目前为止的统计结果
                show();
                clearSubList();
            } else {
                // 检验新的记录是否时合法的格式，若合法，尝试添加新的预定或者删除旧的预约
                Item item = Utils.bookMessageToItem(bookMessage);
                if (item == null) {
                    System.out.println("Error: the booking is invalid!");
                } else if (item.isCancel()) {
                    deleteItemFromList(item);
                } else {
                    addItemToSet(item);
                }
            }
        }
    }

    /** 每次统计之后，清空list和cancelList的四个元素中的元素
     */
    private void clearSubList() {
        for (int i = 0; i < 4; i++) {
            list.get(i).clear();
            cancelList.get(i).clear();
        }
    }

    /**
     * 打印本次统计结果，格式如下：
     * 收入入汇总
     * ---
     * 场地:A
     * 2017-08-01 18:00~20:00 160元
     * 2017-08-01 19:00~22:00 违约金金金 100元
     * 小计:260元
     *
     * 场地:B
     * 2017-08-02 13:00~17:00 200元
     * 小计:200元
     *
     * 场地:C
     * 小计:0元
     *
     * 场地:D
     * 小计:0元
     * ---
     * 总计:460元
     */
    private void show() {
        System.out.println("收入汇总\n---");
        int field;
        List<Item> tempList;
        List<Item> set;
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        for (char ch = 'A'; ch < 'E'; ch++) {
            field = ch - 'A';
            tempList = cancelList.get(field);
            set = list.get(field);
            Collections.sort(tempList);
            Collections.sort(set);
            int tempProfit = 0;
            for (Item i : tempList) {
                tempProfit += i.getProfit();
            }
            for (Item i : set) {
                tempProfit += i.getProfit();
            }
            sum += tempProfit;

            sb.append("场地:" + ch + "\n");

            while (set.size() > 0 && tempList.size() > 0) {
                if (set.get(0).compareTo(tempList.get(0)) < 0) {
                    stringAppend(set.remove(0), sb);
                } else {
                    stringAppend(tempList.remove(0), sb);
                }
            }
            while (set.size() > 0) {
                stringAppend(set.remove(0), sb);
            }
            while (tempList.size() > 0) {
                stringAppend(tempList.remove(0), sb);
            }

            System.out.print(sb);
            sb = new StringBuilder();
            System.out.println("小计：" + tempProfit + "元");

            if (ch != 'D') {
                System.out.println();
            }

        }
        System.out.println("---\n总计:" + sum + "元");
    }

    /**
     * 在sb后面添加一条要打印的预定信息或者取消信息
     * @param item 预定信息或者取消信息
     * @param sb 将预定信息或者取消信息追加在此
     */
    private void stringAppend(Item item, StringBuilder sb) {
//            2017-08-01 18:00~20:00 160元
//            2017-08-01 19:00~22:00 违约金 100元
        Calendar calendar = item.getCalendar();
        sb.append(calendar.get(Calendar.YEAR) + "-");
        int tempMonth = calendar.get(Calendar.MONTH) + 1;
        int tempDate = calendar.get(Calendar.DATE);
        sb.append((tempMonth > 9 ? "" : "0") + tempMonth + '-' + (tempDate > 9 ? "" : "0") + tempDate + " ");

        sb.append((item.getStart() > 9 ? "" : "0") + item.getStart() + ":00~" + (item.getEnd() > 9 ? "" : "0") + item.getEnd() + ":00 ");
        sb.append((item.isCancel() ? "违约金 " : "") + item.getProfit() + "元\n");
    }

    /**
     * 若list张存在和item具有相同时间和场地的预定记录，则将其删除并在cancelList中添加取消预订记录，否则报错
     * @param item 要删除的预定记录
     * @return 删除成功返回true，否则，返回false
     */
    private boolean deleteItemFromList(Item item) {
        char ch = item.getField();
        int field = ch - 'A';
        List<Item> set = list.get(field);
        List<Item> list = cancelList.get(field);
        for (Item i : set) {
            if (i.equals(item)) {
                System.out.println("Success: the booking is accepted!");
                set.remove(i);
                list.add(item);
                return true;
            }
        }
        System.out.println("Error: the booking being cancelled does not exist!");
        return false;
    }

    /**
     * 若item的预定时间和场地不和其他人冲突，则将预定记录存储在list中，否则报错
     * @param item 想要添加的预定记录
     * @return 添加成功返回true，否则，返回false
     */
    private boolean addItemToSet(Item item) {
        char ch = item.getField();
        int field = ch - 'A';
        List<Item> set = list.get(field);
        for (Item i : set) {
            if (i.isConflict(item)) {
                System.out.println("Error: the booking conflicts with existing bookings!");
                return false;
            }
        }
        set.add(item);
        System.out.println("Success: the booking is accepted!");
        return true;
    }
}
