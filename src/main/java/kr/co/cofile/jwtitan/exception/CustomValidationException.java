package kr.co.cofile.jwtitan.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class CustomValidationException extends BusinessException {

    private final BindingResult bindingResult;

    public CustomValidationException(ErrorCode errorCode, BindingResult bindingResult) {
        super(errorCode);
        this.bindingResult = bindingResult;
    }
}