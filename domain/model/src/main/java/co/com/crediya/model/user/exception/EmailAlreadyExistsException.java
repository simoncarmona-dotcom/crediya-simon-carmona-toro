package co.com.crediya.model.user.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String correoElectronico) {
        super("El correo electrónico ya se encuentra registrado: " + correoElectronico);
    }
}
