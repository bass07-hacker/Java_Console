package exception;

/**
 * Exception levée lorsqu'un numéro de compte n'existe pas dans le système.
 */
public class CompteIntrouvableException extends Exception {
    public CompteIntrouvableException(String message) {
        super(message);
    }
}
