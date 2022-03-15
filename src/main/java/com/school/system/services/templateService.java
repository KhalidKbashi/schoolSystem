package com.school.system.services;

import java.util.Collection;
import java.util.UUID;

public interface templateService<entity,entityDTO>
{
    UUID add(entityDTO temp);

    entity get(UUID id);

    Collection<entity> getAll();

    void update(entityDTO entityDTO, UUID id);

    default void patchUpdate(entityDTO entityDTO, UUID id){}

    void delete(UUID id);

    boolean check(UUID id);
}
