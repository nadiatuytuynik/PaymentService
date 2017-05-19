package com.service.controller;

import com.service.model.Basket;
import com.service.service.BasketService;
import com.service.service.BasketServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Nadia on 10.05.2017.
 */
@WebServlet(name="MobileRefillServlet", urlPatterns = "/MobileRefillServlet")
public class MobileRefillServlet extends HttpServlet {

    private BasketService basketService;
    public MobileRefillServlet() {this.basketService = new BasketServiceImpl();}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String phonenumber = (String)req.getParameter("Phone_number");
        double amount = Double.parseDouble(req.getParameter("account"));
        String currency = (String)req.getParameter("currency");
        String status = "not committed";

        String number0 = (String)session.getAttribute("number(0)");
        String number1 = (String)session.getAttribute("number(1)");
        String number2 = (String)session.getAttribute("number(2)");
        String number3 = (String)session.getAttribute("number(3)");

        double account0 = (double)session.getAttribute("account(0)");
        double account1 = (double)session.getAttribute("account(1)");
        double account2 = (double)session.getAttribute("account(2)");
        double account3 = (double)session.getAttribute("account(3)");

        String currency0 = (String)session.getAttribute("currency(0)");
        String currency1 = (String)session.getAttribute("currency(2)");
        String currency2 = (String)session.getAttribute("currency(3)");
        String currency3 = (String)session.getAttribute("currency(4)");

        session.setAttribute("number(0)", number0);
        session.setAttribute("number(1)", number1);
        session.setAttribute("number(2)", number2);
        session.setAttribute("number(3)", number3);

        session.setAttribute("account(0)", account0);
        session.setAttribute("account(1)", account1);
        session.setAttribute("account(2)", account2);
        session.setAttribute("account(3)", account3);

        session.setAttribute("currency(0)", currency0);
        session.setAttribute("currency(1)",currency1);
        session.setAttribute("currency(2)",currency2);
        session.setAttribute("currency(3)",currency3);

        String card1 = req.getParameter("Card_sender");
        String cardsender1 = null;
        if(card1.equals("0")){
            cardsender1 = number0;
        }
        if(card1.equals("1")){
            cardsender1 = number1;
        }
        if(card1.equals("2")){
            cardsender1 = number2;
        }
        if(card1.equals("3")){
            cardsender1 = number3;
        }

        Basket basket = new Basket(cardsender1,phonenumber,amount,currency,status);
        this.basketService.create(basket);

        req.setAttribute("mobilerefill",1);
        session.setAttribute("mobilerefill",1);
        req.setAttribute("cardsender",cardsender1);
        req.setAttribute("cardrecipient", phonenumber);
        req.setAttribute("amount",amount);
        req.setAttribute("currency",currency);

        session.setAttribute("cardsender",cardsender1);
        session.setAttribute("cardrecipient", phonenumber);
        session.setAttribute("amount",amount);
        session.setAttribute("currency",currency);

        req.getRequestDispatcher("index.jsp").forward(req,resp);

    }
}