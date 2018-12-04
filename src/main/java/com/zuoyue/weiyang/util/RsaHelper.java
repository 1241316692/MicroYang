package com.zuoyue.weiyang.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaHelper {

    public static final String KEYPAIR = "keypair";
    public static final String PUBLIC_KEY = "RSAPublicKey"; // 公钥
    public static final String PRIVATE_KEY = "RSAPrivateKey"; // 私钥

    /**
     * 非对称加密密钥算法
     */
    public static final String KEY_ALGORITHM_RSA = "RSA";

    /**
     * 公钥
     */
    private static final String RSA_PUBLIC_KEY = "RSAPublicKey";

    /**
     * 私钥
     */
    private static final String RSA_PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA密钥长度
     * 默认1024位，
     * 密钥长度必须是64的倍数，
     * 范围在512至65536位之间。
     */
    private static final int KEY_SIZE = 1024;

    /**
     * 生成RSA密钥对(默认密钥长度为1024)
     * @return
     */
    public static KeyPair generateRSAKeyPair() {
        return generateRSAKeyPair(KEY_SIZE);
    }

    /**
     * 生成RSA密钥对
     * @param keyLength 密钥长度
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(keyLength);
            return kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new  RuntimeException(e);
        }
    }

    /**
     * 用公钥加密
     * @param data
     * @param pubKey
     * @return
     */
    public static byte[] encryData(byte[] data, PublicKey pubKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 用公钥加密
     * @param str 待加密字符串
     * @param enCode 字符串编码格式
     * @param pubKey 公钥
     * @return 加密后字符串
     */
    public static String encryptData(String str, String enCode, PublicKey pubKey) {
        try {
            byte[] data = str.getBytes(enCode);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return new BASE64Encoder().encode(cipher.doFinal(data));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 用私钥解密
     * @param encryptedData
     * @param priKey
     * @return
     */
    public static byte[] decryptData(byte[] encryptedData, PrivateKey priKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 用私钥解密
     * @param encryptedStr 待解密字符串
     * @param enCode 字符串编码格式
     * @param priKey 私钥
     * @return 解密后字符串
     */
    public static String decryptData(String encryptedStr, String enCode, PrivateKey priKey) throws Exception {
        byte[] encryptedData = new BASE64Decoder().decodeBuffer(encryptedStr);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(encryptedData), enCode);
    }

    /**
     * 使用既有的私钥进行解密
     * @param encryptedStr
     * @param enCode
     * @return
     */
//    public static String decryptData(String encryptedStr, String enCode) {
//        return decryptData(encryptedStr, enCode, PRIVATE_KEY);
//    }

    /**
     * 根据指定私钥对数据进行签名(默认签名算法"SHA1withRSA")
     * @param data 要签名的数据
     * @param priKey 私钥
     * @return
     */
    public static byte[] signData(byte[] data, PrivateKey priKey) {
        return signData(data, priKey, "SHA!withRSA");
    }

    /**
     * 根据指定私钥和算法对数据行进签名
     * @param data 要签名的数据
     * @param priKey 私钥
     * @param algorithm 签名算法
     * @return
     */
    public static byte[] signData(byte[] data, PrivateKey priKey, String algorithm) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(priKey);
            signature.update(data);
            return signature.sign();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 用指定的公钥进行签名验证(默认签名算法为"SHA1withRSA")
     * @param data 数据
     * @param sign 签名结果
     * @param pubKey 公钥
     * @return
     */
    public static boolean verifySign(byte[] data, byte[] sign, PublicKey pubKey) {
        return verifySign(data, sign, pubKey, "SHA1withRSA");
    }

    /**
     *
     * @param data 数据
     * @param sign 签名结果
     * @param pubKey 公钥
     * @param algorithm 签名算法
     * @return
     */
    public static boolean verifySign(byte[] data, byte[] sign, PublicKey pubKey, String algorithm) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initVerify(pubKey);
            signature.update(data);
            return signature.verify(sign);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 从base64字符串获取公钥
     * @param key
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = new BASE64Decoder().decodeBuffer(key);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 从base64字符串获取私钥
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = new BASE64Decoder().decodeBuffer(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 把密钥转成base64字符串
     * @param key
     * @return
     * @throws Exception
     */
    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = new BASE64Encoder().encode(keyBytes);
        return s;
    }


    public static void main(String[] args) throws Exception {
        String str = "123456";
        String pubKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDAUG27qxS2V7t5v1/W6RLrLeaiYMnENjEXeSVy\n" +
                "PffzmaIdlHL8Xbottcwa9Nw86Ra6WYNjdDzlCt0nTvL493OtiUSA0yio0WrQL0tfB5f6eElUbLIC\n" +
                "q3jI+z6ubtjBiVjbpFbZ5Vw4KQqeKiDx1xvXXKaLUsB9NfVLbYXtEn3nswIDAQAB";
        String priKeyStr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMBQbburFLZXu3m/X9bpEust5qJg\n" +
                "ycQ2MRd5JXI99/OZoh2Ucvxdui21zBr03DzpFrpZg2N0POUK3SdO8vj3c62JRIDTKKjRatAvS18H\n" +
                "l/p4SVRssgKreMj7Pq5u2MGJWNukVtnlXDgpCp4qIPHXG9dcpotSwH019Utthe0SfeezAgMBAAEC\n" +
                "gYAnjaM0EPi+lAa8AI9OnTtF4Xfo960nzNmF8WWxPGuNz5BXMxv/KavMEPL8rFXLtUj2ZMiwIM0R\n" +
                "co2Psx4n9IT21V4qHpbF0/mFaypIV7eqbVrEbVtI7R6l+1WliqKYsHtA2k+bdy8RuRBr78iFtvtV\n" +
                "iAVxMPBk6RO0icRJ5iTL4QJBAPX08PRfpGryhh+uNRyKc3ghfiJ6I/qdryLPIvVvAem4S15AeqjA\n" +
                "KnXm7RBFKFzZh5gxrLx6wn5YQgso78aaiJECQQDIKr7JU2OAJCBDsnHXwZyOhQ5yyq01Km5EeN9b\n" +
                "262slMgoc0mgk3OhSKHYnE36jW+rBqvBfPs+rSsptj3YAG4DAkAJ8vWIPRIujU7lmytk0yjmlBxB\n" +
                "EsW/zn8WHmDgorPmG2FZF6yehN9y03uFa/a9AsOcYW4GVcTmTXWmc+p3EysxAkBq02U+zOItYRpX\n" +
                "BsT7ALks1pX/bWtDFIIEyP1raLIniL/J1r1UlV0AlmLJ16FGNnWCMO05NhpgOMrwV/JpdNFBAkEA\n" +
                "3UqI12ulx+GbzxJok/6eTOzTrDwucnz04ZcnZBdxEE+PnnH149GUUp0bHff0K1elqhfvCDVYogjm\n" +
                "+ZgFL0NWNw==";
        PublicKey pubKey = RsaHelper.getPublicKey(pubKeyStr);
        PrivateKey priKey = RsaHelper.getPrivateKey(priKeyStr);
        String encryptedStr = RsaHelper.encryptData(str, "utf-8", pubKey);
        System.out.println("encryptedData: " + encryptedStr);
        System.out.println("str: " + RsaHelper.decryptData(encryptedStr, "utf-8", priKey));
    }
}
