package co.com.crediya.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitud")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Long idSolicitud;

    @Column(name = "documento_identidad", nullable = false, length = 50)
    private String documentoIdentidad;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "monto", nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "plazo", nullable = false)
    private Integer plazo;

    @Column(name = "id_tipo_prestamo", nullable = false)
    private Long idTipoPrestamo;

    @Column(name = "id_estado", nullable = false)
    private Long idEstado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_prestamo", insertable = false, updatable = false)
    private TipoPrestamoEntity tipoPrestamo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado", insertable = false, updatable = false)
    private EstadoEntity estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
