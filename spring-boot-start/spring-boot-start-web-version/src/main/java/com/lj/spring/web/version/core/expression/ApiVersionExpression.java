package com.lj.spring.web.version.core.expression;

import com.lj.spring.common.head.HeadCommon;
import com.lj.spring.web.version.annotation.*;
import com.lj.spring.web.version.common.Common;
import com.lj.spring.web.version.common.VersionOperator;
import com.lj.spring.web.version.common.VersionUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 * Created by junli on 2019-09-05
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApiVersionExpression {

    /**
     * 版本信息
     */
    private String version;

    /**
     * 版本信息运算符
     */
    private VersionOperator versionVersionOperator;

    /**
     * 来源
     */
    private String platForm;

    /**
     * 来源信息运算符
     */
    private VersionOperator platFormVersionOperator;

    /**
     * 渠道信息
     */
    private String channel;

    /**
     * 渠道信息运算符
     */
    private VersionOperator channelVersionOperator;

    /**
     * 计算当前 request 是否匹配
     *
     * @return 匹配返回true
     */
    public boolean match(HttpServletRequest request) {
        //版本信息匹配 - 来源匹配 - 渠道匹配
        return versionMatch(request)
                && platFormMath(request)
                && channelMatch(request);
    }

    /**
     * 版本是否匹配
     *
     * @return 匹配返回true
     */
    private boolean versionMatch(HttpServletRequest request) {
        String headerVersion = request.getHeader(HeadCommon.VERSION);
        return match(version, headerVersion, versionVersionOperator,
                VersionUtil::getVersionNumber, VersionUtil::getVersionNumber);
    }

    /**
     * 来源是否匹配
     *
     * @return 匹配返回true
     */
    private boolean platFormMath(HttpServletRequest request) {
        String headerPlatForm = request.getHeader(HeadCommon.PLATFORM);
        return match(platForm, headerPlatForm, platFormVersionOperator,
                Function.identity(), Function.identity());
    }

    /**
     * 渠道是否匹配
     *
     * @return 匹配返回true
     */
    private boolean channelMatch(HttpServletRequest request) {
        String headerChannel = request.getHeader(HeadCommon.CHANNEL);
        return match(channel, headerChannel, channelVersionOperator,
                Function.identity(), Function.identity());
    }

    /**
     * 判断是否匹配
     * 如果未配置 认为匹配成功
     *
     * @param baseInfo        基础信息
     * @param customer        待匹配信息
     * @param versionOperator 匹配运算符
     * @return 匹配成功返回 true
     */
    private boolean match(String baseInfo, String customer, VersionOperator versionOperator,
                          Function<String, String> handlerBaseInfo, Function<String, String> handlerCustomer) {
        if (Common.DEFAULT_CROSS.equals(baseInfo)) {
            return true;
        }
        if (StringUtils.isAnyBlank(baseInfo, customer)) {
            return false;
        }
        baseInfo = baseInfo.toUpperCase();
        customer = customer.toUpperCase();
        final String customerNumber = handlerCustomer.apply(customer);
        switch (versionOperator) {
            case EQ:
                return handlerBaseInfo.apply(baseInfo).equals(customerNumber);
            case NE:
                return !handlerBaseInfo.apply(baseInfo).equals(customerNumber);
            case GT:
                return customerNumber.compareTo(handlerBaseInfo.apply(baseInfo)) > 0;
            case GTE:
                return customerNumber.compareTo(handlerBaseInfo.apply(baseInfo)) >= 0;
            case LT:
                return customerNumber.compareTo(handlerBaseInfo.apply(baseInfo)) < 0;
            case LTE:
                return customerNumber.compareTo(handlerBaseInfo.apply(baseInfo)) <= 0;
            case IN:
                return Arrays.stream(baseInfo.split(","))
                        .anyMatch(info -> handlerBaseInfo.apply(info).equals(customerNumber));
            case NIL:
                return Arrays.stream(baseInfo.split(","))
                        .noneMatch(info -> handlerBaseInfo.apply(info).equals(customerNumber));
            default:
                return false;
        }
    }

    /**
     * 通过方法注解创建对象
     *
     * @param apiClientInfo 注解信息
     * @return 通过注解信息返回对象
     */
    public static ApiVersionExpression of(ApiClientInfo apiClientInfo) {
        if (Objects.isNull(apiClientInfo)) {
            return null;
        }
        ApiVersionExpression versionExpression = new ApiVersionExpression();
        ApiClientVersion version = apiClientInfo.version();
        versionExpression.setVersion(version.value());
        versionExpression.setVersionVersionOperator(version.operator());
        ApiClientPlatform platform = apiClientInfo.platform();
        versionExpression.setPlatForm(platform.value());
        versionExpression.setPlatFormVersionOperator(platform.operator());
        ApiClientChannel channel = apiClientInfo.channel();
        versionExpression.setChannel(channel.value());
        versionExpression.setChannelVersionOperator(channel.operator());
        return versionExpression;
    }

    /**
     * 通过类注解返回当前
     *
     * @param apiVersion
     * @return ApiVersionExpression
     */
    public static ApiVersionExpression of(ApiVersion apiVersion) {
        if (Objects.isNull(apiVersion)) {
            return null;
        }
        //类信息 只匹配当前的 version
        return ApiVersionExpression.builder()
                .version(apiVersion.value())
                .versionVersionOperator(VersionOperator.GTE)
                .platForm(Common.DEFAULT_CROSS)
                .channel(Common.DEFAULT_CROSS)
                .build();
    }
}
