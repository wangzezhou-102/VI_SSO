package com.secusoft.web.vmcmessage;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ChenYongHeng on 2017/6/9.
 */

public class DES {

    /**
     * DES加密
     *
     * @param encryptBytes 需要加密的数据
     * @param key          加密使用的key
     * @return 加密后的数据
     */
    public static byte[] encryptDES(byte[] encryptBytes, byte[] key) {
        byte[] encryptedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encryptedBytes = cipher.doFinal(encryptBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return encryptedBytes;
    }

    /**
     * DES解密
     *
     * @param decryptBytes 需要解密的数据
     * @param key          解密使用的key
     * @return 解密后的数据
     */
    public static byte[] decryptDES(byte[] decryptBytes, byte[] key) {
        byte[] decryptedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            decryptedBytes = cipher.doFinal(decryptBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return decryptedBytes;
    }
}

