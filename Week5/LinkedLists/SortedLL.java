package Week5.LinkedLists;

import java.util.Scanner;

class SortedLL {

    private class Node {

        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }

    }

    private Node head; // head points to the sentinel node
    private Node z; // z is the tail sentinel node

    public SortedLL() {
        z = new Node(Integer.MAX_VALUE); // Sentinel node with maximum value
        head = new Node(Integer.MIN_VALUE); // Head pointing to a sentinel node with minimum value
        head.next = z;
    }

    public void insert(int x) {
        Node prev = head;
        Node curr = head.next;
        while (curr.data < x) {
            prev = curr;
            curr = curr.next;
        }
        Node newNode = new Node(x);
        newNode.next = curr;
        prev.next = newNode;
    }

    public void display() {
        Node t = head.next; // Skip the sentinel node while displaying
        System.out.print("\nHead -> ");
        while (t != z) {
            System.out.print(t.data + " -> ");
            t = t.next;
        }
        System.out.println("Z\n");
    }

    public static void main(String args[]) {
        SortedLL list = new SortedLL();
        list.display();

        double x;
        int r;

        Scanner in = new Scanner(System.in);

        for (int i = 1; i <= 5; ++i) {
            x = (Math.random() * 100.0);
            r = (int) x;
            System.out.println("Inserting " + r);
            list.insert(r);
            list.display();
        }

        while (!list.isEmpty()) {
            System.out.println("\nInput a value to remove: ");
            r = in.nextInt();
            if (list.remove(r)) {
                System.out.println("\nSuccessfully removed: " + r);
                list.display();
            } else {
                System.out.println("\nValue not in list");
            }
        }

    }

    public boolean remove(int x) {
        Node prev = head;
        Node curr = head.next;

        while (curr != z && curr.data != x) {
            prev = curr;
            curr = curr.next;
        }

        if (curr != z) {
            prev.next = curr.next;
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmpty() {
        return head.next == z;
    }
}
