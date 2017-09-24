import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * test for Item.
 * Created by dongfu on 17-9-8.
 */
public class ItemTest {
    @Test
    public void equals() throws Exception {
        String bm1 = "U002 2017-08-01 19:00~22:00 A"; // 正确预定
        String bm2 = "U002 2017-08-01 19:00~22:00 A C"; // 正确取消预定
        Item item1 = Utils.bookMessageToItem(bm1);
        Item item2 = Utils.bookMessageToItem(bm2);
        Assert.assertEquals(item1,item2);
    }

    @Test
    public void isConflict() throws Exception {
        String bm1 = "U002 2017-08-01 19:00~22:00 A"; // 正确预定
        String bm2 = "U002 2017-08-01 18:00~20:00 A"; // 正确预定
        String bm3 = "U002 2017-08-01 20:00~21:00 A"; // 正确预定
        String bm4 = "U002 2017-08-01 17:00~19:00 A"; // 正确预定
        String bm5 = "U002 2017-08-01 20:00~21:00 B"; // 正确预定

        Item item1 = Utils.bookMessageToItem(bm1);
        Item item2 = Utils.bookMessageToItem(bm2);
        Item item3 = Utils.bookMessageToItem(bm3);
        Item item4 = Utils.bookMessageToItem(bm4);
        Item item5 = Utils.bookMessageToItem(bm5);

        // 交叉
        Assert.assertEquals(true, item1.isConflict(item2));
        // 包含
        Assert.assertEquals(true, item1.isConflict(item3));
        // 相邻
        Assert.assertEquals(false, item1.isConflict(item4));
        // 场地不同，时间包含
        Assert.assertEquals(false, item3.isConflict(item5));


    }

    @Test
    public void compareTo() throws Exception {
        String bm1 = "U002 2017-08-01 18:00~20:00 A"; // 正确预定
        String bm2 = "U002 2018-08-01 18:00~20:00 A"; // 正确预定
        String bm3 = "U002 2017-09-01 18:00~20:00 A"; // 正确预定
        String bm4 = "U002 2017-08-02 18:00~20:00 A"; // 正确预定
        String bm5 = "U002 2017-08-01 19:00~20:00 A"; // 正确预定
        String bm6 = "U002 2017-08-01 18:00~21:00 A"; // 正确预定
        String bm7 = "U002 2017-08-01 18:00~20:00 A"; // 正确预定

        Item item1 = Utils.bookMessageToItem(bm1);
        Item item2 = Utils.bookMessageToItem(bm2);
        Item item3 = Utils.bookMessageToItem(bm3);
        Item item4 = Utils.bookMessageToItem(bm4);
        Item item5 = Utils.bookMessageToItem(bm5);
        Item item6 = Utils.bookMessageToItem(bm6);
        Item item7 = Utils.bookMessageToItem(bm7);

        Assert.assertEquals(-1, item1.compareTo(item2));
        Assert.assertEquals(-1, item1.compareTo(item3));
        Assert.assertEquals(-1, item1.compareTo(item4));
        Assert.assertEquals(-1, item1.compareTo(item5));
        Assert.assertEquals(-1, item1.compareTo(item6));
        Assert.assertEquals(0, item1.compareTo(item7));
        Assert.assertEquals(0, item1.compareTo(item1));
    }

}