package com.example.help_center.security;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESLightEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class Crypto {

    private static final String defaultKey = "97a29cc4f2f5a82ada49286aa6c13250971e6e4d0c70c56e2d55e2471f31d88a";

    public static String performEncrypt(String keyText, String plainText) {
        try{
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] ptBytes = plainText.getBytes();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(true, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(ptBytes.length)];
            int oLen = cipher.processBytes(ptBytes, 0, ptBytes.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(Hex.encode(rv));
        } catch(Exception e) {
            return "Error";
        }
    }

    public static String performEncrypt(String cryptoText) {
        return performEncrypt(defaultKey, cryptoText);
    }

    public static String performDecrypt(String keyText, String cryptoText) {
        try {
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] cipherText = Hex.decode(cryptoText.getBytes());
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(false, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(cipherText.length)];
            int oLen = cipher.processBytes(cipherText, 0, cipherText.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(rv).trim();
        } catch(Exception e) {
            return "Error";
        }
    }

    public static String performDecrypt(String cryptoText) {
        return performDecrypt(defaultKey, cryptoText);
    }

    public static void main(String[] args) {

        String strToEncrypt = "jdbc:postgresql://localhost:5433/hidden_nebula_hrd";//put text to encrypt in here
        String encryptionResult = new Crypto().performEncrypt(strToEncrypt);
        System.out.println("Encryption Result : "+encryptionResult);
        String strToEncrypt1 = "hidden_nebula";//put text to encrypt in here
        String encryptionResult1 = new Crypto().performEncrypt(strToEncrypt1);
        System.out.println("Encryption Result : "+encryptionResult1);
        String strToEncrypt2 = "hiddenNebulaTA";//put text to encrypt in here
        String encryptionResult2 = new Crypto().performEncrypt(strToEncrypt2);
        System.out.println("Encryption Result : "+encryptionResult2);


        String strToDecrypt = "30755c0dc58a6a3bfcc05f61656";//put text to decrypt in here
        String decriptionResult = new Crypto().performDecrypt(strToDecrypt);
        System.out.println("Decryption Result : "+decriptionResult);
    }

}
