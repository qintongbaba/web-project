package org.smart4j.chapter2.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties文件的工具类
 * Created by wuqinghua on 18/2/8.
 */
public class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载配置文件
     *
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream
                    (fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " file is not found.");
            }

            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            LOGGER.error("load properties file failure.", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure.", e);
                }
            }
        }

        return props;
    }


    /**
     * 获取字符串类型的值
     *
     * @param props
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(Properties props, String key, String defaultValue) {
        String val = defaultValue;
        if (props.containsKey(key)) {
            val = props.getProperty(key);
        }
        return val;
    }

    /**
     * 获取字符串类型的值，默认值为空字符串
     *
     * @param props
     * @param key
     * @return
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }


    public static int getInt(Properties props, String key, int defaultVal) {
        int val = defaultVal;
        if (props.containsKey(key)) {
            val = CastUtil.castInteger(props.getProperty(key));
        }
        return val;
    }

    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    public static boolean getBoolean(Properties props, String key, boolean defaultVal) {
        boolean val = defaultVal;
        if (props.containsKey(key)) {
            val = CastUtil.castBoolean(props.getProperty(key));
        }
        return val;
    }

    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }


}
