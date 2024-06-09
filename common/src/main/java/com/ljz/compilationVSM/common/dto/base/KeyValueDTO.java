package com.ljz.compilationVSM.common.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class KeyValueDTO<K, V> implements Serializable {
    private static final long serialVersionUID = -5496977487843948594L;

    private K key;

    private V value;
}