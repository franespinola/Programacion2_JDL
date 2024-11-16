package ar.edu.um.programacion2.service.mapper;

import static ar.edu.um.programacion2.domain.OpcionAsserts.*;
import static ar.edu.um.programacion2.domain.OpcionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpcionMapperTest {

    private OpcionMapper opcionMapper;

    @BeforeEach
    void setUp() {
        opcionMapper = new OpcionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOpcionSample1();
        var actual = opcionMapper.toEntity(opcionMapper.toDto(expected));
        assertOpcionAllPropertiesEquals(expected, actual);
    }
}
