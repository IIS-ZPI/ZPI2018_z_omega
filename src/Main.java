import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Main {

    static String str = new String();
    static String type_currency = new String();
    static String start_date = new String();
    static String stop_date = new String();
    Date[] date = new Date[days];
    static int days;

    public static ReturnItem downloadData()
    {
        LocalDate date1 = LocalDate.now().minusDays(days);
        LocalDate date2 = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        start_date = date1.format(formatter);
        stop_date = date2.format(formatter);
        BigDecimal[] rate = new BigDecimal[days];
        Rates[] rates = new Rates[days];
        int len = 0;

        try {
            URL url = new URL("http://api.nbp.pl/api/exchangerates/rates/A/" + type_currency + "/" + start_date + "/" + stop_date + "/");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            while (null != (str = br.readLine())) {
                Gson gson = new Gson();
                cur cur = gson.fromJson(str, cur.class);

                int i = 0;


                for (Rates length : cur.rates) {
                    len++;
                }

                for (i = 0 ; i < len ; i++)
                {
                    rates[i] = cur.rates.get(i);
                    rate[i] = rates[i].mid;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ReturnItem item = new ReturnItem();
        item.cur = rate;
        item.len = len;

        return item;
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

        ReturnItem item = new ReturnItem();
        //ReturnItem item2 = new ReturnItem();
        //ReturnItem item3 = new ReturnItem();
        //ReturnItem item4 = new ReturnItem();

        switch (days)
        {
            case 1:
            {
                days = 7;
                item = downloadData();
                break;
            }
            case 2:
            {
                days = 14;
                item = downloadData();
                break;
            }
            case 3:
            {
                days = 30;
                item = downloadData();
                break;
            }
            case 4:
            {
                days = 90;
                item = downloadData();
                break;
            }
            case 5:
            {
                days = 182;
                item = downloadData();
                break;
            }
            case 6:
            {
                days = 365;
                item = downloadData();
                break;
            }
        }

        int len = item.len;
        BigDecimal[] cur = item.cur;

        int up = 0;
        int down = 0;
        int none = 0;
        int j = 0;

        for (j = 0 ; j < len-1 ; j++)
        {
            if (cur[j+1].compareTo(cur[j]) > 0){
                up++;
            }
            if (cur[j+1].compareTo(cur[j]) < 0){
                down++;
            }
            if (cur[j+1].compareTo(cur[j]) == 0){
                none++;
            }
        }
        System.out.println("Wzrostów: " + up);
        System.out.println("Spadkwów: " + down);
        System.out.println("Bez zmian: " + none);

    }
}
