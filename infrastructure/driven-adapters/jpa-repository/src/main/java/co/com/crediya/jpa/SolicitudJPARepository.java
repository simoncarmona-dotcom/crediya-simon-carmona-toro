package co.com.crediya.jpa;

import co.com.crediya.jpa.entity.SolicitudEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface SolicitudJPARepository extends CrudRepository<SolicitudEntity, Long>, QueryByExampleExecutor<SolicitudEntity> {
}
