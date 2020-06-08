package po82.nazar.oop.model.Exceptions;

import po82.nazar.oop.model.Client;

public class DublicateAccountNumberException extends Exception{

    public DublicateAccountNumberException(){

    }

    public DublicateAccountNumberException(String message){
        super(message);
    }

    public DublicateAccountNumberException(String message, Client client){
        super(message);
    }
}
