package com.service.util.exeptions;

import java.io.IOException;

/**
 * Created by Nadia on 22.04.2017.
 */
public class PropertyccessException extends RuntimeException{
    public PropertyccessException(String s, IOException e) {
        super(s, e);
    }
}
