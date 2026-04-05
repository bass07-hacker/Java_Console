package exception;

/**
 * Exception levée lorsque le solde d'un compte est insuffisant pour une opération.
 */
public class SoldeInsuffisantException extends Exception {
    public SoldeInsuffisantException(String message) {
        super(message);
    }
}
