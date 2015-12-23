package com.lin.shiro.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

import com.lin.shiro.common.EncodeUtils;

public class MessageDigestTest {
	
	@Test
	public void md5Test(){
		try {
			MessageDigest di = MessageDigest.getInstance("md5");
			
			ByteSource so = ByteSource.Util.bytes("hhhhhh");
			ByteSource sa = ByteSource.Util.bytes("admin");
			System.out.println("pwd: " + so);
			System.out.println("salt: " + sa);
			
			di.reset();
			di.update(sa.getBytes());
			
			byte[] hashed = di.digest(so.getBytes());
			
			di.reset();
			hashed = di.digest(hashed);
			System.out.println(Hex.encodeToString(hashed));
			System.out.println(EncodeUtils.encodeToString(hashed));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
}
