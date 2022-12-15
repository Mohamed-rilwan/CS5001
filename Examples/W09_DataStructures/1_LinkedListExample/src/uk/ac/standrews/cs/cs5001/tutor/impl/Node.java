package uk.ac.standrews.cs.cs5001.tutor.impl;

public class Node<T extends Comparable<T>> {

    public T element;
    public Node<T> next = null;

    public Node(T element) {
        this.element = element;
    }

    public boolean contains(T element) {
        return this.element == element;
    }
}
