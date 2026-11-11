package co.com.crediya.model.solicitud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Solicitud {
    private Long idSolicitud;
    private String documentoIdentidad;
    private String email;
    private BigDecimal monto;
    private Integer plazo;
    private Long idTipoPrestamo;
    private Long idEstado;
    private TipoPrestamo tipoPrestamo;
    private Estado estado;
    private LocalDateTime fechaCreacion;
}
