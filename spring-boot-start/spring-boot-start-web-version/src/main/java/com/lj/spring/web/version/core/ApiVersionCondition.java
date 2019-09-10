package com.lj.spring.web.version.core;

import com.lj.spring.web.version.core.expression.ApiVersionExpression;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by junli on 2019-09-05
 */
@Data
@AllArgsConstructor
@Builder
public class ApiVersionCondition extends AbstractRequestCondition<ApiVersionCondition> {

    private Set<ApiVersionExpression> expressions;

    /**
     * 多个条件合并
     */
    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        Set<ApiVersionExpression> set = new LinkedHashSet<>(this.expressions);
        set.addAll(other.expressions);
        return new ApiVersionCondition(set);
    }

    /**
     * 条件匹配
     */
    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        for (ApiVersionExpression expression : expressions) {
            if (!expression.match(request)) {
                return null;
            }
        }
        return this;
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        return 0;
    }

    @Override
    protected Collection<?> getContent() {
        return Collections.unmodifiableCollection(expressions);
    }

    @Override
    protected String getToStringInfix() {
        return " && ";
    }

    /**
     * 构建方法
     */
    public static ApiVersionCondition of(ApiVersionExpression apiVersionExpression) {
        if (Objects.isNull(apiVersionExpression)) {
            return null;
        }
        HashSet<ApiVersionExpression> versionExpressions = new HashSet<>(4);
        versionExpressions.add(apiVersionExpression);
        return ApiVersionCondition.builder()
                .expressions(versionExpressions)
                .build();
    }
}
