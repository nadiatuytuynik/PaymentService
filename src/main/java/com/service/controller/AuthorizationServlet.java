package com.service.controller;

import com.service.methodRSA;
import com.service.util.DBManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Nadia on 22.04.2017.
 */

@WebServlet(name="AuthorizationServlet", urlPatterns = "/AuthorizationServlet")
public class AuthorizationServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String log = req.getParameter("Log");
        String pass = req.getParameter("Pass");

        if(log.equals("admin@gmail.com")){
            System.out.println("Hello, Admin!");
            //req.setAttribute("admin", 1);
            req.getRequestDispatcher("adminPage.jsp").forward(req, resp);
        }else {

            try {
                DBManager dbManager = DBManager.getInstance();
                Connection connection = dbManager.getConnection();

                CallableStatement existUser = connection.prepareCall("{call ExistUser(?,?,?)}");
                existUser.setString(1, log);
                existUser.registerOutParameter(2, Types.VARCHAR);
                existUser.registerOutParameter(3, Types.INTEGER);
                existUser.executeQuery();

                String codelisting = (String) existUser.getObject(2);
                int authorize = (int) existUser.getObject(3);
                existUser.close();

                CallableStatement getKeys = connection.prepareCall("{call GetKeys(?,?,?,?)}");
                getKeys.registerOutParameter(1, Types.INTEGER);
                getKeys.registerOutParameter(2, Types.INTEGER);
                getKeys.registerOutParameter(3, Types.INTEGER);
                getKeys.registerOutParameter(4, Types.INTEGER);
                getKeys.executeQuery();

                int publickey = (int) getKeys.getObject(1);
                int privatekey = (int) getKeys.getObject(2);
                int publickeyn = (int) getKeys.getObject(3);
                int publickeyfn = (int) getKeys.getObject(4);
                getKeys.close();

                String uncodelisting = methodRSA.uncodeList(codelisting, privatekey, BigInteger.valueOf(publickeyn));

                if (pass.equals(uncodelisting)) {
                    authorize = 1;
                }
                if (authorize == 1) {
                    CallableStatement getCustomerStatus = connection.prepareCall("{call GetCustomerStatus(?,?)}");
                    getCustomerStatus.setString(1, log);
                    getCustomerStatus.registerOutParameter(2, Types.VARCHAR);
                    getCustomerStatus.executeQuery();

                    String caststatus = (String) getCustomerStatus.getObject(2);
                    getCustomerStatus.close();

                    if (caststatus.equals("block")) {
                        req.setAttribute("userblock", 1);
                    } else{

                        req.setAttribute("authorize", 1);
                        CallableStatement userName = connection.prepareCall("{call UserName(?,?,?,?,?)}");
                        userName.setString(1, log);
                        userName.setString(2, codelisting);
                        userName.registerOutParameter(3, Types.VARCHAR);
                        userName.registerOutParameter(4, Types.VARCHAR);
                        userName.registerOutParameter(5, Types.INTEGER);
                        userName.executeQuery();

                        String castname = (String) userName.getObject(3);
                        String castsecondname = (String) userName.getObject(4);
                        int castid = (int) userName.getObject(5);
                        userName.close();

                        session.setAttribute("castid", castid);

                        CallableStatement amountOfCards = connection.prepareCall("{call AmountOfCards(?,?)}");
                        amountOfCards.setInt(1, castid);
                        amountOfCards.registerOutParameter(2, Types.INTEGER);
                        amountOfCards.executeQuery();

                        int amountCards = (int) amountOfCards.getObject(2);
                        amountOfCards.close();

                        req.setAttribute("name", castname);
                        req.setAttribute("secondname", castsecondname);

                        if (amountCards == 0) {
                            req.setAttribute("value", 1);
                            session.setAttribute("value", 1);
                            System.out.println("He has no cards!");

                            req.setAttribute("number", null);
                            req.setAttribute("account", null);
                            req.setAttribute("currency", null);
                        } else {
                            req.setAttribute("val", 1);
                            session.setAttribute("value", 2);
                            System.out.println("He has 1 or more cards!");

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
                                if(showcards ==0){
                                    ccid++;
                                }else {
                                    list_of_cards_id.add(showcards);
                                }
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
                                String cardnumber = (String) cardInfo.getObject(2);
                                cards_numbers.set(i, cardnumber);
                                double cardaccount = (double) cardInfo.getObject(3);
                                cards_accounts.set(i, cardaccount);
                                String cardcurrency = (String) cardInfo.getObject(4);
                                cards_currencies.set(i, cardcurrency);
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

                            ///////
                            session.setAttribute("number(0)", cards_numbers.get(0));
                            session.setAttribute("number(1)", cards_numbers.get(1));
                            session.setAttribute("number(2)", cards_numbers.get(2));
                            session.setAttribute("number(3)", cards_numbers.get(3));

                            session.setAttribute("account(0)", cards_accounts.get(0));
                            session.setAttribute("account(1)", cards_accounts.get(1));
                            session.setAttribute("account(2)", cards_accounts.get(2));
                            session.setAttribute("account(3)", cards_accounts.get(3));

                            session.setAttribute("currency(0)", cards_currencies.get(0));
                            session.setAttribute("currency(1)", cards_currencies.get(1));
                            session.setAttribute("currency(2)", cards_currencies.get(2));
                            session.setAttribute("currency(3)", cards_currencies.get(3));


                            CallableStatement maxmincustomerbasketid = connection.prepareCall("{call MaxMinCustomerBasketId(?,?,?)}");
                            maxmincustomerbasketid.setInt(1, castid);
                            maxmincustomerbasketid.registerOutParameter(2, Types.INTEGER);
                            maxmincustomerbasketid.registerOutParameter(3, Types.INTEGER);
                            maxmincustomerbasketid.executeQuery();
                            int maxcustbaskid = (int) maxmincustomerbasketid.getObject(2);
                            int mincustbaskid = (int) maxmincustomerbasketid.getObject(3);

                            if (maxcustbaskid == 0) {
                                System.out.println("Error!");
                            } else {
                                ArrayList<Integer> castbaskid_list = new ArrayList<Integer>();
                                for (int j = mincustbaskid; j <= maxcustbaskid; j++) {
                                    CallableStatement getBasketId = connection.prepareCall("{call GetBasketId(?,?,?)}");
                                    getBasketId.setInt(1, castid);
                                    getBasketId.setInt(2, j);
                                    getBasketId.registerOutParameter(3, Types.INTEGER);
                                    getBasketId.executeQuery();
                                    int basketid = (int) getBasketId.getObject(3);
                                    System.out.println("basketid = " + basketid);
                                    if ((basketid == 0) && (j == maxcustbaskid)) {
                                        break;
                                    } else {
                                        if (basketid == 0) {
                                            j++;
                                        } else {
                                            castbaskid_list.add(basketid);
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

                                for (int j = 0; j < castbaskid_list.size(); j++) {
                                    int lineid = castbaskid_list.get(j);
                                    CallableStatement basketLines = connection.prepareCall("{call BasketLines(?,?,?,?,?,?)}");
                                    basketLines.setInt(1, lineid);
                                    basketLines.registerOutParameter(2, Types.VARCHAR);
                                    basketLines.registerOutParameter(3, Types.VARCHAR);
                                    basketLines.registerOutParameter(4, Types.DOUBLE);
                                    basketLines.registerOutParameter(5, Types.VARCHAR);
                                    basketLines.registerOutParameter(6, Types.VARCHAR);
                                    basketLines.executeQuery();
                                    String cardsender_basket = (String) basketLines.getObject(2);
                                    String recipient_basket = (String) basketLines.getObject(3);
                                    double amount_basket = (double) basketLines.getObject(4);
                                    String currency_basket = (String) basketLines.getObject(5);
                                    String dataofoperation = (String) basketLines.getObject(6);

                                    if (amount_basket == 0.0) {
                                        AmountsBasketList.set(j, null);
                                        RecipientsBasketList.set(j, null);
                                        CardsSenderBasketList.set(j, null);
                                        CurrenciesBasketList.set(j, null);
                                        DataBasketList.set(j,null);
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

                            }
                        }

                        session.setAttribute("castid", castid);
                        session.setAttribute("name", castname);
                        session.setAttribute("secondname", castsecondname);

                     }
                    }else{
                        System.out.println("Failed!");
                    }


            } catch (SQLException e) {
                e.printStackTrace();
            }


            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

}
