package com.school.system.HATEOAS.assemblers;

import com.school.system.HATEOAS.models.subjectModel;
import com.school.system.controllers.subjectController;
import com.school.system.entities.subject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class subjectAssembler extends RepresentationModelAssemblerSupport<subject, subjectModel>
{
    public subjectAssembler()
    {
        super(subjectController.class, subjectModel.class);
    }

    @Override
    public CollectionModel<subjectModel> toCollectionModel(Iterable<? extends subject> entities)
    {
        return super.toCollectionModel(entities)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(subjectController.class).getAllRequest()).withSelfRel());
    }

    @Override
    public subjectModel toModel(subject subject)
    {
        return new subjectModel(subject).add(WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(subjectController.class)
                                .getRequestById(subject.getId()))
                        .withSelfRel());
    }
}
