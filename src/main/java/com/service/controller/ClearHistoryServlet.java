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


@WebServlet(name="ClearHistoryServlet", urlPatterns = "/ClearHistoryServlet")
public class ClearHistoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int castid = (int)session.getAttribute("castid");

        try {
            DBManager dbManager = DBManager.getInstance();
            Connection connection = dbManager.getConnection();

            CallableStatement customerName = connection.prepareCall("{call CustomerName(?,?,?)}");
            customerName.setInt(1, castid);
            customerName.registerOutParameter(2, Types.VARCHAR);
            customerName.registerOutParameter(3, Types.VARCHAR);
            customerName.executeQuery();

            String castname = (String)customerName.getObject(2);
            String castsecondname = (String)customerName.getObject(3);
            customerName.close();

            req.setAttribute("castname", castname);
            req.setAttribute("castsecondname", castsecondname);

            req.setAttribute("authorize", 1);
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
                    if (showcards == 0) {
                        ccid++;
                    } else {
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


            }

            CallableStatement clearHistory = connection.prepareCall("{call ClearHistory(?)}");
            clearHistory.setInt(1, castid);
            clearHistory.executeQuery();
            clearHistory.close();

            req.setAttribute("cleared",1);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("index.jsp").forward(req,resp);

    }
}
