package StacksGeeks.StacksLL;

import static java.lang.System.exit;
import static java.lang.System.lineSeparator;

public class Stack {
    public static void main(String[] args) {
        
        StackUsingLinkedlist obj = new StackUsingLinkedlist();

        obj.push(10);
        obj.push(20);
        obj.push(30);
        obj.push(40);

        obj.display();
        
        System.out.println("Top Element: " + obj.peek());

        obj.pop();
        obj.pop();

        obj.display();

        System.out.println("Top element:" + obj.peek());
    }
}

class StackUsingLinkedlist{

    private class Node{
        int data;
        Node link;
    }

    Node top;
    StackUsingLinkedlist() {this.top = null;}

    public void push(int x){
        
        Node temp = new Node();


        temp.data = x;

        temp.link = top;

        top = temp;

    }

    public boolean isEmpty(){
        return top == null;
    }

    public int peek(){
        if(!isEmpty()){
            return top.data;


        }
        else{
            return -1;
        }
        
    }

    public void pop(){

        top = (top).link;

    }  

    public void display(){
        if(top == null){
            exit(1);
        }
        else{
            Node temp = top;

            while(temp != null){
                System.out.println(temp.data);

                temp = temp.link;
                if(temp != null){
                    System.out.println(" -> ");
                }
            }
        }
    }




}
