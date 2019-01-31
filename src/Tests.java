import org.junit.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Tests {

    @org.junit.Test
    public void testDownloadforWeek() {
    	// test jednostkowy + wydajnioœciowy dla tygodnia

    	ReturnItem a = new ReturnItem();
        ReturnItem b = new ReturnItem();
        b.len = 5;
        b.cur = new BigDecimal[b.len];
        b.cur[1] = new BigDecimal(3.7893).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[0] = new BigDecimal(3.7844).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[2] = new BigDecimal(3.7592).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[3] = new BigDecimal(3.7563).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[4] = new BigDecimal(3.7577).setScale(4, RoundingMode.HALF_DOWN);
        Main test = new Main();

        test.type_currency = "USD";
        test.days = 7;
    	long startTimeDay = System.currentTimeMillis() ;

    	
        a = test.downloadData();
    	long endTimeDay = System.currentTimeMillis() - startTimeDay ;

    	Assert.assertTrue(endTimeDay <=600);  // 600 milisekund = 0.6 sekundy 

        Assert.assertEquals(a.len, b.len);

        int i = 0;

        for (i = 0 ; i <5 ; i++)
        {
            Assert.assertEquals(a.cur[i], b.cur[i]);
        }
    }
      
        @org.junit.Test
        public void testDownloadforDay() {
        	//test jednostkowy + wydajnoœciowy dla dnia

            ReturnItem a1 = new ReturnItem();
            ReturnItem b1 = new ReturnItem();
            b1.len = 1;
            b1.cur = new BigDecimal[b1.len];
            b1.cur[0] = new BigDecimal(4.2952).setScale(4, RoundingMode.HALF_DOWN);
           
            Main test1 = new Main();

            test1.type_currency = "EUR";
            test1.days = 1;
            long startTimeDay = System.currentTimeMillis() ;

        	
            a1 = test1.downloadData();
        	long endTimeDay = System.currentTimeMillis() - startTimeDay ;

            Assert.assertTrue(endTimeDay <=600);  // 600 milisekund = 0.6 sekundy 

            Assert.assertEquals(a1.len, b1.len);
            Assert.assertEquals(a1.cur[0], b1.cur[0]);

        }
        @org.junit.Test
        public void testPerormanceforYear() {
        	// test wydajnoœciowy dla roku
        	ReturnItem a1 = new ReturnItem();
           
            Main test2 = new Main();

            test2.type_currency = "EUR";
            test2.days = 365;
            long startTimeDay = System.currentTimeMillis() ;

            a1 = test2.downloadData();
        	long endTimeDay = System.currentTimeMillis() - startTimeDay ;

            Assert.assertTrue(endTimeDay <=600);  // 600 milisekund = 0.6 sekundy 

         
        }
        
    }
    
   