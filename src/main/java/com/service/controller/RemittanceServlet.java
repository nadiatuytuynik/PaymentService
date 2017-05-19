package com.service.controller;

import com.service.model.Basket;
import com.service.model.Customer_basket;
import com.service.service.BasketService;
import com.service.service.BasketServiceImpl;
import com.service.service.Customer_basketService;
import com.service.service.Customer_basketServiceImpl;
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

@WebServlet(name="RemittanceServlet", urlPatterns = "/RemittanceServlet")
public class RemittanceServlet extends HttpServlet {

    private BasketService basketService;
    private Customer_basketService customer_basketService;
    public RemittanceServlet() {
        this.basketService = new BasketServiceImpl();
        this.customer_basketService = new Customer_basketServiceImpl();
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setAttribute("authorize", 1);
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

        String card1 = req.getParameter("cards");
        System.out.println("Result is > " + card1);
        String cardsender = null;
        if(card1.equals("0")){
            cardsender = number0;
        }
        if(card1.equals("1")){
            cardsender = number1;
        }
        if(card1.equals("2")){
            cardsender = number2;
        }
        if(card1.equals("3")){
            cardsender = number3;
        }
        String cardrecipient = req.getParameter("Card_2");
        double amount = Double.parseDouble(req.getParameter("amount"));
        String currency = req.getParameter("currency");

        System.out.println(cardsender);
        System.out.println(cardrecipient);
        System.out.println(amount);
        System.out.println(currency);

        try {
            DBManager dbManager = DBManager.getInstance();
            Connection connection = dbManager.getConnection();
            CallableStatement existCard = connection.prepareCall("{call ExistCard(?,?)}");
            existCard.setString(1, cardrecipient);
            existCard.registerOutParameter(2, Types.INTEGER);
            existCard.executeQuery();
            int existcard = (int) existCard.getObject(2);
            existCard.close();

            System.out.println(existcard);

            if(existcard != 0){
                req.setAttribute("Basket",1);
                req.setAttribute("cardsender",cardsender);
                req.setAttribute("cardrecipient", cardrecipient);
                req.setAttribute("amount", amount);
                req.setAttribute("currency", currency);

                session.setAttribute("cardsender", cardsender);
                session.setAttribute("cardrecipient", cardrecipient);
                session.setAttribute("amount", amount);
                session.setAttribute("currency", currency);
                String status = "not active";

                int castid = (int)session.getAttribute("castid");
                Basket basket = new Basket(cardsender,cardrecipient,amount,currency,status);
                this.basketService.create(basket);

                CallableStatement lastBasketLine = connection.prepareCall("{call LastBasketId(?)}");
                lastBasketLine.registerOutParameter(1, Types.INTEGER);
                lastBasketLine.executeQuery();
                int maxbasketid= (int) lastBasketLine.getObject(1);
                lastBasketLine.close();

                Customer_basket customer_basket = new Customer_basket(castid,maxbasketid);
                this.customer_basketService.create(customer_basket);


            }else{
                System.out.println("Card recipient does not exist!");
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        session.setAttribute("mobilerefill", 0);
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
