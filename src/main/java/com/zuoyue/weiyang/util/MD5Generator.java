package com.zuoyue.weiyang.util;

import java.security.MessageDigest;
import java.util.UUID;

public class MD5Generator {

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    public static String toHexString(byte[] data) {
        if (data == null) return null;
        StringBuilder r = new StringBuilder(data.length * 2);
        byte[] arrayOfByte = data;
        int j = data.length;
        for (int i = 0; i < j; i++) {
            byte b =arrayOfByte[i];
            r.append(hexCode[b >> 4 & 0xF]);
            r.append(hexCode[b & 0xF]);
        }
        return r.toString();
    }

    public String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Token: " + new MD5Generator().generateValue());
    }
}
