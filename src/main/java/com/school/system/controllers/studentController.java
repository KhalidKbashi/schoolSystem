package com.school.system.controllers;

import com.school.system.entities.student;
import com.school.system.exceptions.exceptionClasses.NotAcceptableDataException;
import com.school.system.exceptions.exceptionClasses.RecordNotFoundException;
import com.school.system.services.studentService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/student")
public class studentController
{
    private studentService studentService;

    @Autowired
    public studentController(studentService studentService)
    {
        this.studentService = studentService;
    }




    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public student getRequestById(@PathVariable("id") UUID id) throws NotAcceptableDataException,
            RecordNotFoundException
    {
        if(id == null)
            throw new NotAcceptableDataException("Missed ID value");

        student student = this.studentService.get(id);

        if(student == null)
            throw new RecordNotFoundException("Student Not Found in db");
        return student;
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID postRequest(@Valid @RequestBody student student) throws NotAcceptableDataException
    {
        if(Objects.isNull(student)) //works when the assertion in the entity class get removed
            throw new NotAcceptableDataException("Student data is not send probably");

        return this.studentService.add(student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRequest(@PathVariable("id") UUID id) throws NotAcceptableDataException,
            RecordNotFoundException
    {
        if(id == null)
            throw new NotAcceptableDataException("Missed ID value");

        if(!this.studentService.check(id))
            throw new RecordNotFoundException("there is no student with ID : "+id+ " in db to be deleted");

        this.studentService.delete(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void putRequest(@PathVariable("id") @NonNull UUID id, @RequestBody student student)
    {
        if(!this.studentService.check(id))
            throw new RecordNotFoundException("student with ID: "+id+" not found");

        this.studentService.update(student,id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void patchRequest(@PathVariable("id") @NonNull UUID id, @RequestBody student student)
    {
        if(!this.studentService.check(id))
            throw new RecordNotFoundException("student with ID: "+id+" not found");

        this.studentService.patchUpdate(student,id);
    }

}