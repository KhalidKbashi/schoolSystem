package com.school.system.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class teacherDTO implements Serializable
{
    @NotBlank
    @NotEmpty
    private String name;

    @Min(18)@Max(75)
    private int age;

    @Min(0)@Max(25)
    private int yearsOfExperience;

    private UUID subject;

}
