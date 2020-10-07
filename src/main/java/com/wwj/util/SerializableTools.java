package com.wwj.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对象序列化与反序列化工具
 */
public class SerializableTools {

    /**
     * 反序列化
     *
     * @param bt
     * @return
     * @throws Exception
     */
    public static Object byteArrayToObject(byte[] bt) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bt);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }

    /**
     * 对象序列化
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] objectToByteArray(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        return byteArrayOutputStream.toByteArray();
    }
}