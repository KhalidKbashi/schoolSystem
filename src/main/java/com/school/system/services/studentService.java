package com.school.system.services;

import com.school.system.dual;
import com.school.system.entities.student;
import com.school.system.entities.subject;
import com.school.system.repos.studentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class studentService implements templateService<student, studentRepo>
{
    private studentRepo studentRepo;
    private subjectService subjectService;

    @Autowired
    public studentService(studentRepo studentRepo, com.school.system.services.subjectService subjectService)
    {
        this.studentRepo = studentRepo;
        this.subjectService = subjectService;
    }



    @Override
    public UUID add(student student)
    {
        return this.studentRepo.save(student).getId();
    }

    @Override
    public student get(UUID id)
    {
        Optional<student> temp = this.studentRepo.findById(id);
        return temp.orElse(null);
    }

    @Override
    public Collection getAll()
    {
        Collection temp = (Collection) this.studentRepo.findAll();
        return temp;
    }

    @Override
    public void update(student temp,UUID id)
    {
        temp.setId(id);
        this.studentRepo.deleteById(id);
        this.studentRepo.save(temp);
    }

    public void patchUpdate(dual<student, List<UUID>> temp, UUID id)
    {
        Optional<student> target = this.studentRepo.findById(id);
        if(target.isPresent())
        {
            if(!temp.getFirst().getName().isBlank())
                target.get().setName(temp.getFirst().getName());

            if(!temp.getSecond().isEmpty())
            {
                temp.getSecond().stream().distinct().
                        map(uuid -> this.subjectService.get(uuid))
                        .forEach(subject -> target.get().getSubjects().add(subject));
            }
            this.studentRepo.save(target.get());
        }
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