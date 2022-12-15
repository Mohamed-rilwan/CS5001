package uk.ac.standrews.cs.cs5001.tutor.impl;

import uk.ac.standrews.cs.cs5001.tutor.interfaces.LinkedList;
import uk.ac.standrews.cs.cs5001.tutor.interfaces.Visitor;

public class RecursiveLinkedList<T extends Comparable<T>> implements LinkedList<T> {

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

    // Removes a node from the list starting at head, and returns the new head
    private ListNode<T> remove(ListNode<T> node, ListNode<T> head) {
        if (node == null) {
            return head; // do nothing, return head
        } else if (node == head) {
            currentSize--;
            return head.getNext(); // cut head off and return tail
        } else {
            head.setNext(remove(node, head.getNext()));
            return head; // remove node from tail (recursive) and return head
        }
    }

    // Removes a node from the list
    public void remove(ListNode<T> node) {
        root = remove(node, root);
    }

    // Finds an element in the list starting at head
    private ListNode<T> findNode(T element, ListNode<T> head) {
        if (head == null) {
            return null; // empty list - element is not here
        } else if (head.contains(element)) {
            return head; // element is at head - return it!
        } else {
            return findNode(element, head.getNext()); // not in head - search in tail (recursive)
        }
    }

    // Finds an element in the list
    public ListNode<T> findNode(T element) {
        return findNode(element, root);
    }

    // Gets a node at the given index starting at head
    private ListNode<T> getNode(int index, ListNode<T> head) {
        if (index < 0 || head == null) {
            throw new IllegalArgumentException();
        } else if (index == 0) {
            return head;
        } else {
            return getNode(index - 1, head.getNext());
        }
    }

    // Gets a node at the given index
    public ListNode<T> getNode(int index) {
        return getNode(index, root);
    }

    // Visits all nodes in the list, starting at head, in given direction
    private void traverseList(Visitor<T> visitor, ListNode<T> head, boolean forward) {
        if (forward && head != null) {
            visitor.visit(head.getElement()); // visit head
            traverseList(visitor, head.getNext(), forward); // visit all nodes in tail
        } else if (!forward && head != null) {
            traverseList(visitor, head.getNext(), forward); // visit all nodes in tail
            visitor.visit(head.getElement()); // visit head
        }
    }

    // Visits all nodes in the list
    public void traverseList(Visitor<T> visitor) {
        traverseList(visitor, root, true);
    }

    // Visits all nodes in the list, from end to start
    public void reverseTraverseList(Visitor<T> visitor) {
        traverseList(visitor, root, false);
    }
}
