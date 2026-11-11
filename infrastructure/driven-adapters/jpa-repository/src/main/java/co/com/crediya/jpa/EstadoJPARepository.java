package co.com.crediya.jpa;

import co.com.crediya.jpa.entity.EstadoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

public interface EstadoJPARepository extends CrudRepository<EstadoEntity, Long>, QueryByExampleExecutor<EstadoEntity> {
    Optional<EstadoEntity> findByNombre(String nombre);
}
