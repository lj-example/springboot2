package com.lj.spring.web.user.exception;

import com.lj.spring.common.exception.BizException;
import com.lj.spring.common.result.ResultFail;
import lombok.*;

/**
 * Created by junli on 2019-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TokenBizException extends BizException {

    public TokenBizException(@NonNull ResultFail resultFail, String message) {
        super(resultFail, message);
    }

    public TokenBizException(String message) {
        super(message);
    }

    public TokenBizException(@NonNull ResultFail resultFail) {
        super(resultFail);
    }
}

