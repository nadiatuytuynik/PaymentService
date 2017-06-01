package com.service.controller;

import com.service.generateMobilePassword;
import com.service.model.Customer;
import com.service.model.Message;
import com.service.model.Phone_message;
import com.service.service.*;
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


@WebServlet(name="AddCardServlet", urlPatterns = "/AddCardServlet")
public class AddCardServlet extends HttpServlet {

    private MessageService messageService;
    private Phone_messageService phone_messageService;
    public AddCardServlet() {
        this.messageService = new MessageServiceImpl();
        this.phone_messageService = new Phone_messageServiceImpl();
    }


    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String cardname = req.getParameter("Card_name");
        String cardnumber = req.getParameter("Card_number");
        String cardvalidity = req.getParameter("Validity");
        String cardcvv2code = req.getParameter("CVV2_code");
        Double cardaccount = 0.0;
        String cardcurrency = "dollar";

        int castid = (int)session.getAttribute("castid");//достала id  пользователя через сессию
        String castname = (String)session.getAttribute("name");
        String castsecondname = (String)session.getAttribute("secondname");

        int value = (int)session.getAttribute("value");

        req.setAttribute("name", castname);
        req.setAttribute("secondname", castsecondname);//вывод

        session.setAttribute("castid", castid);
        session.setAttribute("name", castname);
        session.setAttribute("secondname", castsecondname);//передача на проверку в другой сервлет

        session.setAttribute("cardname", cardname);
        session.setAttribute("cardnumber", cardnumber);
        session.setAttribute("cardvalidity", cardvalidity);//передача на проверку в другой сервлет
        session.setAttribute("cardcvv2code", cardcvv2code);
        session.setAttribute("cardaccount", cardaccount);
        session.setAttribute("cardcurrency", cardcurrency);

        if(value ==1) {
            try {
                DBManager dbManager = DBManager.getInstance();
                Connection connection = dbManager.getConnection();

                req.setAttribute("checking", 1);
                session.setAttribute("value",1);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(value ==2) {
            try {
                DBManager dbManager = DBManager.getInstance();
                Connection connection = dbManager.getConnection();
                req.setAttribute("checking", 1);
                session.setAttribute("value",2);

                String number0 = (String)session.getAttribute("number(0)");
                String number1 = (String)session.getAttribute("number(1)");
                String number2 = (String)session.getAttribute("number(2)");
                String number3 = (String)session.getAttribute("number(3)");

                double account0 = (double)session.getAttribute("account(0)");
                double account1 = (double)session.getAttribute("account(1)");
                double account2 = (double)session.getAttribute("account(2)");
                double account3 = (double)session.getAttribute("account(3)");

                String currency0 = (String)session.getAttribute("currency(0)");
                String currency1 = (String)session.getAttribute("currency(1)");
                String currency2 = (String)session.getAttribute("currency(2)");
                String currency3 = (String)session.getAttribute("currency(3)");


                req.setAttribute("number(0)", number0);
                req.setAttribute("number(1)", number1);
                req.setAttribute("number(2)",number2);
                req.setAttribute("number(3)",number3);

                req.setAttribute("account(0)",account0);//вывод
                req.setAttribute("account(1)",account1);
                req.setAttribute("account(2)",account2);
                req.setAttribute("account(3)",account3);

                req.setAttribute("currency(0)",currency0);
                req.setAttribute("currency(1)",currency1);
                req.setAttribute("currency(2)",currency2);
                req.setAttribute("currency(3)",currency3);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        int pass = generateMobilePassword.pass();
        String pass2 = Integer.toString(pass);
        Message message = new Message(pass2);
        this.messageService.create(message);


        try {
            DBManager dbManager = DBManager.getInstance();
            Connection connection = dbManager.getConnection();
            CallableStatement lastCustomerId = connection.prepareCall("{call LastCustomerId(?)}");
            lastCustomerId.registerOutParameter(1, Types.INTEGER);
            lastCustomerId.executeQuery();
            int lastcustid = (int) lastCustomerId.getObject(1);
            lastCustomerId.close();

            CallableStatement customerPhone = connection.prepareCall("{call CustomerPhone(?)}");
            customerPhone.setInt(1, lastcustid);
            customerPhone.registerOutParameter(2, Types.INTEGER);
            customerPhone.executeQuery();
            int phoneid = (int) lastCustomerId.getObject(1);
            customerPhone.close();

            Phone_message phone_message = new Phone_message(lastcustid, phoneid);
            this.phone_messageService.create(phone_message);


        }catch (SQLException e) {
            e.printStackTrace();
        }


        session.setAttribute("pass", pass);

        req.setAttribute("authorize", 1);
        req.getRequestDispatcher("index.jsp").forward(req,resp);

    }


}
