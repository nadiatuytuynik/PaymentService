package com.service.controller;

import com.service.util.DBManager;

import javax.servlet.ServletConfig;
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

        try {
            DBManager dbManager = DBManager.getInstance();
            Connection connection = dbManager.getConnection();
            CallableStatement existUser = connection.prepareCall("{call ExistUser(?,?,?)}");
            existUser.setString(1, log);
            existUser.setString(2, pass);
            existUser.registerOutParameter(3, Types.INTEGER);
            existUser.executeQuery();

            int authorize = (int)existUser.getObject(3);
            existUser.close();

            if(authorize==1){
                req.setAttribute("authorize",1);
                CallableStatement userName = connection.prepareCall("{call UserName(?,?,?,?,?)}");
                userName.setString(1, log);
                userName.setString(2, pass);
                userName.registerOutParameter(3, Types.VARCHAR);
                userName.registerOutParameter(4, Types.VARCHAR);
                userName.registerOutParameter(5, Types.INTEGER);
                userName.executeQuery();

                String castname = (String)userName.getObject(3);
                String castsecondname = (String)userName.getObject(4);
                int castid = (int)userName.getObject(5);
                userName.close();

                session.setAttribute("castid", castid);

                CallableStatement amountOfCards = connection.prepareCall("{call AmountOfCards(?,?)}");
                amountOfCards.setInt(1, castid);
                amountOfCards.registerOutParameter(2, Types.INTEGER);
                amountOfCards.executeQuery();

                int amountCards = (int)amountOfCards.getObject(2);
                amountOfCards.close();

                req.setAttribute("name",castname);
                req.setAttribute("secondname",castsecondname);

                if(amountCards == 0){
                    req.setAttribute("value",1);
                    session.setAttribute("value",1);
                    System.out.println("He has no cards!");

                    req.setAttribute("number",null);
                    req.setAttribute("account",null);
                    req.setAttribute("currency",null);
                }else {
                    req.setAttribute("val", 1);
                    session.setAttribute("value", 2);
                    System.out.println("He has 1 or more cards!");

                    CallableStatement maxMinIdCards = connection.prepareCall("{call MaxMinIdCards(?,?,?)}");
                    maxMinIdCards.setInt(1, castid);
                    maxMinIdCards.registerOutParameter(2, Types.INTEGER);
                    maxMinIdCards.registerOutParameter(3, Types.INTEGER);
                    maxMinIdCards.executeQuery();

                    int maxcardid = (int)maxMinIdCards.getObject(2);
                    int mincardid = (int)maxMinIdCards.getObject(3);

                    maxMinIdCards.close();

                    ArrayList<Integer> list_of_cards_id = new ArrayList<Integer>();
                    for (int ccid = mincardid; ccid <= maxcardid; ccid++){
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

                    for(int i = 0; i<4; i++){
                    cards_numbers.add(null);
                    cards_currencies.add(null);}
                    for(int i = 0; i<4; i++){
                        cards_accounts.add(0.0);
                      }


                    int i = 0;
                    while (i<list_of_cards_id.size()) {
                        CallableStatement cardInfo = connection.prepareCall("{call CardInfo(?,?,?,?)}");
                        cardInfo.setInt(1, list_of_cards_id.get(i));
                        cardInfo.registerOutParameter(2, Types.VARCHAR);
                        cardInfo.registerOutParameter(3, Types.DOUBLE);
                        cardInfo.registerOutParameter(4, Types.VARCHAR);
                        cardInfo.executeQuery();
                        String cardnumber = (String) cardInfo.getObject(2);
                        cards_numbers.set(i,cardnumber);
                        double cardaccount = (double) cardInfo.getObject(3);
                        cards_accounts.set(i,cardaccount);
                        String cardcurrency = (String) cardInfo.getObject(4);
                        cards_currencies.set(i,cardcurrency);
                        i++;
                    }

                    req.setAttribute("number(0)", cards_numbers.get(0));
                    req.setAttribute("number(1)",cards_numbers.get(1));
                    req.setAttribute("number(2)",cards_numbers.get(2));
                    req.setAttribute("number(3)",cards_numbers.get(3));

                    req.setAttribute("account(0)",cards_accounts.get(0));
                    req.setAttribute("account(1)",cards_accounts.get(1));
                    req.setAttribute("account(2)",cards_accounts.get(2));
                    req.setAttribute("account(3)",cards_accounts.get(3));

                    req.setAttribute("currency(0)",cards_currencies.get(0));
                    req.setAttribute("currency(1)",cards_currencies.get(1));
                    req.setAttribute("currency(2)",cards_currencies.get(2));
                    req.setAttribute("currency(3)",cards_currencies.get(3));

                    ///////
                    session.setAttribute("number(0)", cards_numbers.get(0));
                    session.setAttribute("number(1)", cards_numbers.get(1));
                    session.setAttribute("number(2)",cards_numbers.get(2));
                    session.setAttribute("number(3)",cards_numbers.get(3));

                    session.setAttribute("account(0)",cards_accounts.get(0));
                    session.setAttribute("account(1)",cards_accounts.get(1));
                    session.setAttribute("account(2)",cards_accounts.get(2));
                    session.setAttribute("account(3)",cards_accounts.get(3));

                    session.setAttribute("currency(0)",cards_currencies.get(0));
                    session.setAttribute("currency(1)",cards_currencies.get(1));
                    session.setAttribute("currency(2)",cards_currencies.get(2));
                    session.setAttribute("currency(3)",cards_currencies.get(3));



                    CallableStatement maxmincustomerbasketid = connection.prepareCall("{call MaxMinCustomerBasketId(?,?,?)}");
                    maxmincustomerbasketid.setInt(1, castid);
                    maxmincustomerbasketid.registerOutParameter(2, Types.INTEGER);
                    maxmincustomerbasketid.registerOutParameter(3, Types.INTEGER);
                    maxmincustomerbasketid.executeQuery();
                    int maxcustbaskid = (int) maxmincustomerbasketid.getObject(2);
                    int mincustbaskid = (int) maxmincustomerbasketid.getObject(3);


                    ArrayList<Integer> castbaskid_list = new ArrayList<Integer>();
                    for(int j = mincustbaskid; j<=maxcustbaskid; j++) {
                        CallableStatement getBasketId = connection.prepareCall("{call GetBasketId(?,?)}");
                        getBasketId.setInt(1, castid);
                        getBasketId.registerOutParameter(2, Types.INTEGER);
                        getBasketId.executeQuery();
                        int basketid = (int) getBasketId.getObject(2);
                        if(basketid == 0){
                            j++;
                        }
                        castbaskid_list.add(basketid);
                    }

                    ArrayList<String> CardsSenderBasketList = new ArrayList<String>();
                    ArrayList<String> RecipientsBasketList = new ArrayList<String>();
                    ArrayList<Double> AmountsBasketList = new ArrayList<Double>();
                    ArrayList<String> CurrenciesBasketList = new ArrayList<String>();

                    for(int j = 0; i<castbaskid_list.size(); j++) {
                        int lineid = castbaskid_list.get(j);
                        CallableStatement basketLines = connection.prepareCall("{call BasketLines(?,?,?,?,?)}");
                        basketLines.setInt(1, lineid);
                        basketLines.registerOutParameter(2, Types.VARCHAR);
                        basketLines.registerOutParameter(3, Types.VARCHAR);
                        basketLines.registerOutParameter(4, Types.DOUBLE);
                        basketLines.registerOutParameter(5, Types.VARCHAR);
                        basketLines.executeQuery();
                        String cardsender_basket = (String) basketLines.getObject(2);
                        CardsSenderBasketList.add(cardsender_basket);
                        String recipient_basket = (String) basketLines.getObject(3);
                        RecipientsBasketList.add(recipient_basket);
                        double amount_basket = (double) basketLines.getObject(4);
                        AmountsBasketList.add(amount_basket);
                        String currency_basket = (String) basketLines.getObject(5);
                        CurrenciesBasketList.add(currency_basket);
                    }

                    System.out.println(CardsSenderBasketList);
                    System.out.println(RecipientsBasketList);
                    System.out.println(AmountsBasketList);
                    System.out.println(CurrenciesBasketList);
                }




                session.setAttribute("castid",castid);
                session.setAttribute("name",castname);
                session.setAttribute("secondname",castsecondname);

            }else{
                System.out.println("Failed!");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }


}
