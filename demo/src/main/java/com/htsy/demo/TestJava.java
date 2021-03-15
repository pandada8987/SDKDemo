package com.htsy.demo;

public class TestJava {
    public static void main(String[] args) {
        String price = "30.123";
        Double money = Double.valueOf(price);
        System.out.println(money.intValue());
    }
}
