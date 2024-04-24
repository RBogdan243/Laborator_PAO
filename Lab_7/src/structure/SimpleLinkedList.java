package structure;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SimpleLinkedList<T> {
    Node<T> head;

    public SimpleLinkedList() {
        this.head = null;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public void apply(Function<T, T> function) {
        Node<T> temp = head;
        while (temp != null) {
            temp.data = function.apply(temp.data);
            temp = temp.next;
        }
    }

    public void display(Predicate<T> predicate, Consumer<T> action) {
        Node<T> temp = head;
        while (temp != null) {
            if (predicate.test(temp.data)) {
                action.accept(temp.data);
            }
            temp = temp.next;
        }
    }
}
