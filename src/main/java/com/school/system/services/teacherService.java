package com.school.system.services;

import com.school.system.DTOs.ElMapper;
import com.school.system.DTOs.teacherDTO;
import com.school.system.entities.teacher;
import com.school.system.repos.teacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class teacherService implements templateService<teacher, teacherDTO>
{
    private final subjectService subjectService;
    private final teacherRepo teacherRepo;
    private final ElMapper<teacher,teacherDTO> mapper;

    @Autowired
    public teacherService(teacherRepo teacherRepo, ElMapper<teacher, teacherDTO> mapper,@Lazy subjectService subjectService)
    {
        this.teacherRepo = teacherRepo;
        this.subjectService = subjectService;
        this.mapper = new ElMapper<>
                (
                        //Function to change Teacher Object to TeacherDTO Object
                        teacher -> new teacherDTO(teacher.getName(), teacher.getAge(),teacher.getYearsOfExperience()
                                ,teacher.getSubject().getId())
                        ,
                        //Function to change TeacherDTO Object to Teacher Object
                        teacherDTO -> new teacher(null, teacherDTO.getName(), teacherDTO.getAge()
                                , teacherDTO.getYearsOfExperience(),this.subjectService.get(teacherDTO.getSubject()))
                        ,
                        //Function to Convert Teacher UUID Collection to full subject Object
                        // send null because there is no Collections
                        null
                );
    }



    @Override
    public UUID add(teacherDTO teacherDTO)
    {
        return this.teacherRepo.save(mapper.toEntityObject(teacherDTO)).getId();
    }

    @Override
    public teacher get(UUID id)
    {
        Optional<teacher> temp = this.teacherRepo.findById(id);
        return temp.orElse(null);
    }

    @Override
    public ArrayList<teacher> getAll()
    {
        return (ArrayList<teacher>) this.teacherRepo.findAll();
    }

    @Override
    public void update(teacherDTO teacherDTO,UUID id)
    {
        if(this.check(id))
        {
            this.teacherRepo.deleteById(id);
            this.teacherRepo.save(this.mapper.toEntityObject(teacherDTO).builder().id(id).build());
        }
    }

    public void patchUpdate(teacherDTO teacherDTO, UUID id)
    {
        Optional<teacher> target = teacherRepo.findById(id);

        if(target.isPresent())
        {
            if(!teacherDTO.getName().isBlank())
                target.get().setName(teacherDTO.getName());

            if(teacherDTO.getAge()!=0)
                target.get().setAge(teacherDTO.getAge());

            if(Objects.nonNull(teacherDTO.getSubject()))
                target.get().setSubject(this.subjectService.get(teacherDTO.getSubject()));

            if(teacherDTO.getYearsOfExperience()!=0)
                target.get().setYearsOfExperience(teacherDTO.getYearsOfExperience());

            this.teacherRepo.save(target.get());
        }else
            System.out.println("subject not found , not added exception");
    }

    @Override
    public void delete(UUID id)
    {
        if(check(id))
            this.teacherRepo.deleteById(id);
    }

    @Override
    public boolean check(UUID id)
    {
        return this.teacherRepo.existsById(id);
    }
}