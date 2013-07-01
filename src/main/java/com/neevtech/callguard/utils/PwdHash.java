/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neevtech.callguard.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author ashish
 */
public class PwdHash {

    public static String getHash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String output = bigInt.toString(16);
            return output;
        } catch (Exception e) {
            return null;
        }
    }
}
