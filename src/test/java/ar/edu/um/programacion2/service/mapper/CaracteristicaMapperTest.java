package ar.edu.um.programacion2.service.mapper;

import static ar.edu.um.programacion2.domain.CaracteristicaAsserts.*;
import static ar.edu.um.programacion2.domain.CaracteristicaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CaracteristicaMapperTest {

    private CaracteristicaMapper caracteristicaMapper;

    @BeforeEach
    void setUp() {
        caracteristicaMapper = new CaracteristicaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCaracteristicaSample1();
        var actual = caracteristicaMapper.toEntity(caracteristicaMapper.toDto(expected));
        assertCaracteristicaAllPropertiesEquals(expected, actual);
    }
}
