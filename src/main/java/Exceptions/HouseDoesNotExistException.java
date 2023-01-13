package Exceptions;

public class HouseDoesNotExistException extends Exception{
    public  HouseDoesNotExistException (String str)
    {
        // calling the constructor of parent Exception
        super(str);
    }
}

