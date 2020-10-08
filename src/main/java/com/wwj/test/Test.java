package com.wwj.test;

import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) {
        CalcDao calc = new CalcImpl();
        CalcInvocationHandler handler = new CalcInvocationHandler(calc);
        CalcDao calcDao = (CalcDao) Proxy.newProxyInstance(calc.getClass().getClassLoader(), calc.getClass().getInterfaces(), handler);
        int result = calcDao.add(1, 2);
        System.out.println(result);
    }
}
