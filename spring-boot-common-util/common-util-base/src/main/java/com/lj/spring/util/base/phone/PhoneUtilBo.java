package com.lj.spring.util.base.phone;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lijun on 2019/4/25
 */
@Data
@NoArgsConstructor
public class PhoneUtilBo {

    /**
     * 省会
     */
    private String provinceName;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 运营商
     */
    private String carrier;
}
