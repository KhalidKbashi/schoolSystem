package com.school.system.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class subjectDTO implements Serializable
{
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "[A-Za-z\\s]{3,}")
    private String name;

    @Min(1) @Max(5)
    private int numOfHours;

    Collection<UUID> students;

    Collection<UUID> teachers;
}
