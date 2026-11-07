package co.com.crediya.usecase.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.BusinessException;
import co.com.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.crediya.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUseCase userUseCase;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = User.builder()
                .nombres("Simón")
                .apellidos("Carmona Toro")
                .fechaNacimiento(LocalDate.of(1998, 5, 15))
                .direccion("Calle 10 # 20-30")
                .telefono("3001234567")
                .correoElectronico("Simon@CrediYa.com")
                .salarioBase(new BigDecimal("3500000"))
                .documentoIdentidad("1020304050")
                .idRol(1L)
                .build();
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        when(userRepository.existsByCorreoElectronico("simon@crediya.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User saved = invocation.getArgument(0);
            saved.setIdUsuario(1L);
            return saved;
        });

        User result = userUseCase.registrarUsuario(validUser);

        assertNotNull(result);
        assertEquals(1L, result.getIdUsuario());
        assertEquals("simon@crediya.com", result.getCorreoElectronico());
        verify(userRepository).save(validUser);
    }

    @Test
    void shouldAllowBoundarySalaryZeroAndFifteenMillion() {
        validUser.setSalarioBase(BigDecimal.ZERO);
        when(userRepository.existsByCorreoElectronico("simon@crediya.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User resZero = userUseCase.registrarUsuario(validUser);
        assertEquals(BigDecimal.ZERO, resZero.getSalarioBase());

        validUser.setSalarioBase(new BigDecimal("15000000"));
        User resMax = userUseCase.registrarUsuario(validUser);
        assertEquals(new BigDecimal("15000000"), resMax.getSalarioBase());
    }

    @Test
    void shouldThrowExceptionWhenUserIsNull() {
        assertThrows(BusinessException.class, () -> userUseCase.registrarUsuario(null));
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenNombresIsBlank() {
        validUser.setNombres("   ");
        assertThrows(BusinessException.class, () -> userUseCase.registrarUsuario(validUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenApellidosIsNull() {
        validUser.setApellidos(null);
        assertThrows(BusinessException.class, () -> userUseCase.registrarUsuario(validUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsBlank() {
        validUser.setCorreoElectronico("");
        assertThrows(BusinessException.class, () -> userUseCase.registrarUsuario(validUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailFormatIsInvalid() {
        validUser.setCorreoElectronico("correo-sin-arroba.com");
        assertThrows(BusinessException.class, () -> userUseCase.registrarUsuario(validUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenSalarioBaseIsNull() {
        validUser.setSalarioBase(null);
        assertThrows(BusinessException.class, () -> userUseCase.registrarUsuario(validUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenSalarioBaseIsNegative() {
        validUser.setSalarioBase(new BigDecimal("-1"));
        assertThrows(BusinessException.class, () -> userUseCase.registrarUsuario(validUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenSalarioBaseExceedsMax() {
        validUser.setSalarioBase(new BigDecimal("15000001"));
        assertThrows(BusinessException.class, () -> userUseCase.registrarUsuario(validUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowEmailAlreadyExistsExceptionWhenDuplicateEmail() {
        when(userRepository.existsByCorreoElectronico("simon@crediya.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userUseCase.registrarUsuario(validUser));
        verify(userRepository, never()).save(any());
    }
}
