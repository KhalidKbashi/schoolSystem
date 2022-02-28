package com.school.system.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class subject
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    //todo add a method to auto store string into "This Way" style
    @NotBlank @NotEmpty
    @Pattern(regexp = "[A-Za-z\\s]{3,}")
    private String name;

    @Min(1) @Max(5)
    private int numOfHours;


    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "student-subjects"
            ,joinColumns = @JoinColumn(name = "subject_id")
            ,inverseJoinColumns = @JoinColumn(name = "student_id"))
    Collection<student> students;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.PERSIST,CascadeType.REFRESH},mappedBy = "subject")
    Collection<teacher> teachers;


    public void addStudentByID(Collection<student> students)
    {
        this.students.addAll(students);
    }

    public void addTeacherByID(Collection<teacher> teachers)
    {
        this.teachers.addAll(teachers);
    }
}
