package po82.nazar.oop.model;

import po82.nazar.oop.model.Exceptions.InvalidAccountNumberException;

import java.time.LocalDate;

public interface Account extends Comparable<Account> {
    public String getNumber();
    public void setNumber(String number) throws InvalidAccountNumberException;
    public double getBalance();
    public void setBalance(double balance);
    public boolean checkNumber(String number);
    public LocalDate getCreationDate();
    public LocalDate getExpirationDate();
    public void setExpirationDate(LocalDate expirationDate);
    public int monthesQuantityBeforeExpiration();

}

