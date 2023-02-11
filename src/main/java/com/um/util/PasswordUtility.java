package com.um.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtility {

	private static final String salt = "@12#$%(!@$";

	// Not recommended
	public static String hashPasswordWithMD5(String plainPassword) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(salt.getBytes());
		byte[] hashedPassword = md.digest(plainPassword.getBytes());
		return new String(hashedPassword);
	}

	// Less recommended
	public static String hashPasswordWithSHA_512(String plainPassword) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(salt.getBytes());
		byte[] hashedPassword = md.digest(plainPassword.getBytes());
		return new String(hashedPassword);
	}

	// Highly recommended
	public static String hashPasswordWithPBKDF2(String plainPassword)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		KeySpec spec = new PBEKeySpec(plainPassword.toCharArray(), salt.getBytes(), 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hashedPassword = factory.generateSecret(spec).getEncoded();
		return new String(hashedPassword);
	}

}
