package com.gonggumoa.domain.user.exception;

import com.gonggumoa.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class RequiredFieldMissingException extends RuntimeException {
  private final ResponseStatus exceptionStatus;

  public RequiredFieldMissingException(ResponseStatus exceptionStatus) {
    super(exceptionStatus.getMessage());
    this.exceptionStatus = exceptionStatus;
  }
}
