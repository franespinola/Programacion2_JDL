package ar.edu.um.programacion2.service.mapper;

import ar.edu.um.programacion2.domain.Opcion;
import ar.edu.um.programacion2.domain.Personalizacion;
import ar.edu.um.programacion2.service.dto.OpcionDTO;
import ar.edu.um.programacion2.service.dto.PersonalizacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Opcion} and its DTO {@link OpcionDTO}.
 */
@Mapper(componentModel = "spring")
public interface OpcionMapper extends EntityMapper<OpcionDTO, Opcion> {
    @Mapping(target = "personalizacion", source = "personalizacion", qualifiedByName = "personalizacionId")
    OpcionDTO toDto(Opcion s);

    @Named("personalizacionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonalizacionDTO toDtoPersonalizacionId(Personalizacion personalizacion);
}
