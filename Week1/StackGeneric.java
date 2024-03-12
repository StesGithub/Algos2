package Week1;

// StackGeneric.java
// Linked list implementation of a Generic Stack

class Stack<T> {
    
    class Node {
        T data;
        Node next;  
    }
    private Node top;
      
    public Stack()
    { 
        top = null;
    }
        
    public void push(T x) {
        Node  t = new Node();
        t.data = x;
        t.next = top;
        top = t;
    }

    // only to be called if list is non-empty.
    // Otherwise an exception should be thrown.
    public T pop(){
        T y = top.data;
		top = top.next;
		return y;
        
    } 

    
    public boolean isEmpty(){
       return top == null;
    }


    public void display() {
        Node t = top;
        System.out.println("\nStack contents are:  ");
        System.out.println(" top - ");
        while (t != null) {
            System.out.print(t.data + " ");
            t = t.next;
        }
        
        System.out.println("\n");
    }

}

class MyPet {
        public String name;
        public String type ;
        public int age;

        public MyPet(String s, String t, int a) {
            name = s;
            type = t;
            age = a;
        }
        
        public String toString() {
            return "\nI am " + name + " the " + type + ", my age is " + age;
        }
}

public class StackGeneric
{
    public static void main( String[] arg){
        Stack <Integer> s = new Stack <Integer>();
        
        System.out.println("Stack is created\n");
        
        s.push(10); s.push(3);
        s.push(11); s.push(7);
        s.push(23);
        
        s.display();
        
       
        Integer i = s.pop();
        System.out.println("Just popped " + i);
        s.display();
        
		
		Stack <MyPet> s2 = new Stack <MyPet>();
        System.out.println("Stack is created\n");
        
        s2.push(new MyPet("Fluyffy", "cat", 6)); 
        s2.push(new MyPet("Rex", "dog", 3)); 
        s2.push(new MyPet("Lola", "rat", 1)); 
        s2.push(new MyPet("Felix", "cat", 7));
        
        
        s2.display();
        
        MyPet p = s2.pop();
        System.out.println("\nJust popped " + p);
        s2.display(); 
		
    }
}
