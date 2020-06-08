package po82.nazar.oop.model;

public class Node {
    private Node next = null;
    private Node prev = null;
    private Account value;

    Node() {
    }

    Node(Node next) {
        this.next = next;
    }

    Node(Account value) {
        this.value = value;
    }

    Node getNext() {
        return next;
    }

    void setNext(Node next) {
        this.next = next;
    }

    Account getValue() {
        return value;
    }

    void setValue(Account value) {
        this.value = value;
    }

    Node getPrev() {
        return prev;
    }

    void setPrev(Node prev) {
        this.prev = prev;
    }
}
