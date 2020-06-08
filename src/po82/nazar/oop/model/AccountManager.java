package po82.nazar.oop.model;

import po82.nazar.oop.model.Exceptions.InvalidAccountNumberException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AccountManager implements Iterable<Client>{
    private Client[] clients;
    private int size = 0;

    public AccountManager(int size) {
        this.clients = new Client[size];
    }

    public AccountManager(Client[] clients) {
        if(clients == null) throw new NullPointerException();

        this.clients = clients;
        this.size = clients.length;
    }

    public boolean add(Client client){
        if(client == null) throw new NullPointerException();

        Client[] copy = new Client[clients.length + 1];
        size++;
        System.arraycopy(clients, 0, copy, 0, clients.length);
        copy[clients.length] = client;
        clients = copy;
        return true;
    }



    public boolean add(Client client, int index){
        if(client == null) throw new NullPointerException();
        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Out");

        Client[] copy = new Client[clients.length + 1];
        size++;

        copy[index - 1] = client;

        int i = 0, j = 0;
        while(i < copy.length){
            if(copy[i] == null){
                copy[i] = clients[j];
                j++;
            }
            i++;
        }
        clients = copy;
        return true;
    }

    public Client get(int index){
        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Out");

        return clients[index];
    }

    public Client set(Client client, int index){
        if(client == null) throw new NullPointerException();
        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Out");

        Client buf = clients[index];
        clients[index] = client;
        if(buf == null && client != null) size++;
        if(buf!=null && client == null) size--;
        return buf;
    }

    public Client remove(int index){
        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Out");

        Client[] copy = new Client[clients.length - 1];
        Client deletedAccount = clients[index - 1];

        int i = 0, j = 0;

        while(i < clients.length){
            if(i != index - 1){
                copy[j] = clients[i];
                j++;
            }
            i++;
        }
        clients = copy;
        return deletedAccount;
    }

    //Возвращат число физ. лиц
    public int size(){
        return size;
    }

    public Client[] getClients(){
        return clients;
    }

    public Client[] getSortedIndividualsByTotalBalance(){
        Client[] buf = getClients();
        for(int i = 0; i<buf.length;i++){
            for(int j = 0; j<buf.length-1;j++){
                if(buf[j].getTotalBalance() > buf[j+1].getTotalBalance()){
                    Client tmp = buf[j];
                    buf[j] = buf[j+1];
                    buf[j+1] = tmp;
                }
            }
        }
        return buf;
    }

    public Account getAccountWithNumber(String number) throws InvalidAccountNumberException {
        String regex = "\\d+";

        if(number == null) throw new NullPointerException();
        if(number.length() != 20){throw new InvalidAccountNumberException("Error number");}
        if(!number.matches(regex)){throw new InvalidAccountNumberException("Is found bukves");}

        for(Client client: clients){
            if(client.get(number) != null){
                return client.get(number);
            }
        }
        throw new NoSuchElementException();
    }

    public Account removeAccount(String number) throws InvalidAccountNumberException {
        String regex = "\\d+";

        if(number == null) throw new NullPointerException();
        if(number.length() != 20){throw new InvalidAccountNumberException("Error number");}
        if(!number.matches(regex)){throw new InvalidAccountNumberException("Is found bukves");}

        for(Client client: clients){
            if(client.get(number) != null){
                Account removeAccount = client.get(number);
                client.remove(number);
                return removeAccount;
            }
        }
        throw new NoSuchElementException();
    }

    public Account setAccount(Account account,String number) throws InvalidAccountNumberException {
        String regex = "\\d+";

        if(account == null || number == null) throw new NullPointerException();
        if(number.length() != 20){throw new InvalidAccountNumberException("Error number");}
        if(!number.matches(regex)){throw new InvalidAccountNumberException("Is found bukves");}

        for(Client client: clients){
            if(client.get(number) != null){
                if(client.hasAccountWithNumber(account.getNumber())) throw new InvalidAccountNumberException("Invalid number");
                return client.set(account,client.indexOf(number));
            }
        }
        return null;
    }

    public Client[] getDebtors(){
        Client[] returnClients = new Client[0];
        Client[] copy;

        for(Client client: clients){
            if(client.getCreditAccounts().length > 0){
                copy = new Client[returnClients.length + 1];
                System.arraycopy(returnClients, 0, copy, 0, returnClients.length);
                copy[returnClients.length] = client;
                returnClients = copy;
            }
        }
        return returnClients;
    }

    public Client[] getWickedDebtors(){
        Client[] returnClients = new Client[0];
        Client[] copy;

        for(Client client: clients){
            if(client.getCreditAccounts().length > 0 && client.getStatus(client.getCreditScores()) == ClientStatus.BAD){
                copy = new Client[returnClients.length + 1];
                System.arraycopy(returnClients, 0, copy, 0, returnClients.length);
                copy[returnClients.length] = client;
                returnClients = copy;
            }
        }
        return returnClients;
    }

    @Override
    public String toString() {
        StringBuilder returnClients = new StringBuilder();
        for(Client client: clients){
            returnClients.append(client.toString()).append("\n");
        }
        return returnClients.toString();
    }

    public boolean remove(Client client){
        if(client == null) throw new NullPointerException();

        Client[] clients = getClients();
        int ind = 0;
        for(Client acc: clients){
            if(acc.equals(client)){
                remove(ind);
                return true;
            }
            ind++;
        }
        return false;
    }

    public int indexOf(Client client){
        if(client == null) throw new NullPointerException();

        Client[] clients = getClients();
        int ind = 0;
        for(Client acc: clients){
            if(acc.equals(client)){
                return ind;
            }
            ind++;
        }
        return 0;
    }

    public Iterator<Client> iterator(){
        return new AccountIterator();
    }

    private class AccountIterator implements Iterator<Client> {

        int index = 0;

        public boolean hasNext() {
            return index < clients.length;
        }


        public Client next() {
            return clients[index++];
        }
    }
}

