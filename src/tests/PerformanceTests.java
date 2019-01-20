package tests;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class downloadDataTest {

//TODO
    // test dla pobierania z api
    @Test
    void testdownloadData() {
    	 downloadData download = new downloadData("http://api.nbp.pl/api/exchangerates/rates/a/gbp/2012-01-02/");
         Map<String, Float> map = download.getValue();
         //System.out.println(map.get("2012-01-02"));
         double x=  5.3480;  //sprawdzona na sztywno
         //System.out.println((float)x);
         assertEquals((float)x,map.get("2012-01-02"));
    }
}