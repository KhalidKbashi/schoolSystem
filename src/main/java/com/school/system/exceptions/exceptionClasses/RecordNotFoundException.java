package com.school.system.exceptions.exceptionClasses;

public class RecordNotFoundException extends RuntimeException
{
    public RecordNotFoundException()
    {
    }

    public RecordNotFoundException(String message)
    {
        super(message);
    }

    public RecordNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public RecordNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}