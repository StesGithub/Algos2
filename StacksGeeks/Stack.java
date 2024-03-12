package StacksGeeks;

public class Stack {
    static final int MAX = 1000;
    int top;
    int a[] = new int[MAX];

    boolean isEmpty() {
        return (top < 0);
    }

    Stack() {
        top = -1;
    }

    boolean push(int x) {
        if (top >= (MAX - 1)) {
            System.out.println("Stack Overflow");
            return false;
        } else {
            a[++top] = x;
            System.out.println("pushed " + x);
            return true;
        }
    }

    int pop() {
        if (top < 0) {
            System.out.println("Stack underflow");
            return 0;

        } else {
            int x = a[top--];
            System.out.println("removing: " + x);
            return x;
        }
    }

    void emptyStack(){
        while(top != -1){
            pop();
        }
    }

    int peek() {
        if (top < 0) {
            System.out.println("Stack Underflow");
            return 0;
        } else {
            int x = a[top];
            return x;
        }
    }

    void print() {
        for (int i = top; i > -1; i--) {
            System.out.print(" " + a[i]);
        }
        System.out.println("printed Stack");

    }

    public static void main(String[] args) {

        Stack s = new Stack();
        s.push(10);
        s.push(20);
        s.push(30);
        s.print();
        System.out.println("\nPopped: " + s.pop());
        s.push(50);
        s.print();
        System.out.println("\ntop element: " + s.peek());
        s.emptyStack();
        s.print();

    }
}
