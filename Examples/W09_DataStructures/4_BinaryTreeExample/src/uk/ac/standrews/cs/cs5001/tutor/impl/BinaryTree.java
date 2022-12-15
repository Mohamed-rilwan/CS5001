package uk.ac.standrews.cs.cs5001.tutor.impl;

import java.util.ArrayList;

import uk.ac.standrews.cs.cs5001.tutor.interfaces.Visitor;

public class BinaryTree<T> {
    private BinaryNode<T> root = null;

    // pre-order traversal
    public void preOrderTraverse(Visitor<T> visitor) {
        preOrderTraverse(visitor, root);
    }

    private void preOrderTraverse(Visitor<T> visitor, BinaryNode<T> node) {
        visitor.visit(node.element);
        if (node.left != null)
            preOrderTraverse(visitor, node.left);
        if (node.right != null)
            preOrderTraverse(visitor, node.right);
    }

    // post-order traversal
    public void postOrderTraverse(Visitor<T> visitor) {
        postOrderTraverse(visitor, root);
    }

    private void postOrderTraverse(Visitor<T> visitor, BinaryNode<T> node) {
        if (node.left != null)
            postOrderTraverse(visitor, node.left);
        if (node.right != null)
            postOrderTraverse(visitor, node.right);
        visitor.visit(node.element);
    }

    // in-order traversal
    public void inOrderTraverse(Visitor<T> visitor) {
        inOrderTraverse(visitor, root);
    }

    private void inOrderTraverse(Visitor<T> visitor, BinaryNode<T> node) {
        if (node.left != null)
            inOrderTraverse(visitor, node.left);
        visitor.visit(node.element);
        if (node.right != null)
            inOrderTraverse(visitor, node.right);
    }

    // breadth-first traversal
    public void breadthFirstTraverse(Visitor<T> visitor) {
        ArrayList<BinaryNode<T>> queue = new ArrayList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BinaryNode<T> node = queue.remove(0);
            visitor.visit(node.element);
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }
    }

    // calculate the size of the tree
    public int size() {
        return size(root);
    }

    private int size(BinaryNode<T> subtree) {
        if (subtree == null)
            return 0;
        else
            return 1 + size(subtree.left) + size(subtree.right);
    }

    // find a node containing an element
    public BinaryNode<T> findNode(T element) {
        return findNode(element, root);
    }

    private BinaryNode<T> findNode(T element, BinaryNode<T> subtree) {
        if (subtree == null)
            return null;
        if (subtree.element.equals(element))
            return subtree;

        BinaryNode<T> find_in_left = findNode(element, subtree.left);
        if (find_in_left != null)
            return find_in_left;

        return findNode(element, subtree.right);
    }

    // find the parent node for an element
    public BinaryNode<T> findParent(T element) {
        return findParent(element, root);
    }

    private BinaryNode<T> findParent(T element, BinaryNode<T> subtree) {
        if (subtree == null || subtree.element.equals(element))
            return null;
        if (subtree.left != null && subtree.left.element.equals(element))
            return subtree;
        if (subtree.right != null && subtree.right.element.equals(element))
            return subtree;

        BinaryNode<T> find_in_left = findParent(element, subtree.left);
        if (find_in_left != null)
            return find_in_left;

        return findParent(element, subtree.right);
    }

    // add an element to the tree
    public void add(T element) {
        BinaryNode<T> node = new BinaryNode<>(element);
        if (root == null) {
            root = node;
        } else {
            add(node, root);
        }
    }

    private void add(BinaryNode<T> node, BinaryNode<T> parent) {
        if (node != null) {
            if (parent.left == null) {
                parent.left = node;
            } else if (parent.right == null) {
                parent.right = node;
            } else if (size(parent.left) <= size(parent.right)) {
                add(node, parent.left);
            } else {
                add(node, parent.right);
            }
        }
    }

    // add an element as the left child of a node containing an element
    public void addLeftChild(T parent, T child) {
        BinaryNode<T> parent_node = findNode(parent);
        if (parent_node != null) {
            BinaryNode<T> child_node = new BinaryNode<>(child);
            child_node.left = parent_node.left;
            parent_node.left = child_node;
        }
    }

    // add an element as the right child of a node containing an element
    public void addRightChild(T parent, T child) {
        BinaryNode<T> parent_node = findNode(parent);
        if (parent_node != null) {
            BinaryNode<T> child_node = new BinaryNode<>(child);
            child_node.right = parent_node.right;
            parent_node.right = child_node;
        }
    }

    // remove a node containing an element
    public void remove(T element) {
        BinaryNode<T> parent = findParent(element);
        BinaryNode<T> node = findNode(element);
        if (node != null) {
            if (parent == null) {
                root = node.left;
                add(node.right, root);
            } else {
                if (parent.left == node) {
                    parent.left = node.left;
                } else {
                    parent.right = node.left;
                }
                add(node.right, parent);
            }
        }
    }

}
