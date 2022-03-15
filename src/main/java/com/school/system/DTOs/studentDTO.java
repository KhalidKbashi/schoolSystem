package com.school.system.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class studentDTO implements Serializable
{
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "[A-Za-z\\s]{3,}")
    private String name;

    @Min(12) @Max(25)
    private int age;

    private String[] subject;
}
