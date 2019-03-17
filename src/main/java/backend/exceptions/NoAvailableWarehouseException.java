package backend.exceptions;

public class NoAvailableWarehouseException extends Exception{

    public NoAvailableWarehouseException(){}

    public NoAvailableWarehouseException(String message)
    {
        super(message);
    }
}
