package com.gbsw.gbswhub.domain.global.Exception;

import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.gbsw.gbswhub.domain")
public class GlobalExceptionHandler{


        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
                ErrorCode errorCode = e.getErrorCode();
                return ResponseEntity
                        .status(errorCode.getStatus())
                        .body(new ErrorResponse(errorCode));
        }

        @ExceptionHandler(AuthorizationDeniedException.class)
        public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
                ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
                return ResponseEntity
                        .status(errorCode.getStatus())
                        .body(new ErrorResponse(errorCode));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleException(Exception e) {
                ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
                return ResponseEntity
                        .status(errorCode.getStatus())
                        .body(new ErrorResponse(errorCode));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
                BindingResult bindingResult = ex.getBindingResult();

                // 첫 번째 에러 메시지만 가져오기
                String firstErrorMessage = bindingResult.getFieldErrors().stream()
                        .map(error -> error.getDefaultMessage())
                        .findFirst()
                        .orElse("잘못된 입력값입니다.");

                // 응답 생성
                Map<String, Object> response = new HashMap<>();
                response.put("status", 400);
                response.put("error", firstErrorMessage);

                return ResponseEntity.badRequest().body(response);
        }

}
