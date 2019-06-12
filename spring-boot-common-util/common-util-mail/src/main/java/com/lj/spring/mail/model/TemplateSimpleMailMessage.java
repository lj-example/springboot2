package com.lj.spring.mail.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;

/**
 * Created by lijun on 2019/4/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateSimpleMailMessage extends SimpleMailMessage {

    /**
     * 模板数据
     */
    private HashMap<String,Object> data;

    /**
     * 模板名称
     */
    private String templateName;

}
