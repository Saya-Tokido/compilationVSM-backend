package com.ljz.compilationVSM.common.utils;

import com.ljz.compilationVSM.common.enums.HashAlgEnum;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用于hash表计算hash值的算法工具类（非加密算法）
 *
 * @author ljz
 * @since 2025-01-22
 */
public class HashAlgorithmUtil {

    /**
     * hash密钥种子生成
     *
     * @return 64位种子
     */
    public static long generateSeed() {
        byte[] bytes = SecureRandom.getSeed(64);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer.getLong();
    }

    /**
     * MurmurHash 算法类
     */
    private static class MurmurHash {

        public static final HashAlgEnum hashAlgEnum = HashAlgEnum.MURMUR_HASH;

        public static int hash32(final byte[] data, int seed) {
            int length = data.length;
            int hash = seed;
            int remainder = length & 3; // length % 4
            int bytes = length - remainder;

            for (int i = 0; i < bytes; i += 4) {
                int k = (data[i] & 0xFF) | ((data[i + 1] & 0xFF) << 8) | ((data[i + 2] & 0xFF) << 16) | ((data[i + 3] & 0xFF) << 24);
                k *= 0x5bd1e995;
                k ^= k >>> 24;
                k *= 0x5bd1e995;
                hash *= 0x5bd1e995;
                hash ^= k;
            }

            // Handle remaining bytes
            if (remainder != 0) {
                int k = 0;
                for (int i = length - remainder; i < length; i++) {
                    k |= (data[i] & 0xFF) << (i & 3) * 8;
                }
                k *= 0x5bd1e995;
                k ^= k >>> 24;
                k *= 0x5bd1e995;
                hash ^= k;
            }

            // Final mix
            hash ^= hash >>> 13;
            hash *= 0x5bd1e995;
            hash ^= hash >>> 15;

            return hash;
        }

        public static int generate(String text, long seed) {
            byte[] data = text.getBytes();
            int hashValue = hash32(data, (int) seed);
            //强制转为无符号整数
            return hashValue & 0x7FFFFFFF;
        }
    }

    /**
     * CityHash 算法类
     */
    private static class CityHash {

        public static final HashAlgEnum hashAlgEnum = HashAlgEnum.CITY_HASH;

        // 32位哈希函数，输入为字节数组
        public static int hash32(byte[] data, int seed) {
            int hash = seed;

            int i = 0;
            while (i + 4 <= data.length) {
                int value = getInt(data, i);
                hash ^= mix32(value);
                i += 4;
            }

            while (i < data.length) {
                hash ^= (data[i] & 0xff) * seed;
                i++;
            }

            // 对哈希结果进行进一步的混合
            hash ^= (hash >>> 15);
            hash *= 0x7feb352d;
            hash ^= (hash >>> 13);
            hash *= 0x6f4b0b9f;
            hash ^= (hash >>> 16);

            return hash;
        }

        // 计算32位混合函数
        private static int mix32(int value) {
            value = (~value) + (value << 15);
            value = value ^ (value >>> 12);
            value = (value + (value << 2)) + (value << 4);
            value = value ^ (value >>> 5);
            value = value + (value << 3);
            value = value ^ (value >>> 10);
            value = value + (value << 7);
            return value;
        }

        // 获取4字节的整型值
        private static int getInt(byte[] data, int i) {
            return ((data[i] & 0xff))
                    | ((data[i + 1] & 0xff) << 8)
                    | ((data[i + 2] & 0xff) << 16)
                    | ((data[i + 3] & 0xff) << 24);
        }

        public static int generate(String text, long seed) {
            byte[] inputBytes = text.getBytes();
            int hashValue = hash32(inputBytes, (int) seed);
            //强制转为无符号整数
            return hashValue & 0x7FFFFFFF;
        }
    }

    /**
     * SipHash 算法类
     */
    private static class SipHash {

        public static final HashAlgEnum hashAlgEnum = HashAlgEnum.SIP_HASH;

        private static final int c0 = 0x736f6d65;
        private static final int c1 = 0x70736575;
        private static final int c2 = 0x646f7261;
        private static final int c3 = 0x6e646f6d;

        private int v0, v1, v2, v3;

        public SipHash(int key0, int key1) {
            v0 = c0 ^ key0;
            v1 = c1 ^ key1;
            v2 = c2 ^ key0;
            v3 = c3 ^ key1;
        }

        private void sipRound() {
            // SipRound
            v0 += v1;
            v1 = Integer.rotateLeft(v1, 13);
            v1 ^= v0;
            v0 = Integer.rotateLeft(v0, 32);
            v2 += v3;
            v3 = Integer.rotateLeft(v3, 16);
            v3 ^= v2;
            v0 += v3;
            v3 = Integer.rotateLeft(v3, 21);
            v3 ^= v0;
            v2 += v1;
            v1 = Integer.rotateLeft(v1, 17);
            v1 ^= v2;
            v2 = Integer.rotateLeft(v2, 32);
        }

        public int hash(byte[] data) {

            int b = 0;
            for (int i = 0; i < data.length; i++) {
                b |= (data[i] & 0xFF) << (8 * (i % 4));
                if (i % 4 == 3 || i == data.length - 1) {
                    v3 ^= b;
                    for (int j = 0; j < 2; j++) {
                        sipRound();
                    }
                    v0 ^= b;
                    b = 0;
                }
            }

            v2 ^= 0xff;
            for (int j = 0; j < 4; j++) {
                sipRound();
            }

            return v0 ^ v1 ^ v2 ^ v3;
        }

        public static int generate(String text, long seed) {
            int key0 = (int) (seed >> 32);
            int key1 = (int) (seed);
            SipHash sipHash = new SipHash(key0, key1);

            byte[] data = text.getBytes();
            int hashValue = sipHash.hash(data);
            //强制转为无符号整数
            return hashValue & 0x7FFFFFFF;
        }
    }

    /**
     * generate hash codes
     *
     * @param text        raw text
     * @param hashSeedMap hash seeds
     * @return hashcode list
     */
    public static List<Integer> generate(String text, Map<String, String> hashSeedMap) {
        ArrayList<Integer> hashCodeList = new ArrayList<>();
        if (hashSeedMap.containsKey(MurmurHash.hashAlgEnum.getCode())) {
            int hashCode = MurmurHash.generate(text, Long.parseLong(hashSeedMap.get(MurmurHash.hashAlgEnum.getCode())));
            hashCodeList.add(hashCode);
        }
        if (hashSeedMap.containsKey(CityHash.hashAlgEnum.getCode())) {
            int hashCode = CityHash.generate(text, Long.parseLong(hashSeedMap.get(CityHash.hashAlgEnum.getCode())));
            hashCodeList.add(hashCode);
        }
        if (hashSeedMap.containsKey(SipHash.hashAlgEnum.getCode())) {
            int hashCode = SipHash.generate(text, Long.parseLong(hashSeedMap.get(SipHash.hashAlgEnum.getCode())));
            hashCodeList.add(hashCode);
        }
        return hashCodeList;
    }
}
