package com.school.system.exceptions;

import com.school.system.exceptions.exceptionClasses.NotAcceptableDataException;
import com.school.system.exceptions.exceptionClasses.RecordNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class exceptionHandlerAdvice extends ResponseEntityExceptionHandler
{

    @ExceptionHandler(NotAcceptableDataException.class)
    public ResponseEntity<ErrorResponse> notAcceptableDataExceptionHandler(NotAcceptableDataException ex,
            WebRequest webRequest)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage()
                , HttpStatus.NOT_ACCEPTABLE
                ,ex.getLocalizedMessage()
                , webRequest.getDescription(false)
                , System.currentTimeMillis());
        return new ResponseEntity(errorResponse, errorResponse.getErrorCode());
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> recordNotFoundExceptionHandler(RecordNotFoundException ex,
            WebRequest webRequest)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage()
                , HttpStatus.NOT_FOUND
                ,ex.getLocalizedMessage()
                , webRequest.getDescription(false)
                , System.currentTimeMillis());
        return new ResponseEntity(errorResponse, errorResponse.getErrorCode());
    }

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(MethodArgumentNotValidException ex,
            HttpServletRequest webRequest)
    {
        ErrorResponse errorResponse = new ErrorResponse("Something missing", HttpStatus.NOT_ACCEPTABLE,
                webRequest.getRequestURI(), System.currentTimeMillis());
        return new ResponseEntity(errorResponse, errorResponse.getErrorCode());
    }*/

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
            , HttpHeaders headers, HttpStatus status, WebRequest webRequest)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                "Validation Error Detected"
                , HttpStatus.NOT_ACCEPTABLE
                ,ex.getLocalizedMessage()
                , webRequest.getDescription(false)
                , System.currentTimeMillis());
        return new ResponseEntity(errorResponse, errorResponse.getErrorCode());
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        return super.handleMissingPathVariable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }
}
