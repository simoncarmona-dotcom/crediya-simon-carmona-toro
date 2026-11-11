package co.com.crediya.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SolicitudResponseDTO(
        @JsonProperty("id_solicitud") Long idSolicitud,
        @JsonProperty("documento_identidad") String documentoIdentidad,
        @JsonProperty("email") String email,
        @JsonProperty("monto") BigDecimal monto,
        @JsonProperty("plazo") Integer plazo,
        @JsonProperty("id_tipo_prestamo") Long idTipoPrestamo,
        @JsonProperty("tipo_prestamo") String tipoPrestamo,
        @JsonProperty("id_estado") Long idEstado,
        @JsonProperty("estado") String estado,
        @JsonProperty("fecha_creacion") LocalDateTime fechaCreacion
) {
}
