package ar.edu.um.programacion2.repository;

import ar.edu.um.programacion2.domain.Dispositivo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Dispositivo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {}
