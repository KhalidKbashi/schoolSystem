package com.school.system.exceptions.exceptionClasses;

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