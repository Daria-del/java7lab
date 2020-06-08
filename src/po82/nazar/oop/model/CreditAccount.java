package po82.nazar.oop.model;

import po82.nazar.oop.model.Exceptions.InvalidAccountNumberException;

import java.time.LocalDate;
import java.util.Objects;

public class CreditAccount extends AbstractAccount implements Credit, Cloneable{
    private double APR = 30;

    public CreditAccount(String number, double balance, double APR) throws InvalidAccountNumberException {
        super(number, LocalDate.now());
        this.APR = APR;
    }
    @Override
    public double getAPR() {
        return APR;
    }

    @Override
    public void setAPR(double APR) {
        this.APR = APR;
    }

    @Override
    public double nextPaymentValue() {
        return getBalance() * (1 + getAPR() * (getExpirationDate().getYear() - getCreationDate().getYear())) / monthesQuantityBeforeExpiration();
    }

    @Override
    public LocalDate nextPaymentDate() {
        LocalDate now = LocalDate.now();
        if(LocalDate.now().getDayOfMonth() > 25){ return now.plusMonths(1);}
        else{return LocalDate.of(now.getYear(), now.getMonth(), 25);}
    }

    @Override
    public String toString() {
        return String.format("Credit Account - number: %s balance %f APR %f  %s-%s", getNumber(), getBalance(),getAPR(),getCreationDate().toString(), getExpirationDate().toString());
    }

    public int hashCode(){
        return 71 * super.hashCode();
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}

enum ClientStatus{
    BAD(-4),
    RISKY(-2),
    GOOD(0),
    GOLD(3),
    PLATINUM(5);

    private final int creditScoreBound;

    ClientStatus(int n){
        this.creditScoreBound = n;
    }

    Integer getCreditScoreBound(){
        return creditScoreBound;
    }

    public static ClientStatus fromId(Integer id) {
        for (ClientStatus status : ClientStatus.values()) {
            if (status.getCreditScoreBound() >= id)
                return status;
        }
        return null;
    }
}