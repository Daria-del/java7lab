package po82.nazar.oop.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Entity implements Client {
    private String title;
    private Node head = null;
    private Node tail = null;
    private int size = 0;
    private int creditScore = 0;


    public Entity(String title) {
        if(title == null) throw new NullPointerException();
        this.title = title;
    }

    public Entity(String title, Account[] types) {
        if(title == null || types == null) throw new NullPointerException();

        this.title = title;
        Node newNode;

        if (types.length == 1) {
            head = new Node(types[0]);
            tail = head;
            size += 1;
        }
        if (types.length >1){
            head = tail = new Node(types[0]);
            size+= 1;
            for (int i = 1; i < types.length; i++) {
                newNode = new Node(types[i]);
                newNode.setPrev(tail);
                tail.setNext(newNode);
                tail = newNode;
                size += 1;
            }
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        if(title == null) throw new NullPointerException();
        this.title = title;
    }

    @Override
    public boolean add(Account account) {
        if(account == null) throw new NullPointerException();

        Node newNode = new Node(account);

        if(head == null){
            head = tail = newNode;
        } else if (head == tail){
            tail = newNode;
            tail.setPrev(head);
            head.setNext(tail);
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean add(Account account, int index) {
        if(account == null) throw new NullPointerException();
        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Out");

        Node newNode = new Node(account);

        Node prevNode, nowNode;
        nowNode = head;
        if(index == 1){
            head.setPrev(newNode);
            newNode.setNext(head);
            head = newNode;
        } else if (index > 1){
            for(int i = 1; i< index; i++) {
                nowNode = nowNode.getNext();
            }
            prevNode = nowNode.getPrev();
            prevNode.setNext(newNode);
            newNode.setNext(nowNode);
            newNode.setPrev(prevNode);
            nowNode.setPrev(newNode);
        }
        size++;
        return false;
    }

    @Override
    public Account get(int index) {
        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Out");

        Node nowNode = head;
        if(index == 1){
            return head.getValue();
        }
        if(index > 1){
            for(int i = 1; i < index; i++){nowNode = nowNode.getNext();}
        }
        if(head == null){return null;}
        else return nowNode.getValue();
    }

    @Override
    public Account get(String number) {
        if(number == null) throw new NullPointerException();

        Node nowNode = head;

        while (nowNode != null){
            if(nowNode.getValue().getNumber() == number){
                return nowNode.getValue();
            }
            nowNode = nowNode.getNext();
        }
        return null;
    }

    @Override
    public boolean hasAccountWithNumber(String number) {
        if(number == null) throw new NullPointerException();

        Node nowNode = head;

        while (nowNode != null){
            if(nowNode.getValue().getNumber().equals(number)){
                return true;
            }
            nowNode = nowNode.getNext();
        };
        return false;
    }

    @Override
    public Account set(Account account, int index) {
        if(account == null) throw new NullPointerException();
        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Out");

        Node newNode = new Node(account);

        Node prevNode, nowNode,nextMode, returnMode = null;
        nowNode = head;
        if(index == 1){
            nextMode = nowNode.getNext();
            returnMode = head;
            head = newNode;
            newNode.setNext(nextMode);
        } else if(index > 1){
            for(int i = 1; i< index; i++) {
                nowNode = nowNode.getNext();
            }
            if(nowNode.getNext() != null) {
                prevNode = nowNode.getPrev();
                nextMode = nowNode.getNext();
                returnMode = nowNode;

                prevNode.setNext(newNode);
                nextMode.setPrev(newNode);

                newNode.setPrev(prevNode);
                newNode.setNext(nextMode);
            } else {
                prevNode = nowNode.getPrev();
                nextMode = null;
                returnMode = nowNode;

                prevNode.setNext(newNode);

                newNode.setPrev(prevNode);
                newNode.setNext(nextMode);
            }
        }
        if(returnMode != null){return returnMode.getValue();}
        return null;
    }

    @Override
    public Account remove(int index) {
        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Out");

        if(head == null){return null;}

        Node prevNode, nowNode,nextMode, returnMode = null;
        nowNode = head;
        if(index == 1){
            nextMode = nowNode.getNext();
            returnMode = head;
            nextMode.setPrev(null);
            head = nextMode;
        } else if(index > 1){
            for(int i = 1; i< index; i++) {
                nowNode = nowNode.getNext();
            }
            if(nowNode.getNext() != null) {
                prevNode = nowNode.getPrev();
                nextMode = nowNode.getNext();
                returnMode = nowNode;

                prevNode.setNext(nextMode);
                nextMode.setPrev(prevNode);
            }else {
                prevNode = nowNode.getPrev();
                nextMode = null;
                returnMode = nowNode;

                prevNode.setNext(nextMode);
            }
        }
        if(returnMode != null){
            size--;
            return returnMode.getValue();
        }
        throw new NoSuchElementException();
    }

    @Override
    public Account remove(String number) {
        if(number == null) throw new NullPointerException();

        Node nowNode = head;
        Node prevNode,nextNode,returnNode = null;

        while (nowNode != null){
            if(nowNode.getValue().getNumber().equals(number)){
                returnNode = nowNode;
                if(returnNode.getNext() != null) {
                    prevNode = nowNode.getPrev();
                    nextNode = nowNode.getNext();

                    prevNode.setNext(nextNode);
                    nextNode.setPrev(prevNode);
                } else {
                    prevNode = nowNode.getPrev();
                    nextNode = null;

                    prevNode.setNext(nextNode);
                }
            }
            nowNode = nowNode.getNext();
        };
        if(returnNode != null){
            size--;
            return returnNode.getValue();
        }
        throw new NoSuchElementException();
    }

    @Override
    public int indexOf(String number) {
        if(number == null) throw new NullPointerException();

        Node nowNode = head;
        int index = 0;

        while (nowNode != null){
            if(nowNode.getValue().getNumber() == number){
                return index;
            }
            nowNode = nowNode.getNext();
            index++;
        }
        if(index == size) throw new NoSuchElementException();
        return index;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Account[] toArray() {
        Account[] accounts = new Account[size];
        Node nowNode = head;
        int lenght = 0;

        if(head == null){return null;}
        if(head == tail){accounts[0] = head.getValue(); return accounts;}

        while(nowNode != null){
            accounts[lenght] = nowNode.getValue();
            nowNode = nowNode.getNext();
            lenght++;
        }
        return accounts;
    }


    @Override
    public double getTotalBalance() {
        double totalBalance = 0;
        Node nowNode = head;
        while (nowNode != null) {
            totalBalance += nowNode.getValue().getBalance();
            nowNode = nowNode.getNext();
        }
        return totalBalance;
    }

    @Override
    public int getCreditScores() {
        return this.creditScore;
    }

    @Override
    public void addCreditScore(int creditScores) {
        this.creditScore += creditScores;
    }

    @Override
    public Account[] getCreditAccounts() {
        Account[] returnAccounts = new CreditAccount[0];
        Account[] copy;
        Account[] accounts = toArray();

        for(Account account: accounts){
            if(account instanceof CreditAccount){
                copy = new CreditAccount[returnAccounts.length + 1];
                System.arraycopy(returnAccounts, 0, copy, 0, returnAccounts.length);
                copy[returnAccounts.length] = account;
                returnAccounts = copy;
            }
        }
        return returnAccounts;
    }

    @Override
    public boolean remove(Account account) {
        Account[] accounts = toArray();
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
        Account[] accounts = toArray();
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
        Account[] accounts = toArray();
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
        Account[] accounts = toArray();
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
        this.title = "";
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.creditScore = 0;
    }

    @Override
    public int compareTo(Client o) {
        return (int)(getTotalBalance() - o.getTotalBalance());
    }

    public Iterator<Account> iterator(){
        return new AccountIterator();
    }

    private class AccountIterator implements Iterator<Account>{
        Node nowNode = head;
        Account returnAccount;

        public boolean hasNext() {
            return nowNode != null;
        }

        public Account next() {
            try {
                returnAccount = nowNode.getValue();
                nowNode = nowNode.getNext();
                return returnAccount;
            } catch (NoSuchElementException e){
                throw new NoSuchElementException("Больше нету файлов");
            }
        }
    }

}
