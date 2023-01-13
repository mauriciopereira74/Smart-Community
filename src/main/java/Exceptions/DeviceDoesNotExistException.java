package Exceptions;

public class DeviceDoesNotExistException  extends Exception{
    public DeviceDoesNotExistException (String str)
    {
        // calling the constructor of parent Exception
        super(str);
    }
}


