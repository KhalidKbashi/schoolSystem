package com.school.system.DTOs;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;

@Component
@NoArgsConstructor
public class ElMapper<entity,dto>
{
    private Function<entity,dto> functionToDTO;
    private Function<dto,entity> functionToEntity;
    private Function<Collection<UUID>,Collection<Object>> uuidsToEntities;

    public ElMapper(Function<entity, dto> functionToDTO, Function<dto, entity> functionToEntity,
            Function<Collection<UUID>,Collection<Object>> uuidsToEntities)
    {
        this.functionToDTO = functionToDTO;
        this.functionToEntity = functionToEntity;
        this.uuidsToEntities = uuidsToEntities;
    }

    public entity toEntityObject(dto dto)
    {
        return functionToEntity.apply(dto);
    }

    public dto toDtoObject(entity entity)
    {
        return functionToDTO.apply(entity);
    }

    //todo enhancement required
    public <T> Collection<T> uuidsToEntitiesConverter(Collection<UUID> uuids)
    {
        return (Collection<T>) this.uuidsToEntities.apply(uuids);
    }
    //todo enhancement required
    public <T> Collection<T> customUuidsToEntitiesConverter(Collection<UUID> uuids,
            Function<Collection<UUID>,Collection<? extends Serializable>>customUuidsToEntitiesObject)
    {
        return (Collection<T>) customUuidsToEntitiesObject.apply(uuids);
    }
}
