package com.school.system.exceptions.exceptionClasses;

public class CollectionEmptyException extends RuntimeException
{
    public CollectionEmptyException()
    {
    }

    public CollectionEmptyException(String message)
    {
        super(message);
    }

    public CollectionEmptyException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CollectionEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}