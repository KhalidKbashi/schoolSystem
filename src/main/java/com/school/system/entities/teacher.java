package com.school.system.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class teacher implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    //Not Needed because the Validation is done in DTO Class - Deleted for Optimizing Performance
    /*@NotBlank
    @NotEmpty
    */
    private String name;

    //@Min(18)@Max(75)
    private int age;

    //@Min(0)@Max(25)
    private int yearsOfExperience;


    //teacher can only handle one subject,there are many subjects
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private subject subject;

    public Collection<student> getStudents()
    {
        return this.subject.students;
    }

}