package com.gonggumoa.global.docs;

import com.gonggumoa.global.response.status.BaseExceptionResponseStatus;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentedApiErrors {
    BaseExceptionResponseStatus[] value();
}