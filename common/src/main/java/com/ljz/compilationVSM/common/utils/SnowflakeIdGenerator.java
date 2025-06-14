package com.ljz.compilationVSM.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 雪花Id生成算法
 *
 * @author ljz
 * @since 2024-12-05
 */
@Component
public class SnowflakeIdGenerator {

    /**
     * 自定义起始时间戳 41位毫秒级
     */
    private final long epoch = 1733356800000L;

    /**
     * 每个数据中心工作(部署的服务)id位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据中心id位数
     */
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 每个服务每个时间戳内能生成的id数位数
     */
    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 服务id
     */
    @Value("${distribute.id-generator.workerId}")
    private long workerId;

    /**
     * 数据中心id
     */
    @Value("${distribute.id-generator.datacenterId}")
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - epoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public long generate() {
        return nextId();
    }

    public static void main(String[] args) {
        System.out.println(new SnowflakeIdGenerator().generate());
    }
}
