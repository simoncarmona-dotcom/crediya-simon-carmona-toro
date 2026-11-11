package co.com.crediya.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateSolicitudRequestDTO(
        @Schema(description = "Documento de identidad del solicitante", example = "1020304050")
        @NotBlank(message = "El campo 'documento_identidad' no puede ser nulo ni vacío")
        @JsonProperty("documento_identidad")
        @JsonAlias({"documentoIdentidad", "documento"})
        String documentoIdentidad,

        @Schema(description = "Correo electrónico del solicitante (opcional)", example = "cliente@crediya.com")
        @Email(message = "El campo 'email' debe tener un formato de email válido")
        @JsonProperty("email")
        @JsonAlias({"correo_electronico", "correoElectronico"})
        String email,

        @Schema(description = "Monto del préstamo solicitado", example = "5000000")
        @NotNull(message = "El campo 'monto' no puede ser nulo")
        @DecimalMin(value = "1.0", inclusive = true, message = "El monto debe ser mayor a cero")
        @JsonProperty("monto")
        BigDecimal monto,

        @Schema(description = "Plazo solicitado en meses", example = "24")
        @NotNull(message = "El campo 'plazo' no puede ser nulo")
        @Min(value = 1, message = "El plazo debe ser de al menos 1 mes")
        @JsonProperty("plazo")
        Integer plazo,

        @Schema(description = "Identificador del tipo de préstamo existente (ej: 1=Personal, 2=Vehículo, 3=Hipotecario)", example = "1")
        @NotNull(message = "El campo 'id_tipo_prestamo' no puede ser nulo")
        @JsonProperty("id_tipo_prestamo")
        @JsonAlias({"idTipoPrestamo", "tipo_prestamo_id"})
        Long idTipoPrestamo
) {
}
