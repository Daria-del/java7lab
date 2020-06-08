package po82.nazar.oop.model;

import po82.nazar.oop.model.Exceptions.InvalidAccountNumberException;

import java.util.*;
import java.util.Arrays;

public class Individual implements Client, Cloneable{
    private String title;
    private Account[] accounts  = new Account[16];;
    private int size = 0;
    private int creditScore = 0;



    public Individual(String title) {
        if(title == null) throw new NullPointerException();
        this.title = title;
    }

    public Individual(int size, String title) {
        this.title = title;
        this.accounts = new Account[size];
    }

    public Individual(String title,Account[] accounts) {
        if(title == null || accounts == null) throw new NullPointerException();

        this.title = title;
        this.accounts = accounts;
        this.size = accounts.length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title == null) throw new NullPointerException();

        this.title = title;
    }

    public boolean add(Account account){
        if(account == null) throw new NullPointerException();

        Account[] copy = new Account[accounts.length + 1];
        size++;
        System.arraycopy(accounts, 0, copy, 0, accounts.length);
        copy[accounts.length] = account;
        accounts = copy;
        return true;
    }


    public boolean add(Account account,int index){
        if(account == null) throw new NullPointerException();

        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Out");

        Account[] copy = new Account[accounts.length + 1];
        size++;

        copy[index - 1] = account;

        int i = 0, j = 0;
        while(i < copy.length){
            if(copy[i] == null){
                copy[i] = accounts[j];
                j++;
            }
            i++;
        }
        accounts = copy;
        return true;
    }

    public Account get(int index){
        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Out");
        return accounts[index];
    }

    public Account get(String number){
        if(number == null) throw new NullPointerException();

        for(Account account: accounts){
            if(account.checkNumber(number)) return account;
        }

        throw new NoSuchElementException();
    }

    public boolean hasAccountWithNumber(String number){
        if(number == null) throw new NullPointerException();

        for(Account account: accounts){
            if(account.checkNumber(number)) return true;
        }
        throw new NoSuchElementException();
    }

    public Account set(Account account,int index) throws InvalidAccountNumberException {
        if(account == null) throw new NullPointerException();
        if(hasAccountWithNumber(account.getNumber())) throw new InvalidAccountNumberException("Invalid number");

        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Out");

        Account buf = accounts[index];
        accounts[index] = account;
        return buf;
    }

    public Account remove(int index){
        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Out");

        Account[] copy = new Account[accounts.length - 1];
        Account deletedAccount = accounts[index - 1];

        int i = 0, j = 0;

        while(i < accounts.length){
            if(i != index - 1){
                copy[j] = accounts[i];
                j++;
            }
            i++;
        }
        accounts = copy;
        return deletedAccount;
    }

    public Account remove(String number){
        if(number == null) throw new NullPointerException();

        Account[] copy = new Account[accounts.length - 1];
        Account deletedAccount = null;

        int i = 0, j = 0;

        while(i < accounts.length){
            if(!accounts[i].getNumber().equals(number)){
                copy[j] = accounts[i];
                j++;
            } else {
                deletedAccount = accounts[i];
            }
            i++;
        }
        accounts = copy;
        if(deletedAccount == null) throw new NoSuchElementException();
        return deletedAccount;
    }

    public int indexOf(String number){
        if(number == null) throw new NullPointerException();

        for(int i = 0; i<size;i++){
            if(accounts[i].getNumber().equals(number)) return i;
        }
        throw new NoSuchElementException();
    }

    public int size(){
        return size;
    }

    public Account[] toArray(){
        return accounts;
    }



    public double getTotalBalance(){
        double totalBalance = 0;
        for(int i = 0; i<size;i++){
            totalBalance+=accounts[i].getBalance();
        }
        return totalBalance;
    }

    @Override
    public int getCreditScores() {
        return creditScore;
    }

    @Override
    public void addCreditScore(int creditScores) {
        this.creditScore += creditScores;
    }

    @Override
    public Account[] getCreditAccounts() {
        Account[] returnAccounts = new CreditAccount[0];
        Account[] copy;


        for(Account account: accounts){
            if(account instanceof CreditAccount){
                copy = new CreditAccount[returnAccounts.length + 1];
                System.arraycopy(returnAccounts, 0, copy, 0, returnAccounts.length);
                copy[returnAccounts.length] = account;
                returnAccounts = copy;
            }
        }
        if(returnAccounts.length == 0) throw new NoSuchElementException();

        return returnAccounts;
    }

    @Override
    public String toString() {
        String returnAccounts = String.format(
                "Client" + "\n"+
                        "name: %s \n" +
                        "credit score: %d", getTitle(), getCreditScores());
        for(Account account: accounts){
            returnAccounts += account.toString() + "\n";
        }
        returnAccounts += "total Balance: " + getTotalBalance();
        return returnAccounts;
    }

    public int hashCode(){
        return Objects.hashCode(getTitle()) ^ Objects.hashCode(getCreditScores()) ^ Objects.hashCode(toArray().length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return size == that.size &&
                creditScore == that.creditScore &&
                Objects.equals(title, that.title) &&
                Arrays.equals(accounts, that.accounts);
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    @Override
    public boolean remove(Account account) {
        if(account == null) throw new NullPointerException();

        int ind = 0;
        for(Account acc: accounts){
            if(acc.equals(account)){
                remove(ind);
                return true;
            }
            ind++;
        }
        throw new NoSuchElementException();
    }

    @Override
    public int indexOf(Account account) {
        if(account == null) throw new NullPointerException();

        int ind = 0;
        for(Account acc: accounts){
            if(acc.equals(account)){
                return ind;
            }
            ind++;
        }
        throw new NoSuchElementException();
    }

    @Override
    public double debtTotal() {
        double sum = 0.0;
        Account[] accounts = getCreditAccounts();
        for(Account credit: accounts){
            sum += credit.getBalance();
        }
        return sum;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for(Account account: accounts){
            if(account.equals(o)) return true;
        }
        return false;
    }


    @Override
    public <T> T[] toArray(T[] a) {
        Account[] accounts = (Account[]) a;
        Account[] buf = toArray();
        System.arraycopy(buf,0,accounts,0,size);
        return (T[]) accounts;
    }

    @Override
    public boolean remove(Object o) {
        for(Account account: accounts){
            if(account.equals(o)){
                remove(account);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator iterator = c.iterator();
        boolean flag = true;
        while(iterator.hasNext()){
            flag&=contains(iterator.next());
        }
        return flag;
    }

    @Override
    public boolean addAll(Collection<? extends Account> c) {
        Iterator iterator = c.iterator();

        while(iterator.hasNext()){
            add((Account) iterator.next());
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator iterator = c.iterator();
        boolean flag = false;
        while(iterator.hasNext()){
            flag|= remove(iterator.next());
        }
        return flag;
    }


    @Override
    public void clear() {
        this.accounts = new Account[16];
        this.size = 0;
    }

    @Override
    public int compareTo(Client o) {
        return (int)(getTotalBalance() - o.getTotalBalance());
    }

    public Iterator<Account> iterator(){
        return new AccountIterator();
    }


    private class AccountIterator implements Iterator<Account> {

        int index = 0;

        public boolean hasNext() {
            return index < accounts.length;
        }


        public Account next() {
            return accounts[index++];
        }
    }
}
