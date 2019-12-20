package com.vicmob.config;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

import static java.util.UUID.randomUUID;

/**
 * AES 加密解密
 * @author peter
 * @version 1.1
 * @date 2019/7/13 14:12
 */
public class SymmetricEncoder {

    /**
     * 加密
     * @param encodeRules
     * @param content
     * @return
     */
    public static String AESEncode(String encodeRules,String content){

        try {
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(encodeRules.getBytes());
            keygen.init(128, secureRandom);
            SecretKey original_key=keygen.generateKey();
            byte [] raw=original_key.getEncoded();
            SecretKey key=new SecretKeySpec(raw, "AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte [] byte_encode=content.getBytes("utf-8");
            byte [] byte_AES=cipher.doFinal(byte_encode);
            String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
            return AES_encode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密
     * @param encodeRules
     * @param content
     * @return
     */
    public static String AESDncode(String encodeRules,String content){
        try {
            System.out.println("===解密==encodeRules:"+encodeRules+",content:"+content);
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(encodeRules.getBytes());
            keygen.init(128, secureRandom);
            SecretKey original_key=keygen.generateKey();
            byte [] raw=original_key.getEncoded();
            SecretKey key=new SecretKeySpec(raw, "AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            return AES_decode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void main(String[] args) {
        String encodeRules="vicmob";
        String jm="4Px47EJGSMobzOUPjPRyBOSQCQxL80C4VUJs9OeXRzkMaZVSmHV4YHWDXxS/AN8w";
        System.out.println("解密后的明文为："+AESDncode(encodeRules,jm));
    }


}
