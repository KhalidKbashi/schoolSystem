package com.school.system.HATEOAS.models;

import com.school.system.entities.subject;
import com.school.system.entities.teacher;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Relation(value = "teacher",collectionRelation = "teachers")
public class teacherModel extends RepresentationModel<teacherModel>
{
    public teacherModel(teacher teacher)
    {
        this.name = teacher.getName();
        this.age = teacher.getAge();
        this.yearsOfExperience = teacher.getYearsOfExperience();
        this.subject = teacher.getSubject();
    }

    private String name;

    //@Min(18)@Max(75)
    private int age;

    //@Min(0)@Max(25)
    private int yearsOfExperience;


    //teacher can only handle one subject,there are many subjects
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private com.school.system.entities.subject subject;
}
