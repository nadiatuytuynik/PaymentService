package com.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class methodRSA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String k = "true";
        int p = 0;
        int q = 0;

        int letter_int = 0;

        ArrayList<String> chars = new ArrayList<String>();
        ArrayList<Integer> results = new ArrayList<Integer>();
        chars.add("q");
        chars.add("w");
        chars.add("e");
        chars.add("r");
        chars.add("T");
        chars.add("1");
        chars.add("2");
        chars.add("3");
        chars.add("4");
        for (int i = 0; i < chars.size(); i++) {
            switch (chars.get(i)) {
                case "a":
                    letter_int = 11;
                    break;
                case "b":
                    letter_int = 12;
                    break;
                case "c":
                    letter_int = 13;
                    break;
                case "d":
                    letter_int = 14;
                    break;
                case "e":
                    letter_int = 15;
                    break;
                case "f":
                    letter_int = 16;
                    break;
                case "g":
                    letter_int = 17;
                    break;
                case "h":
                    letter_int = 18;
                    break;
                case "i":
                    letter_int = 19;
                    break;
                case "j":
                    letter_int = 20;
                    break;
                case "k":
                    letter_int = 21;
                    break;
                case "l":
                    letter_int = 22;
                    break;
                case "m":
                    letter_int = 23;
                    break;
                case "n":
                    letter_int = 24;
                    break;
                case "o":
                    letter_int = 25;
                    break;
                case "p":
                    letter_int = 26;
                    break;
                case "q":
                    letter_int = 27;
                    break;
                case "r":
                    letter_int = 28;
                    break;
                case "s":
                    letter_int = 29;
                    break;
                case "t":
                    letter_int = 30;
                    break;
                case "u":
                    letter_int = 31;
                    break;
                case "v":
                    letter_int = 32;
                    break;
                case "w":
                    letter_int = 33;
                    break;
                case "x":
                    letter_int = 34;
                    break;
                case "y":
                    letter_int = 35;
                    break;
                case "z":
                    letter_int = 36;
                    break;

                case "A":
                    letter_int = 37;
                    break;
                case "B":
                    letter_int = 38;
                    break;
                case "C":
                    letter_int = 39;
                    break;
                case "D":
                    letter_int = 40;
                    break;
                case "E":
                    letter_int = 41;
                    break;
                case "F":
                    letter_int = 42;
                    break;
                case "G":
                    letter_int = 43;
                    break;
                case "H":
                    letter_int = 44;
                    break;
                case "I":
                    letter_int = 45;
                    break;
                case "J":
                    letter_int = 46;
                    break;
                case "K":
                    letter_int = 47;
                    break;
                case "L":
                    letter_int = 48;
                    break;
                case "M":
                    letter_int = 49;
                    break;
                case "N":
                    letter_int = 50;
                    break;
                case "O":
                    letter_int = 51;
                    break;
                case "P":
                    letter_int = 52;
                    break;
                case "Q":
                    letter_int = 53;
                    break;
                case "R":
                    letter_int = 54;
                    break;
                case "S":
                    letter_int = 55;
                    break;
                case "T":
                    letter_int = 56;
                    break;
                case "U":
                    letter_int = 57;
                    break;
                case "V":
                    letter_int = 58;
                    break;
                case "W":
                    letter_int = 59;
                    break;
                case "X":
                    letter_int = 60;
                    break;
                case "Y":
                    letter_int = 61;
                    break;
                case "Z":
                    letter_int = 62;
                    break;
                case "0":
                    letter_int = 63;
                    break;
                case "1":
                    letter_int = 64;
                    break;
                case "2":
                    letter_int = 65;
                    break;
                case "3":
                    letter_int = 66;
                    break;
                case "4":
                    letter_int = 67;
                    break;
                case "5":
                    letter_int = 68;
                    break;
                case "6":
                    letter_int = 69;
                    break;
                case "7":
                    letter_int = 70;
                    break;
                case "8":
                    letter_int = 71;
                    break;
                case "9":
                    letter_int = 72;
                    break;
            }
            results.add(letter_int);
        }

        System.out.println(results);
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        for (int i = 2; i <= 1000; i++) {
            k = "true";
            for (int j = 2; j < i; j++) {
                if ((i % j) == 0) {
                    k = "false";
                }
            }
            if (k.equals("true")) {
                list.add(i);
            }
        }
        double n1 = Math.random() * (list.size());
        int l = (int) (n1);
        p = list.get(l);

        double n2 = Math.random() * (list.size());
        int l2 = (int) (n2);
        q = list.get(l2);

        int n = p * q;
        BigInteger N = BigInteger.valueOf(n);
        int fn = (p - 1) * (q - 1);

        ArrayList<Integer> list2 = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i) < fn) && (fn % list.get(i) != 0)) {
                list2.add(list.get(i));
            }
        }

        double rand = Math.random() * (list2.size() * (0.5));
        int rand2 = (int) rand;
        int e = list2.get(rand2);

        int d = 1;

        while (true) {
            if ((e * d - 1) % fn == 0) {
                //System.out.println("d = " + d);
                break;
            }
            d++;
        }

        System.out.println("Public key {" + e + ", " + n + "}");
        System.out.println("Private key {" + d + ", " + n + "}");

        ArrayList<BigInteger> codelist = new ArrayList<BigInteger>();

        int m;
        for (int number = 0; number < results.size(); number++) {
            m = results.get(number);
            System.out.println("---------------------");

            BigInteger M = BigInteger.valueOf(m);

            BigInteger x = M.pow(e);

            System.out.println("m = " + m);
            System.out.println("e = " + e);
            System.out.println("x = " + x);

            BigInteger C = BigInteger.ONE;
            BigInteger FN = BigInteger.valueOf(fn);
            while (true) {
                if (((C.subtract(x)).mod(N)).equals(BigInteger.ZERO)) {
                    // System.out.println("C = " + C);
                    break;
                }
                C = C.add(BigInteger.ONE);
            }

            System.out.println("Code text = " + C);
            codelist.add(C);

            M = BigInteger.ONE;
            BigInteger uncode = C.pow(d);
            while (true) {
                if (((M.subtract(uncode)).mod(N)).equals(BigInteger.ZERO)) {
                    System.out.println("uncode = " + M);
                    break;
                }
                M = M.add(BigInteger.ONE);
            }


        }
        
        System.out.println("code list > " + codelist);
        String codelisting = "";
        for(int i =0; i<codelist.size(); i++) {
            codelisting = codelisting + codelist.get(i) + "$";
        }
        System.out.println(codelisting);



    }

}
