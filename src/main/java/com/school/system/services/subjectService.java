package com.school.system.services;

import com.school.system.DTOs.ElMapper;
import com.school.system.DTOs.subjectDTO;
import com.school.system.entities.student;
import com.school.system.entities.subject;
import com.school.system.entities.teacher;
import com.school.system.repos.subjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class subjectService implements templateService<subject,subjectDTO>
{
    private final subjectRepo subjectRepo;
    private final studentService studentsService;
    private final teacherService teacherService;
    private final ElMapper<subject, subjectDTO> mapper;

    private final Function<Collection<UUID>,Collection<student>> uuidsToStudentsFunction;
    private final Function<Collection<UUID>,Collection<teacher>> uuidsToTeachersFunction;

    @Autowired
    public subjectService(subjectRepo subjectRepo, @Lazy studentService studentsService,
            @Lazy teacherService teacherService, ElMapper<subject, subjectDTO> mapper)
    {
        this.subjectRepo = subjectRepo;
        this.studentsService = studentsService;
        this.teacherService = teacherService;
        this.mapper = new ElMapper<>
                (
                        //Function to change Subject Object to SubjectDTO Object
                        subject -> new subjectDTO(subject.getName(), subject.getNumOfHours(), subject.getStudents()
                                .stream().map(student -> student.getId()).collect(Collectors.toList())
                                , subject.getTeachers()
                                .stream().map(teacher -> teacher.getId()).collect(Collectors.toList())
                        )
                        ,
                        //Function to change SubjectDTO Object to Subject Object
                        subjectDTO -> new subject(null, subjectDTO.getName(), subjectDTO.getNumOfHours()
                                , subjectDTO.getStudents().stream().distinct().map(uuid -> this.studentsService.get(uuid))
                                .collect(Collectors.toCollection(ArrayList::new))
                                , subjectDTO.getTeachers().stream().distinct().map(uuid -> this.teacherService.get(uuid))
                                .collect(Collectors.toCollection(ArrayList::new)))
                        ,
                        //Function to Convert Subject UUID Collection to full subject Object
                        // send null because there is two Collections students,teachers
                        null
                );

        this.uuidsToStudentsFunction = uuids -> uuids.stream().distinct()
                .map(uuid -> this.studentsService.get(uuid)).collect(Collectors.toList());
        this.uuidsToTeachersFunction = uuids -> uuids.stream().distinct()
                .map(uuid -> this.teacherService.get(uuid)).collect(Collectors.toList());
    }



    @Override
    public UUID add(subjectDTO subjectDTO)
    {
        /*this.subjectRepo.save(new subject().builder().name("Calculus").numOfHours(3).teachers(null).students(null).build());
        this.subjectRepo.save(new subject().builder().name("Discrete Mathematics").numOfHours(2).teachers(null).students(null).build());
        this.subjectRepo.save(new subject().builder().name("Arabic").numOfHours(3).teachers(null).students(null).build());
        this.subjectRepo.save(new subject().builder().name("Numeric Analysis").numOfHours(3).teachers(null).students(null).build());*/
        return this.subjectRepo.save(mapper.toEntityObject(subjectDTO)
                .builder()
/*
                .teachers(mapper.customUuidsToEntitiesConverter(subjectDTO.getTeachers(),this.uuidsToTeachersFunction))
                .students(mapper.customUuidsToEntitiesConverter(subjectDTO.getStudents(),this.uuidsToStudentsFunction))
*/
                .build()).getId();
    }

    @Override
    public subject get(UUID id)
    {
        Optional<subject> temp = this.subjectRepo.findById(id);
        return temp.orElse(null);
    }

    @Override
    public ArrayList<subject> getAll()
    {
        return (ArrayList<subject>) this.subjectRepo.findAll();
    }

    @Override
    public void update(subjectDTO subjectDTO,UUID id)
    {
        if(this.check(id))
        {
            this.subjectRepo.deleteById(id);
            this.subjectRepo.save(mapper.toEntityObject(subjectDTO).builder().id(id).build());
        }
    }

    public void patchUpdate(subjectDTO subjectDTO, UUID id)
    {
        Optional<subject> target = this.subjectRepo.findById(id);

        if(target.isPresent())
        {
            if(!subjectDTO.getName().isBlank())
                target.get().setName(subjectDTO.getName());
            if(subjectDTO.getNumOfHours()!=0)
                target.get().setNumOfHours(subjectDTO.getNumOfHours());

            /*if(!subjectDTO.getTeachers().isEmpty())
                target.get().getTeachers()
                        .addAll(mapper.customUuidsToEntitiesConverter(subjectDTO.getTeachers(),this.uuidsToTeachersFunction));

            if(!subjectDTO.getStudents().isEmpty())
                target.get().getStudents()
                        .addAll(mapper.customUuidsToEntitiesConverter(subjectDTO.getStudents(),this.uuidsToStudentsFunction));*/

            this.subjectRepo.save(target.get());
        }else
            System.out.println("subject not found , not added exception");
    }

    @Override
    public void delete(UUID id)
    {
        if(check(id))
            this.subjectRepo.deleteById(id);
    }

    @Override
    public boolean check(UUID id)
    {
        return this.subjectRepo.existsById(id);
    }


    //Helpers

    private Collection<student> getStudents(Collection<UUID> uuids)
    {
        if (uuids.isEmpty())
            return null;
        return uuids.stream().distinct()
                .map(uuid -> this.studentsService.get(uuid)).collect(Collectors.toCollection(ArrayList::new));
    }

    private Collection<teacher> getTeachers(Collection<UUID> uuids)
    {
        if (uuids.isEmpty())
            return null;
        return uuids.stream().distinct()
                .map(uuid -> this.teacherService.get(uuid)).collect(Collectors.toCollection(ArrayList::new));
    }
}