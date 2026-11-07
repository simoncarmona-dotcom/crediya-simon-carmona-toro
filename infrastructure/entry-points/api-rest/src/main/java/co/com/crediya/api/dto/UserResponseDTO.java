package co.com.crediya.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponseDTO(
        @JsonProperty("id_usuario") Long idUsuario,
        String nombres,
        String apellidos,
        @JsonProperty("fecha_nacimiento") LocalDate fechaNacimiento,
        String direccion,
        String telefono,
        @JsonProperty("correo_electronico") String correoElectronico,
        @JsonProperty("salario_base") BigDecimal salarioBase,
        @JsonProperty("documento_identidad") String documentoIdentidad,
        @JsonProperty("id_rol") Long idRol
) {
}
