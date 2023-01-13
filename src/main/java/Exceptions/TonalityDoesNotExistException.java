package Exceptions;

public class TonalityDoesNotExistException extends Exception{
    public TonalityDoesNotExistException (String str)
    {
        // calling the constructor of parent Exception
        super(str);
    }
}
