package com.school.system.repos;

import com.school.system.entities.student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface studentRepo extends CrudRepository<student, UUID>
{
    public student findByName(String name);
    public Optional<student> findById(UUID Id);
}