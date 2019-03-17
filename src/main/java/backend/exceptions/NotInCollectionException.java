package backend.exceptions;

public class NotInCollectionException extends Exception{

    public NotInCollectionException(){}

    public NotInCollectionException(String message)
    {
        super(message);
    }
}
