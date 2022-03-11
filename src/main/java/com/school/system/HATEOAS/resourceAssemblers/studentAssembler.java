package com.school.system.HATEOAS.resourceAssemblers;

import com.school.system.HATEOAS.resourceSupports.studentModel;
import com.school.system.controllers.studentController;
import com.school.system.entities.student;
import lombok.SneakyThrows;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class studentAssembler extends RepresentationModelAssemblerSupport<student, studentModel>
{
    public studentAssembler()
    {
        super(studentController.class, studentModel.class);
    }

    @SneakyThrows
    @Override
    public studentModel toModel(student student)
    {
        return new studentModel(student)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(studentController.class)
                        .getRequestById(student.getId())).withSelfRel());
    }

    @Override
    public CollectionModel<studentModel> toCollectionModel(Iterable<? extends student> students)
    {
        return super.toCollectionModel(students)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(studentController.class).getAllRequest())
                        .withSelfRel());
    }
}
