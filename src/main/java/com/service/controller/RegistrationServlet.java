package com.service.controller;

import com.service.model.Customer;
import com.service.service.CustomerService;
import com.service.service.CustomerServiceImpl;
import com.service.util.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

@WebServlet(name="RegistrationServlet", urlPatterns = "/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    private CustomerService customerService;
    public RegistrationServlet() {this.customerService = new CustomerServiceImpl();}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("Name");
        String secondName = request.getParameter("Secondname");
        String login = request.getParameter("Login");
        String password = request.getParameter("Password");
        
        try {
            DBManager dbManager = DBManager.getInstance();
            Connection connection = dbManager.getConnection();
            CallableStatement pc = connection.prepareCall("{call Par(?,?)}");
            pc.setString(1, login);
            pc.registerOutParameter(2, Types.INTEGER);
            pc.executeQuery();
            boolean user = (int)pc.getObject(2) != 1;
            pc.close();

            if(user){
                Customer customer = new Customer(name, secondName, login, password);
                this.customerService.create(customer);
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