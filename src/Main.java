import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Main {

    static String str = new String();
    static String type_currency = new String();
    static String start_date = new String();
    static String stop_date = new String();
    Date[] date = new Date[days];
    static int days;
    static Scanner read = new Scanner(System.in);
    static StringBuilder sb = new StringBuilder();
    static BigDecimal med = new BigDecimal(0);

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

                //System.out.println(str);
            }
        } catch (Exception ex) {
            System.out.println("Blad podczas pobierania danych!");
            System.exit(0);
        }

        ReturnItem item = new ReturnItem();
        item.cur = rate;
        item.len = len;

        med = Mediana(rate, len);

        return item;
    }

    public static void Calculations() {
        int choose = 0;

        System.out.println("---");
        System.out.println("Podaj walute: ");
        type_currency = read.next();

        System.out.println("Podaj zakres: ");
        System.out.println("1 - 1 tydzien");
        System.out.println("2 - 2 tygodnie");
        System.out.println("3 - 1 miesiac");
        System.out.println("4 - 1 kwartal");
        System.out.println("5 - pol roku");
        System.out.println("6 - rok");
        choose = read.nextInt();

        ReturnItem item = new ReturnItem();

        switch (choose) {
            case 1: {
                days = 7;
                item = downloadData();
                break;
            }
            case 2: {
                days = 14;
                item = downloadData();
                break;
            }
            case 3: {
                days = 30;
                item = downloadData();
                break;
            }
            case 4: {
                days = 90;
                item = downloadData();
                break;
            }
            case 5: {
                days = 182;
                item = downloadData();
                break;
            }
            case 6: {
                days = 365;
                item = downloadData();
                break;
            }
            default: {
                System.out.println("Zly wybor!");
                System.exit(0);
            }
        }

        int len = item.len;
        BigDecimal[] cur = new BigDecimal[len];
        cur = item.cur;

        int up = 0;
        int down = 0;
        int none = 0;
        int j = 0;

        for (j = 0; j < len - 1; j++) {
            if (cur[j + 1].compareTo(cur[j]) > 0) {
                up++;
            }
            if (cur[j + 1].compareTo(cur[j]) < 0) {
                down++;
            }
            if (cur[j + 1].compareTo(cur[j]) == 0) {
                none++;
            }
        }
        System.out.println("Wzrostów: " + up);
        sb.append("Wzrostow:");
        sb.append(',');
        sb.append(up);
        sb.append('\n');
        System.out.println("Spadkwów: " + down);
        sb.append("Spadkow:");
        sb.append(',');
        sb.append(down);
        sb.append('\n');
        System.out.println("Bez zmian: " + none);
        sb.append("Bez zmian:");
        sb.append(',');
        sb.append(none);
        sb.append('\n');

        BigDecimal[] medCur = cur;
        BigDecimal avg = new BigDecimal(0);
        BigDecimal med = new BigDecimal(0);
        BigDecimal two = new BigDecimal(2);
        BigDecimal tmp = new BigDecimal(0);

        int n = 0;
        int m = 0;

        for (n = 0; n < len - 1; n++) {
            for (m = 0; m < len - 1; m++) {
                if (medCur[m].compareTo(medCur[m + 1]) > 0) {
                    tmp = medCur[m];
                    medCur[m] = medCur[m + 1];
                    medCur[m + 1] = tmp;
                }
            }
        }

        if (len % 2 == 0) {
            avg = medCur[len / 2].add(medCur[(len / 2) - 1]);
            med = avg.divide(two);
        } else {
            med = medCur[len / 2];
        }

        System.out.println("Mediana: " + med);
        sb.append("Mediana:");
        sb.append(',');
        sb.append(med);
        sb.append('\n');

        //Dominanta
        BigDecimal dominanta = new BigDecimal(0);
        int maks = 0;
        int licznik = 0;

        for (int i = 0; i < len - 1; i++) {
            licznik = 0;
            for (int k = 0; k < len - 1; k++) {
                if (medCur[i] == medCur[k]) {
                    licznik++;
                    if (licznik > maks) {
                        dominanta = medCur[i];
                        maks = licznik;
                    }
                }

            }
        }

        System.out.println("Dominanta:" + dominanta);
        sb.append("Dominanta:");
        sb.append(',');
        sb.append(med);
        sb.append('\n');
    }

    public static void TwoCurrency()
    {
        int choose = 0;
        int i = 0;
        String currency1 = new String();
        String currency2 = new String();

        System.out.println("---");
        System.out.println("Podaj walute 1: ");
        currency1 = read.next();
        System.out.println("Podaj walute 2: ");
        currency2 = read.next();

        System.out.println("Podaj zakres: ");
        System.out.println("1 - 1 miesiac");
        System.out.println("2 - 1 kwartal");

        choose = read.nextInt();

        ReturnItem item1 = new ReturnItem();
        ReturnItem item2 = new ReturnItem();

        switch(choose)
        {
            case 1:
            {
                days = 30;

                type_currency = currency1;
                item1 = downloadData();

                type_currency = currency2;
                item2 = downloadData();

                break;
            }
            case 2:
            {
                days = 90;

                type_currency = currency1;
                item1 = downloadData();

                type_currency = currency2;
                item2 = downloadData();

                break;
            }
            default:
            {
                System.out.println("Zly wybor!");
                System.exit(0);
            }
        }

        int len1 = item1.len;
        BigDecimal[] cur1 = new BigDecimal[len1];
        cur1 = item1.cur;

        System.out.println("---");
        System.out.println("Dla waluty: " + currency1);
        System.out.println("---");
        sb.append("Dla waluty:");
        sb.append(',');
        sb.append(currency1);
        sb.append('\n');

        for (i = 0 ; i < len1-1 ; i++)
        {
            System.out.println(i+1 + ". " + cur1[i].subtract(cur1[i+1]));
            sb.append(i+1);
            sb.append(',');
            sb.append(cur1[i].subtract(cur1[i+1]));
            sb.append('\n');
        }

        int len2 = item2.len;
        BigDecimal[] cur2 = new BigDecimal[len2];
        cur2 = item2.cur;

        System.out.println("---");
        System.out.println("Dla waluty: " + currency2);
        System.out.println("---");
        sb.append("Dla waluty:");
        sb.append(',');
        sb.append(currency2);
        sb.append('\n');

        for (i = 0 ; i < len2-1 ; i++)
        {
            System.out.println(i+1 + ". " + cur2[i].subtract(cur2[i+1]));
            sb.append(i+1);
            sb.append(',');
            sb.append(cur2[i].subtract(cur2[i+1]));
            sb.append('\n');
        }
    }

    public static BigDecimal Mediana(BigDecimal[] cur, int len)
    {
        BigDecimal[] medCur = cur;
        BigDecimal avg = new BigDecimal(0);
        BigDecimal med = new BigDecimal(0);
        BigDecimal two = new BigDecimal(2);
        BigDecimal tmp = new BigDecimal(0);

        int n = 0;
        int m = 0;

        for (n = 0; n < len - 1; n++) {
            for (m = 0; m < len - 1; m++) {
                if (medCur[m].compareTo(medCur[m + 1]) > 0) {
                    tmp = medCur[m];
                    medCur[m] = medCur[m + 1];
                    medCur[m + 1] = tmp;
                }
            }
        }

        if (len % 2 == 0) {
            avg = medCur[len / 2].add(medCur[(len / 2) - 1]);
            med = avg.divide(two);
        } else {
            med = medCur[len / 2];
        }



        return med;
    }

    public static void main(String[] args) {

        int choose = 0;

        System.out.println("---WALUTY---");

        System.out.println("1 - Sesje i miary statystyczne");
        System.out.println("2 - Rozklad zmian 2 walut");
        choose = read.nextInt();



        switch(choose)
        {
            case 1:
            {
                Calculations();
                break;
            }
            case 2:
            {
                TwoCurrency();
                break;
            }
            default:
            {
                System.out.println("Zly wybor!");
            }
        }

        try
        {
            PrintWriter pw = new PrintWriter(new File("result.csv"));
            pw.write(sb.toString());
            pw.close();
            System.out.println("Zapisano plik!");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("B³¹d zapisu pliku!");
        }
    }
}