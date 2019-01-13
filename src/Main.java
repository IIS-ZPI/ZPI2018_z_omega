import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    static String str = new String();
    static String type_currency = new String();
    static String start_date = new String();
    static String stop_date = new String();
    Date[] date = new Date[days];
    static int days;
    BigDecimal[] mid = new BigDecimal[days];

    public static void downloadData()
    {
        LocalDate date1 = LocalDate.now().minusDays(days);
        LocalDate date2 = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        start_date = date1.format(formatter);
        stop_date = date2.format(formatter);

        try {
            URL url = new URL("http://api.nbp.pl/api/exchangerates/rates/A/" + type_currency + "/" + start_date + "/" + stop_date + "/");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            while (null != (str = br.readLine())) {
                Gson gson = new Gson();
                cur cur = gson.fromJson(str, cur.class);
                for (Rates rates : cur.rates)
                    System.out.println("    " + rates.mid);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Scanner read = new Scanner(System.in);

        System.out.println("---WALUTY---");
        System.out.println("Podaj walute: ");
        type_currency = read.nextLine();

        System.out.println("Podaj zakres: ");
        System.out.println("1 - 1 tydzien");
        System.out.println("2 - 2 tygodnie");
        System.out.println("3 - 1 miesiac");
        System.out.println("4 - 1 kwartal");
        System.out.println("5 - pol roku");
        System.out.println("6 - rok");
        days = read.nextInt();

        switch (days)
        {
            case 1:
            {
                days = 7;
                downloadData();
                break;
            }
            case 2:
            {
                days = 14;
                break;
            }
            case 3:
            {
                days = 30;
                break;
            }
            case 4:
            {
                days = 90;
                break;
            }
            case 5:
            {
                days = 182;
                break;
            }
            case 6:
            {
                days = 365;
                break;
            }
        }




        //Type hMapType = new TypeToken<HashMap<String, List<String>>>() {}.getType();
        //Map<String, List<String>> list = new HashMap<String, List<String>>();
        //list = gson.fromJson(str, hMapType);
        //Set<String> key = list.keySet();
        //String[] tabKey = new String[10];
        //key.toArray(tabKey);
        //List<String> listKey = new ArrayList<>();

        //for (int i = 0; i < tabKey.length; i++)
        //{
        //    listKey.add(tabKey[i]);
        //    //System.out.println(listKey);
        //}
    }
}
