package com.elbanking.core.util;

import ch.qos.logback.core.util.StringUtil;

import java.util.UUID;

public class IdUtil {
    public static boolean isValidUUID(String id){
        if(StringUtil.isNullOrEmpty(id)){
            return false;
        }
        try{
            UUID.fromString(id);
            return true;
        }catch(IllegalArgumentException e){
            return false;
        }
    }
}
