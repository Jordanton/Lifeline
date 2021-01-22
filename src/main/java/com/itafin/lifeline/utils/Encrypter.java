package com.itafin.lifeline.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {		
  public static String getEncrypted(String password)
    throws UnsupportedEncodingException, NoSuchAlgorithmException {

    String retEncryptedBigIntegerPwStr = "";
  
    MessageDigest md = MessageDigest.getInstance("SHA-1");
    md.update(password.getBytes("UTF-8"));
    byte[] encryptedPasswordByte = md.digest();
  
    BigInteger encryptedPassworBI = new BigInteger(encryptedPasswordByte);
    retEncryptedBigIntegerPwStr = encryptedPassworBI.toString();
  
    return retEncryptedBigIntegerPwStr;
  }
}
