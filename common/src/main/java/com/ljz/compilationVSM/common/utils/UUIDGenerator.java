package com.ljz.compilationVSM.common.utils;

import java.util.UUID;

public class UUIDGenerator {
    public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }
}
