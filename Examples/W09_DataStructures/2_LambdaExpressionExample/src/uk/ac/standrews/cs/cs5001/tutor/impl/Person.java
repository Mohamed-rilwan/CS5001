package uk.ac.standrews.cs.cs5001.tutor.impl;

public class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }

    @Override
    public int compareTo(Person p) {
        if (p.age > this.age) {
            return -1;
        } else if (p.age < this.age) {
            return 1;
        } else {
            return p.name.compareTo(this.name);
        }
    }
}
