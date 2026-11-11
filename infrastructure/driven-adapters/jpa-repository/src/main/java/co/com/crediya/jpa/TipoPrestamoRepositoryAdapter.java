package co.com.crediya.jpa;

import co.com.crediya.jpa.entity.TipoPrestamoEntity;
import co.com.crediya.jpa.helper.AdapterOperations;
import co.com.crediya.model.solicitud.TipoPrestamo;
import co.com.crediya.model.solicitud.gateways.TipoPrestamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class TipoPrestamoRepositoryAdapter extends AdapterOperations<TipoPrestamo, TipoPrestamoEntity, Long, TipoPrestamoJPARepository>
        implements TipoPrestamoRepository {

    public TipoPrestamoRepositoryAdapter(TipoPrestamoJPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, TipoPrestamo.class));
    }
}
