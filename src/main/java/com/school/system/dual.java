package com.school.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown=true)
public class dual <S,T>
{
    private S first;
    private T second;

    public dual(S first, T second)
    {
        this.first = first;
        this.second = second;
    }
}
