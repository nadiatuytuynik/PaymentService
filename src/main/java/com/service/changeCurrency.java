package com.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class changeCurrency {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Document doc = Jsoup.connect("https://kurs.com.ua/?gclid=CjwKEAjwja_JBRD8idHpxaz0t3wSJAB4rXW5UuqtFwLuhEelPcP4MOSTtO-TLMskz_KgEiWPWWLdtxoCGEXw_wcB").get();

            Elements h1Elements = doc.getElementsByAttributeValue("class", "ipsKurs_rate");
            String text = h1Elements.text();

            ArrayList<String> currencies = new ArrayList<>();
            for (String retval : text.split(" ")) {
                currencies.add(retval);
            }

            double value;
            ArrayList<Double> currencies_double = new ArrayList<>();
            for(int i =0; i<currencies.size(); i++) {
                value = Double.parseDouble(currencies.get(i).replace(",", "."));
                currencies_double.add(value);
            }
            //System.out.println(currencies_double);

            double usd = UAN_USD(400, currencies_double.get(0));
            System.out.println("UAN > 400;  USD > " + usd);

            double uan = USD_UAN(400, currencies_double.get(0));
            System.out.println("USD > 400;  UAN > " + uan);

            double euro = UAN_EUR(400, currencies_double.get(2));
            System.out.println("UAN > 400;  EUR > " + euro);

            double uan2 = EUR_UAN(400, currencies_double.get(2));//
            System.out.println("EUR > 400;  UAN > " + uan2);

            double eur2 = USD_EUR(400, currencies_double.get(0),currencies_double.get(2));
            System.out.println("USD > 400;  EUR > " + eur2);

            double usd2 = EUR_USD(400, currencies_double.get(0),currencies_double.get(2));
            System.out.println("EUR > 400;  USD > " + usd2);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static double UAN_USD(double account_1, double course){
        double account_2 = (account_1)/(course);
        account_2 = account_2*100;
        account_2 = Math.rint(account_2);
        account_2 = account_2/100;
        return account_2;
    }


    public static double USD_UAN(double account_1,double course){
        double account_2 = (account_1)*(course);
        account_2 = account_2*100;
        account_2 = Math.rint(account_2);
        account_2 = account_2/100;
        return account_2;
    }

    public static double UAN_EUR(double account_1,double course){
        double account_2 = (account_1)/(course);
        account_2 = account_2*100;
        account_2 = Math.rint(account_2);
        account_2 = account_2/100;
        return account_2;
    }

    public static double EUR_UAN(double account_1,double course){
        double account_2 = (account_1)*(course);
        account_2 = account_2*100;
        account_2 = Math.rint(account_2);
        account_2 = account_2/100;
        return account_2;
    }

    public static double USD_EUR(double account_1,double course_usd, double course_eur){
        double account = (account_1)*(course_usd);
        double account_2 = (account)/(course_eur);
        account_2 = account_2*100;
        account_2 = Math.rint(account_2);
        account_2 = account_2/100;
        return account_2;
    }

    public static double EUR_USD(double account_1,double course_usd, double course_eur){
        double account = (account_1)*(course_eur);
        double account_2 = (account)/(course_usd);
        account_2 = account_2*100;
        account_2 = Math.rint(account_2);
        account_2 = account_2/100;
        return account_2;
    }

}
