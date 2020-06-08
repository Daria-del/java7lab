package po82.nazar.oop.model;

import po82.nazar.oop.model.Exceptions.InvalidAccountNumberException;

import java.util.*;

public interface Client extends Iterable<Account>, Comparable<Client>, Collection<Account> {
    public String getTitle();
    public void setTitle(String title);
    public boolean add(Account account);
    public boolean add(Account account,int index);
    public Account get(int index);
    public Account get(String number) throws InvalidAccountNumberException;
    public boolean hasAccountWithNumber(String number) throws InvalidAccountNumberException;
    public Account set(Account account,int index) throws InvalidAccountNumberException;
    public Account remove(int index);
    public Account remove(String number) throws InvalidAccountNumberException;
    public int size();
    public Account[] toArray();
    public double getTotalBalance();
    public int indexOf(String number);
    public int getCreditScores();
    public void addCreditScore(int creditScores);
    default ClientStatus getStatus(int CreditScoreBound){
        return ClientStatus.fromId(CreditScoreBound);
    };
    public Account[] getCreditAccounts();
    public boolean remove(Account account);
    public int indexOf(Account account);
    public double debtTotal();

    public default boolean retainAll(Collection<?> c){
        Iterator iterator = iterator();
        boolean flag = false;
        while(iterator.hasNext()){
            Account account = (Account) iterator.next();
            if(!c.contains(account)) {
                flag |= remove(account);
            }
        }
        return flag;
    }

    public default List<Account> SortedServicesByBalance(){
        ArrayList<Account> services = new ArrayList<>();
        Account[] buf = toArray();
        Arrays.sort(buf);
        for(int i = 0; i<buf.length;i++){
            services.add(buf[i]);
        }
        return services;
    }
    public default Collection<Client> getTypedServices(ClientStatus status){
        Objects.requireNonNull(status);
        LinkedList<Client> list = new LinkedList<>();
        Iterator iterator = iterator();
        while(iterator.hasNext()){
            Client account = (Client) iterator.next();
            if (account != null && account.getStatus(account.getCreditScores()).equals(status)) {
                list.add(account);
            }
        }
        return list;
    }
}