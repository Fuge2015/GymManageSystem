import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 用来表示预定信息或者删除预定的信息
 * Created by dongfu on 17-9-8.
 */
public class Item implements Comparable<Item> {
    // 用户
    private final String user;
    // 年月日
    private Calendar calendar = new GregorianCalendar();
    // 几点开始
    private final int start;
    // 几点结束
    private final int end;
    // 预定场地
    private final char field;
    // 是否是取消预订记录
    private final boolean cancel;
    // 此预定或者取消预订带来的收益
    private int profit;

    public Item(String user, Calendar calendar, int start, int end, char field, boolean cancel) {
        this.user = user;
        this.calendar = calendar;
        this.start = start;
        this.end = end;
        this.field = field;
        this.cancel = cancel;
        charge();
    }

    public String getUser() {
        return user;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public char getField() {
        return field;
    }

    public boolean isCancel() {
        return cancel;
    }

    public int getProfit() {
        return profit;
    }

    /**
     * 如果用户、时间和场地相同则认为两条记录相同
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass() == Item.class) {
            Item itemObj = (Item) obj;
            // if user, time and field equal, return true, otherwise return false
            // U002 2017-08-01 19:00~22:00 A
            if (this.user.equals(itemObj.user) &&
                    this.calendar.get(Calendar.YEAR) == itemObj.calendar.get(Calendar.YEAR) &&
                    this.calendar.get(Calendar.MONTH) == itemObj.calendar.get(Calendar.MONTH) &&
                    this.calendar.get(Calendar.DATE) == itemObj.calendar.get(Calendar.DATE) &&
                    this.start == itemObj.start &&
                    this.end == itemObj.end &&
                    this.field == itemObj.field) {
                // TODO: this.calendar.compareTo(calendar) == 0　有问题
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两条预定的时间和场地是否冲突
     */
    public boolean isConflict(Item item) {
        // TODO ：时间和场地冲突，返回true
        return (this.field == item.field &&
                this.calendar.get(Calendar.YEAR) == item.calendar.get(Calendar.YEAR) &&
                this.calendar.get(Calendar.MONTH) == item.calendar.get(Calendar.MONTH) &&
                this.calendar.get(Calendar.DATE) == item.calendar.get(Calendar.DATE) &&
                !(start >= item.end || end <= item.start));
    }

    /**
     * 返回本条记录的hashcode，其中为了防止两条不同的记录返回相同的hashcode，
     * 采用开始时间的31倍加上结束时间
     */
    @Override
    public int hashCode() {
        // 返回每条订单的
        return user.hashCode() + calendar.hashCode() + start * 31 + end + field + (cancel ? 0 : 17);
    }

    /**
     * Item类实现了Comparable接口
     * 若两个Item对象的年月日、开始时间和结束时间相同，则认为相等
     * 若this对象小于 anotherItem对象则返回-1，若相等则返回0，否则返回1
     *
     * @param anotherItem 比较的对象
     * @return this和anotherItem的大小关系
     */
    @Override
    public int compareTo(Item anotherItem) {
        if (this.calendar.get(Calendar.YEAR) == anotherItem.calendar.get(Calendar.YEAR)) {
            if (this.calendar.get(Calendar.MONTH) == anotherItem.calendar.get(Calendar.MONTH)) {
                if (this.calendar.get(Calendar.DATE) == anotherItem.calendar.get(Calendar.DATE)) {
                    if (this.start == anotherItem.start) {
                        if (this.end == anotherItem.end) {
                            return 0;
                        }
                        return this.end - anotherItem.end < 0 ? -1 : 1;
                    }
                    return this.start - anotherItem.start < 0 ? -1 : 1;
                }
                return this.calendar.get(Calendar.DATE) - anotherItem.calendar.get(Calendar.DATE) < 0 ? -1 : 1;
            }
            return this.calendar.get(Calendar.MONTH) - anotherItem.calendar.get(Calendar.MONTH) < 0 ? -1 : 1;
        }
        return this.calendar.get(Calendar.YEAR) - anotherItem.calendar.get(Calendar.YEAR) < 0 ? -1 : 1;
    }

    /**
     * 计算本条预定信息或者取消预定信息带来的收益
     */
    private void charge() {
        //计算该条预定和取消带来的收入
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if (day > 1 && day < 7) {
            //Monday to Friday
//            9:00 ~ 12:00 30元/时
//            12:00 ~ 18:00 50元/时
//            18:00 ~ 20:00 80元/时
//            20:00 ~ 22:00 60元/时
            if (start < 12) {
                if (end < 13) {
                    profit = 30 * (end - start);
                } else if (end < 19) {
                    profit = 30 * (12 - start) + 50 * (end - 12);
                } else if (end < 21) {
                    profit = 30 * (12 - start) + 50 * (18 - 12) + 80 * (end - 18);
                } else {
                    profit = 30 * (12 - start) + 50 * (18 - 12) + 80 * (20 - 18) + 60 * (end - 20);
                }
            } else if (start < 18) {
                if (end < 19) {
                    profit = 50 * (end - start);
                } else if (end < 21) {
                    profit = 50 * (18 - start) + 80 * (end - 18);
                } else {
                    profit = 50 * (18 - start) + 80 * (20 - 18) + 60 * (end - 20);
                }
            } else if (start < 20) {
                if (end < 21) {
                    profit = 80 * (end - start);
                } else {
                    profit = 80 * (20 - start) + 60 * (end - 20);
                }
            } else {
                profit = 60 * (end - start);
            }

            if (cancel) {
                profit /= 2;
            }
        } else {
//            9:00 ~ 12:00 40元/时
//            12:00 ~ 18:00 50元/时
//            18:00 ~ 22:00 60元/时
            if (start < 12) {
                if (end < 13) {
                    profit = 40 * (end - start);
                } else if (end < 19) {
                    profit = 40 * (12 - start) + 50 * (end - 12);
                } else {
                    profit = 40 * (12 - start) + 50 * (18 - 12) + 60 * (end - 18);
                }
            } else if (start < 18) {
                if (end < 19) {
                    profit = 50 * (end - start);
                } else {
                    profit = 50 * (18 - start) + 60 * (end - 18);
                }
            } else {
                profit = 60 * (end - start);
            }

            if (cancel) {
                profit *= 0.25;
            }
        }
    }
}
