package ar.edu.um.programacion2.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import ar.edu.um.programacion2.domain.Dispositivo;
import ar.edu.um.programacion2.domain.Venta;
import ar.edu.um.programacion2.repository.*;
import ar.edu.um.programacion2.service.dto.VentaRequest;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private DispositivoRepository dispositivoRepository;

    @Mock
    private PersonalizacionRepository personalizacionRepository;

    @Mock
    private OpcionRepository opcionRepository;

    @Mock
    private AdicionalRepository adicionalRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private VentaService ventaService;

    // Variable para almacenar el mock que necesitamos verificar
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @BeforeEach
    void setUp() {
        // Establecer valores en el servicio
        ReflectionTestUtils.setField(ventaService, "baseUrl", "http://mockurl.com");
        ReflectionTestUtils.setField(ventaService, "apiToken", "mock-token");
        // Otras configuraciones comunes a todos los tests
    }

    // Método para configurar el WebClient mockeado y evitar redundancias
    private void configurarWebClientMock() {
        // Configurar los mocks del WebClient.Builder
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(webClientBuilder);

        // Mockear el WebClient
        WebClient webClientMock = Mockito.mock(WebClient.class);
        when(webClientBuilder.build()).thenReturn(webClientMock);

        // Mockear las etapas del WebClient
        requestBodyUriSpecMock = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpecMock = Mockito.mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec<?> requestHeadersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        // Configurar el flujo del WebClient
        when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        when(requestBodyUriSpecMock.uri(anyString())).thenReturn(requestBodySpecMock);
        Mockito.<WebClient.RequestHeadersSpec<?>>when(requestBodySpecMock.bodyValue(any())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(String.class)).thenReturn(Mono.just("Mock Response"));

        // Inicializar el WebClient en el servicio
        ventaService.initWebClient();
    }

    @Test
    void testRegistrarVenta_Success() {
        // Configurar el WebClient mockeado
        configurarWebClientMock();

        // Datos de entrada
        VentaRequest request = new VentaRequest();
        request.setIdDispositivo(1L);

        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(1L);
        dispositivo.setPrecioBase(BigDecimal.valueOf(100));

        // Mockear repositorios
        when(dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Llamar al método
        Venta result = ventaService.registrarVenta(request);

        // Validar resultados
        Assertions.assertNotNull(result);
        Assertions.assertEquals(dispositivo, result.getDispositivo());
        Assertions.assertEquals(BigDecimal.valueOf(100), result.getPrecioFinal());

        // Verificar interacciones
        verify(dispositivoRepository, times(1)).findById(1L);
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }

    @Test
    void testRegistrarVenta_DispositivoNoEncontrado() {
        // Datos de entrada
        VentaRequest request = new VentaRequest();
        request.setIdDispositivo(99L);

        // Mockear repositorio para devolver vacío
        when(dispositivoRepository.findById(99L)).thenReturn(Optional.empty());

        // Llamar al método y esperar excepción
        Assertions.assertThrows(IllegalArgumentException.class, () -> ventaService.registrarVenta(request));

        // Verificar que no se guardó ninguna venta
        verify(ventaRepository, never()).save(any());
    }

    @Test
    void testRegistrarVentaEnServicioExterno() {
        // Configurar el WebClient mockeado
        configurarWebClientMock();

        // Datos de entrada
        Venta venta = new Venta();
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setIdExterno(1L);
        venta.setDispositivo(dispositivo);
        venta.setPrecioFinal(BigDecimal.valueOf(150));
        venta.setFechaVenta(ZonedDateTime.now());

        // Llamar al método
        ventaService.registrarVentaEnServicioExterno(venta);

        // Verificar que se llamó al método uri con el argumento correcto
        verify(requestBodyUriSpecMock, times(1)).uri("/vender");
        // Puedes agregar más verificaciones si es necesario
    }
}
