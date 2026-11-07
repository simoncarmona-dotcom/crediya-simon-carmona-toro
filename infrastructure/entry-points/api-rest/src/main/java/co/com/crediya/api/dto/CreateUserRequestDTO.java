package co.com.crediya.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateUserRequestDTO(
        @Schema(description = "Nombres del solicitante", example = "Simón")
        @NotBlank(message = "El campo 'nombres' no puede ser nulo ni vacío")
        String nombres,

        @Schema(description = "Apellidos del solicitante", example = "Carmona Toro")
        @NotBlank(message = "El campo 'apellidos' no puede ser nulo ni vacío")
        String apellidos,

        @Schema(description = "Fecha de nacimiento del solicitante", example = "1998-05-15")
        @JsonProperty("fecha_nacimiento")
        LocalDate fechaNacimiento,

        @Schema(description = "Dirección de residencia", example = "Calle 10 # 20-30")
        String direccion,

        @Schema(description = "Teléfono de contacto", example = "3001234567")
        String telefono,

        @Schema(description = "Correo electrónico único del solicitante", example = "simon@crediya.com")
        @NotBlank(message = "El campo 'correo_electronico' no puede ser nulo ni vacío")
        @Email(message = "El campo 'correo_electronico' debe tener un formato de email válido")
        @JsonProperty("correo_electronico")
        String correoElectronico,

        @Schema(description = "Salario base mensual entre 0 y 15000000", example = "3500000")
        @NotNull(message = "El campo 'salario_base' no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = true, message = "El campo 'salario_base' debe ser mayor o igual a 0")
        @DecimalMax(value = "15000000.0", inclusive = true, message = "El campo 'salario_base' no puede superar 15000000")
        @JsonProperty("salario_base")
        BigDecimal salarioBase,

        @Schema(description = "Documento de identidad (opcional)", example = "1020304050")
        @JsonProperty("documento_identidad")
        String documentoIdentidad,

        @Schema(description = "Identificador del rol (opcional)", example = "1")
        @JsonProperty("id_rol")
        Long idRol
) {
}
