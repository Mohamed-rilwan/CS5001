package uk.ac.standrews.cs.cs5001.tutor.impl;

import uk.ac.standrews.cs.cs5001.tutor.interfaces.LinkedList;
import uk.ac.standrews.cs.cs5001.tutor.interfaces.Visitor;

public class IterativeLinkedList<T extends Comparable<T>> implements LinkedList<T> {

    private ListNode<T> root;
    private int currentSize;

    // Inserts an element after a node
    public void insertAfter(T element, ListNode<T> predecessor) {
        ListNode<T> new_node = new ListNode<T>(element);

        if (predecessor == null) {
            new_node.setNext(root);
            root = new_node;
        } else {
            new_node.setNext(predecessor.getNext());
            predecessor.setNext(new_node);
        }
        currentSize++;
    }

    // Removes a node from the list
    public void remove(ListNode<T> node) {
        if (node == null) {
            return;
        } else if (root == node) {
            root = node.getNext();
        } else {
            ListNode<T> predecessor = root;
            while (predecessor.getNext() != node) {
                predecessor = predecessor.getNext();
                if (predecessor == null) {
                    return;
                }
            }
            predecessor.setNext(node.getNext());
        }
        currentSize--;
    }

    // Finds an element in the list
    public ListNode<T> findNode(T element) {
        ListNode<T> node = root;
        while (node != null && !node.contains(element)) {
            node = node.getNext();
        }
        return node;
    }

    // Gets a node at the given index
    public ListNode<T> getNode(int index) {
        if (index < 0 || index >= currentSize) {
            throw new IllegalArgumentException();
        }
        ListNode<T> node = root;

        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node;
    }

    // Traverses the list
    public void traverseList(Visitor<T> visitor) {
        for (ListNode<T> node = root; node != null; node = node.getNext()) {
            // 'visit' the node
            visitor.visit(node.getElement());
        }
    }

    // Traverses the list from end to start
    public void reverseTraverseList(Visitor<T> visitor) {
        for (int i = currentSize - 1; i >= 0; i--) {
            // 'visit' the node
            visitor.visit(getNode(i).getElement());
        }
    }
}
