package com.smashrating.common.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.warn("CustomException 예외 발생, msg:{}", e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        int errorCodeValue = errorCode.getStatus().value();
//        if(errorCode == ErrorCode.WEBCLIENT_ERROR)
//            return ResponseEntity.status(HttpStatus.valueOf(errorCodeValue))
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(createErrorResponse(errorCode, e.getMessage()));
        return ResponseEntity.status(HttpStatus.valueOf(errorCodeValue))
                .contentType(MediaType.APPLICATION_JSON)
                .body(createErrorResponse(errorCode));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ConstraintViolationException e) {
        log.warn("ConstraintViolationException 예외 발생, msg:{}", e.getMessage());
        ErrorCode errorCode = CommonErrorCode.VALIDATION_ERROR;

        return ResponseEntity.status(HttpStatus.valueOf(errorCode.getStatus().value()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(createErrorResponse(errorCode, e.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.warn("handleMethodArgumentNotValid 예외 발생, msg:{}", e.getMessage());
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return ResponseEntity
                .status(errorCode.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(createErrorResponse(e, errorCode));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn("handleHttpRequestMethodNotSupported 예외 발생, msg:{}", ex.getMessage());
        ErrorCode errorCode = CommonErrorCode.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.getStatus().value())).body(createErrorResponse(errorCode));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn("handleNoHandlerFoundException 예외 발생, msg:{}", ex.getMessage());
        ErrorCode errorCode = CommonErrorCode.URL_NOT_FOUND;
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.getStatus().value())).body(createErrorResponse(errorCode));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn("handleTypeMismatch 예외 발생, msg:{}", ex.getMessage());
        ErrorCode errorCode = CommonErrorCode.TYPE_MISMATCH;
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.getStatus().value())).body(createErrorResponse(errorCode));
    }

    private ErrorResponse createErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    private ErrorResponse createErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();
    }

    private ErrorResponse createErrorResponse(BindException e, ErrorCode errorCode) {
        List<ErrorResponse.ValidationError> validationErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .toList();
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .errors(validationErrors)
                .build();
    }
}
