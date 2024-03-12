public class Queue {

    private class Node {
        int data;
        Node next;
    }

    Node z, head, tail;

    public Queue() {
        z = new Node();
        z.next = z;
        head = z;
        tail = null;
    }

    public void enQueue(int x) {
        Node t;
        t = new Node();
        t.data = x;
        t.next = z;
        if (head == z) // case of empty list
            head = t;
        else // case of list not empty
            tail.next = t;
        tail = t; // new node is now at the tail
    }

    public static void main(String[] arg) // throws Exception
    {
        Queue q = new Queue();
        System.out.println("Queue is created\n");

        // piece of code to test our queue
        q.enQueue(6);
        q.enQueue(9);
        q.enQueue(12);

        q.display();
    }

    public void display() {
        Node t = head;
        System.out.println("\nQueue contents are:  ");

        while (t != z) {
            System.out.print(t.data + " ");
            t = t.next;
        }
        System.out.println("\n");
    }
}
