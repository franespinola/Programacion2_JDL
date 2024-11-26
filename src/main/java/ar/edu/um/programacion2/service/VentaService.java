package ar.edu.um.programacion2.service;

import ar.edu.um.programacion2.domain.Adicional;
import ar.edu.um.programacion2.domain.Dispositivo;
import ar.edu.um.programacion2.domain.Opcion;
import ar.edu.um.programacion2.domain.Personalizacion;
import ar.edu.um.programacion2.domain.Venta;
import ar.edu.um.programacion2.repository.AdicionalRepository;
import ar.edu.um.programacion2.repository.DispositivoRepository;
import ar.edu.um.programacion2.repository.OpcionRepository;
import ar.edu.um.programacion2.repository.PersonalizacionRepository;
import ar.edu.um.programacion2.repository.VentaRepository;
import ar.edu.um.programacion2.service.dto.VentaDTO;
import ar.edu.um.programacion2.service.dto.VentaRequest;
import ar.edu.um.programacion2.service.mapper.VentaMapper;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * Service Implementation for managing {@link ar.edu.um.programacion2.domain.Venta}.
 */
@Service
@Transactional
public class VentaService {

    private static final Logger LOG = LoggerFactory.getLogger(VentaService.class);

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;
    private final WebClient.Builder webClientBuilder;
    private WebClient webClient;
    private final DispositivoRepository dispositivoRepository;
    private final PersonalizacionRepository personalizacionRepository;
    private final OpcionRepository opcionRepository;
    private final AdicionalRepository adicionalRepository;

    @Value("${SERVICE_BASE_URL}")
    private String baseUrl;

    @Value("${SERVICE_API_TOKEN}")
    private String apiToken;

    public VentaService(
        VentaRepository ventaRepository,
        VentaMapper ventaMapper,
        WebClient.Builder webClientBuilder,
        DispositivoRepository dispositivoRepository,
        PersonalizacionRepository personalizacionRepository,
        OpcionRepository opcionRepository,
        AdicionalRepository adicionalRepository
    ) {
        this.ventaRepository = ventaRepository;
        this.ventaMapper = ventaMapper;
        this.webClientBuilder = webClientBuilder;
        this.dispositivoRepository = dispositivoRepository;
        this.personalizacionRepository = personalizacionRepository;
        this.opcionRepository = opcionRepository;
        this.adicionalRepository = adicionalRepository;
    }

    @PostConstruct
    public void initWebClient() {
        LOG.info("Inicializando WebClient con Base URL: {}", baseUrl);
        this.webClient = webClientBuilder.baseUrl(baseUrl).defaultHeader("Authorization", "Bearer " + apiToken).build();
    }

    /**
     * Guarda la venta en la base de datos.
     *
     * @param ventaDTO Datos de la venta.
     * @return VentaDTO registrado.
     */
    public VentaDTO save(VentaDTO ventaDTO) {
        LOG.debug("Request to save Venta : {}", ventaDTO);
        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        return ventaMapper.toDto(venta);
    }

    /**
     * Update a venta.
     *
     * @param ventaDTO the entity to save.
     * @return the persisted entity.
     */
    public VentaDTO update(VentaDTO ventaDTO) {
        LOG.debug("Request to update Venta : {}", ventaDTO);
        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        return ventaMapper.toDto(venta);
    }

    /**
     * Partially update a venta.
     *
     * @param ventaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VentaDTO> partialUpdate(VentaDTO ventaDTO) {
        LOG.debug("Request to partially update Venta : {}", ventaDTO);

        return ventaRepository
            .findById(ventaDTO.getId())
            .map(existingVenta -> {
                ventaMapper.partialUpdate(existingVenta, ventaDTO);

                return existingVenta;
            })
            .map(ventaRepository::save)
            .map(ventaMapper::toDto);
    }

    /**
     * Get all the ventas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VentaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Ventas");
        return ventaRepository.findAll(pageable).map(ventaMapper::toDto);
    }

    /**
     * Get all the ventas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<VentaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ventaRepository.findAllWithEagerRelationships(pageable).map(ventaMapper::toDto);
    }

    /**
     * Get one venta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VentaDTO> findOne(Long id) {
        LOG.debug("Request to get Venta : {}", id);
        return ventaRepository.findOneWithEagerRelationships(id).map(ventaMapper::toDto);
    }

    /**
     * Delete the venta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
    }

    @Transactional
    public Venta registrarVenta(VentaRequest request) {
        // 1. Buscar el dispositivo
        Dispositivo dispositivo = dispositivoRepository
            .findById(request.getIdDispositivo())
            .orElseThrow(() -> new IllegalArgumentException("Dispositivo no encontrado con ID: " + request.getIdDispositivo()));

        // 2. Crear la venta
        Venta venta = new Venta();
        venta.setDispositivo(dispositivo);

        // Usar la fecha proporcionada o la actual
        venta.setFechaVenta(request.getFechaVenta() != null ? request.getFechaVenta() : ZonedDateTime.now());

        BigDecimal precioCalculado = dispositivo.getPrecioBase();

        // 3. Procesar personalizaciones
        if (request.getPersonalizaciones() != null) {
            for (VentaRequest.PersonalizacionRequest personalizacionRequest : request.getPersonalizaciones()) {
                // Buscar la personalización
                Personalizacion personalizacion = personalizacionRepository
                    .findById(personalizacionRequest.getId())
                    .orElseThrow(() ->
                        new IllegalArgumentException("Personalización no encontrada con ID: " + personalizacionRequest.getId())
                    );

                // Buscar la opción seleccionada
                Opcion opcion = opcionRepository
                    .findById(personalizacionRequest.getOpcion().getId())
                    .orElseThrow(() ->
                        new IllegalArgumentException("Opción no encontrada con ID: " + personalizacionRequest.getOpcion().getId())
                    );

                // Sumar el precio adicional de la opción seleccionada
                precioCalculado = precioCalculado.add(opcion.getPrecioAdicional());

                // Asociar la personalización a la venta
                venta.addPersonalizaciones(personalizacion);
            }
        }

        // 4. Procesar adicionales
        if (request.getAdicionales() != null) {
            for (VentaRequest.AdicionalRequest adicionalRequest : request.getAdicionales()) {
                // Buscar el adicional
                Adicional adicional = adicionalRepository
                    .findById(adicionalRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Adicional no encontrado con ID: " + adicionalRequest.getId()));

                // Verificar si el adicional está en promoción
                boolean enPromocion =
                    adicional.getPrecioGratis().compareTo(BigDecimal.ZERO) >= 0 &&
                    precioCalculado.compareTo(adicional.getPrecioGratis()) >= 0;

                if (!enPromocion) {
                    // Sumar el precio del adicional si no está en promoción
                    precioCalculado = precioCalculado.add(adicional.getPrecio());
                }

                // Asociar el adicional a la venta
                venta.addAdicionales(adicional);
            }
        }

        // 5. Establecer el precio final
        venta.setPrecioFinal(precioCalculado);

        // 6. Guardar la venta en la base de datos
        Venta ventaGuardada = ventaRepository.save(venta);

        // 7. Registrar la venta en el servicio externo
        registrarVentaEnServicioExterno(ventaGuardada);

        // 8. Retornar la venta registrada
        return ventaGuardada;
    }

    public void registrarVentaEnServicioExterno(Venta venta) {
        Map<String, Object> requestBody = new HashMap<>();

        // Agregar idExterno del dispositivo
        requestBody.put("idDispositivo", venta.getDispositivo().getIdExterno());

        // Transformar personalizaciones
        List<Map<String, Object>> personalizaciones = venta
            .getPersonalizaciones()
            .stream()
            .map(personalizacion -> {
                Map<String, Object> personalizacionMap = new HashMap<>();
                personalizacionMap.put("id", personalizacion.getIdExterno());
                personalizacionMap.put("precio", BigDecimal.ZERO);
                Map<String, Object> opcionMap = new HashMap<>();

                // Buscar las opciones asociadas a la personalización
                List<Opcion> opciones = opcionRepository.findByPersonalizacionId(personalizacion.getId());
                if (opciones.isEmpty()) {
                    throw new IllegalArgumentException(
                        "No se encontraron opciones para la personalización con ID: " + personalizacion.getId()
                    );
                }
                if (opciones.size() > 1) {
                    System.out.println(
                        "Advertencia: Se encontraron múltiples opciones para la personalización con ID: " + personalizacion.getId()
                    );
                }

                // Tomar la primera opción (o aplicar una lógica de selección específica)
                Opcion opcionSeleccionada = opciones.get(0);
                opcionMap.put("id", opcionSeleccionada.getIdExterno());
                personalizacionMap.put("opcion", opcionMap);
                return personalizacionMap;
            })
            .toList();
        requestBody.put("personalizaciones", personalizaciones);

        // Transformar adicionales
        List<Map<String, Object>> adicionales = venta
            .getAdicionales()
            .stream()
            .map(adicional -> {
                Map<String, Object> adicionalMap = new HashMap<>();
                adicionalMap.put("id", adicional.getIdExterno());
                adicionalMap.put("precio", adicional.getPrecio());
                return adicionalMap;
            })
            .toList();
        requestBody.put("adicionales", adicionales);

        // Agregar precio final y fecha de venta
        requestBody.put("precioFinal", venta.getPrecioFinal());
        requestBody.put("fechaVenta", venta.getFechaVenta().toString());

        // Enviar la solicitud al servicio externo
        webClient
            .post()
            .uri("/vender")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class)
            .doOnSuccess(response -> LOG.info("Venta registrada exitosamente en el servicio externo: {}", response))
            .doOnError(WebClientResponseException.class, error ->
                LOG.error("Error al registrar la venta en el servicio externo: {}", error.getResponseBodyAsString())
            )
            .subscribe();
    }
}
