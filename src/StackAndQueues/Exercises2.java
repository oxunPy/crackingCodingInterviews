package StackAndQueues;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedList;

public class Exercises2 {
    public class SetOfStacks{
        public ArrayList<Stack> stacks = new ArrayList<Stack>();
        public int capacity;

        public SetOfStacks(int capacity){
            this.capacity = capacity;
        }

        public Stack getLastStack(){
            if(stacks.size() == 0) return null;
            Stack last = stacks.get(stacks.size() - 1);
            return last;
        }
        public void push(int value){
            Stack last = getLastStack();
            if(last != null && !last.isFull()){
                last.push(value);               // adding to the last stack.
            }
            else{           // add to new stack.
                Stack stack = new Stack(capacity);
                stack.push(value);
                stacks.add(stack);
            }
        }

        public int pop(){           // popping from last stack of stacks.
            Stack last = getLastStack();

            if(last == null){
                throw new EmptyStackException();
            }
            int v = last.pop();
            last.size--;
            if(last.size == 0){
                stacks.remove(stacks.size() - 1);
            }
            return v;
        }

        public int popAt(int index){
            return leftShift(index, true);
        }

        private int leftShift(int index, boolean removeTop){
            Stack stack = stacks.get(index);
            int removed_item;

            if(removeTop) removed_item = stack.pop();
            else removed_item = stack.removeBottom();

            if(stack.isEmpty()){
                stacks.remove(index);
            }
            else if(stacks.size() > index + 1){
                int v = leftShift(index + 1, false);
                stack.push(v);
            }
            return removed_item;
        }
    }

    class Stack{

        private int capacity;
        public Node top, bottom;
        public int size;

        public Stack(int capacity){
            this.capacity = capacity;
        }

        public boolean isFull(){
            return capacity == size;
        }
        public boolean push(int value){
            if(size >= capacity){
                return false;
            }
            size++;
            Node n = new Node(value);
            if(size == 1){
                bottom = n;
            }
            join(n, top);
            top = n;
            return true;
        }
        public void join(Node above, Node below){
            if(above != null) above.below = below;
            if(below != null) below.above = above;
        }
        public int pop(){
            Node t = top;
            top = top.below;
            size--;
            return t.data;
        }
        public boolean isEmpty(){
            return size == 0;
        }
        public int removeBottom(){
            Node b = bottom;
            bottom = bottom.above;
            if(bottom != null) bottom.below = null;
            size--;
            return b.data;
        }


    }


    class Node{
        int data;
        public Node above;
        public Node below;

        public Node(int data){
            this.data = data;
        }
        public Node(int data, Node above, Node below){
            this.data = data;
            this.above = above;
            this.below = below;
        }

    }

    public class MyQueue<T>{
        java.util.Stack<T> stackOldest, stackNewest;

        public int size(){
            return stackNewest.size() + stackOldest.size();
        }

        public void add(T val){
            stackNewest.add(val);
        }

        private void shiftStacks(){
            if(stackOldest.isEmpty()){
                while(!stackNewest.isEmpty()){
                    stackOldest.push(stackNewest.pop());
                }
            }
        }
        public T peek(){
            shiftStacks();          // ensure that stackOldest has current elements.
            return stackOldest.peek();
        }
        public T pop(){
            shiftStacks();
            return stackOldest.pop();
        }
    }

    public void sort(java.util.Stack<Integer> s){
        java.util.Stack<Integer> r = new java.util.Stack<Integer>();

        while(!s.isEmpty()){
            int temp = s.pop();

            while(!r.isEmpty() && r.peek() > temp){
                s.push(r.pop());
            }
            r.push(temp);
        }
        // copy the elements from back into s.
        while(!r.isEmpty()){
            s.push(r.peek());
        }
    }


    abstract class Animal{
        private int order;
        protected String name;
        public Animal(String n){
            name = n;
        }
        public void setOrder(int order){
            this.order = order;
        }
        public int getOrder(){
            return order;
        }

        public boolean isOlderThan(Animal a){
            return this.order < a.order;
        }
    }

    class AnimalQueue{
        LinkedList<Dog> dogs = new LinkedList<>();
        LinkedList<Cat> cats = new LinkedList<>();
        public int order = 0;

        public void enqueue(Animal a){
            a.setOrder(order);
            order++;

            if(a instanceof Dog){
            dogs.addLast((Dog) a);
            }
            else{
                cats.addLast((Cat) a);
            }
        }

        public Animal dequeueAny(){
            if(cats.isEmpty()){
                return dequeueDog();
            }
            else if(dogs.isEmpty()){
                return dequeueCat();
            }

            Dog dog = dogs.peek();
            Cat cat = cats.peek();

            if(dog.isOlderThan(cat)){
                return dequeueDog();
            }
            else{
                return dequeueCat();
            }
        }

        public Animal dequeueCat(){
            Animal cat = cats.poll();
            return cat;
        }
        public Animal dequeueDog(){
            Animal dog = dogs.poll();
            return dog;
        }
    }


    class Dog extends Animal{
        public Dog(String n){
            super(n);
        }
    }
    class Cat extends Animal{
        public Cat(String n){
            super(n);
        }
    }



























}