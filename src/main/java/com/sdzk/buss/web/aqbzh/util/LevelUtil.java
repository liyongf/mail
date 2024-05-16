package com.sdzk.buss.web.aqbzh.util;

import org.apache.commons.lang3.StringUtils;

public class LevelUtil {

    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    public static String calculateLevel(String paraentLevel, Integer parrentId) {
        if (StringUtils.isBlank(paraentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(paraentLevel, SEPARATOR, parrentId);
        }
    }
}
