package co.com.crediya.jpa;

import co.com.crediya.jpa.entity.UserEntity;
import co.com.crediya.jpa.helper.AdapterOperations;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
public class JPARepositoryAdapter extends AdapterOperations<User, UserEntity, Long, JPARepository>
        implements UserRepository {

    public JPARepositoryAdapter(JPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    @Transactional
    public User save(User user) {
        log.info("Persistiendo usuario de forma transaccional con correo: {}", user.getCorreoElectronico());
        return super.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCorreoElectronico(String correoElectronico) {
        log.debug("Consultando en base de datos si existe el correo: {}", correoElectronico);
        return repository.existsByCorreoElectronico(correoElectronico);
    }
}
