package com.school.system.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Setter
@Getter
public class ErrorResponse
{
    private final String Massage;
    private final HttpStatus ErrorCode;
    private final String LocalizedMessage;
    private final String RequestedURL;
    private final long Timestamp;

    public String getErrorMessage()
    {
        return "Massage : "+this.Massage
                +"\nRequested URL : "+this.RequestedURL
                +"\nTimestamp : "+this.Timestamp
                +"\nErrorCode : "+this.ErrorCode
                +"\nDetailed Error Massage : "+this.LocalizedMessage;
    }
}