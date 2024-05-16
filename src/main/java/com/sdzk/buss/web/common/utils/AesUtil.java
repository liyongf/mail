package com.sdzk.buss.web.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * AES加密，解密类
 *
 */
public class AesUtil {

    private static final String ALGORITHM = "AES";
	public static final String IVSTR = "j#bd0@trxsj!3jnv";
	private static String hexString="0123456789ABCDEF";

	private AesUtil(){
	}
    public static String getSecretKey()  throws Exception{
        //KeyGenerator提供对称密钥生成器的功能，支持各种算法
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //SecretKey负责保存对称密钥
        SecretKey deskey = keygen.generateKey();
        return AesUtil.getByteStr(deskey.getEncoded());
    }
	public static String toHexString(String str){
		//根据默认编码获取字节数组
		byte[] bytes=str.getBytes();
		StringBuilder sb=new StringBuilder(bytes.length*2);
		//将字节数组中每个字节拆解成2位16进制整数
		for(int i=0;i<bytes.length;i++){
			sb.append(hexString.charAt((bytes[i]&0xf0)>>4));
			sb.append(hexString.charAt((bytes[i]&0x0f)>>0));
		}
		return sb.toString();
	}

    /**
     * 加密方法
     *
     * @param initString
     * @return
     * @throws Exception
     */
    public static String encryptWithIV(String initString,String key) throws Exception {
        initString = AesUtil.toHexString(initString);
        SecretKeySpec skeySpec = new SecretKeySpec(getKeyByStr(key),
                ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec,new IvParameterSpec(IVSTR.getBytes()));
	    return new String(new Base64().encode(cipher.doFinal(hexString2Bytes(initString))));
    }

	/**
	 * 解密
	 *
	 * @return
	 * @throws Exception
	 */
	public static String decryptWithIV(String source ,String key) throws Exception {
        byte[] ms = new Base64().decode(source);
		SecretKeySpec skeySpec = new SecretKeySpec(getKeyByStr(key),
				ALGORITHM);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec,new IvParameterSpec(IVSTR.getBytes()));

		return new String(cipher.doFinal(ms));
	}

    /**
     * 将16进制的字符串转化为10进制值 输入密码的字符形式，返回字节数组形式。 如输入字符串：AD67EA2F3BE6E5AD
     * 返回字节数组：{173,103,234,47,59,230,229,173}
     */
    public static byte[] getKeyByStr(String strKey) {
        byte[] bRet = new byte[strKey.length() / 2];
        for (int i = 0; i < strKey.length() / 2; i++) {
            Integer itg = new Integer(16 * getChrInt(strKey.charAt(2 * i))
                    + getChrInt(strKey.charAt(2 * i + 1)));
            bRet[i] = itg.byteValue();
        }
        return bRet;
    }

    /**
     * 将两个ASCII字符合成一个字节；
     * 如："EF"--> 0xEF
     * @param src0 byte
     * @param src1 byte
     * @return byte
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        return (byte) (b0 ^ b1);
    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式
     * 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9}
     * @param src String
     * @return byte[]
     */
    public static byte[] hexString2Bytes(String src) {
        int len = src.length()/2;
        byte[] ret = new byte[len];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < len; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    /**
     * 计算一个16进制字符的10进制值 输入：0-F
     */
    private static int getChrInt(char chr) {
        int iRet = 0;
        if (chr == "0".charAt(0)){
            iRet = 0;
        }
        if (chr == "1".charAt(0)){
            iRet = 1;
        }
        if (chr == "2".charAt(0)){
            iRet = 2;
        }
        if (chr == "3".charAt(0)){
            iRet = 3;
        }
        if (chr == "4".charAt(0)){
            iRet = 4;
        }
        if (chr == "5".charAt(0)){
            iRet = 5;
        }
        if (chr == "6".charAt(0)){
            iRet = 6;
        }
        if (chr == "7".charAt(0)){
            iRet = 7;
        }
        if (chr == "8".charAt(0)){
            iRet = 8;
        }
        if (chr == "9".charAt(0)){
            iRet = 9;
        }
        if (chr == "A".charAt(0)){
            iRet = 10;
        }
        if (chr == "B".charAt(0)){
            iRet = 11;
        }
        if (chr == "C".charAt(0)){
            iRet = 12;
        }
        if (chr == "D".charAt(0)){
            iRet = 13;
        }
        if (chr == "E".charAt(0)){
            iRet = 14;
        }
        if (chr == "F".charAt(0)){
            iRet = 15;
        }
        return iRet;
    }

    /**
     * 将字节转化为字符串 生成密钥时使用
     *
     * @param byt
     * @return
     */
    public static String getByteStr(byte[] byt) {
        String strRet = "";
        for (int i = 0; i < byt.length; i++) {
            strRet += getHexValue((byt[i] & 240) / 16);
            strRet += getHexValue(byt[i] & 15);
        }
        return strRet;
    }

    /**
     * 计算一个10进制字符的16进制值 输入字数 生成密钥时使用
     */
    private static String getHexValue(int s) {
        String sRet = null;
        switch (s) {
            case 0:
                sRet = "0";
                break;
            case 1:
                sRet = "1";
                break;
            case 2:
                sRet = "2";
                break;
            case 3:
                sRet = "3";
                break;
            case 4:
                sRet = "4";
                break;
            case 5:
                sRet = "5";
                break;
            case 6:
                sRet = "6";
                break;
            case 7:
                sRet = "7";
                break;
            case 8:
                sRet = "8";
                break;
            case 9:
                sRet = "9";
                break;
            case 10:
                sRet = "A";
                break;
            case 11:
                sRet = "B";
                break;
            case 12:
                sRet = "C";
                break;
            case 13:
                sRet = "D";
                break;
            case 14:
                sRet = "E";
                break;
            case 15:
                sRet = "F";
	            break;
            default:
	            sRet = "";
        }
        return sRet;
    }

    public static void main(String args [] ) throws Exception {


//        String strkey = "370481B0012000411401";
//        String msg = "Token=111&dataSource=1&mineCode=1";

        String mineCode = "370826B0012000411411";
        String token = "2F79A18434B51A6D06A0ACC62A08F66F";


        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        System.out.println("十六位字符：" + AesUtil.toHexString(tempToken));
        String base64StrWithIV = AesUtil.encryptWithIV(tempToken, token);
        System.out.println("密钥"+token);
        System.out.println("明文:"+tempToken);
        System.out.println("加密后:\n"+base64StrWithIV);
//
        byte[] ms = new Base64().decode(base64StrWithIV);
//
////        base64StrWithIV = "R8dsq5JKVftu2YE+lDPi3r7fGeaMP1XeLHv9XzcZiDjlwvrUSG/lTX8tSZPbqg2emz7JyuTUPV2jKSeNW/FxuBk6fKiM81et4d5U9eznbH4=";
//
        String str = AesUtil.decryptWithIV(base64StrWithIV, token);
        System.out.println("\n\n解密后：" + str);
//        System.out.println("///////////////////////////////");

//        String token = "65C1F03CD6AF3F0175604757D64D20A9";
//        String mineCode = "370481B0012000411403";
//
//        String tempToken = "token=" + token + "&mineCode=" + mineCode;
//        String ciphertext = null;
//        try {
//            ciphertext = AesUtil.encryptWithIV(tempToken, token);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(ciphertext);

    }
}
