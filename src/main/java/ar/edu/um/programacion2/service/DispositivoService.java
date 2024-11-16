package ar.edu.um.programacion2.service;

import ar.edu.um.programacion2.domain.Adicional;
import ar.edu.um.programacion2.domain.Caracteristica;
import ar.edu.um.programacion2.domain.Dispositivo;
import ar.edu.um.programacion2.domain.Opcion;
import ar.edu.um.programacion2.domain.Personalizacion;
import ar.edu.um.programacion2.domain.enumeration.Moneda;
import ar.edu.um.programacion2.repository.AdicionalRepository;
import ar.edu.um.programacion2.repository.CaracteristicaRepository;
import ar.edu.um.programacion2.repository.DispositivoRepository;
import ar.edu.um.programacion2.repository.OpcionRepository;
import ar.edu.um.programacion2.repository.PersonalizacionRepository;
import ar.edu.um.programacion2.service.dto.DispositivoDTO;
import ar.edu.um.programacion2.service.dto.DispositivoExternoDTO;
import ar.edu.um.programacion2.service.mapper.DispositivoMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ar.edu.um.programacion2.domain.Dispositivo}.
 */
@Service
@Transactional
public class DispositivoService {

    private static final Logger LOG = LoggerFactory.getLogger(DispositivoService.class);

    private final DispositivoRepository dispositivoRepository;

    private final DispositivoMapper dispositivoMapper;

    private final CaracteristicaRepository caracteristicaRepository;

    private final PersonalizacionRepository personalizacionRepository;

    private final OpcionRepository opcionRepository;

    private final AdicionalRepository adicionalRepository;

    private final WebClient.Builder webClientBuilder;

    public DispositivoService(
        DispositivoRepository dispositivoRepository,
        DispositivoMapper dispositivoMapper,
        CaracteristicaRepository caracteristicaRepository,
        PersonalizacionRepository personalizacionRepository,
        OpcionRepository opcionRepository,
        AdicionalRepository adicionalRepository,
        WebClient.Builder webClientBuilder
    ) {
        this.dispositivoRepository = dispositivoRepository;
        this.dispositivoMapper = dispositivoMapper;
        this.caracteristicaRepository = caracteristicaRepository;
        this.personalizacionRepository = personalizacionRepository;
        this.opcionRepository = opcionRepository;
        this.adicionalRepository = adicionalRepository;
        this.webClientBuilder = webClientBuilder;
    }

    /**
     * Save a dispositivo.
     *
     * @param dispositivoDTO the entity to save.
     * @return the persisted entity.
     */
    public DispositivoDTO save(DispositivoDTO dispositivoDTO) {
        LOG.debug("Request to save Dispositivo : {}", dispositivoDTO);
        Dispositivo dispositivo = dispositivoMapper.toEntity(dispositivoDTO);
        dispositivo = dispositivoRepository.save(dispositivo);
        return dispositivoMapper.toDto(dispositivo);
    }

    /**
     * Update a dispositivo.
     *
     * @param dispositivoDTO the entity to save.
     * @return the persisted entity.
     */
    public DispositivoDTO update(DispositivoDTO dispositivoDTO) {
        LOG.debug("Request to update Dispositivo : {}", dispositivoDTO);
        Dispositivo dispositivo = dispositivoMapper.toEntity(dispositivoDTO);
        dispositivo = dispositivoRepository.save(dispositivo);
        return dispositivoMapper.toDto(dispositivo);
    }

    /**
     * Partially update a dispositivo.
     *
     * @param dispositivoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DispositivoDTO> partialUpdate(DispositivoDTO dispositivoDTO) {
        LOG.debug("Request to partially update Dispositivo : {}", dispositivoDTO);

        return dispositivoRepository
            .findById(dispositivoDTO.getId())
            .map(existingDispositivo -> {
                dispositivoMapper.partialUpdate(existingDispositivo, dispositivoDTO);

                return existingDispositivo;
            })
            .map(dispositivoRepository::save)
            .map(dispositivoMapper::toDto);
    }

    /**
     * Get all the dispositivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DispositivoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Dispositivos");
        return dispositivoRepository.findAll(pageable).map(dispositivoMapper::toDto);
    }

    /**
     * Get one dispositivo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DispositivoDTO> findOne(Long id) {
        LOG.debug("Request to get Dispositivo : {}", id);
        return dispositivoRepository.findById(id).map(dispositivoMapper::toDto);
    }

    /**
     * Delete the dispositivo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Dispositivo : {}", id);
        dispositivoRepository.deleteById(id);
    }

    //sincronizacion periodica

    @Scheduled(fixedRate = 600000)
    public void sincronizarDispositivos() {
        LOG.info("Iniciando sincronización periódica de dispositivos...");
        traerDispositivos(); // Llama al método para obtener y guardar dispositivos
        LOG.info("Sincronización de dispositivos completada.");
    }

    public List<DispositivoDTO> traerDispositivos() {
        String token =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmcmFuY2lzY28iLCJleHAiOjE3Mzk1NjM0ODYsImF1dGgiOiJST0xFX1VTRVIiLCJpYXQiOjE3MzA5MjM0ODZ9.FKX6gcguzQrRjosFJO_tex08Eu3UeDePJzNVTF8zn3Q1JsiriUX4LnobwU24Z8J0LBUSaAsy7P5C7vEbpcR7Pw";
        WebClient webClient = webClientBuilder.build();

        Mono<DispositivoExternoDTO[]> response = webClient
            .get()
            .uri("http://10.145.1.1:8080/api/catedra/dispositivos")
            .header("Authorization", "Bearer " + token)
            .retrieve()
            .bodyToMono(DispositivoExternoDTO[].class);

        DispositivoExternoDTO[] dispositivosExternoArray = response.block();
        if (dispositivosExternoArray == null || dispositivosExternoArray.length == 0) {
            LOG.error("No se recibieron dispositivos de la API");
            return Collections.emptyList();
        }

        List<Dispositivo> dispositivosGuardados = new ArrayList<>();
        for (DispositivoExternoDTO dispositivoExterno : dispositivosExternoArray) {
            // 1. Buscar dispositivo existente por código
            Optional<Dispositivo> dispositivoExistenteOpt = dispositivoRepository.findByCodigo(dispositivoExterno.getCodigo());

            Dispositivo dispositivo;
            if (dispositivoExistenteOpt.isPresent()) {
                // 2. Si existe, actualizar valores
                dispositivo = dispositivoExistenteOpt.get();
                dispositivo.setIdExterno(dispositivoExterno.getId());
                dispositivo.setNombre(dispositivoExterno.getNombre());
                dispositivo.setDescripcion(dispositivoExterno.getDescripcion());
                dispositivo.setPrecioBase(dispositivoExterno.getPrecioBase());
                dispositivo.setMoneda(Moneda.valueOf(dispositivoExterno.getMoneda()));
            } else {
                // 3. Si no existe, crear nuevo dispositivo
                dispositivo = new Dispositivo();
                dispositivo.setIdExterno(dispositivoExterno.getId());
                dispositivo.setCodigo(dispositivoExterno.getCodigo());
                dispositivo.setNombre(dispositivoExterno.getNombre());
                dispositivo.setDescripcion(dispositivoExterno.getDescripcion());
                dispositivo.setPrecioBase(dispositivoExterno.getPrecioBase());
                dispositivo.setMoneda(Moneda.valueOf(dispositivoExterno.getMoneda()));
            }

            Dispositivo dispositivoGuardado = dispositivoRepository.save(dispositivo);

            // 4. Sincronizar características
            for (DispositivoExternoDTO.CaracteristicaDTO caracteristicaDTO : dispositivoExterno.getCaracteristicas()) {
                Caracteristica caracteristica = caracteristicaRepository
                    .findByNombreAndDispositivo(caracteristicaDTO.getNombre(), dispositivoGuardado)
                    .orElse(new Caracteristica());
                caracteristica.setIdExterno(caracteristicaDTO.getId()); // Sincronizar idExterno
                caracteristica.setNombre(caracteristicaDTO.getNombre());
                caracteristica.setDescripcion(caracteristicaDTO.getDescripcion());
                caracteristica.setDispositivo(dispositivoGuardado);
                caracteristicaRepository.save(caracteristica);
            }

            // 5. Sincronizar personalizaciones y opciones
            for (DispositivoExternoDTO.PersonalizacionDTO personalizacionDTO : dispositivoExterno.getPersonalizaciones()) {
                Personalizacion personalizacion = personalizacionRepository
                    .findByNombreAndDispositivo(personalizacionDTO.getNombre(), dispositivoGuardado)
                    .orElse(new Personalizacion());
                personalizacion.setIdExterno(personalizacionDTO.getId()); // Sincronizar idExterno
                personalizacion.setNombre(personalizacionDTO.getNombre());
                personalizacion.setDescripcion(personalizacionDTO.getDescripcion());
                personalizacion.setDispositivo(dispositivoGuardado);
                Personalizacion personalizacionGuardada = personalizacionRepository.save(personalizacion);

                // Sincronizar opciones de la personalización
                for (DispositivoExternoDTO.OpcionDTO opcionDTO : personalizacionDTO.getOpciones()) {
                    Opcion opcion = opcionRepository
                        .findByCodigoAndPersonalizacion(opcionDTO.getCodigo(), personalizacionGuardada)
                        .orElse(new Opcion());
                    opcion.setIdExterno(opcionDTO.getId()); // Sincronizar idExterno
                    opcion.setCodigo(opcionDTO.getCodigo());
                    opcion.setNombre(opcionDTO.getNombre());
                    opcion.setDescripcion(opcionDTO.getDescripcion());
                    opcion.setPrecioAdicional(opcionDTO.getPrecioAdicional());
                    opcion.setPersonalizacion(personalizacionGuardada);
                    opcionRepository.save(opcion);
                }
            }

            // 6. Sincronizar adicionales
            for (DispositivoExternoDTO.AdicionalDTO adicionalDTO : dispositivoExterno.getAdicionales()) {
                Adicional adicional = adicionalRepository
                    .findByNombreAndDispositivo(adicionalDTO.getNombre(), dispositivoGuardado)
                    .orElse(new Adicional());
                adicional.setIdExterno(adicionalDTO.getId()); // Sincronizar idExterno
                adicional.setNombre(adicionalDTO.getNombre());
                adicional.setDescripcion(adicionalDTO.getDescripcion());
                adicional.setPrecio(adicionalDTO.getPrecio());
                adicional.setPrecioGratis(adicionalDTO.getPrecioGratis());
                adicional.setDispositivo(dispositivoGuardado);
                adicionalRepository.save(adicional);
            }

            dispositivosGuardados.add(dispositivoGuardado);
        }

        System.out.println("Dispositivos y entidades relacionadas sincronizadas en la base de datos");
        return dispositivoMapper.toDto(dispositivosGuardados);
    }
}
