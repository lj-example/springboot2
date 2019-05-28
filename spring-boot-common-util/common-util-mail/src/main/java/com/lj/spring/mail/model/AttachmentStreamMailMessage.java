package com.lj.spring.mail.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.InputStreamSource;

import java.util.List;

/**
 * 附件邮件,文件流形式
 * Created by lijun on 2019/4/24
 */
@Data
public class AttachmentStreamMailMessage extends SimpleMailMessage {

    /**
     * 附件信息
     */
    private List<AttachmentStream> attachmentStreamList;

    /**
     * 附件信息
     */
    @Builder
    @Data
    public static class AttachmentStream {
        /**
         * 文件名称 - 必须以带有文件后缀
         */
        private String fileName;

        /**
         * 文件流
         */
        private InputStreamSource inputStreamSource;
    }
}
