package uk.ac.standrews.cs.cs5001.tutor;

import uk.ac.standrews.cs.cs5001.tutor.impl.*;
import uk.ac.standrews.cs.cs5001.tutor.interfaces.*;

public class Test {

    public static void main(String[] args) {
        /** Define the behaviour of visitors using anonymous classes & lambda expressions * */
        // instance of anonymous class
        Visitor<Person> v1 =
                new Visitor<Person>() {
                    public void visit(Person p) {
                        System.out.println(p);
                    }
                };

        // Lambda expression, including optional type of formal parameter,
        // parentheses and curly braces
        Visitor<Person> v2 =
                (Person p) -> {
                    System.out.println(p);
                };

        // Lambda expression excluding optional elements
        Visitor<Person> v3 = p -> System.out.println(p);

        // add some people to a list and print it
        LinkedList<Person> bobList = new LinkedList<Person>();
        for (int i = 5; i > 0; i--) {
            bobList.insertAfter(new Person("Bob " + i, i * 10), null);
        }
        System.out.println("Traverse list of Bobs:");
        bobList.traverseList(v1);

        // add some people to another list
        LinkedList<Person> sueList = new LinkedList<Person>();
        for (int i = 5; i > 0; i--) {
            sueList.insertAfter(new Person("Sue " + i, i * 10), null);
        }
        System.out.println("\nTraverse list of Sues:");
        sueList.traverseList(v2);

        // try to insert an element in bob list after a node from sue list
        Node<Person> sue2 = sueList.getNode(1);
        System.out.println("\nTrying to add 'Bobby' to list of Bobs, after 'Sue 2'....");
        bobList.insertAfter(new Person("Bobby", 15), sue2);
        System.out.println("Traverse list of Bobs:");
        bobList.traverseList(v3);
        System.out.println("Traverse list of Sues:");
        sueList.traverseList(v3);
    }
}
