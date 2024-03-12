package Week3.QueueTest;

class QueueException extends Exception {
    // You can customize this exception, for example, add a message or additional details.
    public QueueException(String message) {
        super(message);
    }
}

interface Queue {
    void enQueue(int x) throws QueueException;
    int deQueue() throws QueueException;
    boolean isEmpty();
}

class QueueLL implements Queue {
    private class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front, rear;

    public QueueLL() {
        front = rear = null;
    }

    public void enQueue(int x) {
        Node newNode = new Node(x);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    public int deQueue() throws QueueException {
        if (isEmpty()) {
            throw new QueueException("Queue underflow: Cannot dequeue from an empty queue");
        }

        int data = front.data;
        front = front.next;

        if (front == null) {
            rear = null; // If the queue becomes empty after dequeue
        }

        return data;
    }

    public boolean isEmpty() {
        return front == null;
    }
}

class QueueCB implements Queue {
    private int q[], back, front;
    private int qmax, size;

    public QueueCB() {
        qmax = 4;
        size = front = back = 0;
        q = new int[qmax];
    }

    public void enQueue(int x) throws QueueException {
        if (size == qmax) {
            throw new QueueException("Queue overflow: Cannot enqueue, the queue is full");
        }

        q[back] = x;
        back = (back + 1) % qmax;
        size++;
    }

    public int deQueue() throws QueueException {
        if (isEmpty()) {
            throw new QueueException("Queue underflow: Cannot dequeue from an empty queue");
        }

        int data = q[front];
        front = (front + 1) % qmax;
        size--;

        return data;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

class QueueTest2 {
    public static void main(String[] arg) {
        Queue q1, q2;
        q1 = new QueueLL();
        q2 = new QueueCB();

        try {
            for (int i = 1; i < 6; ++i) {
                q1.enQueue(i);
            }

            while (!q1.isEmpty()) {
                System.out.println("Dequeued from q1: " + q1.deQueue());
            }

            // Uncomment the line below to test underflow exception
            // System.out.println("Dequeued from q1: " + q1.deQueue());
        } catch (QueueException e) {
            System.out.println("Exception thrown: " + e);
        }

        try {
            for (int i = 1; i < 6; ++i) {
                q2.enQueue(i);
            }

            while (!q2.isEmpty()) {
                System.out.println("Dequeued from q2: " + q2.deQueue());
            }

            // Uncomment the line below to test underflow exception
            // System.out.println("Dequeued from q2: " + q2.deQueue());
        } catch (QueueException e) {
            System.out.println("Exception thrown: " + e);
        }
    }
}
