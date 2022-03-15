package com.school.system.repos;

import com.school.system.entities.subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface subjectRepo extends CrudRepository<subject, UUID>
{
    subject findByName(String name);
    Optional<subject> findById(UUID Id);
}