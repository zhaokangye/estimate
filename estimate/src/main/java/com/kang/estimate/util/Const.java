package com.kang.estimate.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kang
 */
public class Const {
    public static String FIND_WEBAPPS="cd /usr;find -name 'webapps' -type d";
    public static String FIND_BIN="cd /usr;find -name 'bin' -type d";

    public static String UPLOAD_PATH="C:\\Users\\hasee\\Desktop\\fileSave\\";

    public static Map<String, BigDecimal> UPLOAD_PERCENTAGE=new HashMap<>();
}
