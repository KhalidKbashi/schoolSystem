package com.school.system.HATEOAS.assemblers;

import com.school.system.HATEOAS.models.teacherModel;
import com.school.system.controllers.subjectController;
import com.school.system.controllers.teacherController;
import com.school.system.entities.teacher;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class teacherAssembler extends RepresentationModelAssemblerSupport<teacher, teacherModel>
{
    public teacherAssembler()
    {
        super(teacherController.class, teacherModel.class);
    }

    @Override
    public CollectionModel<teacherModel> toCollectionModel(Iterable<? extends teacher> entities)
    {
        return super.toCollectionModel(entities)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(subjectController.class).getAllRequest()).withSelfRel());
    }

    @Override
    public teacherModel toModel(teacher teacher)
    {
        return new teacherModel(teacher).add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(subjectController.class)
                        .getRequestById(teacher.getId()))
                .withSelfRel());
    }
}