package com.service.controller;

import com.service.methodRSA;
import com.service.model.Customer;
import com.service.model.Keys;
import com.service.model.Phone;
import com.service.service.*;
import com.service.util.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

@WebServlet(name="RegistrationServlet", urlPatterns = "/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    private CustomerService customerService;
    private PhoneService phoneService;
    public RegistrationServlet() {
        this.customerService = new CustomerServiceImpl();
        this.phoneService = new PhoneServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("Name");
        String secondName = request.getParameter("Secondname");
        String login = request.getParameter("Login");
        String password = request.getParameter("Password");
        String customer_status = request.getParameter("Status");
        String phone_number = request.getParameter("Phone_number");

        String status = "unblock";
        if(customer_status.equals("admin")){
            status = "admin";
        }
        
        try {
            DBManager dbManager = DBManager.getInstance();
            Connection connection = dbManager.getConnection();
            CallableStatement pc = connection.prepareCall("{call ExistSameLogin(?,?)}");
            pc.setString(1, login);
            pc.registerOutParameter(2, Types.INTEGER);
            pc.executeQuery();
            boolean user = (int)pc.getObject(2) != 1;
            pc.close();

            if(user){

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

                ArrayList<BigInteger> code = methodRSA.codeList(password, publickey, BigInteger.valueOf(publickeyn),publickeyfn);

                String codelisting = "";
                for (int i = 0; i < code.size(); i++) {
                    codelisting = codelisting + code.get(i) + "-";
                }


                Customer customer = new Customer(name, secondName, login, codelisting, status);
                this.customerService.create(customer);

                CallableStatement lastCustomerId = connection.prepareCall("{call LastCustomerId(?)}");
                lastCustomerId.registerOutParameter(1, Types.INTEGER);
                lastCustomerId.executeQuery();
                int lastcustid = (int) lastCustomerId.getObject(1);
                lastCustomerId.close();

                Phone phone = new Phone(lastcustid, "Kyivstar", phone_number, 0.0, "dollar");
                this.phoneService.create(phone);

                request.setAttribute("param1", "Successfully");
                System.out.println("Successfully");
            }else{
                request.setAttribute("param2", "Filed!");
                System.out.println("Failed!");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("index.jsp").forward(request,response);   //для перехода на страницу по завершени работы сервлета
    }
}