package co.com.crediya.jpa;

import co.com.crediya.jpa.entity.SolicitudEntity;
import co.com.crediya.jpa.helper.AdapterOperations;
import co.com.crediya.model.solicitud.Estado;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.TipoPrestamo;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
public class SolicitudRepositoryAdapter extends AdapterOperations<Solicitud, SolicitudEntity, Long, SolicitudJPARepository>
        implements SolicitudRepository {

    public SolicitudRepositoryAdapter(SolicitudJPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> {
            Solicitud s = mapper.map(d, Solicitud.class);
            if (d.getTipoPrestamo() != null) {
                s.setTipoPrestamo(mapper.map(d.getTipoPrestamo(), TipoPrestamo.class));
            }
            if (d.getEstado() != null) {
                s.setEstado(mapper.map(d.getEstado(), Estado.class));
            }
            return s;
        });
    }

    @Override
    @Transactional
    public Solicitud save(Solicitud solicitud) {
        log.info("Persistiendo solicitud de crédito de forma transaccional para documento: {}",
                solicitud.getDocumentoIdentidad());
        return super.save(solicitud);
    }

    @Override
    protected SolicitudEntity toData(Solicitud entity) {
        SolicitudEntity data = super.toData(entity);
        if (entity.getIdTipoPrestamo() != null) {
            data.setIdTipoPrestamo(entity.getIdTipoPrestamo());
        } else if (entity.getTipoPrestamo() != null) {
            data.setIdTipoPrestamo(entity.getTipoPrestamo().getIdTipoPrestamo());
        }
        if (entity.getIdEstado() != null) {
            data.setIdEstado(entity.getIdEstado());
        } else if (entity.getEstado() != null) {
            data.setIdEstado(entity.getEstado().getIdEstado());
        }
        return data;
    }
}
