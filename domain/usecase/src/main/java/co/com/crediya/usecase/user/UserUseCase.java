package co.com.crediya.usecase.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.BusinessException;
import co.com.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class UserUseCase {

    private static final Logger LOG = Logger.getLogger(UserUseCase.class.getName());
    private static final BigDecimal SALARIO_MINIMO = BigDecimal.ZERO;
    private static final BigDecimal SALARIO_MAXIMO = new BigDecimal("15000000");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final UserRepository userRepository;

    public User registrarUsuario(User user) {
        if (user == null) {
            throw new BusinessException("Los datos del usuario son obligatorios.");
        }

        LOG.info("Ejecutando validaciones de dominio para registro de usuario");
        validarDatosObligatoriosYFormato(user);

        String correoNormalizado = user.getCorreoElectronico().trim().toLowerCase();
        user.setCorreoElectronico(correoNormalizado);

        if (userRepository.existsByCorreoElectronico(correoNormalizado)) {
            LOG.warning("Intento de registro rechazado: el correo ya se encuentra registrado");
            throw new EmailAlreadyExistsException(correoNormalizado);
        }

        LOG.info("Validaciones superadas, procediendo a persistir el usuario");
        return userRepository.save(user);
    }

    private void validarDatosObligatoriosYFormato(User user) {
        if (user.getNombres() == null || user.getNombres().isBlank()) {
            throw new BusinessException("El campo 'nombres' es obligatorio y no puede estar vacío.");
        }
        if (user.getApellidos() == null || user.getApellidos().isBlank()) {
            throw new BusinessException("El campo 'apellidos' es obligatorio y no puede estar vacío.");
        }
        if (user.getCorreoElectronico() == null || user.getCorreoElectronico().isBlank()) {
            throw new BusinessException("El campo 'correo_electronico' es obligatorio y no puede estar vacío.");
        }
        if (!EMAIL_PATTERN.matcher(user.getCorreoElectronico().trim()).matches()) {
            throw new BusinessException("El campo 'correo_electronico' no tiene un formato de email válido.");
        }
        if (user.getSalarioBase() == null) {
            throw new BusinessException("El campo 'salario_base' es obligatorio.");
        }
        if (user.getSalarioBase().compareTo(SALARIO_MINIMO) < 0
                || user.getSalarioBase().compareTo(SALARIO_MAXIMO) > 0) {
            throw new BusinessException("El campo 'salario_base' debe estar entre 0 y 15000000.");
        }
    }
}
