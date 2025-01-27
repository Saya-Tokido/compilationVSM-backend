package com.ljz.compilationVSM.common.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;


/**
 * BCrypt算法加密工具类
 *
 * @author ljz
 * @since 2024-12-25
 */
@Component
public class BCryptUtil {

    /**
     * 校验密码是否匹配密文
     *
     * @param plainText 密码原文
     * @param encryptText 密文
     * @return 匹配结果
     */
    public boolean checkMessage(String plainText, String encryptText){
        return BCrypt.checkpw(plainText,encryptText);
    }

    /**
     * 生成密文
     *
     * @param plainText 明文信息
     * @return 密文信息
     */
    public String encryptMessage(String plainText){
        return BCrypt.hashpw(plainText,BCrypt.gensalt());
    }

    public static void main(String[] args) {
        System.out.println(new BCryptUtil().encryptMessage("04a28b58a6b142cc98bb5e4dba840fa9"));
    }
}
