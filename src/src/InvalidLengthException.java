package src;

public class InvalidLengthException extends Exception {
    private static final long serialVersionUID = 8174739145878820656L;

    public InvalidLengthException(String message) {
        super(message);
    }
}