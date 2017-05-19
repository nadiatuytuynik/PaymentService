package com.service.controller;


import com.service.model.Card;
import com.service.model.Card_customer;
import com.service.service.CardService;
import com.service.service.CardServiceImpl;
import com.service.service.Card_customerService;
import com.service.service.Card_customerServiceImpl;
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


@WebServlet(name="CheckingPasswordServlet", urlPatterns = "/CheckingPasswordServlet")
public class CheckingPasswordServlet extends HttpServlet {

    private CardService cardService;
    private Card_customerService card_customerService;
    public CheckingPasswordServlet() {
        this.cardService = new CardServiceImpl();
        this.card_customerService = new Card_customerServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try{
            DBManager dbManager = DBManager.getInstance();
            Connection connection = dbManager.getConnection();

            String pass = "1234";
            String smspassword = (String)req.getParameter("sms_password");
            System.out.println(smspassword);

            int value = (int)session.getAttribute("value");

            int castid = (int) session.getAttribute("castid");
            String name = (String) session.getAttribute("name");
            String secondname = (String) session.getAttribute("secondname");

            String cardname = (String) session.getAttribute("cardname");
            String cardnumber = (String) session.getAttribute("cardnumber");
            String cardvalidity = (String) session.getAttribute("cardvalidity");
            String cardcvv2code = (String) session.getAttribute("cardcvv2code");
            double cardaccount = (double) session.getAttribute("cardaccount");
            String cardcurrency = (String) session.getAttribute("cardcurrency");

            req.setAttribute("name", name);
            req.setAttribute("secondname", secondname);//גûגמה ןנמפאיכא
            session.setAttribute("name", name);
            session.setAttribute("secondname", secondname);

            if(smspassword.equals(pass)){
                System.out.println("Correct! Ready to add card");
                req.setAttribute("smsresult", 1);
                req.setAttribute("authorize", 1);

                Card card = new Card(cardname, cardnumber, cardvalidity, cardcvv2code, cardaccount, cardcurrency);
                this.cardService.create(card);

                CallableStatement lastCard = connection.prepareCall("{call LastCard(?)}");
                lastCard.registerOutParameter(1, Types.INTEGER);
                lastCard.executeQuery();
                int cardid = (int) lastCard.getObject(1);

                Card_customer card_customer = new Card_customer(cardid,castid);
                this.card_customerService.create(card_customer);
                if(value ==2){
                    System.out.println("He has 1 or more cards!");
                    CallableStatement amountOfCards = connection.prepareCall("{call AmountOfCards(?,?)}");
                    amountOfCards.setInt(1, castid);
                    amountOfCards.registerOutParameter(2, Types.INTEGER);
                    amountOfCards.executeQuery();

                    int amountCards = (int)amountOfCards.getObject(2);
                    amountOfCards.close();

                    if(amountCards !=0) {
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
                            list_of_cards_id2.add(showcards);
                            showCards.close();
                        }

                        ArrayList<String> cards_numbers2 = new ArrayList<String>();
                        ArrayList<Double> cards_accounts2 = new ArrayList<Double>();
                        ArrayList<String> cards_currencies2 = new ArrayList<String>();

                        System.out.println(cards_numbers2);
                        System.out.println(cards_accounts2);
                        System.out.println(cards_currencies2);

                        for (int i = 0; i < 4; i++) {
                            cards_numbers2.add(null);
                            cards_currencies2.add(null);
                        }
                        for (int i = 0; i < 4; i++) {
                            cards_accounts2.add(0.0);
                        }


                        int i = 0;
                        while (i < list_of_cards_id2.size()) {
                            CallableStatement cardInfo = connection.prepareCall("{call CardInfo(?,?,?,?)}");
                            cardInfo.setInt(1, list_of_cards_id2.get(i));
                            cardInfo.registerOutParameter(2, Types.VARCHAR);
                            cardInfo.registerOutParameter(3, Types.DOUBLE);
                            cardInfo.registerOutParameter(4, Types.VARCHAR);
                            cardInfo.executeQuery();
                            String cardnumber2 = (String) cardInfo.getObject(2);
                            cards_numbers2.set(i, cardnumber2);
                            double cardaccount2 = (double) cardInfo.getObject(3);
                            cards_accounts2.set(i, cardaccount2);
                            String cardcurrency2 = (String) cardInfo.getObject(4);
                            cards_currencies2.set(i, cardcurrency2);
                            i++;
                        }

                        req.setAttribute("number(0)", cards_numbers2.get(0));
                        req.setAttribute("number(1)", cards_numbers2.get(1));
                        req.setAttribute("number(2)", cards_numbers2.get(2));
                        req.setAttribute("number(3)", cards_numbers2.get(3));

                        req.setAttribute("account(0)", cards_accounts2.get(0));
                        req.setAttribute("account(1)", cards_accounts2.get(1));
                        req.setAttribute("account(2)", cards_accounts2.get(2));
                        req.setAttribute("account(3)", cards_accounts2.get(3));
                        System.out.println(list_of_cards_id2);
                    }else{
                        System.out.println("Amount of cards is 0!");
                    }
                }else{
                    System.out.println("He didn't has cards. But now he has!");
                }
            }else{
                System.out.println("Password is not correct!");
                req.setAttribute("smsresult", null);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("index.jsp").forward(req,resp);

    }

}