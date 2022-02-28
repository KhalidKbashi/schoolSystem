package com.school.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class student
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    //todo add a method to auto store string into "This Way" style
    @NotBlank @NotEmpty
    @Pattern(regexp = "[A-Za-z\\s]{3,}")
    private String name;

    @Min(12) @Max(23)
    private int age;

    @ManyToMany(mappedBy = "students", cascade = {CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    Collection<subject> subjects;

    public void addSubjectByID(Collection<subject> subjects)
    {
        this.subjects.addAll(subjects);
    }
}
