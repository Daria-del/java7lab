package po82.nazar.oop.model;

import po82.nazar.oop.model.Exceptions.InvalidAccountNumberException;

import java.time.LocalDate;
import java.util.Objects;

public class AbstractAccount implements Account, Cloneable{
    private double balance = 0.0;
    private String number = "";
    private LocalDate creationDate = LocalDate.now();
    private LocalDate expirationDate;

    protected AbstractAccount(String number, double balance, LocalDate creationDate, LocalDate expirationDate) throws InvalidAccountNumberException {
        String regex = "\\d+";

        if(number == null || creationDate == null || expirationDate == null){
            throw new NullPointerException("Objects is null");
        }
        if(creationDate.isAfter(LocalDate.now()) || creationDate.isAfter(expirationDate)){
            throw new IllegalArgumentException("Data is wrong");
        }
        if(number.length() != 20){throw new InvalidAccountNumberException("Error number");}
        if(!number.matches(regex)){throw new InvalidAccountNumberException("Is found bukves");}


        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public AbstractAccount(String number, LocalDate expirationDate) throws InvalidAccountNumberException {
        String regex = "\\d+";

        if(number.length() != 20){throw new InvalidAccountNumberException("Error number");}
        if(!number.matches(regex)){throw new InvalidAccountNumberException("Is found bukves");}

        this.number = number;
        this.expirationDate = expirationDate;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public void setNumber(String number) throws InvalidAccountNumberException {
        String regex = "\\d+";

        if(number.length() != 20){throw new InvalidAccountNumberException("Error number");}
        if(!number.matches(regex)){throw new InvalidAccountNumberException("Is found bukves");}

        this.number = number;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean checkNumber(String number) {
        return this.number.equals(number);
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public int monthesQuantityBeforeExpiration() {
        int day = creationDate.getDayOfMonth();
        int month = creationDate.getMonthValue();

        if(day < 26){ return month;}
        else {
            if(month == 12){
                return 1;
            } else {
                return creationDate.getMonthValue() + 1;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractAccount that = (AbstractAccount) o;
        return Double.compare(that.balance, balance) == 0 &&
                Objects.equals(number, that.number) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(balance) & Objects.hashCode(number) & Objects.hashCode(creationDate) & Objects.hashCode(expirationDate);
    }

    @Override
    public String toString() {
        return "Number: " + number + " balance: " + balance + " " + creationDate + "-" + expirationDate;
    }

    protected Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    @Override
    public int compareTo(Account o) {
        return (int)(this.balance - o.getBalance());
    }
}

