package com.school.system.controllers;

import com.school.system.entities.teacher;
import com.school.system.exceptions.exceptionClasses.NotAcceptableDataException;
import com.school.system.exceptions.exceptionClasses.RecordNotFoundException;
import com.school.system.services.teacherService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

@RestController("/teacher")
public class teacherController
{
    private teacherService teacherService;

    @Autowired
    public teacherController(teacherService teacherService)
    {
        this.teacherService = teacherService;
    }




    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public teacher getRequestById(@PathVariable("id") UUID id) throws NotAcceptableDataException,
            RecordNotFoundException, MethodArgumentNotValidException, NoSuchMethodException
    {

        teacher teacher = this.teacherService.get(id);

        if(teacher == null)
            throw new RecordNotFoundException("Teacher Not Found in db");
        return teacher;
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID postRequest(@Valid @RequestBody teacher teacher) throws NotAcceptableDataException
    {
        if(Objects.isNull(teacher)) //works when the assertion in the entity class get removed
            throw new NotAcceptableDataException("teacher data is not send probably");

        return this.teacherService.add(teacher);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRequest(@PathVariable("id") UUID id) throws NotAcceptableDataException,
            RecordNotFoundException
    {
        if(id == null)
            throw new NotAcceptableDataException("Missed ID value");

        if(!this.teacherService.check(id))
            throw new RecordNotFoundException("there is no subject with ID : "+id+ " in db to be deleted");

        this.teacherService.delete(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void putRequest(@PathVariable("id") @NonNull UUID id, @RequestBody teacher teacher)
    {
        if(!this.teacherService.check(id))
            throw new RecordNotFoundException("teacher with ID: "+id+" not found");

        this.teacherService.update(teacher,id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void patchRequest(@PathVariable("id") @NonNull UUID id, @RequestBody teacher teacher)
    {
        if(!this.teacherService.check(id))
            throw new RecordNotFoundException("teacher with ID: "+id+" not found");

        this.teacherService.patchUpdate(teacher,id);
    }
}
