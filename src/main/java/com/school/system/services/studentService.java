package com.school.system.services;

import com.school.system.DTOs.ElMapper;
import com.school.system.DTOs.studentDTO;
import com.school.system.entities.student;
import com.school.system.repos.studentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class studentService implements templateService<student, studentDTO>
{
    private final studentRepo studentRepo;
    private final subjectService subjectService;
    private final ElMapper<student,studentDTO> mapper;

    @Autowired
    public studentService(studentRepo studentRepo, @Lazy subjectService subjectService
            , ElMapper<student,studentDTO> mapper)
    {
        this.studentRepo = studentRepo;
        this.subjectService = subjectService;

        this.mapper = new ElMapper<>
                (
                        //Function to change Student Object to StudentDTO Object -toDTO()
                        student -> new studentDTO(student.getName(), student.getAge(), (String[]) student.getSubjects()
                                .stream().map(subject -> subject.getId()).toArray())
                        ,
                        //Function to change StudentDTO Object to Student Object -toEntity()
                        studentDTO -> new student(null, studentDTO.getName(), studentDTO.getAge(),
                                Arrays
                                        .stream(studentDTO.getSubject())
                                        .distinct()
                                        .map(uuid -> this.subjectService.get(UUID.fromString(uuid)))
                                        .collect(Collectors.toCollection(ArrayList::new)))
                        ,
                        //Function to Convert Subject UUID Collection to full subject Object
                        uuids -> uuids.stream().distinct().map(uuid -> this.get(uuid)).collect(Collectors.toList())
                );
    }



    @Override
    public UUID add(studentDTO studentDTO)
    {
        return this.studentRepo.save(mapper.toEntityObject(studentDTO)) .getId();
    }

    @Override
    public student get(UUID id)
    {
        Optional<student> temp = this.studentRepo.findById(id);
        return temp.orElse(null);
    }

    @Override
    public ArrayList<student> getAll()
    {
        return (ArrayList<student>) this.studentRepo.findAll();
    }

    @Override
    public void update(studentDTO studentDTO,UUID id)
    {
        if(this.check(id))
        {
            this.studentRepo.deleteById(id);
            this.studentRepo.save(mapper.toEntityObject(studentDTO).builder().id(id).build());
        }

    }

    public void patchUpdate(studentDTO studentDTO, UUID id)
    {
        Optional<student> target = this.studentRepo.findById(id);

        if(target.isPresent())
        {
            if(!studentDTO.getName().isBlank())
                target.get().setName(studentDTO.getName());

            if(studentDTO.getAge()!=0)
                target.get().setAge(studentDTO.getAge());

            if(studentDTO.getSubject()==null)
                target.get().getSubjects()
                        .addAll(mapper.uuidsToEntitiesConverter(Arrays.stream(studentDTO.getSubject()
                        ).distinct().map(s -> UUID.fromString(s)).collect(Collectors.toList())));

            this.studentRepo.save(target.get());
        }
        else
            System.out.println("student not found , not added exception");
    }

    @Override
    public void delete(UUID id)
    {
        if(check(id))
            this.studentRepo.deleteById(id);
    }

    @Override
    public boolean check(UUID id)
    {
        return this.studentRepo.existsById(id);
    }
}