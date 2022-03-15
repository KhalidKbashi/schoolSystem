package com.school.system.HATEOAS.models;
import com.school.system.entities.student;
import com.school.system.entities.subject;
import com.school.system.entities.teacher;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

@Getter
@Relation(value = "subject",collectionRelation = "subjects")
public class subjectModel extends RepresentationModel<subjectModel>
{
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "[A-Za-z\\s]{3,}")
    private final String name;

    @Min(1) @Max(5)
    private final int numOfHours;

    //Copy Constructor
    public subjectModel(subject subject)
    {
        this.name = subject.getName();
        this.numOfHours = subject.getNumOfHours();
        this.students = subject.getStudents();
        this.teachers = subject.getTeachers();
    }

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "student-subjects"
            ,joinColumns = @JoinColumn(name = "subject_id")
            ,inverseJoinColumns = @JoinColumn(name = "student_id"))
    Collection<student> students;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.PERSIST,CascadeType.REFRESH},mappedBy = "subject")
    Collection<teacher> teachers;
}
