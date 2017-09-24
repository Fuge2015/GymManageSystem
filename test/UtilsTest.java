import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Test for Utils
 * Created by dongfu on 17-9-8.
 */
public class UtilsTest {
    @Test
    public void bookMessageToItem() throws Exception {
        String bm1 = "abcdefghijklmnopqrst1234567890";// 乱码
        String bm2 = "U002 2017-08-01 19:0~22:00 A"; // 19:0
        String bm3 = "U002 2017-08-01 19:00~22:00 A"; // 正确预定
        String bm4 = "U002 2017-08-01 19:00~22:00 A C"; // 正确取消预定
        String bm5 = "U002 2017-08-01 19:00~23:00 A"; // 时间23:00大于22:00

        Calendar date = new GregorianCalendar(2017, 7, 1);
        Item item1 = new Item("U002", date, 19, 22, 'A', false);
        Item item2 = new Item("U002", date, 19, 22, 'A', true);

        Item item3 = Utils.bookMessageToItem(bm1);
        Assert.assertEquals(null, item3);

        Item item4 = Utils.bookMessageToItem(bm2);
        Assert.assertEquals(null, item4);

        Item item5 = Utils.bookMessageToItem(bm3);
        Assert.assertEquals("U002", item5.getUser());
        Assert.assertEquals(2017, item5.getCalendar().get(Calendar.YEAR));
        Assert.assertEquals(7, item5.getCalendar().get(Calendar.MONTH));
        Assert.assertEquals(1, item5.getCalendar().get(Calendar.DATE));
        Assert.assertEquals(19, item5.getStart());
        Assert.assertEquals(22, item5.getEnd());
        Assert.assertEquals(false, item5.isCancel());

        Item item6 = Utils.bookMessageToItem(bm4);
        Assert.assertEquals("U002", item6.getUser());
        Assert.assertEquals(2017, item6.getCalendar().get(Calendar.YEAR));
        Assert.assertEquals(7, item6.getCalendar().get(Calendar.MONTH));
        Assert.assertEquals(1, item6.getCalendar().get(Calendar.DATE));
        Assert.assertEquals(19, item6.getStart());
        Assert.assertEquals(22, item6.getEnd());
        Assert.assertEquals(true, item6.isCancel());

        Item item7 = Utils.bookMessageToItem(bm5);
        Assert.assertEquals(null, item7);
    }

}