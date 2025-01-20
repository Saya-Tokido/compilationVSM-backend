package com.ljz.compilationVSM.common.utils;

import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 信息编码工具
 *
 * @author ljz
 * @since 2025-01-17
 */
@Component
@Slf4j
public class MessageEncodeUtil {

    /**
     * 使用MD5进行编码
     *
     * @param charSequence 明文
     * @return 编码后的密文
     */
    public String encode(CharSequence charSequence) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            String hexString = toHexString(digest.digest(charSequence.toString().getBytes()));
            // 生成的md5编码为32位，所以截取后16位
            return hexString.substring(16);
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5编码异常");
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
    }

    /**
     * 两个字符串是否匹配
     *
     * @param charSequence 明文
     * @param s            密文
     * @return 匹配结果
     */
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(encode(charSequence));
    }

    /**
     * @param tmp 转16进制字节数组
     * @return 返回16进制字符串
     */
    private String toHexString(byte[] tmp) {
        StringBuilder builder = new StringBuilder();
        for (byte b : tmp) {
            String s = Integer.toHexString(b & 0xFF);
            if (s.length() == 1) {
                builder.append("0");
            }
            builder.append(s);
        }

        return builder.toString();

    }
}
