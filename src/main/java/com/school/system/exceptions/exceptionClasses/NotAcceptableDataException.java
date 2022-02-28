package com.school.system.exceptions.exceptionClasses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableDataException extends RuntimeException
{
    public NotAcceptableDataException()
    {
    }

    public NotAcceptableDataException(String message)
    {
        super(message);
    }

    public NotAcceptableDataException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NotAcceptableDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}