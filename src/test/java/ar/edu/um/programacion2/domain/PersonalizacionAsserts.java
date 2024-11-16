package ar.edu.um.programacion2.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonalizacionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPersonalizacionAllPropertiesEquals(Personalizacion expected, Personalizacion actual) {
        assertPersonalizacionAutoGeneratedPropertiesEquals(expected, actual);
        assertPersonalizacionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPersonalizacionAllUpdatablePropertiesEquals(Personalizacion expected, Personalizacion actual) {
        assertPersonalizacionUpdatableFieldsEquals(expected, actual);
        assertPersonalizacionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPersonalizacionAutoGeneratedPropertiesEquals(Personalizacion expected, Personalizacion actual) {
        assertThat(expected)
            .as("Verify Personalizacion auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPersonalizacionUpdatableFieldsEquals(Personalizacion expected, Personalizacion actual) {
        assertThat(expected)
            .as("Verify Personalizacion relevant properties")
            .satisfies(e -> assertThat(e.getIdExterno()).as("check idExterno").isEqualTo(actual.getIdExterno()))
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPersonalizacionUpdatableRelationshipsEquals(Personalizacion expected, Personalizacion actual) {
        assertThat(expected)
            .as("Verify Personalizacion relationships")
            .satisfies(e -> assertThat(e.getDispositivo()).as("check dispositivo").isEqualTo(actual.getDispositivo()))
            .satisfies(e -> assertThat(e.getVentas()).as("check ventas").isEqualTo(actual.getVentas()));
    }
}
