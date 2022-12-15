package uk.ac.standrews.cs.cs5001.tutor.impl;

public class BinaryNode<T> {
    public T element;
    public BinaryNode<T> left;
    public BinaryNode<T> right;

    public BinaryNode(T element) {
        this.element = element;
    }
}
