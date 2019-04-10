package com.lj.spring.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by lijun on 2019/4/10
 */
@Data
@NoArgsConstructor
public class SimpleMailMessage {

    /**
     * 收件人
     */
    private List<String> toUserList;

    /**
     * 抄送人
     */
    private List<String> ccUserList;

    /**
     * 秘密抄送人
     */
    private List<String> bccUserList;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date date;


}
