package br.ufpr.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	public static String getMD5(String data) {
		MessageDigest md;
		String senhaCriptografada = "";
		try {
			md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, md.digest(data.getBytes()));
			senhaCriptografada = hash.toString(16);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return senhaCriptografada;

	}
}
