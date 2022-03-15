package com.school.system.HATEOAS.models;

import com.school.system.entities.student;
import com.school.system.entities.subject;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.*;
import java.util.Collection;

@Getter
@Relation(value = "student",collectionRelation = "students")
public class studentModel extends RepresentationModel<studentModel>
{
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "[A-Za-z\\s]{3,}")
    private final String name;
    //Copy Constructor
    public studentModel(student student)
    {
        this.name = student.getName();
        this.age = student.getAge();
        this.subjects = student.getSubjects();
    }

    @Min(12) @Max(23)
    private final int age;

    @ManyToMany(mappedBy = "students", cascade = {CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    Collection<subject> subjects;

    public void addSubjectByID(Collection<subject> subjects)
    {
        this.subjects.addAll(subjects);
    }
}
