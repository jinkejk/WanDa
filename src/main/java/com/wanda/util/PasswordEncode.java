package com.wanda.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * md5加密
 * @author jinkejk
 *
 */
public class PasswordEncode {

	/**
	 * Md5加密
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String md5(String str,String salt){
		return new Md5Hash(str,salt).toString();
	}
}
