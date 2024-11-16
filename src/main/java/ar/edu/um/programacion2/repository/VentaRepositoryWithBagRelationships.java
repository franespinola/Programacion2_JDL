package ar.edu.um.programacion2.repository;

import ar.edu.um.programacion2.domain.Venta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface VentaRepositoryWithBagRelationships {
    Optional<Venta> fetchBagRelationships(Optional<Venta> venta);

    List<Venta> fetchBagRelationships(List<Venta> ventas);

    Page<Venta> fetchBagRelationships(Page<Venta> ventas);
}
