package com.school.system.repos;

import com.school.system.entities.teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface teacherRepo extends CrudRepository<teacher, UUID>
{
    teacher findByName(String name);
    Optional<teacher> findById(UUID Id);
}