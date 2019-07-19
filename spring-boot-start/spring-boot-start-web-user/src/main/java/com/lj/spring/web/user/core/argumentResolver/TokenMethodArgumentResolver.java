package com.lj.spring.web.user.core.argumentResolver;

import com.lj.spring.web.user.annotation.UserToken;
import com.lj.spring.web.user.model.UserSession;
import com.lj.spring.web.user.model.UserSessionRedis;
import com.lj.spring.web.user.service.session.UserIllegalTokenHandleService;
import com.lj.spring.web.user.service.session.UserSessionService;
import com.lj.spring.web.user.util.UserSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;

import java.util.Objects;

/**
 * 把 head 头中的token 转换成对应的 controller 中的方法类型，
 * 目前可以支持的目标参数类型 - UserSession 对象，Long(userId)
 * Created by junli on 2019-07-01
 */
@Slf4j
public class TokenMethodArgumentResolver extends RequestHeaderMethodArgumentResolver {

    private UserSessionService userSessionService;
    private UserIllegalTokenHandleService userIllegalTokenHandleService;

    public TokenMethodArgumentResolver(ConfigurableBeanFactory beanFactory,
                                       UserSessionService userSessionService,
                                       UserIllegalTokenHandleService userIllegalTokenHandleService) {
        super(beanFactory);
        this.userSessionService = userSessionService;
        this.userIllegalTokenHandleService = userIllegalTokenHandleService;
    }

    /**
     * 支持 参数解析类型
     * 1.必须有 token 注解
     * 2.数据类型为 UserSession 或 int
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserToken.class) && nonIllegalType(parameter.getParameterType());
    }

    /**
     * 注册一个 可以被识别的 对象
     */
    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        UserToken parameterAnnotation = parameter.getParameterAnnotation(UserToken.class);
        return new TokenNamedValueInfo(parameterAnnotation);
    }

    /**
     * 参数解析
     */
    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        String token = (String) super.resolveName(name, parameter, request);
        if (StringUtils.isBlank(token)) {
            userIllegalTokenHandleService.assertAndHandleNoTokenHead();
        }
        //获取配置信息
        UserToken tokenConfig = parameter.getParameterAnnotation(UserToken.class);
        Class<?> parameterType = parameter.getParameterType();
        //是否只需要简单数据（userId）
        boolean isSimple = parameterType == long.class || Long.class.isAssignableFrom(parameterType);
        if (isSimple && !tokenConfig.check()) {
            return userSessionService.getUserIdByToken(token);
        }
        UserSessionRedis userSessionRedisByToken = userSessionService.getUserSessionRedisByToken(token);
        if (Objects.isNull(userSessionRedisByToken) || StringUtils.isBlank(userSessionRedisByToken.getMobile())) {
            //信息不存、手机号不存在
            userIllegalTokenHandleService.assertAndHandleNoTokenInfo();
        }
        if (tokenConfig.check()) {
            //校验token
            assertAndHandleSession(userSessionRedisByToken, token);
        }
        if (isSimple) {
            return Long.valueOf(userSessionRedisByToken.getUserId());
        } else {
            return UserSession.builder()
                    .userId(Long.valueOf(userSessionRedisByToken.getUserId()))
                    .mobile(userSessionRedisByToken.getMobile())
                    .build();
        }
    }

    /**
     * 校验参数
     *
     * @param clazz 支持的数据转换类型
     * @return 支持返回true
     */
    private boolean nonIllegalType(Class clazz) {
        if (clazz == long.class || Long.class.isAssignableFrom(clazz)) {
            return true;
        }
        if (UserSession.class.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }

    /**
     * 校验token
     */
    private void assertAndHandleSession(UserSessionRedis userSessionRedis, String token) {
        String mobile = userSessionRedis.getMobile();
        //判断当前是否是oldToken
        if (userSessionRedis.getOldToken()) {
            //系统预处理一次 userToken
            userSessionService.handleOldToken(token);
            //组件调用处理 userToken
            userIllegalTokenHandleService.assertAndHandOldToken(userSessionRedis);
        } else {
            String userTokenKey = UserSessionUtil.buildUserTokenKey(mobile);
            userSessionService.handleNormalUserToken(userTokenKey);
            userSessionService.handleNormalTokenCacheInfo(token);
        }
    }

    /**
     * default TokenNamedValueInfo
     */
    static class TokenNamedValueInfo extends NamedValueInfo {
        public TokenNamedValueInfo(UserToken userToken) {
            super(userToken.name(), userToken.required(), userToken.defaultValue());
        }
    }
}

