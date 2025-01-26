package com.ljz.compilationVSM.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * hash 算法枚举类
 *
 * @author ljz
 * @since 2025-01-22
 */
@AllArgsConstructor
@Getter
public enum HashAlgEnum {
    MURMUR_HASH("1","MurmurHash"),
    CITY_HASH("2","CityHash"),
    SIP_HASH("3","SipHash");

    private final String code;
    private final String desc;
}
