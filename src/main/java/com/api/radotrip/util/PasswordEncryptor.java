package com.api.radotrip.util;

import org.jasypt.util.text.AES256TextEncryptor;

public class PasswordEncryptor {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java PasswordEncryptor <encryptionKey> <plainPassword>");
            return;
        }
        String key = args[0];
        String plain = args[1];
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword(key);
        String encrypted = encryptor.encrypt(plain);
        System.out.println("ENC(" + encrypted + ")");
    }
}
