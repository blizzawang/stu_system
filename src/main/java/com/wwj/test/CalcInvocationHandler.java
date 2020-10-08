package com.wwj.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CalcInvocationHandler implements InvocationHandler {

    private Object obj;

    public CalcInvocationHandler(Object obj){
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int result = 0;
        if(method.getName().equals("add")){
            System.out.println("输出日志信息,执行方法名:" + method.getName() + ",方法参数:" + Arrays.asList(args));
            method.invoke(obj,args);
            result = Integer.valueOf(args[0].toString()) + Integer.valueOf(args[1].toString());
        }
        return result;
    }
}
