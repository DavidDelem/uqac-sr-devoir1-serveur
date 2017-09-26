package com.company;
import java.io.Serializable;

public class Calc implements Serializable {

    int a;
    int b;

    public Calc(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public int add(String a, String b) {
        int x = Integer.parseInt(a);
        int y = Integer.parseInt(b);
        return x + y;
    }

    public int substract(String a, String b) {
        int x = Integer.parseInt(a);
        int y = Integer.parseInt(b);
        return x - y;
    }

}