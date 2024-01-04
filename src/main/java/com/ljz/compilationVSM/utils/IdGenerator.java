package com.ljz.compilationVSM.utils;

import java.util.UUID;

public class IdGenerator {
    public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }
}
