package com.lj.spring.common.exception;

import com.lj.spring.common.result.ResultFail;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * 业务异常
 * Created by lijun on 2019/4/4
 */
@Getter
public final class BizException extends AbstractException {

    @Builder
    public BizException(@NonNull ResultFail resultFail, String message) {
        super(message);
        this.resultFail = resultFail;
        this.message = message;
    }

    public BizException(String message) {
        super(message);
        this.resultFail = ResultFail.buildResult(message);
        this.message = message;
    }

    public BizException(@NonNull ResultFail resultFail) {
        super(resultFail.getMessage());
        this.resultFail = resultFail;
        this.message = resultFail.getMessage();
    }

}
