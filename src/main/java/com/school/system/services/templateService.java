package com.school.system.services;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.UUID;

public interface templateService<entity,repo extends CrudRepository<entity, UUID>>
{
    public UUID add(entity temp);

    public entity get(UUID id);

    public Object[] getAll();

    public void update(entity temp);

    public void patchUpdate(entity temp,UUID id);

    public void delete(UUID id);

    public boolean check(UUID id);
}
