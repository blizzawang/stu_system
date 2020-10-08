package com.wwj.test;

public class CalcImpl implements CalcDao{

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
