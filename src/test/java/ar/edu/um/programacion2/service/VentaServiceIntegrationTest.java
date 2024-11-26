package ar.edu.um.programacion2.service;

import ar.edu.um.programacion2.domain.Dispositivo;
import ar.edu.um.programacion2.domain.Venta;
import ar.edu.um.programacion2.domain.enumeration.Moneda;
import ar.edu.um.programacion2.repository.DispositivoRepository;
import ar.edu.um.programacion2.repository.VentaRepository;
import ar.edu.um.programacion2.service.dto.VentaRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VentaServiceIntegrationTest {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Test
    void testRegistrarVentaIntegration() {
        // Crear dispositivo en la base de datos con todos los campos requeridos
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setCodigo("DISP001"); // Código único
        dispositivo.setNombre("Dispositivo Test"); // Nombre no nulo
        dispositivo.setDescripcion("Descripción del dispositivo"); // Descripción no nula
        dispositivo.setPrecioBase(BigDecimal.valueOf(150)); // Precio base
        dispositivo.setMoneda(Moneda.USD); // Moneda válida (enum Moneda)
        dispositivo.setIdExterno(1L); // ID externo válido (Long)

        // Guardar el dispositivo en el repositorio
        dispositivoRepository.save(dispositivo);

        // Crear solicitud de venta
        VentaRequest request = new VentaRequest();
        request.setIdDispositivo(dispositivo.getId());

        // Llamar al método y verificar resultados
        Venta result = ventaService.registrarVenta(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dispositivo, result.getDispositivo());
        Assertions.assertEquals(0, BigDecimal.valueOf(150).compareTo(result.getPrecioFinal()));
        Assertions.assertTrue(ventaRepository.findById(result.getId()).isPresent());
    }
}
