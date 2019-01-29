//import junit.framework.Assert;
import org.junit.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Test {

    @org.junit.Test
    public void testDownload() {
        String result = "{\"table\":\"A\",\"currency\":\"dolar amerykański\",\"code\":\"USD\",\"rates\":[{\"no\":\"009/A/NBP/2019\",\"effectiveDate\":\"2019-01-14\",\"mid\":3.7458},{\"no\":\"010/A/NBP/2019\",\"effectiveDate\":\"2019-01-15\",\"mid\":3.7542},{\"no\":\"011/A/NBP/2019\",\"effectiveDate\":\"2019-01-16\",\"mid\":3.7619},{\"no\":\"012/A/NBP/2019\",\"effectiveDate\":\"2019-01-17\",\"mid\":3.7615},{\"no\":\"013/A/NBP/2019\",\"effectiveDate\":\"2019-01-18\",\"mid\":3.7694}]}\n";
        ReturnItem a = new ReturnItem();
        ReturnItem b = new ReturnItem();
        b.len = 6;
        b.cur = new BigDecimal[b.len];
        b.cur[0] = new BigDecimal(3.7727).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[1] = new BigDecimal(3.7747).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[2] = new BigDecimal(3.7844).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[3] = new BigDecimal(3.7893).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[4] = new BigDecimal(3.7592).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[5] = new BigDecimal(3.7563).setScale(4, RoundingMode.HALF_DOWN);

        Main test = new Main();

        test.type_currency = "USD";
        test.days = 7;
        a = test.downloadData();

        Assert.assertEquals(a.len, b.len);

        int i = 0;

        for (i = 0 ; i < 5 ; i++)
        {
            Assert.assertEquals(a.cur[i], b.cur[i]);
        }
    }

}
