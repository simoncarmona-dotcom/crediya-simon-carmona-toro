package co.com.crediya.model.solicitud.gateways;

import co.com.crediya.model.solicitud.Solicitud;

import java.util.List;

public interface SolicitudRepository {
    Solicitud save(Solicitud solicitud);

    Solicitud findById(Long id);

    List<Solicitud> findAll();
}
