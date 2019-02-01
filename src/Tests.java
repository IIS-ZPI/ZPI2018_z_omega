import org.junit.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Tests {

    @org.junit.Test
    public void testDownloadforWeek() {
    	// test jednostkowy + wydajnioœciowy dla tygodnia + test mediany

    	ReturnItem a = new ReturnItem();
        ReturnItem b = new ReturnItem();
        b.len = 5;
        b.cur = new BigDecimal[b.len];
        b.cur[0] = new BigDecimal(3.7243).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[1] = new BigDecimal(3.7271).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[2] = new BigDecimal(3.7563).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[3] = new BigDecimal(3.7577).setScale(4, RoundingMode.HALF_DOWN);
        b.cur[4] = new BigDecimal(3.7592).setScale(4, RoundingMode.HALF_DOWN);


        Main test = new Main();

        test.type_currency = "USD";
        test.days = 5;
    	long startTimeDay = System.currentTimeMillis() ;

    	
        a = test.downloadData();
    	long endTimeDay = System.currentTimeMillis() - startTimeDay ;

    	Assert.assertTrue(endTimeDay <=2000);  // 2000 milisekund= 2 sekundy

        Assert.assertEquals(a.len, b.len);

        int i = 0;

        for (i = 0 ; i <5 ; i++)
        {
            Assert.assertEquals(a.cur[i], b.cur[i]);
        }
        BigDecimal mediana = new BigDecimal(3.7563).setScale(4, RoundingMode.HALF_DOWN);;
        BigDecimal mediana_org = test.med;

        Assert.assertEquals(mediana, mediana_org);
    }
      
        @org.junit.Test
        public void testDownloadfortTerm() {
        	//test wydajniosciowy dla kwartalu

        	ReturnItem a1 = new ReturnItem();
         
           
            Main test1 = new Main();

            test1.type_currency = "USD";
            test1.days = 90;
            long startTimeDay = System.currentTimeMillis() ;

        	
            a1 = test1.downloadData();
        	long endTimeDay = System.currentTimeMillis() - startTimeDay ;

            Assert.assertTrue(endTimeDay <=600);  // 600 milisekund = 0.6 sekundy 

           

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

            Assert.assertTrue(endTimeDay <=800);  // 800 milisekund = 0.8 sekundy 

         
        }
        @org.junit.Test
        public void testPerormanceforTwoWeeks() {
        	// test wydajnoœciowy dla 2 tygodni 
        	ReturnItem a1 = new ReturnItem();
           
            Main test2 = new Main();

            test2.type_currency = "JPY";
            test2.days = 10;
            long startTimeDay = System.currentTimeMillis() ;

            a1 = test2.downloadData();
        	long endTimeDay = System.currentTimeMillis() - startTimeDay ;

            Assert.assertTrue(endTimeDay <=800);  // 800 milisekund = 0.8 sekundy 

         
        }
        @org.junit.Test
        public void testPerormanceforHalfaYear() {
        	// test wydajnoœciowy dla po³ roku 
        	ReturnItem a1 = new ReturnItem();
           
            Main test2 = new Main();

            test2.type_currency = "GBP";
            test2.days = 182;
            long startTimeDay = System.currentTimeMillis() ;

            a1 = test2.downloadData();
        	long endTimeDay = System.currentTimeMillis() - startTimeDay ;

            Assert.assertTrue(endTimeDay <=800);  // 800 milisekund = 0.8 sekundy 

         
        }
   
        }
        
    
    
   