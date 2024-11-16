package ar.edu.um.programacion2.service.dto;

import java.math.BigDecimal;
import java.util.List;

public class DispositivoExternoDTO {

    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precioBase;
    private String moneda;
    private List<CaracteristicaDTO> caracteristicas;
    private List<PersonalizacionDTO> personalizaciones;
    private List<AdicionalDTO> adicionales;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public List<CaracteristicaDTO> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<CaracteristicaDTO> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public List<PersonalizacionDTO> getPersonalizaciones() {
        return personalizaciones;
    }

    public void setPersonalizaciones(List<PersonalizacionDTO> personalizaciones) {
        this.personalizaciones = personalizaciones;
    }

    public List<AdicionalDTO> getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(List<AdicionalDTO> adicionales) {
        this.adicionales = adicionales;
    }

    // Clases internas

    public static class CaracteristicaDTO {

        private Long id;
        private String nombre;
        private String descripcion;

        // Getters y Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    }

    public static class PersonalizacionDTO {

        private Long id;
        private String nombre;
        private String descripcion;
        private List<OpcionDTO> opciones;

        // Getters y Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public List<OpcionDTO> getOpciones() {
            return opciones;
        }

        public void setOpciones(List<OpcionDTO> opciones) {
            this.opciones = opciones;
        }
    }

    public static class OpcionDTO {

        private Long id;
        private String codigo;
        private String nombre;
        private String descripcion;
        private BigDecimal precioAdicional;

        // Getters y Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public BigDecimal getPrecioAdicional() {
            return precioAdicional;
        }

        public void setPrecioAdicional(BigDecimal precioAdicional) {
            this.precioAdicional = precioAdicional;
        }
    }

    public static class AdicionalDTO {

        private Long id;
        private String nombre;
        private String descripcion;
        private BigDecimal precio;
        private BigDecimal precioGratis;

        // Getters y Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public BigDecimal getPrecio() {
            return precio;
        }

        public void setPrecio(BigDecimal precio) {
            this.precio = precio;
        }

        public BigDecimal getPrecioGratis() {
            return precioGratis;
        }

        public void setPrecioGratis(BigDecimal precioGratis) {
            this.precioGratis = precioGratis;
        }
    }
}
