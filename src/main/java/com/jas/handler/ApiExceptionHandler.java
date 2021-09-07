package com.jas.handler;

import com.jas.exception.AccountNotFoundExecption;
import com.jas.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import resource.ErrorResource;
import resource.FieldResource;
import resource.InvalidErrorResource;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * 處理找不到資源異常 404
     * @param e
     * @return
     */
    @ExceptionHandler(AccountNotFoundExecption.class)
    @ResponseBody
    public ResponseEntity<?> handleNotFound(AccountNotFoundExecption e) {

        ErrorResource errorResource = new ErrorResource(e.getMessage());

        return new ResponseEntity<Object>(errorResource, HttpStatus.NOT_FOUND);
    }

    /**
     * 處理參數驗證失敗異常
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    public ResponseEntity<?> handleInvalidRequest(InvalidRequestException e) {
        Errors errors = e.getErrors();
        List<FieldResource> fieldResources = new ArrayList<>();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            FieldResource fieldResource = new FieldResource(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage());
            fieldResources.add(fieldResource);
        }
        InvalidErrorResource invalidErrorResource = new InvalidErrorResource(e.getMessage(), fieldResources);
        return new ResponseEntity<Object>(invalidErrorResource, HttpStatus.BAD_REQUEST);
    }

    /**
     * 處理全局異常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(Exception e) {
        return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
