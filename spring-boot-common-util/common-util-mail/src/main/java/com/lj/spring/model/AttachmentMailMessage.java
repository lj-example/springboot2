package com.lj.spring.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 模板邮件
 * Created by lijun on 2019/4/22
 */
@Data
public class AttachmentMailMessage extends SimpleMailMessage {

    /**
     * 附加信息
     */
    private List<Attachment> attachmentList;

    /**
     * 附件信息
     */
    @Builder
    @Data
    public static class Attachment {

        /**
         * 文件名称
         */
        private String fileName;

        /**
         * 绝对附件路径
         */
        private String filePath;

        /**
         * 是否需要在发送完成之后删除该文件
         */
        private Boolean isDelete;
    }
}
