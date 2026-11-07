package co.com.crediya.jpa.helper;

import co.com.crediya.jpa.JPARepository;
import co.com.crediya.jpa.JPARepositoryAdapter;
import co.com.crediya.jpa.entity.UserEntity;
import co.com.crediya.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AdapterOperationsTest {

    @Mock
    private JPARepository repository;

    @Mock
    private ObjectMapper objectMapper;

    private JPARepositoryAdapter adapter;
    private User domainUser;
    private UserEntity entityUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        domainUser = User.builder()
                .idUsuario(1L)
                .nombres("Simón")
                .apellidos("Carmona")
                .correoElectronico("simon@crediya.com")
                .salarioBase(new BigDecimal("3500000"))
                .build();

        entityUser = UserEntity.builder()
                .idUsuario(1L)
                .nombres("Simón")
                .apellidos("Carmona")
                .correoElectronico("simon@crediya.com")
                .salarioBase(new BigDecimal("3500000"))
                .build();

        when(objectMapper.map(domainUser, UserEntity.class)).thenReturn(entityUser);
        when(objectMapper.map(entityUser, User.class)).thenReturn(domainUser);

        adapter = new JPARepositoryAdapter(repository, objectMapper);
    }

    @Test
    void testSave() {
        when(repository.save(entityUser)).thenReturn(entityUser);

        User result = adapter.save(domainUser);

        assertEquals(domainUser.getCorreoElectronico(), result.getCorreoElectronico());
    }

    @Test
    void testExistsByCorreoElectronico() {
        when(repository.existsByCorreoElectronico("simon@crediya.com")).thenReturn(true);

        assertTrue(adapter.existsByCorreoElectronico("simon@crediya.com"));
    }

    @Test
    void testSaveAllEntities() {
        List<User> users = List.of(domainUser);
        List<UserEntity> entities = List.of(entityUser);

        when(repository.saveAll(entities)).thenReturn(entities);

        List<User> result = adapter.saveAllEntities(users);

        assertEquals(1, result.size());
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(entityUser));

        User result = adapter.findById(1L);

        assertEquals(1L, result.getIdUsuario());
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(List.of(entityUser));

        List<User> result = adapter.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void testFindByExample() {
        when(repository.findAll(any(Example.class))).thenReturn(List.of(entityUser));

        List<User> result = adapter.findByExample(domainUser);

        assertEquals(1, result.size());
    }
}
