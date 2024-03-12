package Stacks;
// Array implementation of ADT Stack

 class StackException extends Exception { 
    public StackException(String s) {
        super(s);
    }
}

class Stack {

  private int a[];
  private int top, s_max;
  
  public Stack(){
    top = 0;
	a = new int[4];
	s_max = 4;
  }
  
  public int pop() throws StackException {  
    if(top==0) 
        throw new StackException("\nCARELESS coding: cannot pop empty stack\n");
    int x = a[--top];
	return x;
  }
  
  public void push(int x) throws StackException {
    if(top == s_max) 
	    throw new StackException("\nStack array already full\n");
		 
    a[top++] = x;  
  } 
  
  public void display() {
    int t = 0;
    System.out.println("\nStack contents are:  ");
        
    while (t != top) {            
        System.out.print(a[t] + " ");
        ++t;
    }       
    System.out.println("\n");
  }

}

public class StackTest2
{
    public static void main(String[] arg) //throws Exception 
    {
        Stack s = new Stack();
        System.out.println("Stack is created\n");
        
        // piece of code to test our exception mechanism     
        try {
            s.pop();
        } catch (StackException e){
            System.out.println("Exception thrown: " + e);
        }
        
        try {
            s.push(10); s.push(3); s.push(11); s.push(7);
        } catch( StackException e) {
            System.out.println("Exception thrown: " + e);
        }
        s.display();
		
        try{
            s.push(2);
        } catch(StackException e) {
            System.out.println("Exception thrown: " + e);
        }
        
        s.display();
       
  
    }
}


