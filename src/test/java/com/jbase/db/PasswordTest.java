package com.jbase.db;

import org.junit.Test;

import com.jbase.common.security.Cryptos;

public class PasswordTest
{

    @Test
    public void testGenUserName() {
        System.out.println("username: " + Cryptos.aesEncrypt("root"));
    }
    
    @Test
    public void testGenUserPassword() {
        System.out.println("password: " + Cryptos.aesEncrypt("123456"));
    }
}
