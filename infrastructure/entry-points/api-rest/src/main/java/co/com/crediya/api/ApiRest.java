package co.com.crediya.api;

import co.com.crediya.api.dto.CreateUserRequestDTO;
import co.com.crediya.api.dto.UserResponseDTO;
import co.com.crediya.model.user.User;
import co.com.crediya.usecase.user.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión y registro de usuarios en CrediYa")
public class ApiRest {

    private final UserUseCase userUseCase;

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Registra un nuevo solicitante validando sus datos personales y correo único."
    )
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada nulos, vacíos o con formato inválido")
    @ApiResponse(responseCode = "409", description = "El correo electrónico proporcionado ya está registrado")
    @ApiResponse(responseCode = "500", description = "Error interno procesado de manera controlada")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> registrarUsuario(@Valid @RequestBody CreateUserRequestDTO request) {
        log.info("Recibida petición POST /api/v1/usuarios para correo: {}", request.correoElectronico());

        User domainUser = User.builder()
                .nombres(request.nombres())
                .apellidos(request.apellidos())
                .fechaNacimiento(request.fechaNacimiento())
                .direccion(request.direccion())
                .telefono(request.telefono())
                .correoElectronico(request.correoElectronico())
                .salarioBase(request.salarioBase())
                .documentoIdentidad(request.documentoIdentidad())
                .idRol(request.idRol())
                .build();

        User savedUser = userUseCase.registrarUsuario(domainUser);
        log.info("Usuario registrado satisfactoriamente con id_usuario: {}", savedUser.getIdUsuario());

        UserResponseDTO responseDTO = new UserResponseDTO(
                savedUser.getIdUsuario(),
                savedUser.getNombres(),
                savedUser.getApellidos(),
                savedUser.getFechaNacimiento(),
                savedUser.getDireccion(),
                savedUser.getTelefono(),
                savedUser.getCorreoElectronico(),
                savedUser.getSalarioBase(),
                savedUser.getDocumentoIdentidad(),
                savedUser.getIdRol()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
