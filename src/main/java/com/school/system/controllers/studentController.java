package com.school.system.controllers;

import com.school.system.DTOs.studentDTO;
import com.school.system.HATEOAS.assemblers.studentAssembler;
import com.school.system.HATEOAS.models.studentModel;
import com.school.system.entities.student;
import com.school.system.exceptions.exceptionClasses.CollectionEmptyException;
import com.school.system.exceptions.exceptionClasses.NotAcceptableDataException;
import com.school.system.exceptions.exceptionClasses.RecordNotFoundException;
import com.school.system.services.studentService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/student",produces = "application/HAL+JSON")
public class studentController
{
    private final studentService studentService;
    private final studentAssembler studentAssembler;

    @Autowired
    public studentController(@Lazy studentService studentService, studentAssembler studentAssembler)
    {
        this.studentService = studentService;
        this.studentAssembler = studentAssembler;
    }




    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public studentModel getRequestById(@PathVariable("id") UUID id) throws RecordNotFoundException
    {

        student student = this.studentService.get(id);

        if(Objects.isNull(student))
            throw new RecordNotFoundException("Student Not Found in db");

        return this.studentAssembler.toModel(student);
    }

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<studentModel> getAllRequest() throws CollectionEmptyException
    {
        List<student> temp = List.copyOf(this.studentService.getAll());

        if(temp.isEmpty())
            throw new CollectionEmptyException("There is no students data found in db");

        return this.studentAssembler.toCollectionModel(temp);
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID postRequest(@Valid @RequestBody studentDTO studentDTO) throws NotAcceptableDataException
    {
        if(Objects.isNull(studentDTO)) //works when the assertion in the entity class get removed
            throw new NotAcceptableDataException("Student data is not send probably");

        System.out.println("HIIIIIIIIIIIIIIIIIIIIIIii");
        Arrays.stream(studentDTO.getSubject()).forEach(uuid -> System.out.println("id "+uuid));

        return this.studentService.add(studentDTO);
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
    public void putRequest(@PathVariable("id") @NonNull UUID id, @Valid @RequestBody studentDTO studentDTO)
    {
        if(!this.studentService.check(id))
            throw new RecordNotFoundException("student with ID: "+id+" not found");

        this.studentService.update(studentDTO,id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void patchRequest(@PathVariable("id") @NonNull UUID id,@Valid @RequestBody  studentDTO studentDTO)
    {
        if(!this.studentService.check(id))
            throw new RecordNotFoundException("student with ID: "+id+" not found");

        this.studentService.patchUpdate(studentDTO,id);
    }

}