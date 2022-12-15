package uk.ac.standrews.cs.cs5001.tutor.interfaces;

import uk.ac.standrews.cs.cs5001.tutor.impl.ListNode;

public interface LinkedList<T extends Comparable<T>> {

    // Inserts an element after a node
    public void insertAfter(T element, ListNode<T> predecessor);

    // Removes a node from the list
    public void remove(ListNode<T> node);

    // Finds an element in the list
    public ListNode<T> findNode(T element);

    // Gets a node at the given index
    public ListNode<T> getNode(int index);

    // Traverses the list
    public void traverseList(Visitor<T> visitor);

    // Traverses the list from end to start
    public void reverseTraverseList(Visitor<T> visitor);
}
