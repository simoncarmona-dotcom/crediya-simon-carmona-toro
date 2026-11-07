package co.com.crediya.api.exception;

import co.com.crediya.model.user.exception.BusinessException;
import co.com.crediya.model.user.exception.EmailAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        log.warn("Conflicto al registrar usuario: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), null);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
        log.warn("Error de regla de negocio: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                fieldErrors.put(err.getField(), err.getDefaultMessage())
        );
        log.warn("Error de validación en petición de entrada: {}", fieldErrors);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Datos de entrada inválidos", fieldErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidJson(HttpMessageNotReadableException ex) {
        log.warn("Formato JSON o tipo de dato inválido en la petición: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST,
                "El cuerpo de la petición tiene un formato inválido o tipos de datos incorrectos (verifique fechas y valores numéricos).",
                null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Error inesperado procesando la petición", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno procesando la solicitud. Por favor, intente nuevamente más tarde.",
                null);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            HttpStatus status, String mensaje, Object detalles) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("codigo", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("mensaje", mensaje);
        if (detalles != null) {
            response.put("detalles", detalles);
        }
        return ResponseEntity.status(status).body(response);
    }
}
