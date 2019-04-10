package com.lj.spring.model;

import lombok.Data;

import java.util.HashMap;

/**
 * Created by lijun on 2019/4/10
 */
@Data
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
