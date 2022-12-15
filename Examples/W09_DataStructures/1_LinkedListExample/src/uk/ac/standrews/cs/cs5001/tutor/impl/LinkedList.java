package uk.ac.standrews.cs.cs5001.tutor.impl;

import uk.ac.standrews.cs.cs5001.tutor.interfaces.*;

public class LinkedList<T extends Comparable<T>> {

    private Node<T> root;

    // Traverses the list
    public void traverseList(Visitor<T> visitor) {
        for (Node<T> node = root; node != null; node = node.next) {
            // 'visit' the node
            visitor.visit(node.element);
        }
    }

    // Finds a node containing an element in the list
    public Node<T> findNode(T element) {
        Node<T> node = root;
        while (node != null && !node.contains(element)) {
            node = node.next;
        }
        return node;
    }

    // Gets a node at the given index
    public Node<T> getNode(int index) {
        if (index < 0) {
            return null;
        }

        Node<T> node = root;
        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }
        return node;
    }

    // Inserts an element after a node
    public void insertAfter(T element, Node<T> predecessor) {
        Node<T> new_node = new Node<T>(element);

        if (predecessor == null) {
            new_node.next = root;
            root = new_node;
        } else {
            new_node.next = predecessor.next;
            predecessor.next = new_node;
        }
    }

    // Removes a node from the list
    public void remove(Node<T> node) {
        if (node == null) {
            return;
        } else if (root == node) {
            root = node.next;
        } else {
            Node<T> predecessor = root;
            while (predecessor.next != node) {
                predecessor = predecessor.next;
                if (predecessor == null) {
                    return;
                }
            }
            predecessor.next = node.next;
        }
    }
}
