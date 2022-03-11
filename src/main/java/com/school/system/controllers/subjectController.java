package com.school.system.controllers;

import com.school.system.HATEOAS.resourceAssemblers.subjectAssembler;
import com.school.system.HATEOAS.resourceSupports.subjectModel;
import com.school.system.entities.subject;
import com.school.system.exceptions.exceptionClasses.CollectionEmptyException;
import com.school.system.exceptions.exceptionClasses.NotAcceptableDataException;
import com.school.system.exceptions.exceptionClasses.RecordNotFoundException;
import com.school.system.services.subjectService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api/subject",produces = "application/HAL+JSON")
public class subjectController
{
    private subjectService subjectService;
    private subjectAssembler subjectAssembler;

    @Autowired
    public subjectController(subjectService subjectService, subjectAssembler subjectAssembler)
    {
        this.subjectService = subjectService;
        this.subjectAssembler = subjectAssembler;
    }




    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public subjectModel getRequestById(@PathVariable("id") UUID id)
            throws RecordNotFoundException
    {

        subject subject = this.subjectService.get(id);
        if(subject == null)
            throw new RecordNotFoundException("Subject Not Found in db");

        return this.subjectAssembler.toModel(subject);
    }

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<subjectModel> getAllRequest() throws CollectionEmptyException
    {
        List temp = List.copyOf(this.subjectService.getAll());

        if(temp.isEmpty())
            throw new CollectionEmptyException("There is no subjects data found in db");

        return this.subjectAssembler.toCollectionModel(temp);
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID postRequest(@Valid @RequestBody subject subject) throws NotAcceptableDataException
    {
        if(Objects.isNull(subject)) //works when the assertion in the entity class get removed
            throw new NotAcceptableDataException("Subject data is not send probably");

        return this.subjectService.add(subject);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRequest(@PathVariable("id") UUID id) throws NotAcceptableDataException,
            RecordNotFoundException
    {
        if(id == null)
            throw new NotAcceptableDataException("Missed ID value");

        if(!this.subjectService.check(id))
            throw new RecordNotFoundException("there is no subject with ID : "+id+ " in db to be deleted");

        this.subjectService.delete(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void putRequest(@PathVariable("id") @NonNull UUID id, @RequestBody subject subject)
    {
        if(!this.subjectService.check(id))
            throw new RecordNotFoundException("subject with ID: "+id+" not found");

        this.subjectService.update(subject,id);
    }

    //NOT WORKING API -NEED TO UPDATE
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void patchRequest(@PathVariable("id") @NonNull UUID id, @RequestBody subject subject)
    {
        if(!this.subjectService.check(id))
            throw new RecordNotFoundException("subject with ID: "+id+" not found");

        this.subjectService.patchUpdate(subject,id);
    }
}
