package com.school.system.services;

import com.school.system.entities.teacher;
import com.school.system.repos.teacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class teacherService implements templateService<teacher, teacherRepo>
{
    private teacherRepo teacherRepo;

    @Autowired
    public teacherService(teacherRepo teacherRepo)
    {
        this.teacherRepo = teacherRepo;
    }



    @Override
    public UUID add(teacher teacher)
    {
        return this.teacherRepo.save(teacher).getId();
    }

    @Override
    public teacher get(UUID id)
    {
        Optional<teacher> temp = this.teacherRepo.findById(id);
        return temp.orElse(null);
    }

    @Override
    public Collection getAll()
    {
        Collection temp = (Collection) this.teacherRepo.findAll();
        return temp;
    }

    @Override
    public void update(teacher temp,UUID id)
    {
        temp.setId(id);
        this.teacherRepo.deleteById(id);
        this.teacherRepo.save(temp);
    }

    public void patchUpdate(teacher temp, UUID id)
    {
        Optional<teacher> target = teacherRepo.findById(id);
        if(target.isPresent())
        {
            if(Objects.nonNull(temp.getName()))
                target.get().setName(temp.getName());

            if(temp.getAge()!=0)
                target.get().setAge(temp.getAge());

            if(Objects.nonNull(temp.getSubject()))
                target.get().setSubject(temp.getSubject());

            if(temp.getYearsOfExperience()!=0)
                target.get().setYearsOfExperience(temp.getYearsOfExperience());
        }
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