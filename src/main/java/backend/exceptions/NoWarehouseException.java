package backend.exceptions;

public class NoWarehouseException extends Exception {
    public NoWarehouseException(){}
    public NoWarehouseException(String message)
    {
        super(message);
    }
}
