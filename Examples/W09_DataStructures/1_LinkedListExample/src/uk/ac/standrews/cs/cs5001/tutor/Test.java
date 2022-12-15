package uk.ac.standrews.cs.cs5001.tutor;

import uk.ac.standrews.cs.cs5001.tutor.impl.*;
import uk.ac.standrews.cs.cs5001.tutor.interfaces.*;

public class Test {

    public static void main(String[] args) {
        // instance of anonymous class to define behaviour of visitor
        Visitor<Person> visitor =
                new Visitor<Person>() {
                    public void visit(Person p) {
                        System.out.println(p);
                    }
                };

        // add some people to a list and print it
        LinkedList<Person> bobList = new LinkedList<Person>();
        for (int i = 5; i > 0; i--) {
            bobList.insertAfter(new Person("Bob " + i, i * 10), null);
        }
        System.out.println("Traverse list of Bobs:");
        bobList.traverseList(visitor);

        // add some people to another list
        LinkedList<Person> sueList = new LinkedList<Person>();
        for (int i = 5; i > 0; i--) {
            sueList.insertAfter(new Person("Sue " + i, i * 10), null);
        }
        System.out.println("\nTraverse list of Sues:");
        sueList.traverseList(visitor);

        // try to insert an element in bob list after a node from sue list
        Node<Person> sue2 = sueList.getNode(1);
        System.out.println("\nTrying to add 'Bobby' to list of Bobs, after 'Sue 2'....");
        bobList.insertAfter(new Person("Bobby", 15), sue2);
        System.out.println("Traverse list of Bobs:");
        bobList.traverseList(visitor);
        System.out.println("Traverse list of Sues:");
        sueList.traverseList(visitor);

        // append the lists - only possible because we have direct access to nodes
        /* System.out.println("\nAppend list of Sues to list of Bobs");
        bobList.getNode(4).next = sueList.getNode(0);
        System.out.println("Traverse list of Bobs:");
        bobList.traverseList(visitor);
        System.out.println("Traverse list of Sues:");
        sueList.traverseList(visitor); */

        // make the list cyclical - MAKE SURE YOU PUT A BREAKPOINT HERE AND RUN IN DEBUG - otherwise
        // you will have an infinite loop!!!
        /* System.out.println("\nMake list of Sues cyclical");
        sueList.getNode(4).next = sue2;
        System.out.println("Traverse list of Sues:");
        sueList.traverseList(visitor); */
    }
}
