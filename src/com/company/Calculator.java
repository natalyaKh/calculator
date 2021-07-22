package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calculator {

    private final Map<String, Integer> unaryOperators;

    public static void main(String[] args) {
        String str = "200+5*3-2/1";
        String str2 = "10*5-3/1";
        Double rez = calculate(str2);
        System.err.println("your result = " + rez );
    }

    public Calculator() {
        unaryOperators  = new HashMap<>();
        unaryOperators.put("+", 1);
        unaryOperators.put("-", 1);
        unaryOperators.put("*", 2);
        unaryOperators.put("/", 2);
    }

    private static double calculate(String str) {
        double rez = 0.0;
        String[] numbers = str.split("[\\+\\-\\*\\%\\/]");
        String[] symb = str.split("[0-9]+");
        ArrayList<String> rezult = new ArrayList();
        rezult.add(numbers[0]);
        for (int i = 1; i < symb.length; i++) {
            if (symb[i].equals("*")) {
                rezult.remove(rezult.get(rezult.size()-1));
                double x = Double.parseDouble(numbers[i - 1]) * Double.parseDouble(numbers[i]);
                rezult.add(String.valueOf(x));
            } else if (symb[i].equals("/")) {
                rezult.remove(rezult.get(rezult.size()-1));
                double x = Double.parseDouble(numbers[i - 1]) / Double.parseDouble(numbers[i]);
                rezult.add(String.valueOf(x));
            } else {
                rezult.add(symb[i]);
                rezult.add(numbers[i]);
            }
        }
        rez = Double.parseDouble(rezult.get(0));
        for (int i = 0; i < rezult.size()-1; i++) {
            if (i % 2 != 0) {
                if ("+".equals(rezult.get(i))) {
                    rez = rez + Double.parseDouble(rezult.get(i + 1));
                } else {
                    rez = rez - Double.parseDouble(rezult.get(i + 1));
                }
            }
        }
        return rez;
    }
}
