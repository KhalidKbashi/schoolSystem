package com.school.system.repos;

import com.school.system.entities.subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface subjectRepo extends CrudRepository<subject, UUID>
{
    public subject findByName(String name);
    public Optional<subject> findById(UUID Id);
}