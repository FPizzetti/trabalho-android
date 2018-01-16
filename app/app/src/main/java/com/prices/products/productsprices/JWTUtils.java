package com.prices.products.productsprices;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class JWTUtils {

    public static String decoded(String JWTEncoded) {
        String[] split = JWTEncoded.split("\\.");
        try {
            return getJson(split[1]);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}