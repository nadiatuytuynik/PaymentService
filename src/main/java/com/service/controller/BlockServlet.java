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

/**
 * Created by Nadia on 22.05.2017.
 */
@WebServlet(name="BlockServlet", urlPatterns = "/BlockServlet")
public class BlockServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String custlogin = req.getParameter("Customer_login");

        DBManager dbManager = DBManager.getInstance();
        try {
            Connection connection = dbManager.getConnection();

            CallableStatement customerBlock = connection.prepareCall("{call CustomerBlock(?)");
            customerBlock.setString(1, custlogin);

            customerBlock.executeQuery();
            customerBlock.close();

            req.setAttribute("block",1);


        } catch (SQLException e) {
            e.printStackTrace();
        }


        req.getRequestDispatcher("adminPage.jsp").forward(req,resp);

    }
}
