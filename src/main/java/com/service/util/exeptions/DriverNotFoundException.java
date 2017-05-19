package com.service.util.exeptions;

/**
 * Created by Nadia on 22.04.2017.
 */
public class DriverNotFoundException extends RuntimeException{
    public DriverNotFoundException(String s, ClassNotFoundException e) {
        super(s,e);
    }
}
