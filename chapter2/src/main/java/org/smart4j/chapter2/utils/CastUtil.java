package org.smart4j.chapter2.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 转型操作类
 * Created by wuqinghua on 18/2/8.
 */
public class CastUtil {


    /**
     * 转换为字符串
     */
    public static String castString(Object obj, String defaultVal) {
        return obj != null ? String.valueOf(obj) : defaultVal;
    }

    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * 转换为double
     */
    public static Double castDouble(Object obj, Double defaultVal) {
        double doubleVal = defaultVal;
        if (obj != null) {
            String strVal = castString(obj);
            if (StringUtils.isNotEmpty(strVal)) {
                try {
                    doubleVal = Double.parseDouble(strVal);
                } catch (NumberFormatException e) {
                    doubleVal = defaultVal;
                }
            }
        }
        return doubleVal;
    }

    public static Double castDouble(Object obj) {
        return castDouble(obj, 0.0);
    }

    /**
     * 转换为LONG
     */
    public static Long castLong(Object obj, Long defaultVal) {
        long longVal = defaultVal;
        if (obj != null) {
            String strVal = castString(obj);
            if (StringUtils.isNotEmpty(strVal)) {
                try {
                    longVal = Long.parseLong(strVal);
                } catch (NumberFormatException e) {
                    longVal = defaultVal;
                }
            }
        }
        return longVal;
    }

    public static Long castLong(Object obj) {
        return castLong(obj, 0L);
    }


    /**
     * 转换为int
     */
    public static Integer castInteger(Object obj, Integer defaultVal) {
        int intVal = defaultVal;
        if (obj != null) {
            String strVal = castString(obj);
            if (StringUtils.isNotEmpty(strVal)) {
                try {
                    intVal = Integer.parseInt(strVal);
                } catch (NumberFormatException e) {
                    intVal = defaultVal;
                }
            }
        }
        return intVal;
    }

    public static Integer castInteger(Object obj) {
        return castInteger(obj, 0);
    }


    /**
     * 转换为boolean
     */
    public static Boolean castBoolean(Object obj, Boolean defaultVal) {
        Boolean booleanVal = defaultVal;
        if (obj != null) {
            booleanVal = Boolean.parseBoolean(castString(obj));
        }
        return booleanVal;
    }

    public static Boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }
}
