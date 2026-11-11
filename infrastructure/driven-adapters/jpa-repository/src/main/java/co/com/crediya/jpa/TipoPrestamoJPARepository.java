package co.com.crediya.jpa;

import co.com.crediya.jpa.entity.TipoPrestamoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface TipoPrestamoJPARepository extends CrudRepository<TipoPrestamoEntity, Long>, QueryByExampleExecutor<TipoPrestamoEntity> {
}
