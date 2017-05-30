package com.service.controller;

import com.service.methodRSA;
import com.service.model.Keys;
import com.service.service.CustomerService;
import com.service.service.CustomerServiceImpl;
import com.service.service.KeysService;
import com.service.service.KeysServiceImpl;
import com.service.util.DBManager;

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

/**
 * Created by Nadia on 22.05.2017.
 */
@WebServlet(name="GeneratedKeysServlet", urlPatterns = "/GeneratedKeysServlet")
public class GeneratedKeysServlet extends HttpServlet {

    private CustomerService customerService;
    private KeysService keysService;
    public GeneratedKeysServlet() {
        this.customerService = new CustomerServiceImpl();
        this.keysService = new KeysServiceImpl();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        ArrayList<BigInteger> new_keys = methodRSA.generateKeys();
        System.out.println(new_keys);

        try {
            DBManager dbManager = DBManager.getInstance();
            Connection connection = dbManager.getConnection();


            CallableStatement amountOfCustomers = connection.prepareCall("{call AmountOfCustomers(?,?)");

            amountOfCustomers.registerOutParameter(1, Types.INTEGER);
            amountOfCustomers.registerOutParameter(2, Types.INTEGER);
            amountOfCustomers.executeQuery();

            int maxcustid = (int) amountOfCustomers.getObject(1);
            int mincustid = (int) amountOfCustomers.getObject(2);
            amountOfCustomers.close();

            if (maxcustid != 0) {
                for (int i = mincustid; i <= maxcustid; i++) {

                    CallableStatement getAllCustomers = connection.prepareCall("{call GetAllCustomers(?,?,?,?,?,?)");
                    getAllCustomers.setInt(1, i);
                    getAllCustomers.registerOutParameter(2, Types.VARCHAR);
                    getAllCustomers.registerOutParameter(3, Types.VARCHAR);
                    getAllCustomers.registerOutParameter(4, Types.VARCHAR);
                    getAllCustomers.registerOutParameter(5, Types.VARCHAR);
                    getAllCustomers.registerOutParameter(6, Types.VARCHAR);
                    getAllCustomers.executeQuery();

                    String custname = (String) getAllCustomers.getObject(2);
                    String custsecondname = (String) getAllCustomers.getObject(3);
                    String custlogin = (String) getAllCustomers.getObject(4);
                    String custpass = (String) getAllCustomers.getObject(5);
                    String custstatus = (String) getAllCustomers.getObject(6);
                    getAllCustomers.close();

            /////uncode
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


            System.out.println(privatekey);
            System.out.println(publickeyn);

            String uncodelisting = methodRSA.uncodeList(custpass, privatekey, BigInteger.valueOf(publickeyn));
                    System.out.println("uncodelisting = " + uncodelisting);

                    ArrayList<BigInteger> code = methodRSA.codeList(uncodelisting, new_keys.get(0).intValue(),new_keys.get(2),new_keys.get(3).intValue());

                    System.out.println("code = " + code);

                    String codelisting = "";
                    for (int j = 0; j < code.size(); j++) {
                        codelisting = codelisting + code.get(j) + "-";
                    }

                    System.out.println("codelisting = " + codelisting);

                    CallableStatement updateCustPassWithNewKey = connection.prepareCall("{call UpdateCustPassWithNewKey(?,?)");
                    updateCustPassWithNewKey.setInt(1, i);
                    updateCustPassWithNewKey.setString(2, codelisting);
                    updateCustPassWithNewKey.executeQuery();
                    updateCustPassWithNewKey.close();


                }
    }

            CallableStatement updateKeys = connection.prepareCall("{call UpdateKeys()");
            updateKeys.executeQuery();
            updateKeys.close();

            //////new keys
            Keys keys = new Keys(new_keys.get(0).intValue(), new_keys.get(1).intValue(), new_keys.get(2).intValue(), new_keys.get(3).intValue());
            this.keysService.create(keys);

            ////new code

            req.setAttribute("generatedKeys", 1);

            req.getRequestDispatcher("adminPage.jsp").forward(req, resp);

        }catch(SQLException e) {
            e.printStackTrace();
        }

    }
}
