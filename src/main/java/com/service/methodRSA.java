package com.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class methodRSA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        long st1, en1;
        long st2, en2;
        long st3, en3;
        long time1, time2, time3;
        st1 = System.nanoTime();

        ArrayList<BigInteger> keys = generateKeys();
        System.out.println(keys);

        en1 = System.nanoTime();
        time1 =  en1 - st1;
        System.out.println("time1 = " + time1);

        st2 = System.nanoTime();

        ArrayList<BigInteger> code = codeList("lastPass9", keys.get(0).intValue(), keys.get(2), keys.get(3).intValue());


        String codelisting = "";
        for (int i = 0; i < code.size(); i++) {
            codelisting = codelisting + code.get(i) + "-";
        }

        System.out.println(codelisting);

        en2 = System.nanoTime();
        time2 =  en2 - st2;
        System.out.println("time2 = " + time2);

        st3 = System.nanoTime();
        String uncodelisting = uncodeList(codelisting, keys.get(1).intValue(), keys.get(2));
        System.out.println(uncodelisting);

        en3 = System.nanoTime();
        time3 =  en3 - st3;
        System.out.println("time3 = " + time3);

    }


    public static ArrayList<BigInteger> generateKeys() {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String k = "true";
        int p = 0;
        int q = 0;
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
                break;
            }
            d++;
        }
        ArrayList<BigInteger> returnList = new ArrayList<BigInteger>();


        returnList.add(BigInteger.valueOf(e));
        returnList.add(BigInteger.valueOf(d));
        returnList.add(BigInteger.valueOf(n));
        returnList.add(BigInteger.valueOf(fn));


        return returnList;
    }



    public static ArrayList<BigInteger> codeList(String password, int e, BigInteger N, int fn){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String k = "true";
        int p = 0;
        int q = 0;

        int letter_int = 0;

        ArrayList<String> chars = new ArrayList<String>();
        for (char retval1 : password.toCharArray()) {
            String retval2 = String.valueOf(retval1);
            chars.add(retval2);
        }
        ArrayList<Integer> results = new ArrayList<Integer>();

        for (int i = 0; i < chars.size(); i++) {
            switch (chars.get(i)) {
                case "a":letter_int = 11;break;
                case "b":letter_int = 12;break;
                case "c":letter_int = 13;break;
                case "d":letter_int = 14;break;
                case "e":letter_int = 15;break;
                case "f":letter_int = 16;break;
                case "g":letter_int = 17;break;
                case "h":letter_int = 18;break;
                case "i":letter_int = 19;break;
                case "j":letter_int = 20;break;
                case "k":letter_int = 21;break;
                case "l":letter_int = 22;break;
                case "m":letter_int = 23;break;
                case "n":letter_int = 24;break;
                case "o":letter_int = 25;break;
                case "p":letter_int = 26;break;
                case "q":letter_int = 27;break;
                case "r":letter_int = 28;break;
                case "s":letter_int = 29;break;
                case "t":letter_int = 30;break;
                case "u":letter_int = 31;break;
                case "v":letter_int = 32;break;
                case "w":letter_int = 33;break;
                case "x":letter_int = 34;break;
                case "y":letter_int = 35;break;
                case "z":letter_int = 36;break;
                case "A":letter_int = 37;break;
                case "B":letter_int = 38;break;
                case "C":letter_int = 39;break;
                case "D":letter_int = 40;break;
                case "E":letter_int = 41;break;
                case "F":letter_int = 42;break;
                case "G":letter_int = 43;break;
                case "H":letter_int = 44;break;
                case "I":letter_int = 45;break;
                case "J":letter_int = 46;break;
                case "K":letter_int = 47;break;
                case "L":letter_int = 48;break;
                case "M":letter_int = 49;break;
                case "N":letter_int = 50;break;
                case "O":letter_int = 51;break;
                case "P":letter_int = 52;break;
                case "Q":letter_int = 53;break;
                case "R":letter_int = 54;break;
                case "S":letter_int = 55;break;
                case "T":letter_int = 56;break;
                case "U":letter_int = 57;break;
                case "V":letter_int = 58;break;
                case "W":letter_int = 59;break;
                case "X":letter_int = 60;break;
                case "Y":letter_int = 61;break;
                case "Z":letter_int = 62;break;
                case "0":letter_int = 63;break;
                case "1":letter_int = 64;break;
                case "2":letter_int = 65;break;
                case "3":letter_int = 66;break;
                case "4":letter_int = 67;break;
                case "5":letter_int = 68;break;
                case "6":letter_int = 69;break;
                case "7":letter_int = 70;break;
                case "8":letter_int = 71;break;
                case "9":letter_int = 72;break;
            }
            results.add(letter_int);
        }


        ArrayList<BigInteger> codelist = new ArrayList<BigInteger>();
        int m;
        for (int number = 0; number < results.size(); number++) {
            m = results.get(number);

            BigInteger M = BigInteger.valueOf(m);

            BigInteger x = M.pow(e);

            BigInteger C = BigInteger.ONE;
            BigInteger FN = BigInteger.valueOf(fn);
            while (true) {
                if (((C.subtract(x)).mod(N)).equals(BigInteger.ZERO)) {

                    break;
                }
                C = C.add(BigInteger.ONE);
            }


            codelist.add(C);

        }
        return codelist;
    }




    public static String uncodeList(String codelisting, int d, BigInteger N){

        ArrayList<String> listT = new ArrayList<>();
        for (String retval : codelisting.split("-")) {
            listT.add(retval);
        }

        ArrayList<BigInteger> listBigInt = new ArrayList<>();
        for (int i = 0; i < listT.size(); i++) {
            Integer x = Integer.valueOf(listT.get(i));
            BigInteger X = BigInteger.valueOf(x);
            listBigInt.add(X);
        }

        ArrayList<BigInteger> uncodelist = new ArrayList<>();

        for (int i = 0; i < listBigInt.size(); i++) {
            BigInteger C = listBigInt.get(i);

            BigInteger M = BigInteger.ONE;
            BigInteger uncode = C.pow(d);
            while (true) {
                if (((M.subtract(uncode)).mod(N)).equals(BigInteger.ZERO)) {
                    uncodelist.add(M);
                    break;
                }
                M = M.add(BigInteger.ONE);
            }
        }


        ArrayList<String> uncodeStringList = new ArrayList<>();
        String letter_string = "";
        for (int i = 0; i < uncodelist.size(); i++) {
            switch (uncodelist.get(i).intValue()) {
                case 11:letter_string = "a";break;
                case 12:letter_string = "b";break;
                case 13:letter_string = "c";break;
                case 14:letter_string = "d";break;
                case 15:letter_string = "e";break;
                case 16:letter_string = "f";break;
                case 17:letter_string = "g";break;
                case 18:letter_string = "h";break;
                case 19:letter_string = "i";break;
                case 20:letter_string = "j";break;
                case 21:letter_string = "k";break;
                case 22:letter_string = "l";break;
                case 23:letter_string = "m";break;
                case 24:letter_string = "n";break;
                case 25:letter_string = "o";break;
                case 26:letter_string = "p";break;
                case 27:letter_string = "q";break;
                case 28:letter_string = "r";break;
                case 29:letter_string = "s";break;
                case 30:letter_string = "t";break;
                case 31:letter_string = "u";break;
                case 32:letter_string = "v";break;
                case 33:letter_string = "w";break;
                case 34:letter_string = "x";break;
                case 35:letter_string = "y";break;
                case 36:letter_string = "z";break;
                case 37:letter_string = "A";break;
                case 38:letter_string = "B";break;
                case 39:letter_string = "C";break;
                case 40:letter_string = "D";break;
                case 41:letter_string = "E";break;
                case 42:letter_string = "F";break;
                case 43:letter_string = "G";break;
                case 44:letter_string = "H";break;
                case 45:letter_string = "I";break;
                case 46:letter_string = "J";break;
                case 47:letter_string = "K";break;
                case 48:letter_string = "L";break;
                case 49:letter_string = "M";break;
                case 50:letter_string = "N";break;
                case 51:letter_string = "O";break;
                case 52:letter_string = "P";break;
                case 53:letter_string = "Q";break;
                case 54:letter_string = "R";break;
                case 55:letter_string = "S";break;
                case 56:letter_string = "T";break;
                case 57:letter_string = "U";break;
                case 58:letter_string = "V";break;
                case 59:letter_string = "W";break;
                case 60:letter_string = "X";break;
                case 61:letter_string = "Y";break;
                case 62:letter_string = "Z";break;
                case 63:letter_string = "O";break;
                case 64:letter_string = "1";break;
                case 65:letter_string = "2";break;
                case 66:letter_string = "3";break;
                case 67:letter_string = "4";break;
                case 68:letter_string = "5";break;
                case 69:letter_string = "6";break;
                case 70:letter_string = "7";break;
                case 71:letter_string = "8";break;
                case 72:letter_string = "9";break;
            }
            uncodeStringList.add(letter_string);
        }
        String uncodelisting = "";

        for (int i = 0; i < uncodeStringList.size(); i++) {
            uncodelisting = uncodelisting + uncodeStringList.get(i);
        }

        return uncodelisting;
    }
}
