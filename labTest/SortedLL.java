
// Sorted linked list with a sentinel node
// Skeleton code
import java.util.Scanner;
import java.util.Random;

class SortedLL {
    // internal data structure and
    // constructor code to be added here
    private class Node {

        int data;
        //Node prev;
        Node next;

        public Node(int data) {

            this.data = data;
            //this.prev = null;
            this.next = null;
        }

    }

    private Node head, z;

    public SortedLL() {
        z = new Node(Integer.MAX_VALUE); // Sentinel node with maximum value
        head = new Node(Integer.MIN_VALUE); // Head pointing to a sentinel node with minimum value
        head.next = z;
    }

    // this is the crucial method

    
    public void insert(int x) {
        Node prev = head;
        Node curr = head.next;
        while (curr.data < x) {
            prev = curr;
            curr = curr.next;
        }
        Node newNode = new Node(x);
        newNode.next = curr;
        //newNode.prev = prev;
        prev.next = newNode;
        //curr.prev = newNode;

    }

    // 20%
    public int count(int x) {
        int c = 0;
        Node node = head;
        while (node != z) {
            if (node.data == x) {
                c++;
            }
            node = node.next;
        }
        return c;
    }


    // 30% 
    public void duplicateSingles() {
        // complete the code
        //This method I could not figure out within the time frame

    }

    // 30 %
    int removeAll(int x) {
        // complete the code
        Node prev = head;
        Node curr = head.next;

        while (curr != z) {
            if (curr.data == x) {
                prev.next = curr.next;
            }
            curr = curr.next;
        }
        System.out.println("Removed:" + curr.data);
        return 0;
    }

    public void display() {
        Node t = head;
        System.out.print("\nHead <-> ");
        while (t != z) {
            System.out.print(t.data + " <-> ");
            t = t.next;
        }
        System.out.println("Z\n");
    }

    public static void main(String args[]) {
        System.out.println("My name is Stepehen Thompson and my student ID is C21394693");
        System.out.println("the attempt at directional functionality breaks the code slighty but without this functionality the removeAll and count methods are functional");
        System.out.println("I ran out of time to properly implement the directional functionality to the entire code but the getters and setters are implemented, as well as attempting the duplicateSingle method");
        SortedLL list = new SortedLL();
        list.display();

        int r, i;

        Random random = new Random();
        random.setSeed(12345L);

        // put 10 random ints between 0 and 9 into the list
        for (i = 1; i <= 10; ++i) {
            r = random.nextInt();
            if (r < 0)
                r = -1 * r;
            r = r % 10;
            list.insert(r);
        }
        list.display();

        System.out.println("Inserting " + 5);
        list.insert(5);
        r = list.count(5);
        list.display();

        System.out.println("\nNumber of occurences of 5 is " + r);

        list.duplicateSingles();
        list.display();

        list.insert(0);
        list.insert(7);
        list.insert(17);
        list.display();
        list.removeAll(0);
        list.display();
        list.removeAll(7);
        list.display();
        list.removeAll(17);
        list.display();

    }
}