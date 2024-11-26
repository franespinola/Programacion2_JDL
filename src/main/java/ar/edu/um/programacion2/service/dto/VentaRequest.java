package ar.edu.um.programacion2.service.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public class VentaRequest {

    private Long idDispositivo;
    private List<PersonalizacionRequest> personalizaciones;
    private List<AdicionalRequest> adicionales;
    private BigDecimal precioFinal;
    private ZonedDateTime fechaVenta;

    // Getters y Setters
    public Long getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Long idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public List<PersonalizacionRequest> getPersonalizaciones() {
        return personalizaciones;
    }

    public void setPersonalizaciones(List<PersonalizacionRequest> personalizaciones) {
        this.personalizaciones = personalizaciones;
    }

    public List<AdicionalRequest> getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(List<AdicionalRequest> adicionales) {
        this.adicionales = adicionales;
    }

    public BigDecimal getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(BigDecimal precioFinal) {
        this.precioFinal = precioFinal;
    }

    public ZonedDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(ZonedDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public static class PersonalizacionRequest {

        private Long id;
        private BigDecimal precio;
        private OpcionRequest opcion;

        // Getters y Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public BigDecimal getPrecio() {
            return precio;
        }

        public void setPrecio(BigDecimal precio) {
            this.precio = precio;
        }

        public OpcionRequest getOpcion() {
            return opcion;
        }

        public void setOpcion(OpcionRequest opcion) {
            this.opcion = opcion;
        }
    }

    public static class OpcionRequest {

        private Long id;

        // Getters y Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public static class AdicionalRequest {

        private Long id;
        private BigDecimal precio;

        // Getters y Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public BigDecimal getPrecio() {
            return precio;
        }

        public void setPrecio(BigDecimal precio) {
            this.precio = precio;
        }
    }
}
