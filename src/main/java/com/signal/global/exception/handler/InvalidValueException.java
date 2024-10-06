package com.signal.global.exception.handler;

import com.signal.global.exception.errorCode.ErrorCode;

public class InvalidValueException extends CustomException{

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
