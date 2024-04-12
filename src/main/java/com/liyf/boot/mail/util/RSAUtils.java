package com.liyf.boot.mail.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


public class RSAUtils {
    private static final int DEFAULT_RSA_KEY_SIZE = 512;

    private static final String KEY_ALGORITHM = "RSA";

    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    public static final String publicKey="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIxjel4alGMhVMoroceX1dG9p36k4lSoIdLFjWApBanNjgQY7FmPYYha8Kppe6WbMYO71S/SVkXGleU76NPvKjECAwEAAQ==";
;
    public static final String privateKey="MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAjGN6XhqUYyFUyiuhx5fV0b2nfqTiVKgh0sWNYCkFqc2OBBjsWY9hiFrwqml7pZsxg7vVL9JWRcaV5Tvo0+8qMQIDAQABAkA0R1MpOfKqG55sjuT246bfLSXzdklGRzoZODHWblBKk86pRbLmKdxuTywopQy+RW9x28ql8p5hXh3L9jOCdEQBAiEA5O9uvPsCYudokuhvX+wJHk3VMISCjsSEmfc9CYMLhsECIQCc/D24YEtoNzx10qnxOlmnd94u6kHbKaSYefg02itvcQIgCvqsneWP1LxyWHFLPhdszIZBDiWcuUoVbejchYNCX0ECIFPxfX9t3mSnxKisMTtQnEg0jJZBpV1v8xelgNeiIb/BAiADClFBePPcKXRZQBzzTPbJXV1XHP2iiuT1+pldtFFieA==";

    public static void main(String [] args) throws Exception {
        Map<String,String> map=generateRsaKey(DEFAULT_RSA_KEY_SIZE);
        System.out.println(map.get("publicKey"));
        System.out.println(map.get("privateKey"));
    }

    /**
     * 生成RSA 公私钥,可选长度为1025,2048位.
     */
    public static Map<String,String> generateRsaKey(int keySize) {
        Map<String,String> result = new HashMap<>(2);
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

            // 初始化密钥对生成器，密钥大小为1024 2048位
            keyPairGen.initialize(keySize, new SecureRandom());

            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();

            // 得到公钥字符串
            result.put("publicKey", new String(Base64.encodeBase64(keyPair.getPublic().getEncoded())));

            // 得到私钥字符串
            result.put("privateKey", new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded())));

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * RSA私钥解密
     * @param str  解密字符串
     * @param privateKey  私钥
     * @return 明文
     */
    public static String decrypt(String str, String privateKey) {
        //64位解码加密后的字符串
        byte[] inputByte;
        String outStr = "";
        try {
            inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte));
        } catch (UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return outStr;
    }

    /**
     *  RSA公钥加密
     * @param str 需要加密的字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }
}
