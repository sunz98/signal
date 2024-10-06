package com.signal.global.exception.handler;

import com.signal.global.exception.errorCode.ErrorCode;
import org.aspectj.weaver.ast.Not;

public class NotFoundException extends CustomException{

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
