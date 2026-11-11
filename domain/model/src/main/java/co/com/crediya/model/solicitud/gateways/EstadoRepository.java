package co.com.crediya.model.solicitud.gateways;

import co.com.crediya.model.solicitud.Estado;

import java.util.Optional;

public interface EstadoRepository {
    Estado save(Estado estado);

    Estado findById(Long id);

    Optional<Estado> findByNombre(String nombre);
}
