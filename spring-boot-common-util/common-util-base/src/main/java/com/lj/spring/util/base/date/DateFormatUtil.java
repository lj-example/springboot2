package com.lj.spring.util.base.date;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * Created by junli on 2019-07-11
 */
public class DateFormatUtil {

    public static final FastDateFormat DEFAULT_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    public static final FastDateFormat NORMAL_MONTH_FORMAT = FastDateFormat.getInstance("yyyy-MM");
    public static final FastDateFormat NORMAL_DAY_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final FastDateFormat NORMAL_TIME_FORMAT = FastDateFormat.getInstance("HH:mm:ss");

    public static final FastDateFormat LONG_NUMBER_FORMAT = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
    public static final FastDateFormat NUMBER_FORMAT = FastDateFormat.getInstance("yyyyMMddHHmmss");

}

