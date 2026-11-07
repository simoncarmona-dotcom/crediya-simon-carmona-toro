package co.com.crediya.api;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.crediya.usecase.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfig.class)
class ApiRestTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserUseCase userUseCase;

    private RestTestClient client;

    @BeforeEach
    void setup() {
        Mockito.reset(userUseCase);
        client = RestTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void shouldRegisterUserAndReturn201Created() {
        User savedUser = User.builder()
                .idUsuario(1L)
                .nombres("Simón")
                .apellidos("Carmona Toro")
                .fechaNacimiento(LocalDate.of(1998, 5, 15))
                .direccion("Calle 10 # 20-30")
                .telefono("3001234567")
                .correoElectronico("simon@crediya.com")
                .salarioBase(new BigDecimal("3500000"))
                .build();

        when(userUseCase.registrarUsuario(any(User.class))).thenReturn(savedUser);

        String requestBody = """
                {
                  "nombres": "Simón",
                  "apellidos": "Carmona Toro",
                  "fecha_nacimiento": "1998-05-15",
                  "direccion": "Calle 10 # 20-30",
                  "telefono": "3001234567",
                  "correo_electronico": "simon@crediya.com",
                  "salario_base": 3500000
                }
                """;

        client.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id_usuario").isEqualTo(1)
                .jsonPath("$.correo_electronico").isEqualTo("simon@crediya.com");
    }

    @Test
    void shouldReturn400WhenRequiredFieldsAreInvalid() {
        String invalidBody = """
                {
                  "nombres": "",
                  "apellidos": "Carmona",
                  "correo_electronico": "formato-invalido",
                  "salario_base": 25000000
                }
                """;

        client.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(invalidBody)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.codigo").isEqualTo(400);
    }

    @Test
    void shouldReturn409ConflictWhenEmailAlreadyExists() {
        when(userUseCase.registrarUsuario(any(User.class)))
                .thenThrow(new EmailAlreadyExistsException("simon@crediya.com"));

        String requestBody = """
                {
                  "nombres": "Simón",
                  "apellidos": "Carmona Toro",
                  "correo_electronico": "simon@crediya.com",
                  "salario_base": 3500000
                }
                """;

        client.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody()
                .jsonPath("$.codigo").isEqualTo(409);
    }
}
