package com.school.system.exceptions;

import com.school.system.exceptions.exceptionClasses.NotAcceptableDataException;
import com.school.system.exceptions.exceptionClasses.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class exceptionHandlerAdvice extends ResponseEntityExceptionHandler
{

    @ExceptionHandler(NotAcceptableDataException.class)
    public ResponseEntity<ErrorResponse> notAcceptableDataExceptionHandler(NotAcceptableDataException ex,
            HttpServletRequest webRequest)
    {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE,
                webRequest.getRequestURI(),System.currentTimeMillis());
        return new ResponseEntity(errorResponse,errorResponse.getErrorCode());
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> recordNotFoundExceptionHandler(RecordNotFoundException ex,
            HttpServletRequest webRequest)
    {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND,
                webRequest.getRequestURI(),System.currentTimeMillis());
        return new ResponseEntity(errorResponse,errorResponse.getErrorCode());
    }

}
