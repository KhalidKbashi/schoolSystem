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

@ControllerAdvice
public class exceptionHandlerAdvice extends ResponseEntityExceptionHandler
{
    //todo Allign Error Response paylod
    //NOT_ACCEPTABLE
    @ExceptionHandler(NotAcceptableDataException.class)
    public ResponseEntity<ErrorResponse> notAcceptableDataExceptionHandler(NotAcceptableDataException ex,
            WebRequest request)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage()
                , HttpStatus.NOT_ACCEPTABLE
                ,ex.getLocalizedMessage()
                , request.getDescription(false)
                , System.currentTimeMillis());
        return new ResponseEntity(errorResponse, errorResponse.getErrorCode());
    }
    //NOT_FOUND
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> recordNotFoundExceptionHandler(RecordNotFoundException ex,
            WebRequest request)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage()
                , HttpStatus.NOT_FOUND
                ,ex.getLocalizedMessage()
                , request.getDescription(false)
                , System.currentTimeMillis());
        return new ResponseEntity(errorResponse, errorResponse.getErrorCode());
    }
    //NOT_ACCEPTABLE
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
            , HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                "Validation Error Detected"
                , HttpStatus.NOT_ACCEPTABLE
                ,ex.getLocalizedMessage()
                , request.getDescription(false)
                , System.currentTimeMillis());
        return new ResponseEntity(errorResponse, errorResponse.getErrorCode());
    }
    //RESET_CONTENT
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex
            , HttpHeaders headers, HttpStatus status, WebRequest request)
    {

        ErrorResponse errorResponse = new ErrorResponse(
                "Missing Path Variable"
                , HttpStatus.RESET_CONTENT
                ,ex.getLocalizedMessage()
                , request.getDescription(false)
                , System.currentTimeMillis());
        return new ResponseEntity(errorResponse, errorResponse.getErrorCode());
    }
    //INTERNAL_SERVER_ERROR
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex
            , HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                "Something Gone Wrong"
                , HttpStatus.INTERNAL_SERVER_ERROR
                ,ex.getLocalizedMessage()
                , request.getDescription(false)
                , System.currentTimeMillis());
        return new ResponseEntity(errorResponse, errorResponse.getErrorCode());
    }
}
