package com.sergeydeveloper7.domain;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by serg on 11.01.18.
 */

public class Util {

    /*public static SecretKey generateKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new SecretKeySpec(password.getBytes(), "AES");
    }*/

    public static byte[] encrypt(String message) {
        Cipher cipher;
        byte[] cipherText = new byte[0];
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(message.getBytes(), "AES"));
            cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException
                | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return cipherText;
    }
}
