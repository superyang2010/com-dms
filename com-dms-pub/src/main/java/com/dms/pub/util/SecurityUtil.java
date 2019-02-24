package com.dms.pub.util;

import com.dms.pub.common.CommonConstant;
import com.dms.pub.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author yangchao.
 * @date 2018/11/23 10:45
 */
@Slf4j
public class SecurityUtil {

    public static String md5Encrypt(String s){
        char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] bytes = s.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        }catch (Exception e){
            return null;
        }
    }

    public static String urlEncode(String data){
        if (StringUtils.isEmpty(data)) {
            return data;
        }
        byte[] datas = null;
        try {
            data = URLEncoder.encode(data, CommonConstant.DEFAULT_CHARSET);
            datas = data.getBytes(CommonConstant.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported encoding["+CommonConstant.DEFAULT_CHARSET+"] exception.", e);
            ExceptionHandler.publish("DMS-PUB-000001", "Unsupported encoding["+CommonConstant.DEFAULT_CHARSET+"] exception.", e);
        }
        String str= new String(Base64.encodeBase64(datas, false));
        return str;
    }

    public static String urlDecode(String data) {
        if (StringUtils.isEmpty(data)) {
            return data;
        }
        String str = null;
        try {
            byte[] datas = Base64.decodeBase64(data.getBytes());
            data = new String(datas, CommonConstant.DEFAULT_CHARSET);
            str = URLDecoder.decode(data, CommonConstant.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported encoding["+CommonConstant.DEFAULT_CHARSET+"] exception.", e);
            ExceptionHandler.publish("DMS-PUB-000001", "Unsupported encoding["+CommonConstant.DEFAULT_CHARSET+"] exception.", e);
        }
        return str;
    }

    public static String encrypt(String content, String password) {
        if (StringUtils.isEmpty(content)||StringUtils.isEmpty(password)) {
            return content;
        }
        byte[] byteContent = null;
        try {
            byteContent = content.getBytes(CommonConstant.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported encoding["+CommonConstant.DEFAULT_CHARSET+"] exception.", e);
            ExceptionHandler.publish("DMS-PUB-000001", "Unsupported encoding["+CommonConstant.DEFAULT_CHARSET+"] exception.", e);
        }
        String str = encrypt(byteContent,password);
        int length = str.length();
        int cnt = length/8;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cnt; i++) {
            if(i == 0){
                sb.append(str.substring(i*8, (i+1)*8));
            }else{
                sb.append("-").append(str.substring(i*8, (i+1)*8));
            }
        }
        return sb.toString();
    }

    private static String encrypt(byte[] content, String password) {
        try {
            SecretKey secretKey = getKey(password);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            String aft_aes = parseByte2HexStr(result);
            // 加密
            return aft_aes;
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException.", e);
            ExceptionHandler.publish("DMS-PUB-000000", "Inner Exception,please contact administrator", e);
        } catch (NoSuchPaddingException e) {
            log.error("NoSuchPaddingException.", e);
            ExceptionHandler.publish("DMS-PUB-000000", "Inner Exception,please contact administrator", e);
        } catch (InvalidKeyException e) {
            log.error("InvalidKeyException.", e);
            ExceptionHandler.publish("DMS-PUB-000000", "Inner Exception,please contact administrator", e);
        } catch (IllegalBlockSizeException e) {
            log.error("IllegalBlockSizeException.", e);
            ExceptionHandler.publish("DMS-PUB-000000", "Inner Exception,please contact administrator", e);
        } catch (BadPaddingException e) {
            log.error("BadPaddingException.", e);
            ExceptionHandler.publish("DMS-PUB-000000", "Inner Exception,please contact administrator", e);
        }
        return null;
    }

    public static String decrypt(String aft_aes, String password) {
        if (StringUtils.isEmpty(aft_aes)||StringUtils.isEmpty(password)) {
            return aft_aes;
        }else{
            aft_aes = aft_aes.replaceAll("-", "");
        }
        try {
            byte[] content = parseHexStr2Byte(aft_aes);
            SecretKey secretKey = getKey(password);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            String bef_aes = new String(result);
            return bef_aes;
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException.", e);
            ExceptionHandler.publish("DMS-PUB-000000", "Inner Exception,please contact administrator", e);
        } catch (NoSuchPaddingException e) {
            log.error("NoSuchPaddingException.", e);
            ExceptionHandler.publish("DMS-PUB-000000", "Inner Exception,please contact administrator", e);
        } catch (InvalidKeyException e) {
            log.error("InvalidKeyException.", e);
            ExceptionHandler.publish("DMS-PUB-000000", "Inner Exception,please contact administrator", e);
        } catch (IllegalBlockSizeException e) {
            log.error("IllegalBlockSizeException.", e);
            ExceptionHandler.publish("DMS-PUB-000000", "Inner Exception,please contact administrator", e);
        } catch (BadPaddingException e) {
            log.error("BadPaddingException.", e);
            ExceptionHandler.publish("DMS-PUB-000000", "Inner Exception,please contact administrator", e);
        }
        return null;
    }

    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int value = Integer.parseInt(hexStr.substring(i*2, i*2+2), 16);
            result[i] = (byte)value;
        }
        return result;
    }
    private static SecretKey getKey(String strKey) throws NoSuchAlgorithmException {
        SecretKey secretKey = null;
        KeyGenerator _generator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(strKey.getBytes());
        _generator.init(128,secureRandom);
        secretKey =  _generator.generateKey();
        return secretKey;
    }

    public static void main(String[] args) {
        System.out.println(md5Encrypt("admin"));
    }
}
