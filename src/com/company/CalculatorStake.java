package com.company;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CalculatorStake {
    //нужен для того, что бы мы потом понимали, какая операция какой имеет вес. Логика - чем выше цифра веса,
    //тем важнее операция. то есть тем раньше она должна быть сделана
    private static final Map<String, Integer> unaryOperators = new HashMap<>();
    private static LinkedList<String> stack = new LinkedList<>();
    private static LinkedList<String> numbers = new LinkedList<>();
    private static LinkedList<String> symbols = new LinkedList<>();

    //эта карта приоритетов. Благодаря этой карте мы знаем какие операции выполняются первыми, какие потом
    public CalculatorStake(Map<String, Integer> unaryOperators) {
        unaryOperators.put("+", 2);
        unaryOperators.put("-", 2);
        unaryOperators.put("*", 3);
        unaryOperators.put("/", 3);
        unaryOperators.put("(", 1);
        unaryOperators.put(")", 0);
    }

    public static void main(String[] args) {
        CalculatorStake c = new CalculatorStake(unaryOperators);
        String str = "200+5*3-2/1";
        String str2 = "10-5-3";
        String str3 = "a-b*c+d";
        double x = calculate(str);
        System.err.println(" your result -> " + x);
    }

    private static double calculate(String str) {
        //делаем из стринга, который мы приняли массив
        String[] strArr = createArrayFromString(str);
//        print(strArr);
        //тут мы обрабатываем эту строку
        for (int i = 0; i < strArr.length; i++) {
            //нсли пустой сивпол - он нас совсем не интересует
            if (!strArr[i].equals("")) {
                if (strArr[i].equals("(")) {
                    addSymbol(symbols, strArr[i]);
                } else if (unaryOperators.containsKey(strArr[i])) {
                    //проверяем - если список с операциями пустой - просто добавляем туда знак
                    if (symbols.size() == 0) {
                        addSymbol(symbols, strArr[i]);
                        //проверяем скобочки
                    } else if (strArr[i].equals(")")) {
                        //если есть скобочки - то логика другая - нужно сдлелать все то
                        // же самое, но до открывающейся скобочки
                        checkSpecificSymbols(strArr, i);
                    } else {
                        String last = symbols.getLast();
                        int lastOp = unaryOperators.get(last);
                        int cur = unaryOperators.get(strArr[i]);
                        changeSymbol(last, lastOp, cur);
                        addSymbol(symbols, strArr[i]);
                    }
                } else {
                    addSymbol(numbers, strArr[i]);
                }
            }
        }
        while (!symbols.isEmpty()) {
            addSymbol(numbers, symbols.removeLast());
        }
        Double rez = getResult(numbers);
        System.err.println(numbers);
        return rez;
    }

    private static void changeSymbol(String last, int lastOp, int cur) {
        while (cur <= lastOp && !symbols.isEmpty()) {
            checkSymbols(numbers, symbols, last);
            if (symbols.isEmpty()) break;
            last = symbols.getLast();
            lastOp = unaryOperators.get(last);
        }
    }

    private static void checkSpecificSymbols(String[] strArr, int i) {
        String last = symbols.getLast();
        while (!last.equals("(")) {
            if (!last.equals("(")) {
                int lastOp = unaryOperators.get(last);
                int cur = unaryOperators.get(strArr[i]);
                while (cur <= lastOp && !symbols.isEmpty()) {
                    checkSymbols(numbers, symbols, last);
                    if (symbols.isEmpty()) break;
                    last = symbols.getLast();
                    if (last.equals("(")) {
                        symbols.removeLast();
                        break;
                    }
                    lastOp = unaryOperators.get(last);
                }
            } else {
                symbols.removeLast();
            }
        }
    }

    private static Double getResult(LinkedList<String> numbers) {
        Double rezTmp = null;
        List<Double> rez = new LinkedList<>();

        for (int i = 0; i < numbers.size(); i++) {
            if (unaryOperators.containsKey(numbers.get(i))) {
                rezTmp = operations(rez.get(rez.size() - 2), rez.get(rez.size() - 1), numbers.get(i));
                rez.remove(rez.get(rez.size() - 2));
                rez.remove(rez.get(rez.size() - 1));
                rez.add(rezTmp);
            } else {
                rez.add(Double.valueOf(numbers.get(i)));
            }
        }
        return rez.get(0);
    }

    private static double operations(Double op1, Double op2, String operator) {
        double res = 0.0;
        switch (operator) {
            case "+":
                res = op1 + op2;
                break;
            case "-":
                res = op1 - op2;
                break;
            case "*":
                res = op1 * op2;
                break;
            case "/":
                res = op1 / op2;
                break;
        }
        return res;
    }

    private static void addSymbol(LinkedList<String> symbols, String s) {
        symbols.add(s);
    }

    private static void checkSymbols(LinkedList<String> numbers, LinkedList<String> symbols, String last) {
        symbols.removeLast();
        addSymbol(numbers, last);
    }


    private static void print(String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            System.err.println("!!!" + strArr[i] + "!!!");
        }
    }

    private static String[] createArrayFromString(String str) {
        String line = str.replace(" ", "")
            .replaceAll("(?=[\\+-/\\*\\(\\)])", " ")
            .replaceAll("(?<=[\\+-/\\*\\(\\)])", " ")
            .replaceAll("  ", " ");
        String[] arrStr = line.split(" ");
        return arrStr;
    }
}
