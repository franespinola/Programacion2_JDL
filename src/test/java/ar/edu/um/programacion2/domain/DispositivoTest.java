package ar.edu.um.programacion2.domain;

import static ar.edu.um.programacion2.domain.DispositivoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.programacion2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DispositivoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dispositivo.class);
        Dispositivo dispositivo1 = getDispositivoSample1();
        Dispositivo dispositivo2 = new Dispositivo();
        assertThat(dispositivo1).isNotEqualTo(dispositivo2);

        dispositivo2.setId(dispositivo1.getId());
        assertThat(dispositivo1).isEqualTo(dispositivo2);

        dispositivo2 = getDispositivoSample2();
        assertThat(dispositivo1).isNotEqualTo(dispositivo2);
    }
}
