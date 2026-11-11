package co.com.crediya.jpa.config;

import co.com.crediya.jpa.EstadoJPARepository;
import co.com.crediya.jpa.TipoPrestamoJPARepository;
import co.com.crediya.jpa.entity.EstadoEntity;
import co.com.crediya.jpa.entity.TipoPrestamoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EstadoJPARepository estadoRepository;
    private final TipoPrestamoJPARepository tipoPrestamoRepository;

    @Override
    public void run(String... args) {
        inicializarEstados();
        inicializarTiposPrestamo();
    }

    private void inicializarEstados() {
        if (estadoRepository.count() == 0) {
            log.info("Inicializando catálogo de estados en la base de datos...");
            estadoRepository.saveAll(List.of(
                    EstadoEntity.builder()
                            .idEstado(1L)
                            .nombre("Pendiente de revisión")
                            .descripcion("Solicitud registrada pendiente de evaluación")
                            .build(),
                    EstadoEntity.builder()
                            .idEstado(2L)
                            .nombre("Aprobado")
                            .descripcion("Solicitud de crédito aprobada")
                            .build(),
                    EstadoEntity.builder()
                            .idEstado(3L)
                            .nombre("Rechazado")
                            .descripcion("Solicitud de crédito rechazada")
                            .build(),
                    EstadoEntity.builder()
                            .idEstado(4L)
                            .nombre("Revision manual")
                            .descripcion("Solicitud requiere revisión manual por un asesor")
                            .build()
            ));
        }
    }

    private void inicializarTiposPrestamo() {
        if (tipoPrestamoRepository.count() == 0) {
            log.info("Inicializando catálogo de tipos de préstamo en la base de datos...");
            tipoPrestamoRepository.saveAll(List.of(
                    TipoPrestamoEntity.builder()
                            .idTipoPrestamo(1L)
                            .nombre("Personal")
                            .montoMinimo(new BigDecimal("500000.00"))
                            .montoMaximo(new BigDecimal("20000000.00"))
                            .tasaInteres(new BigDecimal("0.0200"))
                            .validacionAutomatica(false)
                            .build(),
                    TipoPrestamoEntity.builder()
                            .idTipoPrestamo(2L)
                            .nombre("Vehículo")
                            .montoMinimo(new BigDecimal("5000000.00"))
                            .montoMaximo(new BigDecimal("80000000.00"))
                            .tasaInteres(new BigDecimal("0.0150"))
                            .validacionAutomatica(true)
                            .build(),
                    TipoPrestamoEntity.builder()
                            .idTipoPrestamo(3L)
                            .nombre("Hipotecario")
                            .montoMinimo(new BigDecimal("30000000.00"))
                            .montoMaximo(new BigDecimal("300000000.00"))
                            .tasaInteres(new BigDecimal("0.0120"))
                            .validacionAutomatica(false)
                            .build()
            ));
        }
    }
}
