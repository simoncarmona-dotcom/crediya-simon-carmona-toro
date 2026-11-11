package co.com.crediya.model.solicitud.gateways;

import co.com.crediya.model.solicitud.TipoPrestamo;

import java.util.List;

public interface TipoPrestamoRepository {
    TipoPrestamo save(TipoPrestamo tipoPrestamo);

    TipoPrestamo findById(Long id);

    List<TipoPrestamo> findAll();
}
