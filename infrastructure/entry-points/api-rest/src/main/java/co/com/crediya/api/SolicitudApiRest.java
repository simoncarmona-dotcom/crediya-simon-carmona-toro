package co.com.crediya.api;

import co.com.crediya.api.dto.CreateSolicitudRequestDTO;
import co.com.crediya.api.dto.SolicitudResponseDTO;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.usecase.solicitud.SolicitudUseCase;
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
@RequestMapping(value = {"/api/v1/solicitud", "/api/v1/solicitudes"}, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Solicitudes", description = "Gestión de solicitudes de préstamos de CrediYa")
public class SolicitudApiRest {

    private final SolicitudUseCase solicitudUseCase;

    @Operation(
            summary = "Registrar una nueva solicitud de préstamo",
            description = "Registra una solicitud de crédito con estado inicial 'Pendiente de revisión' tras validar documento, monto, plazo y tipo de préstamo."
    )
    @ApiResponse(responseCode = "201", description = "Solicitud registrada exitosamente con estado inicial Pendiente de revisión")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o tipo de préstamo inexistente")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor procesado de forma controlada")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SolicitudResponseDTO> registrarSolicitud(@Valid @RequestBody CreateSolicitudRequestDTO request) {
        log.info("Recibida petición POST /api/v1/solicitud para documento: {}, monto: {}, tipo: {}",
                request.documentoIdentidad(), request.monto(), request.idTipoPrestamo());

        Solicitud domainSolicitud = Solicitud.builder()
                .documentoIdentidad(request.documentoIdentidad())
                .email(request.email())
                .monto(request.monto())
                .plazo(request.plazo())
                .idTipoPrestamo(request.idTipoPrestamo())
                .build();

        Solicitud saved = solicitudUseCase.registrarSolicitud(domainSolicitud);

        log.info("Solicitud creada con ID: {}, estado: {}", saved.getIdSolicitud(),
                saved.getEstado() != null ? saved.getEstado().getNombre() : "Pendiente de revisión");

        SolicitudResponseDTO responseDTO = new SolicitudResponseDTO(
                saved.getIdSolicitud(),
                saved.getDocumentoIdentidad(),
                saved.getEmail(),
                saved.getMonto(),
                saved.getPlazo(),
                saved.getIdTipoPrestamo(),
                saved.getTipoPrestamo() != null ? saved.getTipoPrestamo().getNombre() : null,
                saved.getIdEstado(),
                saved.getEstado() != null ? saved.getEstado().getNombre() : "Pendiente de revisión",
                saved.getFechaCreacion()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
