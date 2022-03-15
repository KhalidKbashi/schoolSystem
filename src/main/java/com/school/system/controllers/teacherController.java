package com.school.system.controllers;

import com.school.system.DTOs.teacherDTO;
import com.school.system.HATEOAS.assemblers.teacherAssembler;
import com.school.system.HATEOAS.models.teacherModel;
import com.school.system.entities.teacher;
import com.school.system.exceptions.exceptionClasses.CollectionEmptyException;
import com.school.system.exceptions.exceptionClasses.NotAcceptableDataException;
import com.school.system.exceptions.exceptionClasses.RecordNotFoundException;
import com.school.system.services.teacherService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/teacher",produces = "application/HAL+JSON")
public class teacherController
{
    private final teacherService teacherService;
    private final com.school.system.HATEOAS.assemblers.teacherAssembler teacherAssembler;

    @Autowired
    public teacherController(@Lazy teacherService teacherService, teacherAssembler teacherAssembler)
    {
        this.teacherService = teacherService;
        this.teacherAssembler = teacherAssembler;
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public teacherModel getRequestById(@PathVariable("id") UUID id) throws RecordNotFoundException
    {

        teacher teacher = this.teacherService.get(id);

        if(Objects.isNull(teacher))
            throw new RecordNotFoundException("Teacher Not Found in db");

        return this.teacherAssembler.toModel(teacher);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<teacherModel> getAllRequest() throws CollectionEmptyException
    {
        List<teacher> temp = List.copyOf(this.teacherService.getAll());

        if(temp.isEmpty())
            throw new CollectionEmptyException("There is no teachers data found in db");
        return this.teacherAssembler.toCollectionModel(temp);
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID postRequest(@Valid @RequestBody teacherDTO teacherDTO) throws NotAcceptableDataException
    {
        if(Objects.isNull(teacherDTO)) //works when the assertion in the entity class get removed
            throw new NotAcceptableDataException("teacher data is not send probably");

        return this.teacherService.add(teacherDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRequest(@PathVariable("id") UUID id) throws NotAcceptableDataException,
            RecordNotFoundException
    {
        if(id == null)
            throw new NotAcceptableDataException("Missed ID value");

        if(!this.teacherService.check(id))
            throw new RecordNotFoundException("there is no teacher with ID : "+id+ " in db to be deleted");

        this.teacherService.delete(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void putRequest(@PathVariable("id") @NonNull UUID id, @RequestBody teacherDTO teacherDTO)
    {
        if(!this.teacherService.check(id))
            throw new RecordNotFoundException("teacher with ID: "+id+" not found");

        this.teacherService.update(teacherDTO,id);
    }

    //NOT WORKING API -NEED TO UPDATE
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void patchRequest(@PathVariable("id") @NonNull UUID id, @RequestBody teacherDTO teacherDTO)
    {
        if(!this.teacherService.check(id))
            throw new RecordNotFoundException("teacher with ID: "+id+" not found");

        this.teacherService.patchUpdate(teacherDTO,id);
    }
}
