package com.service.util;

import com.service.util.exeptions.DriverNotFoundException;
import com.service.util.exeptions.PropertyccessException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Nadia on 22.04.2017.
 */
public class DBManager {//подключение к базе данных
    private static final String USER_NAME = "jdbc.username";
    private static final String PASSWORD = "jdbc.password";
    private static final String URL = "jdbc.url";
    private static final String DRIVER = "jdbc.driver";

    private static final String ORACLE_PROPERTIES = "/oracle.properties";
    private static final String QUERY_PROPERTIES = "/query.properties";

    private Properties env;
    private Properties queries;

    private static DBManager instance;

    public static DBManager getInstance(){//проверка на то .что подключения еще нет. тогда и подключить
        if(instance==null){
            instance=new DBManager();
        }
        return instance;
    }


    //////
    public Connection getConnection() throws SQLException {//подключение по параметрам .которые нужны для подключения

        return DriverManager.getConnection(env.getProperty(URL), env.getProperty(USER_NAME), env.getProperty(PASSWORD));
    }
    ///////


    public String getQuery(String name){//метод скачивания SQL-запросов
        if(this.queries == null)
            this.queries = this.loadProperties(QUERY_PROPERTIES);

        return this.queries.getProperty(name);
    }

    private DBManager(){
        env = this.loadProperties(ORACLE_PROPERTIES);
        this.loadDriver();
    }

    private void loadDriver() {
        try{
            Class.forName(env.getProperty(DRIVER));
        }catch(ClassNotFoundException e){
            throw new DriverNotFoundException("JDBC driver is missing " + env.getProperty(DRIVER),e);
        }
    }

    private Properties loadProperties(String location){
        try(InputStream in = this.getClass().getResourceAsStream(location)){

            Properties prop = new Properties();
            prop.load(in);
            return prop;
        } catch (IOException e){
            throw new PropertyccessException("Failed to load Properties: " + location, e);
        }
    }
}
