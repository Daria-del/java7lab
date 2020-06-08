package po82.nazar.oop.model;

import po82.nazar.oop.model.Exceptions.InvalidAccountNumberException;

import java.time.LocalDate;
import java.util.Objects;

public class DebitAccount extends AbstractAccount implements Cloneable{

    public DebitAccount(String number, double balance) throws InvalidAccountNumberException {
        super(number, LocalDate.now());
        setBalance(balance);
    }

    @Override
    public String toString() {
        return String.format("Debit account number: %s balance: %f  %s-%s", getNumber(), getBalance(), getCreationDate().toString(), getExpirationDate().toString());
    }

    public int hashCode(){
        return  53 * super.hashCode();
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

}