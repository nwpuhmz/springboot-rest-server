package com.jkz.XzRestServer.web.exception;

import com.jkz.XzRestServer.constant.ErrorCode;
import com.jkz.XzRestServer.model.dto.*;
import com.jkz.XzRestServer.model.dto.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by scuhmz on 2017/9/17.
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    private static Logger log= LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(HttpServletRequest request, ResourceNotFoundException ex){
        logError(request, ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Error()
                        .setCode(ErrorCode.RESOURCE_NOT_FOUND_ERROR)
                        .setMessage(ex.getMessage()));
    }

    @ExceptionHandler(ParameterIllegalException.class)
    public ResponseEntity<?> parameterIllegalExceptionHandler(HttpServletRequest request, ParameterIllegalException ex) {
        logError(request, ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Error()
                        .setCode(ErrorCode.PARAMETER_ILLEGAL_ERROR)
                        .setMessage("URL请求中，参数错误！"));
    }

    @ExceptionHandler(ServerInternalErrorException.class)
    public ResponseEntity<?> serverInternalErrorExceptionHandler(HttpServletRequest request, ServerInternalErrorException ex) {
        logError(request, ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error()
                        .setCode(ErrorCode.RESOURCE_NOT_FOUND_ERROR)
                        .setMessage("服务器内部发生错误，请重试！"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, Exception ex) {
        logError(request, ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error()
                        .setCode(ErrorCode.SERVER_INTERNAL_ERROR)
                        .setMessage("服务器发生意外崩溃，请联系管理员！"));
    }
    /********************************** HELPER METHOD **********************************/
    private void logError(HttpServletRequest request, Exception e) {
        log.error("[URI: " + request.getRequestURI() + "]"
                + "[error: " + e.getMessage() + "]");
    }
}
