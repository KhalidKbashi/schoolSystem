package com.school.system.controllers;

import com.school.system.entities.subject;
import com.school.system.exceptions.exceptionClasses.NotAcceptableDataException;
import com.school.system.exceptions.exceptionClasses.RecordNotFoundException;
import com.school.system.services.subjectService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/subject")
public class subjectController
{
    private subjectService subjectService;

    @Autowired
    public subjectController(subjectService subjectService)
    {
        this.subjectService = subjectService;
    }




    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public subject getRequestById(@PathVariable("id") UUID id) throws NotAcceptableDataException,
            RecordNotFoundException, MethodArgumentNotValidException, NoSuchMethodException
    {

        subject subject = this.subjectService.get(id);

        if(subject == null)
            throw new RecordNotFoundException("Subject Not Found in db");
        return subject;
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

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void patchRequest(@PathVariable("id") @NonNull UUID id, @RequestBody subject subject)
    {
        if(!this.subjectService.check(id))
            throw new RecordNotFoundException("subject with ID: "+id+" not found");

        this.subjectService.patchUpdate(subject,id);
    }
}
