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
public class subject implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    //Not Needed because the Validation is done in DTO Class - Deleted for Optimizing Performance
    /*@NotBlank @NotEmpty
    @Pattern(regexp = "[A-Za-z\\s]{3,}")
    */
    private String name;

    //@Min(1) @Max(5)
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
