package com.school.system.services;

import com.school.system.entities.student;
import com.school.system.repos.studentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class studentService implements templateService<student, studentRepo>
{
    private studentRepo studentRepo;

    @Autowired
    public studentService(studentRepo studentRepo)
    {
        this.studentRepo = studentRepo;
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
    public Object[] getAll()
    {
        Collection temp = (Collection) this.studentRepo.findAll();
        return temp.toArray();
    }

    @Override
    public void update(student temp,UUID id)
    {
        temp.setId(id);
        this.studentRepo.deleteById(id);
        this.studentRepo.save(temp);
    }

    @Override
    public void patchUpdate(student temp, UUID id)
    {
        Optional<student> target = this.studentRepo.findById(id);
        if(target.isPresent())
        {
            if(Objects.nonNull(temp.getName()))
                target.get().setName(temp.getName());
            if(Objects.nonNull(temp.getSubjects()))
                target.get().setSubjects(temp.getSubjects());
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