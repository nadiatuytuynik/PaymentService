package com.service.controller;

import com.service.changeCurrency;
import com.service.util.DBManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

@WebServlet(name="BasketServlet", urlPatterns = "/BasketServlet")
public class BasketServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String name = (String) session.getAttribute("name");
        String secondname = (String) session.getAttribute("secondname");
        req.setAttribute("name", name);
        req.setAttribute("secondname", secondname);

        session.setAttribute("name",name);
        session.setAttribute("secondname", secondname);

        req.setAttribute("authorize", 1);

        String confirm = req.getParameter("confirm1");
        System.out.println("confirm1 = " + confirm);



        int mobilerefill = (int) session.getAttribute("mobilerefill");

        if (mobilerefill == 1) {
            req.setAttribute("authorize", 2);
            System.out.println("Mobile refill!");
            String cardsender = (String) session.getAttribute("cardsender");
            String phonerecipient = (String) session.getAttribute("cardrecipient");
            double amount = (double) session.getAttribute("amount");
            String currency = (String) session.getAttribute("currency");

            System.out.println(cardsender);
            System.out.println(phonerecipient);
            System.out.println(amount);
            System.out.println(currency);


            try {
                DBManager dbManager = DBManager.getInstance();
                Connection connection = dbManager.getConnection();
                CallableStatement cardInfoNumber = connection.prepareCall("{call CardInfoNumber(?,?,?)}");
                cardInfoNumber.setString(1, cardsender);
                cardInfoNumber.registerOutParameter(2, Types.DOUBLE);
                cardInfoNumber.registerOutParameter(3, Types.VARCHAR);
                cardInfoNumber.executeQuery();
                double cardaccount = (double) cardInfoNumber.getObject(2);
                String cardcurrency = (String) cardInfoNumber.getObject(3);
                cardInfoNumber.close();

                System.out.println("sender");
                System.out.println(cardaccount);
                System.out.println(cardcurrency);


                CallableStatement cardInfoNumber2 = connection.prepareCall("{call PhoneInfoNumber(?,?,?)}");
                cardInfoNumber2.setString(1, phonerecipient);
                cardInfoNumber2.registerOutParameter(2, Types.DOUBLE);
                cardInfoNumber2.registerOutParameter(3, Types.VARCHAR);
                cardInfoNumber2.executeQuery();
                double phoneaccount2 = (double) cardInfoNumber2.getObject(2);
                String phonecurrency2 = (String) cardInfoNumber2.getObject(3);
                cardInfoNumber2.close();

                System.out.println("recipient");
                System.out.println(phoneaccount2);
                System.out.println(phonecurrency2);

                double amount_plus_commission;
                double newaccount;

                //------Exchange rates ------------------------------------//
                double euro;
                double dollar;
                double rates = 0.919920887;//dollar = rates*euro
                //------Exchange rates ------------------------------------//

                double comission;
                if (cardcurrency.equals(phonecurrency2)) {//одинаковая валюта
                    comission = amount * 0.01;
                    amount_plus_commission = amount + comission;
                    if (cardaccount >= amount_plus_commission) {
                        System.out.println("Correct! Ready to transfer.");
                        newaccount = cardaccount - amount_plus_commission;
                        cardaccount = newaccount;
                        phoneaccount2 = phoneaccount2 + amount;

                        CallableStatement updateCard = connection.prepareCall("{call UpdateCard(?,?)}");
                        updateCard.setString(1, cardsender);
                        updateCard.setDouble(2, cardaccount);
                        updateCard.executeQuery();
                        updateCard.close();

                        CallableStatement updateCard2 = connection.prepareCall("{call UpdatePhone(?,?)}");
                        updateCard2.setString(1, phonerecipient);
                        updateCard2.setDouble(2, phoneaccount2);
                        updateCard2.executeQuery();
                        updateCard2.close();

                        CallableStatement cardInfoNumber3 = connection.prepareCall("{call CardInfoNumber(?,?,?)}");
                        cardInfoNumber3.setString(1, "1010101010101010");
                        cardInfoNumber3.registerOutParameter(2, Types.DOUBLE);
                        cardInfoNumber3.registerOutParameter(3, Types.VARCHAR);
                        cardInfoNumber3.executeQuery();
                        double cardaccount3 = (double) cardInfoNumber3.getObject(2);
                        String cardcurrency3 = (String) cardInfoNumber3.getObject(3);
                        cardInfoNumber3.close();

                        cardaccount3 = cardaccount3 + comission;


                        CallableStatement updateCard3 = connection.prepareCall("{call UpdateCard(?,?)}");
                        updateCard3.setString(1, "1010101010101010");
                        updateCard3.setDouble(2, cardaccount3);
                        updateCard3.executeQuery();
                        updateCard3.close();

                        System.out.println("Card sender = " + cardaccount);
                        System.out.println("Card recipient = " + phoneaccount2);
                        req.setAttribute("update", 1);

                        String data2 = (String)session.getAttribute("data2");
                        CallableStatement updateBasket = connection.prepareCall("{call UpdateBasket(?,?)}");
                        updateBasket.setString(1, "committed");
                        updateBasket.setString(2, data2);
                        updateBasket.executeQuery();
                        updateBasket.close();


                    } else {
                        System.out.println("Error! Not enough money.");
                        req.setAttribute("not_enough_money2",1);
                        int castid = (int) session.getAttribute("castid");

                        CallableStatement maxMinIdCards = connection.prepareCall("{call MaxMinIdCards(?,?,?)}");
                        maxMinIdCards.setInt(1, castid);
                        maxMinIdCards.registerOutParameter(2, Types.INTEGER);
                        maxMinIdCards.registerOutParameter(3, Types.INTEGER);
                        maxMinIdCards.executeQuery();

                        int maxcardid = (int) maxMinIdCards.getObject(2);
                        int mincardid = (int) maxMinIdCards.getObject(3);

                        maxMinIdCards.close();

                        ArrayList<Integer> list_of_cards_id3 = new ArrayList<Integer>();
                        for (int ccid = mincardid; ccid <= maxcardid; ccid++) {
                            CallableStatement showCards = connection.prepareCall("{call ShowCards(?,?,?)}");
                            showCards.setInt(1, ccid);
                            showCards.setInt(2, castid);
                            showCards.registerOutParameter(3, Types.INTEGER);
                            showCards.executeQuery();
                            int showcards = (int) showCards.getObject(3);
                            showCards.close();
                            if (showcards == 0) {
                                ccid++;
                            } else {
                                list_of_cards_id3.add(showcards);
                            }
                        }

                        ArrayList<String> cards_numbers3 = new ArrayList<String>();
                        ArrayList<Double> cards_accounts3 = new ArrayList<Double>();
                        ArrayList<String> cards_currencies3 = new ArrayList<String>();

                        for (int i = 0; i < 4; i++) {
                            cards_numbers3.add(null);
                            cards_currencies3.add(null);
                        }
                        for (int i = 0; i < 4; i++) {
                            cards_accounts3.add(0.0);
                        }


                        System.out.println(list_of_cards_id3.size());
                        for (int l = 0; l < list_of_cards_id3.size(); l++) {
                            CallableStatement cardInfo = connection.prepareCall("{call CardInfo(?,?,?,?)}");
                            cardInfo.setInt(1, list_of_cards_id3.get(l));
                            cardInfo.registerOutParameter(2, Types.VARCHAR);
                            cardInfo.registerOutParameter(3, Types.DOUBLE);
                            cardInfo.registerOutParameter(4, Types.VARCHAR);
                            cardInfo.executeQuery();

                            String cardnumber_basket = (String) cardInfo.getObject(2);
                            double cardaccount_basket = (double) cardInfo.getObject(3);
                            String cardcurrency_basket = (String) cardInfo.getObject(4);

                            cardInfo.close();
                            if (cardnumber_basket.equals("none") && l >= list_of_cards_id3.size()) {
                                break;
                            }

                            if (cardnumber_basket.equals("none")) {
                                l++;
                            } else {

                                cards_currencies3.set(l, cardcurrency_basket);
                                cards_numbers3.set(l, cardnumber_basket);
                                cards_accounts3.set(l, cardaccount_basket);

                            }
                        }

                        System.out.println(cards_numbers3);

                        req.setAttribute("number(0)", cards_numbers3.get(0));
                        req.setAttribute("number(1)", cards_numbers3.get(1));
                        req.setAttribute("number(2)", cards_numbers3.get(2));
                        req.setAttribute("number(3)", cards_numbers3.get(3));

                        req.setAttribute("account(0)", cards_accounts3.get(0));
                        req.setAttribute("account(1)", cards_accounts3.get(1));
                        req.setAttribute("account(2)", cards_accounts3.get(2));
                        req.setAttribute("account(3)", cards_accounts3.get(3));

                        req.setAttribute("currency(0)", cards_currencies3.get(0));
                        req.setAttribute("currency(1)", cards_currencies3.get(1));
                        req.setAttribute("currency(2)", cards_currencies3.get(2));
                        req.setAttribute("currency(3)", cards_currencies3.get(3));
                    }
                } else {
                    System.out.println("We need to change currency!");

                    if (cardcurrency.equals("dollar") && phonecurrency2.equals("euro") && currency.equals("dollar")) {

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

                        amount_plus_commission = amount + amount * 0.01;
                        newaccount = cardaccount - amount_plus_commission;
                        cardaccount = newaccount;
                        double eur = changeCurrency.USD_EUR(amount,currencies_double.get(0),currencies_double.get(2));
                        phoneaccount2 = phoneaccount2 + eur;

                        CallableStatement updateCard = connection.prepareCall("{call UpdateCard(?,?)}");
                        updateCard.setString(1, cardsender);
                        updateCard.setDouble(2, cardaccount);
                        updateCard.executeQuery();
                        updateCard.close();

                        CallableStatement updateCard2 = connection.prepareCall("{call UpdateCard(?,?)}");
                        updateCard2.setString(1, phonerecipient);
                        updateCard2.setDouble(2, phoneaccount2);
                        updateCard2.executeQuery();
                        updateCard2.close();

                        CallableStatement cardInfoNumber3 = connection.prepareCall("{call CardInfoNumber(?,?,?)}");
                        cardInfoNumber3.setString(1, "1010101010101010");
                        cardInfoNumber3.registerOutParameter(2, Types.DOUBLE);
                        cardInfoNumber3.registerOutParameter(3, Types.VARCHAR);
                        cardInfoNumber3.executeQuery();
                        double cardaccount3 = (double) cardInfoNumber3.getObject(2);
                        String cardcurrency3 = (String) cardInfoNumber3.getObject(3);
                        cardInfoNumber3.close();

                        cardaccount3 = cardaccount3 + amount * 0.01;


                        CallableStatement updateCard3 = connection.prepareCall("{call UpdateCard(?,?)}");
                        updateCard3.setString(1, "1010101010101010");
                        updateCard3.setDouble(2, cardaccount3);
                        updateCard3.executeQuery();
                        updateCard3.close();


                    }
                    if (cardcurrency.equals("euro") && phonecurrency2.equals("dollar") && currency.equals("dollar")) {
                        System.out.println("Change currency!");
                    }
                }

                int castid = (int) session.getAttribute("castid");

                CallableStatement maxMinIdCards = connection.prepareCall("{call MaxMinIdCards(?,?,?)}");
                maxMinIdCards.setInt(1, castid);
                maxMinIdCards.registerOutParameter(2, Types.INTEGER);
                maxMinIdCards.registerOutParameter(3, Types.INTEGER);
                maxMinIdCards.executeQuery();

                int maxcardid = (int) maxMinIdCards.getObject(2);
                int mincardid = (int) maxMinIdCards.getObject(3);

                maxMinIdCards.close();

                ArrayList<Integer> list_of_cards_id2 = new ArrayList<Integer>();
                for (int ccid = mincardid; ccid <= maxcardid; ccid++) {
                    CallableStatement showCards = connection.prepareCall("{call ShowCards(?,?,?)}");
                    showCards.setInt(1, ccid);
                    showCards.setInt(2, castid);
                    showCards.registerOutParameter(3, Types.INTEGER);
                    showCards.executeQuery();
                    int showcards = (int) showCards.getObject(3);
                    showCards.close();
                    if((showcards ==0) && (ccid ==maxcardid)){
                        break;
                    }else {
                        if (showcards == 0) {
                            ccid++;
                        } else {
                            list_of_cards_id2.add(showcards);
                        }
                    }
                }

                ArrayList<String> cards_numbers2 = new ArrayList<String>();
                ArrayList<Double> cards_accounts2 = new ArrayList<Double>();
                ArrayList<String> cards_currencies2 = new ArrayList<String>();

                for (int i = 0; i < 4; i++) {
                    cards_numbers2.add(null);
                    cards_currencies2.add(null);
                }
                for (int i = 0; i < 4; i++) {
                    cards_accounts2.add(0.0);
                }


                for (int m=0; m < list_of_cards_id2.size(); m++) {
                    CallableStatement cardInfo = connection.prepareCall("{call CardInfo(?,?,?,?)}");
                    cardInfo.setInt(1, list_of_cards_id2.get(m));
                    cardInfo.registerOutParameter(2, Types.VARCHAR);
                    cardInfo.registerOutParameter(3, Types.DOUBLE);
                    cardInfo.registerOutParameter(4, Types.VARCHAR);
                    cardInfo.executeQuery();
                    String cardnumber_basket = (String) cardInfo.getObject(2);
                    double cardaccount_basket = (double) cardInfo.getObject(3);
                    String cardcurrency_basket = (String) cardInfo.getObject(4);

                    if(cardnumber_basket.equals("none") && (m==(list_of_cards_id2.size()-1))){
                        break;
                    }
                    if(cardnumber_basket.equals("none")){
                        m++;
                    }else {
                        cards_numbers2.set(m, cardnumber_basket);
                        cards_accounts2.set(m, cardaccount_basket);
                        cards_currencies2.set(m, cardcurrency_basket);
                    }

                }

                req.setAttribute("number(0)", cards_numbers2.get(0));
                req.setAttribute("number(1)", cards_numbers2.get(1));
                req.setAttribute("number(2)", cards_numbers2.get(2));
                req.setAttribute("number(3)", cards_numbers2.get(3));

                req.setAttribute("account(0)", cards_accounts2.get(0));
                req.setAttribute("account(1)", cards_accounts2.get(1));
                req.setAttribute("account(2)", cards_accounts2.get(2));
                req.setAttribute("account(3)", cards_accounts2.get(3));

                req.setAttribute("currency(0)", cards_currencies2.get(0));
                req.setAttribute("currency(1)", cards_currencies2.get(1));
                req.setAttribute("currency(2)", cards_currencies2.get(2));
                req.setAttribute("currency(3)", cards_currencies2.get(3));



                CallableStatement maxmincustomerbasketid2 = connection.prepareCall("{call MaxMinCustomerBasketId(?,?,?)}");
                maxmincustomerbasketid2.setInt(1, castid);
                maxmincustomerbasketid2.registerOutParameter(2, Types.INTEGER);
                maxmincustomerbasketid2.registerOutParameter(3, Types.INTEGER);
                maxmincustomerbasketid2.executeQuery();
                int maxcustbaskid2 = (int) maxmincustomerbasketid2.getObject(2);
                int mincustbaskid2 = (int) maxmincustomerbasketid2.getObject(3);

                //System.out.println("maxcustbaskid = " + maxcustbaskid2);
                //System.out.println("mincustbaskid = " + mincustbaskid2);

                if (maxcustbaskid2 == 0) {
                    System.out.println("Error!");
                } else {
                    ArrayList<Integer> castbaskid_list = new ArrayList<Integer>();
                    for (int j = mincustbaskid2; j <= maxcustbaskid2; j++) {
                        CallableStatement getBasketId2 = connection.prepareCall("{call GetBasketId(?,?,?)}");
                        getBasketId2.setInt(1, castid);
                        getBasketId2.setInt(2, j);
                        getBasketId2.registerOutParameter(3, Types.INTEGER);
                        getBasketId2.executeQuery();
                        int basketid = (int) getBasketId2.getObject(3);
                        if ((basketid == 0) && (j > maxcustbaskid2)) {
                            break;
                        } else {
                            if ((basketid == 0) && (j < maxcustbaskid2)) {
                                j++;
                            } else {
                                castbaskid_list.add(basketid);
                            }
                        }
                    }

                    ArrayList<String> CardsSenderBasketList2 = new ArrayList<String>();
                    ArrayList<String> RecipientsBasketList2 = new ArrayList<String>();
                    ArrayList<Double> AmountsBasketList2 = new ArrayList<Double>();
                    ArrayList<String> CurrenciesBasketList2 = new ArrayList<String>();
                    ArrayList<String> DataBasketList2 = new ArrayList<String>();


                    for (int j = 0; j < 10; j++) {
                        CardsSenderBasketList2.add(null);
                        RecipientsBasketList2.add(null);
                        AmountsBasketList2.add(null);
                        CurrenciesBasketList2.add(null);
                        DataBasketList2.add(null);
                    }


                    //System.out.println("castbaskid_list > " + castbaskid_list);

                    for (int j = 0; j < castbaskid_list.size(); j++) {
                        int lineid = castbaskid_list.get(j);
                        CallableStatement basketLines2 = connection.prepareCall("{call BasketLines(?,?,?,?,?,?)}");
                        basketLines2.setInt(1, lineid);
                        basketLines2.registerOutParameter(2, Types.VARCHAR);
                        basketLines2.registerOutParameter(3, Types.VARCHAR);
                        basketLines2.registerOutParameter(4, Types.DOUBLE);
                        basketLines2.registerOutParameter(5, Types.VARCHAR);
                        basketLines2.registerOutParameter(6, Types.VARCHAR);
                        basketLines2.executeQuery();
                        String cardsender_basket2 = (String) basketLines2.getObject(2);
                        String recipient_basket2 = (String) basketLines2.getObject(3);
                        double amount_basket2 = (double) basketLines2.getObject(4);
                        String currency_basket2 = (String) basketLines2.getObject(5);
                        String dataofoperation2 = (String) basketLines2.getObject(6);

                        if (amount_basket2 == 0.0) {
                            AmountsBasketList2.set(j, null);
                            RecipientsBasketList2.set(j, null);
                            CardsSenderBasketList2.set(j, null);
                            CurrenciesBasketList2.set(j, null);
                            DataBasketList2.set(j, null);
                        } else {
                            AmountsBasketList2.set(j, amount_basket2);
                            RecipientsBasketList2.set(j, recipient_basket2);
                            CardsSenderBasketList2.set(j, cardsender_basket2);
                            CurrenciesBasketList2.set(j, currency_basket2);
                            DataBasketList2.set(j, dataofoperation2);
                        }
                    }

                    req.setAttribute("bsender(0)", CardsSenderBasketList2.get(0));
                    req.setAttribute("bsender(1)", CardsSenderBasketList2.get(1));
                    req.setAttribute("bsender(2)", CardsSenderBasketList2.get(2));
                    req.setAttribute("bsender(3)", CardsSenderBasketList2.get(3));
                    req.setAttribute("bsender(4)", CardsSenderBasketList2.get(4));
                    req.setAttribute("bsender(5)", CardsSenderBasketList2.get(5));
                    req.setAttribute("bsender(6)", CardsSenderBasketList2.get(6));
                    req.setAttribute("bsender(7)", CardsSenderBasketList2.get(7));
                    req.setAttribute("bsender(8)", CardsSenderBasketList2.get(8));
                    req.setAttribute("bsender(9)", CardsSenderBasketList2.get(9));

                    req.setAttribute("brecipient(0)", RecipientsBasketList2.get(0));
                    req.setAttribute("brecipient(1)", RecipientsBasketList2.get(1));
                    req.setAttribute("brecipient(2)", RecipientsBasketList2.get(2));
                    req.setAttribute("brecipient(3)", RecipientsBasketList2.get(3));
                    req.setAttribute("brecipient(4)", RecipientsBasketList2.get(4));
                    req.setAttribute("brecipient(5)", RecipientsBasketList2.get(5));
                    req.setAttribute("brecipient(6)", RecipientsBasketList2.get(6));
                    req.setAttribute("brecipient(7)", RecipientsBasketList2.get(7));
                    req.setAttribute("brecipient(8)", RecipientsBasketList2.get(8));
                    req.setAttribute("brecipient(9)", RecipientsBasketList2.get(9));

                    req.setAttribute("bamount(0)", AmountsBasketList2.get(0));
                    req.setAttribute("bamount(1)", AmountsBasketList2.get(1));
                    req.setAttribute("bamount(2)", AmountsBasketList2.get(2));
                    req.setAttribute("bamount(3)", AmountsBasketList2.get(3));
                    req.setAttribute("bamount(4)", AmountsBasketList2.get(4));
                    req.setAttribute("bamount(5)", AmountsBasketList2.get(5));
                    req.setAttribute("bamount(6)", AmountsBasketList2.get(6));
                    req.setAttribute("bamount(7)", AmountsBasketList2.get(7));
                    req.setAttribute("bamount(8)", AmountsBasketList2.get(8));
                    req.setAttribute("bamount(9)", AmountsBasketList2.get(9));

                    req.setAttribute("bcurrency(0)", CurrenciesBasketList2.get(0));
                    req.setAttribute("bcurrency(1)", CurrenciesBasketList2.get(1));
                    req.setAttribute("bcurrency(2)", CurrenciesBasketList2.get(2));
                    req.setAttribute("bcurrency(3)", CurrenciesBasketList2.get(3));
                    req.setAttribute("bcurrency(4)", CurrenciesBasketList2.get(4));
                    req.setAttribute("bcurrency(5)", CurrenciesBasketList2.get(5));
                    req.setAttribute("bcurrency(6)", CurrenciesBasketList2.get(6));
                    req.setAttribute("bcurrency(7)", CurrenciesBasketList2.get(7));
                    req.setAttribute("bcurrency(8)", CurrenciesBasketList2.get(8));
                    req.setAttribute("bcurrency(9)", CurrenciesBasketList2.get(9));

                    req.setAttribute("data(0)", DataBasketList2.get(0));
                    req.setAttribute("data(1)", DataBasketList2.get(1));
                    req.setAttribute("data(2)", DataBasketList2.get(2));
                    req.setAttribute("data(3)", DataBasketList2.get(3));
                    req.setAttribute("data(4)", DataBasketList2.get(4));
                    req.setAttribute("data(5)", DataBasketList2.get(5));
                    req.setAttribute("data(6)", DataBasketList2.get(6));
                    req.setAttribute("data(7)", DataBasketList2.get(7));
                    req.setAttribute("data(8)", DataBasketList2.get(8));
                    req.setAttribute("data(9)", DataBasketList2.get(9));
                }

                } catch (SQLException e) {
                e.printStackTrace();
            }
        }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (mobilerefill != 1) {
            req.setAttribute("authorize", 1);
            String cardsender = (String) session.getAttribute("cardsender");
            String cardrecipient = (String) session.getAttribute("cardrecipient");
            double amount = (double) session.getAttribute("amount");
            String currency = (String) session.getAttribute("currency");

            try {
                DBManager dbManager = DBManager.getInstance();
                Connection connection = dbManager.getConnection();
                CallableStatement cardInfoNumber = connection.prepareCall("{call CardInfoNumber(?,?,?)}");
                cardInfoNumber.setString(1, cardsender);
                cardInfoNumber.registerOutParameter(2, Types.DOUBLE);
                cardInfoNumber.registerOutParameter(3, Types.VARCHAR);
                cardInfoNumber.executeQuery();
                double cardaccount = (double) cardInfoNumber.getObject(2);
                String cardcurrency = (String) cardInfoNumber.getObject(3);
                cardInfoNumber.close();

                //System.out.println("sender");
                //System.out.println(cardaccount);
                //System.out.println(cardcurrency);


                CallableStatement cardInfoNumber2 = connection.prepareCall("{call CardInfoNumber(?,?,?)}");
                cardInfoNumber2.setString(1, cardrecipient);
                cardInfoNumber2.registerOutParameter(2, Types.DOUBLE);
                cardInfoNumber2.registerOutParameter(3, Types.VARCHAR);
                cardInfoNumber2.executeQuery();
                double cardaccount2 = (double) cardInfoNumber2.getObject(2);
                String cardcurrency2 = (String) cardInfoNumber2.getObject(3);
                cardInfoNumber2.close();

               // System.out.println("recipient");
                //System.out.println(cardaccount2);
                //System.out.println(cardcurrency2);

                double amount_plus_commission;
                double newaccount;

                //------Exchange rates ------------------------------------//
                double euro;
                double dollar;
                double rates = 0.919920887;//dollar = rates*euro
                //------Exchange rates ------------------------------------//

                double comission;
                if (cardcurrency.equals(cardcurrency2)) {//одинаковая валюта
                    comission = amount * 0.01;
                    amount_plus_commission = amount + comission;
                    if (cardaccount >= amount_plus_commission) {
                        System.out.println("Correct! Ready to transfer.");
                        newaccount = cardaccount - amount_plus_commission;
                        cardaccount = newaccount;
                        cardaccount2 = cardaccount2 + amount;

                        CallableStatement updateCard = connection.prepareCall("{call UpdateCard(?,?)}");
                        updateCard.setString(1, cardsender);
                        updateCard.setDouble(2, cardaccount);
                        updateCard.executeQuery();
                        updateCard.close();

                        CallableStatement updateCard2 = connection.prepareCall("{call UpdateCard(?,?)}");
                        updateCard2.setString(1, cardrecipient);
                        updateCard2.setDouble(2, cardaccount2);
                        updateCard2.executeQuery();
                        updateCard2.close();

                        CallableStatement cardInfoNumber3 = connection.prepareCall("{call CardInfoNumber(?,?,?)}");
                        cardInfoNumber3.setString(1, "1010101010101010");
                        cardInfoNumber3.registerOutParameter(2, Types.DOUBLE);
                        cardInfoNumber3.registerOutParameter(3, Types.VARCHAR);
                        cardInfoNumber3.executeQuery();
                        double cardaccount3 = (double) cardInfoNumber3.getObject(2);
                        String cardcurrency3 = (String) cardInfoNumber3.getObject(3);
                        cardInfoNumber3.close();

                        cardaccount3 = cardaccount3 + comission;


                        CallableStatement updateCard3 = connection.prepareCall("{call UpdateCard(?,?)}");
                        updateCard3.setString(1, "1010101010101010");
                        updateCard3.setDouble(2, cardaccount3);
                        updateCard3.executeQuery();
                        updateCard3.close();

                        //System.out.println("Card sender = " + cardaccount);
                        //System.out.println("Card recipient = " + cardaccount2);
                        req.setAttribute("update", 1);

                        String data1 = (String) session.getAttribute("data1");
                        CallableStatement updateBasket = connection.prepareCall("{call UpdateBasket(?,?)}");
                        updateBasket.setString(1, "committed");
                        updateBasket.setString(2, data1);
                        updateBasket.executeQuery();
                        updateBasket.close();


                    } else {
                        System.out.println("Error!");
                        req.setAttribute("not_enough_money",1);
                        int castid = (int) session.getAttribute("castid");

                        CallableStatement maxMinIdCards = connection.prepareCall("{call MaxMinIdCards(?,?,?)}");
                        maxMinIdCards.setInt(1, castid);
                        maxMinIdCards.registerOutParameter(2, Types.INTEGER);
                        maxMinIdCards.registerOutParameter(3, Types.INTEGER);
                        maxMinIdCards.executeQuery();

                        int maxcardid = (int) maxMinIdCards.getObject(2);
                        int mincardid = (int) maxMinIdCards.getObject(3);

                        maxMinIdCards.close();

                        ArrayList<Integer> list_of_cards_id3 = new ArrayList<Integer>();
                        for (int ccid = mincardid; ccid <= maxcardid; ccid++) {
                            CallableStatement showCards = connection.prepareCall("{call ShowCards(?,?,?)}");
                            showCards.setInt(1, ccid);
                            showCards.setInt(2, castid);
                            showCards.registerOutParameter(3, Types.INTEGER);
                            showCards.executeQuery();
                            int showcards = (int) showCards.getObject(3);
                            showCards.close();
                            if (showcards == 0) {
                                ccid++;
                            } else {
                                list_of_cards_id3.add(showcards);
                            }
                        }

                        ArrayList<String> cards_numbers3 = new ArrayList<String>();
                        ArrayList<Double> cards_accounts3 = new ArrayList<Double>();
                        ArrayList<String> cards_currencies3 = new ArrayList<String>();

                        for (int i = 0; i < 4; i++) {
                            cards_numbers3.add(null);
                            cards_currencies3.add(null);
                        }
                        for (int i = 0; i < 4; i++) {
                            cards_accounts3.add(0.0);
                        }


                        System.out.println(list_of_cards_id3.size());
                        for (int l = 0; l < list_of_cards_id3.size(); l++) {
                            CallableStatement cardInfo = connection.prepareCall("{call CardInfo(?,?,?,?)}");
                            cardInfo.setInt(1, list_of_cards_id3.get(l));
                            cardInfo.registerOutParameter(2, Types.VARCHAR);
                            cardInfo.registerOutParameter(3, Types.DOUBLE);
                            cardInfo.registerOutParameter(4, Types.VARCHAR);
                            cardInfo.executeQuery();

                            String cardnumber_basket = (String) cardInfo.getObject(2);
                            double cardaccount_basket = (double) cardInfo.getObject(3);
                            String cardcurrency_basket = (String) cardInfo.getObject(4);

                            if (cardnumber_basket.equals("none") && l >= list_of_cards_id3.size()) {
                                break;
                            }

                            if (cardnumber_basket.equals("none")) {
                                l++;
                            } else {

                                cards_currencies3.set(l, cardcurrency_basket);
                                cards_numbers3.set(l, cardnumber_basket);
                                cards_accounts3.set(l, cardaccount_basket);

                            }
                        }

                        System.out.println(cards_numbers3);

                        req.setAttribute("number(0)", cards_numbers3.get(0));
                        req.setAttribute("number(1)", cards_numbers3.get(1));
                        req.setAttribute("number(2)", cards_numbers3.get(2));
                        req.setAttribute("number(3)", cards_numbers3.get(3));

                        req.setAttribute("account(0)", cards_accounts3.get(0));
                        req.setAttribute("account(1)", cards_accounts3.get(1));
                        req.setAttribute("account(2)", cards_accounts3.get(2));
                        req.setAttribute("account(3)", cards_accounts3.get(3));

                        req.setAttribute("currency(0)", cards_currencies3.get(0));
                        req.setAttribute("currency(1)", cards_currencies3.get(1));
                        req.setAttribute("currency(2)", cards_currencies3.get(2));
                        req.setAttribute("currency(3)", cards_currencies3.get(3));
                    }
                } else {
                    System.out.println("We need to change currency!");
                    if (cardcurrency.equals("dollar") && cardcurrency2.equals("euro") && currency.equals("dollar")) {
                        amount_plus_commission = amount + amount * 0.01;
                        newaccount = cardaccount - amount_plus_commission;
                        cardaccount = newaccount;
                        dollar = amount;
                        euro = dollar / rates;
                        amount = euro;
                        cardaccount2 = cardaccount2 + amount;
                    }
                    if (cardcurrency.equals("euro") && cardcurrency2.equals("dollar") && currency.equals("dollar")) {

                    }
                }

                int castid = (int) session.getAttribute("castid");

                CallableStatement maxMinIdCards = connection.prepareCall("{call MaxMinIdCards(?,?,?)}");
                maxMinIdCards.setInt(1, castid);
                maxMinIdCards.registerOutParameter(2, Types.INTEGER);
                maxMinIdCards.registerOutParameter(3, Types.INTEGER);
                maxMinIdCards.executeQuery();

                int maxcardid = (int) maxMinIdCards.getObject(2);
                int mincardid = (int) maxMinIdCards.getObject(3);

                maxMinIdCards.close();

                ArrayList<Integer> list_of_cards_id = new ArrayList<Integer>();
                for (int ccid = mincardid; ccid <= maxcardid; ccid++) {
                    CallableStatement showCards = connection.prepareCall("{call ShowCards(?,?,?)}");
                    showCards.setInt(1, ccid);
                    showCards.setInt(2, castid);
                    showCards.registerOutParameter(3, Types.INTEGER);
                    showCards.executeQuery();
                    int showcards = (int) showCards.getObject(3);
                    showCards.close();
                    if (showcards == 0) {
                        ccid++;
                    } else {
                        list_of_cards_id.add(showcards);
                    }
                }

                ArrayList<String> cards_numbers = new ArrayList<String>();
                ArrayList<Double> cards_accounts = new ArrayList<Double>();
                ArrayList<String> cards_currencies = new ArrayList<String>();

                for (int i = 0; i < 4; i++) {
                    cards_numbers.add(null);
                    cards_currencies.add(null);
                }
                for (int i = 0; i < 4; i++) {
                    cards_accounts.add(0.0);
                }


                System.out.println(list_of_cards_id.size());
                for (int k = 0; k < list_of_cards_id.size(); k++) {
                    CallableStatement cardInfo = connection.prepareCall("{call CardInfo(?,?,?,?)}");
                    cardInfo.setInt(1, list_of_cards_id.get(k));
                    cardInfo.registerOutParameter(2, Types.VARCHAR);
                    cardInfo.registerOutParameter(3, Types.DOUBLE);
                    cardInfo.registerOutParameter(4, Types.VARCHAR);
                    cardInfo.executeQuery();

                    String cardnumber_basket = (String) cardInfo.getObject(2);
                    double cardaccount_basket = (double) cardInfo.getObject(3);
                    String cardcurrency_basket = (String) cardInfo.getObject(4);

                    if (cardnumber_basket.equals("none") && k >= list_of_cards_id.size()) {
                        break;
                    }

                    if (cardnumber_basket.equals("none")) {
                        k++;
                    } else {

                        cards_currencies.set(k, cardcurrency_basket);
                        cards_numbers.set(k, cardnumber_basket);
                        cards_accounts.set(k, cardaccount_basket);

                    }
                }

                System.out.println(cards_numbers);

                req.setAttribute("number(0)", cards_numbers.get(0));
                req.setAttribute("number(1)", cards_numbers.get(1));
                req.setAttribute("number(2)", cards_numbers.get(2));
                req.setAttribute("number(3)", cards_numbers.get(3));

                req.setAttribute("account(0)", cards_accounts.get(0));
                req.setAttribute("account(1)", cards_accounts.get(1));
                req.setAttribute("account(2)", cards_accounts.get(2));
                req.setAttribute("account(3)", cards_accounts.get(3));

                req.setAttribute("currency(0)", cards_currencies.get(0));
                req.setAttribute("currency(1)", cards_currencies.get(1));
                req.setAttribute("currency(2)", cards_currencies.get(2));
                req.setAttribute("currency(3)", cards_currencies.get(3));


                ////////////

                CallableStatement maxmincustomerbasketid = connection.prepareCall("{call MaxMinCustomerBasketId(?,?,?)}");
                maxmincustomerbasketid.setInt(1, castid);
                maxmincustomerbasketid.registerOutParameter(2, Types.INTEGER);
                maxmincustomerbasketid.registerOutParameter(3, Types.INTEGER);
                maxmincustomerbasketid.executeQuery();
                int maxcustbaskid = (int) maxmincustomerbasketid.getObject(2);
                int mincustbaskid = (int) maxmincustomerbasketid.getObject(3);

               // System.out.println("maxcustbaskid = " + maxcustbaskid);
                //System.out.println("mincustbaskid = " + mincustbaskid);

                if (maxcustbaskid == 0) {
                    System.out.println("Error!");
                } else {
                    ArrayList<Integer> castbaskid_list2 = new ArrayList<Integer>();
                    for (int j = mincustbaskid; j <= maxcustbaskid; j++) {
                        CallableStatement getBasketId = connection.prepareCall("{call GetBasketId(?,?,?)}");
                        getBasketId.setInt(1, castid);
                        getBasketId.setInt(2, j);
                        getBasketId.registerOutParameter(3, Types.INTEGER);
                        getBasketId.executeQuery();
                        int basketid = (int) getBasketId.getObject(3);
                        if ((basketid == 0) && (j > maxcustbaskid)) {
                            break;
                        } else {
                            if ((basketid == 0) && (j < maxcustbaskid)) {
                                j++;
                            } else {
                                castbaskid_list2.add(basketid);
                            }
                        }
                    }

                    ArrayList<String> CardsSenderBasketList = new ArrayList<String>();
                    ArrayList<String> RecipientsBasketList = new ArrayList<String>();
                    ArrayList<Double> AmountsBasketList = new ArrayList<Double>();
                    ArrayList<String> CurrenciesBasketList = new ArrayList<String>();
                    ArrayList<String> DataBasketList = new ArrayList<String>();


                    for (int j = 0; j < 10; j++) {
                        CardsSenderBasketList.add(null);
                        RecipientsBasketList.add(null);
                        AmountsBasketList.add(null);
                        CurrenciesBasketList.add(null);
                        DataBasketList.add(null);
                    }


                    //System.out.println("castbaskid_list2 > " + castbaskid_list2);

                    for (int j = 0; j < castbaskid_list2.size(); j++) {
                        int lineid = castbaskid_list2.get(j);
                        CallableStatement basketLines2 = connection.prepareCall("{call BasketLines(?,?,?,?,?,?)}");
                        basketLines2.setInt(1, lineid);
                        basketLines2.registerOutParameter(2, Types.VARCHAR);
                        basketLines2.registerOutParameter(3, Types.VARCHAR);
                        basketLines2.registerOutParameter(4, Types.DOUBLE);
                        basketLines2.registerOutParameter(5, Types.VARCHAR);
                        basketLines2.registerOutParameter(6, Types.VARCHAR);
                        basketLines2.executeQuery();
                        String cardsender_basket = (String) basketLines2.getObject(2);
                        String recipient_basket = (String) basketLines2.getObject(3);
                        double amount_basket = (double) basketLines2.getObject(4);
                        String currency_basket = (String) basketLines2.getObject(5);
                        String dataofoperation = (String) basketLines2.getObject(6);

                        if (amount_basket == 0.0) {
                            AmountsBasketList.set(j, null);
                            RecipientsBasketList.set(j, null);
                            CardsSenderBasketList.set(j, null);
                            CurrenciesBasketList.set(j, null);
                            DataBasketList.set(j, null);
                        } else {
                            AmountsBasketList.set(j, amount_basket);
                            RecipientsBasketList.set(j, recipient_basket);
                            CardsSenderBasketList.set(j, cardsender_basket);
                            CurrenciesBasketList.set(j, currency_basket);
                            DataBasketList.set(j, dataofoperation);
                        }
                    }


                    req.setAttribute("bsender(0)", CardsSenderBasketList.get(0));
                    req.setAttribute("bsender(1)", CardsSenderBasketList.get(1));
                    req.setAttribute("bsender(2)", CardsSenderBasketList.get(2));
                    req.setAttribute("bsender(3)", CardsSenderBasketList.get(3));
                    req.setAttribute("bsender(4)", CardsSenderBasketList.get(4));
                    req.setAttribute("bsender(5)", CardsSenderBasketList.get(5));
                    req.setAttribute("bsender(6)", CardsSenderBasketList.get(6));
                    req.setAttribute("bsender(7)", CardsSenderBasketList.get(7));
                    req.setAttribute("bsender(8)", CardsSenderBasketList.get(8));
                    req.setAttribute("bsender(9)", CardsSenderBasketList.get(9));

                    req.setAttribute("brecipient(0)", RecipientsBasketList.get(0));
                    req.setAttribute("brecipient(1)", RecipientsBasketList.get(1));
                    req.setAttribute("brecipient(2)", RecipientsBasketList.get(2));
                    req.setAttribute("brecipient(3)", RecipientsBasketList.get(3));
                    req.setAttribute("brecipient(4)", RecipientsBasketList.get(4));
                    req.setAttribute("brecipient(5)", RecipientsBasketList.get(5));
                    req.setAttribute("brecipient(6)", RecipientsBasketList.get(6));
                    req.setAttribute("brecipient(7)", RecipientsBasketList.get(7));
                    req.setAttribute("brecipient(8)", RecipientsBasketList.get(8));
                    req.setAttribute("brecipient(9)", RecipientsBasketList.get(9));

                    req.setAttribute("bamount(0)", AmountsBasketList.get(0));
                    req.setAttribute("bamount(1)", AmountsBasketList.get(1));
                    req.setAttribute("bamount(2)", AmountsBasketList.get(2));
                    req.setAttribute("bamount(3)", AmountsBasketList.get(3));
                    req.setAttribute("bamount(4)", AmountsBasketList.get(4));
                    req.setAttribute("bamount(5)", AmountsBasketList.get(5));
                    req.setAttribute("bamount(6)", AmountsBasketList.get(6));
                    req.setAttribute("bamount(7)", AmountsBasketList.get(7));
                    req.setAttribute("bamount(8)", AmountsBasketList.get(8));
                    req.setAttribute("bamount(9)", AmountsBasketList.get(9));

                    req.setAttribute("bcurrency(0)", CurrenciesBasketList.get(0));
                    req.setAttribute("bcurrency(1)", CurrenciesBasketList.get(1));
                    req.setAttribute("bcurrency(2)", CurrenciesBasketList.get(2));
                    req.setAttribute("bcurrency(3)", CurrenciesBasketList.get(3));
                    req.setAttribute("bcurrency(4)", CurrenciesBasketList.get(4));
                    req.setAttribute("bcurrency(5)", CurrenciesBasketList.get(5));
                    req.setAttribute("bcurrency(6)", CurrenciesBasketList.get(6));
                    req.setAttribute("bcurrency(7)", CurrenciesBasketList.get(7));
                    req.setAttribute("bcurrency(8)", CurrenciesBasketList.get(8));
                    req.setAttribute("bcurrency(9)", CurrenciesBasketList.get(9));

                    req.setAttribute("data(0)", DataBasketList.get(0));
                    req.setAttribute("data(1)", DataBasketList.get(1));
                    req.setAttribute("data(2)", DataBasketList.get(2));
                    req.setAttribute("data(3)", DataBasketList.get(3));
                    req.setAttribute("data(4)", DataBasketList.get(4));
                    req.setAttribute("data(5)", DataBasketList.get(5));
                    req.setAttribute("data(6)", DataBasketList.get(6));
                    req.setAttribute("data(7)", DataBasketList.get(7));
                    req.setAttribute("data(8)", DataBasketList.get(8));
                    req.setAttribute("data(9)", DataBasketList.get(9));


                    System.out.println(DataBasketList);
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
