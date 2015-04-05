package com.opteral.springsms;

import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import static org.junit.Assert.assertEquals;

public class MD5EncoderTest {

    @Test
    public void md5test()
    {
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        assertEquals("e201994dca9320fc94336603b1cfc970", md5PasswordEncoder.encodePassword("secreto", null));
    }
}
