package ar.edu.um.programacion2.service.mapper;

import ar.edu.um.programacion2.domain.Dispositivo;
import ar.edu.um.programacion2.service.dto.DispositivoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dispositivo} and its DTO {@link DispositivoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DispositivoMapper extends EntityMapper<DispositivoDTO, Dispositivo> {}
