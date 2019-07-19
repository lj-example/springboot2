package com.lj.spring.util.base.phone;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 手机号 处理工具类
 * Created by lijun on 2019/4/25
 */
@UtilityClass
public class PhoneUtil {

    /**
     * 直辖市/自治区
     */
    private static final String[] SPECIAL_PROVINCIAL = {"北京市", "天津市", "上海市", "重庆市",
            "新疆", "内蒙古", "西藏", "宁夏", "广西"};

    private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private static PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();
    private static PhoneNumberOfflineGeocoder geocode = PhoneNumberOfflineGeocoder.getInstance();

    /**
     * 默认国家信息
     */
    private static final String DEFAULT_REGION = Locale.SIMPLIFIED_CHINESE.getCountry();

    /**
     * 默认空字符串
     */
    private static final String STRING_EMPTY = "";

    /**
     * 校验手机号是否否和标准
     * 符合标准,返回true;
     * 手机号异常,返回 false,
     */
    public static boolean checkPhoneNumber(String phoneNumber) {
        try {
            Phonenumber.PhoneNumber number = parseNumber(phoneNumber);
            return phoneNumberUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }

    /**
     * 获取手机运营商
     *
     * @param phoneNumber 手机号
     * @return 运行商名称，默认放回 ""
     */
    public static String getCarrier(String phoneNumber) {
        try {
            Phonenumber.PhoneNumber number = parseNumber(phoneNumber);
            String nameForNumber = carrierMapper.getNameForNumber(number, Locale.CHINESE);
            return nameForNumber;
        } catch (NumberParseException e) {
            return STRING_EMPTY;
        }
    }

    /**
     * 获取手机号归属地
     *
     * @param phoneNumber 手机号
     * @return 归属地信息
     */
    public static String getGeo(String phoneNumber) {
        try {
            Phonenumber.PhoneNumber number = parseNumber(phoneNumber);
            return geocode.getDescriptionForNumber(number, Locale.CHINA);
        } catch (NumberParseException e) {
            e.printStackTrace();
            return STRING_EMPTY;
        }
    }

    /**
     * 获取手机号 信息
     *
     * @param phoneNumber 手机号
     * @return 手机号信息
     */
    public static PhoneUtilBo getPhoneBoInfo(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            return null;
        }
        final PhoneUtilBo phoneUtilBo = new PhoneUtilBo();
        //运营商
        final String carrier = getCarrier(phoneNumber);
        phoneUtilBo.setCarrier(carrier);
        //归属地
        String geo = getGeo(phoneNumber);
        if (!STRING_EMPTY.equals(geo)) {
            final Optional<String> optional = Stream.of(SPECIAL_PROVINCIAL)
                    .filter(specialProvincial -> geo.startsWith(specialProvincial))
                    .findFirst();
            String provincialName = optional.orElseGet(() -> geo.split("省")[0] + "省");
            phoneUtilBo.setProvinceName(provincialName);
            String[] geoInfoArray = geo.split(provincialName);
            if (geoInfoArray.length == 0) {
                phoneUtilBo.setCityName(provincialName);
            } else {
                phoneUtilBo.setCityName(geoInfoArray[geoInfoArray.length > 1 ? 1 : 0]);
            }
        }
        return phoneUtilBo;
    }

    /**
     * 默认手机号解析器
     */
    private static Phonenumber.PhoneNumber parseNumber(String phoneNumber) throws NumberParseException {
        return phoneNumberUtil.parse(phoneNumber, DEFAULT_REGION);
    }

}
