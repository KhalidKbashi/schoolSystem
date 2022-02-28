package com.school.system.services;

import com.school.system.entities.subject;
import com.school.system.repos.subjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class subjectService implements templateService<subject,subjectRepo>
{
    @Autowired
    private subjectRepo subjectRepo;



    @Override
    public UUID add(subject subject)
    {
        return this.subjectRepo.save(subject).getId();
    }

    @Override
    public subject get(UUID id)
    {
        Optional<subject> temp = this.subjectRepo.findById(id);
        return temp.orElse(null);
    }

    @Override
    public Object[] getAll()
    {
        Collection temp = (Collection) this.subjectRepo.findAll();
        return temp.toArray();
    }

    @Override
    public void update(subject temp)
    {
        this.subjectRepo.save(temp);
    }

    @Override
    public void patchUpdate(subject temp, UUID id)
    {
        Optional<subject> target = this.subjectRepo.findById(id);
        if(target.isPresent())
        {
            if(Objects.nonNull(temp.getName()))
                target.get().setName(temp.getName());
            if(temp.getNumOfHours()!=0)
                target.get().setNumOfHours(temp.getNumOfHours());
        }
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
}