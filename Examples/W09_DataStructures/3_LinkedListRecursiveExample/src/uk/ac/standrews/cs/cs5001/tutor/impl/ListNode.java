package uk.ac.standrews.cs.cs5001.tutor.impl;

public class ListNode<T extends Comparable<T>> {

    private T element;
    private ListNode<T> next = null;

    public ListNode(T element) {
        this.element = element;
    }

    public ListNode(T element, ListNode<T> next) {
        this.element = element;
        this.next = next;
    }

    public ListNode<T> getNext() {
        return next;
    }

    public T getElement() {
        return element;
    }

    public void setNext(ListNode<T> next) {
        this.next = next;
    }

    public boolean contains(T element) {
        return this.element == element;
    }
}
