package com.school.system.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class student implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    //Not Needed because the Validation is done in DTO Class
    /*@NotBlank @NotEmpty
    @Pattern(regexp = "[A-Za-z\\s]{3,}")
    */
    private String name;

    //@Min(12) @Max(23)
    private int age;

    @ManyToMany(mappedBy = "students", cascade = {CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Collection<subject> subjects;

    public void addSubject(subject subject)
    {
        this.subjects.add(subject);
    }
}