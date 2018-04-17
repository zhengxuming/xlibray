package com.zxm.xlibray.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Helper {
    private final String[] m;

    public MD5Helper() {
        this.m = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    }

    public static MD5Helper getInstance() {
        return new MD5Helper();
    }

    private String a(byte var1) {
        int var2 = var1;
        if(var1 < 0) {
            var2 = var1 + 256;
        }

        int var3 = var2 / 16;
        int var4 = var2 % 16;
        return this.m[var3] + this.m[var4];
    }

    private String a(byte[] var1) {
        StringBuffer var2 = new StringBuffer();

        for(int var3 = 0; var3 < var1.length; ++var3) {
            var2.append(this.a(var1[var3]));
        }

        return var2.toString();
    }

    public String convertToMD5(String var1) {
        String var2 = null;

        try {
            new String(var1);
            MessageDigest var3 = MessageDigest.getInstance("MD5");
            var2 = this.a(var3.digest(var1.getBytes()));
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        }

        return var2;
    }
}