package com.charles.idol.utils;
import org.apache.commons.codec.digest.DigestUtils;
public class EnCoder {
	public static String md5Encoder(byte[] pass)
	{
		return DigestUtils.md5Hex(pass);
	}
}
