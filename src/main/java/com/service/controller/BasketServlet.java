package com.service.controller;

import com.service.util.DBManager;

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
        req.setAttribute("authorize", 1);


        int mobilerefill = (int) session.getAttribute("mobilerefill");

        if (mobilerefill == 1) {
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
                double cardaccount2 = (double) cardInfoNumber2.getObject(2);
                String cardcurrency2 = (String) cardInfoNumber2.getObject(3);
                cardInfoNumber2.close();

                System.out.println("recipient");
                System.out.println(cardaccount2);
                System.out.println(cardcurrency2);

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

                        CallableStatement updateCard2 = connection.prepareCall("{call UpdatePhone(?,?)}");
                        updateCard2.setString(1, phonerecipient);
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

                        System.out.println("Card sender = " + cardaccount);
                        System.out.println("Card recipient = " + cardaccount2);
                        req.setAttribute("update", 1);

                        CallableStatement updateBasket = connection.prepareCall("{call UpdateBasket(?)}");
                        updateBasket.setString(1, "committed");
                        updateBasket.executeQuery();
                        updateBasket.close();


                    } else {
                        System.out.println("Error!");
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
                    list_of_cards_id.add(showcards);
                    showCards.close();
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


                int i = 0;
                while (i < list_of_cards_id.size()) {
                    CallableStatement cardInfo = connection.prepareCall("{call CardInfo(?,?,?,?)}");
                    cardInfo.setInt(1, list_of_cards_id.get(i));
                    cardInfo.registerOutParameter(2, Types.VARCHAR);
                    cardInfo.registerOutParameter(3, Types.DOUBLE);
                    cardInfo.registerOutParameter(4, Types.VARCHAR);
                    cardInfo.executeQuery();
                    String cardnumber_basket = (String) cardInfo.getObject(2);
                    cards_numbers.set(i, cardnumber_basket);
                    double cardaccount_basket = (double) cardInfo.getObject(3);
                    cards_accounts.set(i, cardaccount_basket);
                    String cardcurrency_basket = (String) cardInfo.getObject(4);
                    cards_currencies.set(i, cardcurrency_basket);
                    i++;
                }

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

                System.out.println("sender");
                System.out.println(cardaccount);
                System.out.println(cardcurrency);


                CallableStatement cardInfoNumber2 = connection.prepareCall("{call CardInfoNumber(?,?,?)}");
                cardInfoNumber2.setString(1, cardrecipient);
                cardInfoNumber2.registerOutParameter(2, Types.DOUBLE);
                cardInfoNumber2.registerOutParameter(3, Types.VARCHAR);
                cardInfoNumber2.executeQuery();
                double cardaccount2 = (double) cardInfoNumber2.getObject(2);
                String cardcurrency2 = (String) cardInfoNumber2.getObject(3);
                cardInfoNumber2.close();

                System.out.println("recipient");
                System.out.println(cardaccount2);
                System.out.println(cardcurrency2);

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

                        System.out.println("Card sender = " + cardaccount);
                        System.out.println("Card recipient = " + cardaccount2);
                        req.setAttribute("update", 1);

                        CallableStatement updateBasket = connection.prepareCall("{call UpdateBasket(?)}");
                        updateBasket.setString(1, "committed");
                        updateBasket.executeQuery();
                        updateBasket.close();


                    } else {
                        System.out.println("Error!");
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
                    list_of_cards_id.add(showcards);
                    showCards.close();
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


                int i = 0;
                while (i < list_of_cards_id.size()) {
                    CallableStatement cardInfo = connection.prepareCall("{call CardInfo(?,?,?,?)}");
                    cardInfo.setInt(1, list_of_cards_id.get(i));
                    cardInfo.registerOutParameter(2, Types.VARCHAR);
                    cardInfo.registerOutParameter(3, Types.DOUBLE);
                    cardInfo.registerOutParameter(4, Types.VARCHAR);
                    cardInfo.executeQuery();
                    String cardnumber_basket = (String) cardInfo.getObject(2);
                    cards_numbers.set(i, cardnumber_basket);
                    double cardaccount_basket = (double) cardInfo.getObject(3);
                    cards_accounts.set(i, cardaccount_basket);
                    String cardcurrency_basket = (String) cardInfo.getObject(4);
                    cards_currencies.set(i, cardcurrency_basket);
                    i++;
                }

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


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
