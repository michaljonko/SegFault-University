package events.segfault.gdansk2019.stub;

public final class NotAdultException extends RuntimeException {

    public NotAdultException() {
        super("Customer is not adult.");
    }
}
