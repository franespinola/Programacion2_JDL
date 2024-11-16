package ar.edu.um.programacion2.domain;

import static ar.edu.um.programacion2.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class OpcionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpcionAllPropertiesEquals(Opcion expected, Opcion actual) {
        assertOpcionAutoGeneratedPropertiesEquals(expected, actual);
        assertOpcionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpcionAllUpdatablePropertiesEquals(Opcion expected, Opcion actual) {
        assertOpcionUpdatableFieldsEquals(expected, actual);
        assertOpcionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpcionAutoGeneratedPropertiesEquals(Opcion expected, Opcion actual) {
        assertThat(expected)
            .as("Verify Opcion auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpcionUpdatableFieldsEquals(Opcion expected, Opcion actual) {
        assertThat(expected)
            .as("Verify Opcion relevant properties")
            .satisfies(e -> assertThat(e.getIdExterno()).as("check idExterno").isEqualTo(actual.getIdExterno()))
            .satisfies(e -> assertThat(e.getCodigo()).as("check codigo").isEqualTo(actual.getCodigo()))
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()))
            .satisfies(e ->
                assertThat(e.getPrecioAdicional())
                    .as("check precioAdicional")
                    .usingComparator(bigDecimalCompareTo)
                    .isEqualTo(actual.getPrecioAdicional())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpcionUpdatableRelationshipsEquals(Opcion expected, Opcion actual) {
        assertThat(expected)
            .as("Verify Opcion relationships")
            .satisfies(e -> assertThat(e.getPersonalizacion()).as("check personalizacion").isEqualTo(actual.getPersonalizacion()));
    }
}
