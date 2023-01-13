package Exceptions;

public class RoomDoesNotExistException extends Exception{
    public RoomDoesNotExistException (String str)
    {
        // calling the constructor of parent Exception
        super(str);
    }
}

