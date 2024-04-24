import data.Person;
import structure.SimpleLinkedList;

import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        SimpleLinkedList<Person> list = new SimpleLinkedList<>();
        list.add(new Person("Becali", 30));
        list.add(new Person("Iohanes", 25));
        list.add(new Person("Mutu", 35));

        Predicate<Person> agePredicate = person -> person.getAge() >= 30;
        System.out.println("Înainte de adunare:");
        list.display(agePredicate, System.out::println);
        list.apply(person -> new Person(person.getName(), person.getAge() + 5));
        System.out.println("După de adunare:");
        list.display(agePredicate, System.out::println);
    }
}