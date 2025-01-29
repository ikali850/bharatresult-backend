package com.admin.bharatresult.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    public static String generatePassword(String plainText) {
        String hashedPassword = BCrypt.hashpw(plainText, BCrypt.gensalt());
        return hashedPassword;
    }

    public static boolean verifyPassword(String pass, String hashedPassword) {
        return BCrypt.checkpw(pass, hashedPassword);
    }

}
