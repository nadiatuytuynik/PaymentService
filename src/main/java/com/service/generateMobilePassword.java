package com.service;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by Nadia on 30.05.2017.
 */
public class generateMobilePassword {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int random = pass();
        System.out.println(random);
    }

    public static int pass(){
        Random rand = new Random();
        int random = rand.nextInt((9999 - 1000) + 1) + 1000;
        return random;
    }
}
