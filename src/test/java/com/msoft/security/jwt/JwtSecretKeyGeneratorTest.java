package com.msoft.security.jwt;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;

public class JwtSecretKeyGeneratorTest {

	@Test
	public void keyGenerator() {
		SecretKey secretKey = Jwts.SIG.HS512.key().build();
		String encodedKey = DatatypeConverter.printHexBinary(secretKey.getEncoded());
		System.out.println("Secret Key :: " + encodedKey);
	}
	
}
