package com.school.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class teacher
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    //todo add a method to auto store string into "This Way" style
    @NotBlank
    @NotEmpty
    private String name;

    @Min(18)@Max(75)
    private int age;

    @Min(0)@Max(25)
    private int yearsOfExperience;


    //teacher can only handle one subject,there are many subjects
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    subject subject;

    public Collection<student> getStudents()
    {
        return this.subject.students;
    }

}