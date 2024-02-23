package com.ljz.compilationVSM.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class passwordEncoder{

    public String encode(CharSequence charSequence) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return toHexString(digest.digest(charSequence.toString().getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }


    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(encode(charSequence));
    }

    /**
     * @param tmp 转16进制字节数组
     * @return 返回16进制字符串
     */
    private String toHexString(byte [] tmp){
        StringBuilder builder = new StringBuilder();
        for (byte b :tmp){
            String s = Integer.toHexString(b & 0xFF);
            if (s.length()==1){
                builder.append("0");
            }
            builder.append(s);
        }

        return builder.toString();

    }
}
