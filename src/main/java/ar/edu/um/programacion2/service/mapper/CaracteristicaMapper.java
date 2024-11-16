package ar.edu.um.programacion2.service.mapper;

import ar.edu.um.programacion2.domain.Caracteristica;
import ar.edu.um.programacion2.domain.Dispositivo;
import ar.edu.um.programacion2.service.dto.CaracteristicaDTO;
import ar.edu.um.programacion2.service.dto.DispositivoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Caracteristica} and its DTO {@link CaracteristicaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CaracteristicaMapper extends EntityMapper<CaracteristicaDTO, Caracteristica> {
    @Mapping(target = "dispositivo", source = "dispositivo", qualifiedByName = "dispositivoId")
    CaracteristicaDTO toDto(Caracteristica s);

    @Named("dispositivoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispositivoDTO toDtoDispositivoId(Dispositivo dispositivo);
}
