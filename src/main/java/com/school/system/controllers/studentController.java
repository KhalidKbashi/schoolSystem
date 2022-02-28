package com.school.system.controllers;

import com.school.system.entities.student;
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
    public student getRequestById(@PathVariable("id") UUID id)
    {
        student student = this.studentService.get(id);
        return student;
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID postRequest(@Valid @RequestBody student student)
    {
        return this.studentService.add(student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRequest(@PathVariable("id") UUID id)
    {
        this.studentService.delete(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void putRequest(@PathVariable("id") @NonNull UUID id, @RequestBody student student)
    {
        this.studentService.add(student);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void patchRequest(@PathVariable("id") @NonNull UUID id, @RequestBody student student)
    {
        this.studentService.patchUpdate(student,id);
    }

}