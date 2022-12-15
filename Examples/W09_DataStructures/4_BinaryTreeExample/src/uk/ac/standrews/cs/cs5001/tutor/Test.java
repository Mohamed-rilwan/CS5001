package uk.ac.standrews.cs.cs5001.tutor;

import uk.ac.standrews.cs.cs5001.tutor.impl.BinaryTree;

public class Test {

    public static void main(String[] args) {
        // build our binary tree
        BinaryTree<String> bt = new BinaryTree<>();
        bt.add("A");
        bt.addLeftChild("A", "B");
        bt.addRightChild("A", "C");
        bt.addLeftChild("B", "D");
        bt.addRightChild("B", "E");
        bt.addLeftChild("C", "F");
        bt.addRightChild("C", "G");
        bt.addLeftChild("D", "H");
        bt.addLeftChild("E", "I");
        bt.addRightChild("E", "J");
        bt.addRightChild("F", "K");
        bt.addRightChild("G", "L");

        // traverse the tree using variety of strategies
        System.out.println("Pre-Order:");
        bt.preOrderTraverse(s -> System.out.print(s));
        System.out.println("\nPost-Order:");
        bt.postOrderTraverse(s -> System.out.print(s));
        System.out.println("\nIn-Order:");
        bt.inOrderTraverse(s -> System.out.print(s));
        System.out.println("\nBreadth First:");
        bt.breadthFirstTraverse(s -> System.out.print(s));

        // remove and element
        System.out.println("\n\nRemove node containing B");
        bt.remove("B");
        // traverse the modified tree using variety of strategies
        System.out.println("Pre-Order:");
        bt.preOrderTraverse(s -> System.out.print(s));
        System.out.println("\nPost-Order:");
        bt.postOrderTraverse(s -> System.out.print(s));
        System.out.println("\nIn-Order:");
        bt.inOrderTraverse(s -> System.out.print(s));
        System.out.println("\nBreadth First:");
        bt.breadthFirstTraverse(s -> System.out.print(s));
    }
}
