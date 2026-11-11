package co.com.crediya.jpa;

import co.com.crediya.jpa.entity.EstadoEntity;
import co.com.crediya.jpa.helper.AdapterOperations;
import co.com.crediya.model.solicitud.Estado;
import co.com.crediya.model.solicitud.gateways.EstadoRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
public class EstadoRepositoryAdapter extends AdapterOperations<Estado, EstadoEntity, Long, EstadoJPARepository>
        implements EstadoRepository {

    public EstadoRepositoryAdapter(EstadoJPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Estado.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Estado> findByNombre(String nombre) {
        return repository.findByNombre(nombre).map(this::toEntity);
    }
}
