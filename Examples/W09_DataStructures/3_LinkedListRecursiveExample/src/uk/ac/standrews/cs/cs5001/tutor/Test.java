package uk.ac.standrews.cs.cs5001.tutor;

import uk.ac.standrews.cs.cs5001.tutor.impl.*;
import uk.ac.standrews.cs.cs5001.tutor.interfaces.*;

public class Test {

    public static void main(String[] args) {
        // Create the list - either Iterative or Recursive should have same result
        LinkedList<Person> personList = new IterativeLinkedList<Person>();
        // LinkedList<Person> personList = new RecursiveLinkedList<Person>();

        // Create some people, add them to the list and print them out
        ListNode<Person> prev = null;
        for (int i = 1; i < 6; i++) {
            personList.insertAfter(new Person("Person " + i, i * 10), prev);
            prev = personList.getNode(i - 1);
        }
        System.out.println("Traverse the list:");
        personList.traverseList(p -> System.out.println(p));
        System.out.println("\nTraverse the list in reverse order:");
        personList.reverseTraverseList(p -> System.out.println(p));

        personList.remove(personList.getNode(2));

        System.out.println("\nTraverse the list:");
        personList.traverseList(p -> System.out.println(p));
        System.out.println("\nTraverse the list in reverse order:");
        personList.reverseTraverseList(p -> System.out.println(p));
    }
}
