package StackAndQueues;

import java.util.*;

public class Exercises {

    public static class FixedStack{
        private int numberOfStacks = 3;
        private int stackCapacity;
        public int[] values;
        public int[] sizes;


        public FixedStack(int stackSize){
            values = new int[stackSize * numberOfStacks];
            sizes = new int[numberOfStacks];
            stackCapacity = stackSize;
        }

        // Push value onto stack.
        public void push(int stackNumber, int value) throws StackOverflowError{
            // check that we have a space for the next element.
            if(isFull(stackNumber)){
                throw new StackOverflowError();
            }
            // Increment stack pointer and then update top value.
            sizes[stackNumber]++;
            values[indexOfTop(stackNumber)] = value;
        }

        public int pop(int stackNumber){
            if(isEmpty(stackNumber)){
                throw new EmptyStackException();
            }
            int top = indexOfTop(stackNumber);
            int value = values[top];        // get top.
            sizes[stackNumber]--;       // Shrink.
            return value;
        }

        // Return top element.
        public int peek(int stackNumber){
            if(isEmpty(stackNumber)){
                throw new EmptyStackException();
            }
            int top = indexOfTop(stackNumber);
            return values[top];
        }

        public boolean isEmpty(int stackNumber){
            return sizes[stackNumber] == 0;
        }
        public boolean isFull(int stackNumber){
            return sizes[stackNumber] == stackCapacity;
        }

        // Return index of the top of the stack.
        private int indexOfTop(int stackNumber){
            int offset = stackNumber * stackCapacity;
            int size = sizes[stackNumber];
            return offset + size - 1;
        }
    }

    class NodeWithMin{
        public int value;
        public int min;

        public NodeWithMin(int value, int min){
            this.value = value;
            this.min = min;
        }
    }

    public class SetOfStacks{
        ArrayList<Stack> stacks = new ArrayList<Stack>();
        public int capacity;

        public SetOfStacks(int capacity){
            this.capacity = capacity;
        }

        public Stack getLastStack(){
            if(stacks.size() == 0){
                return null;
            }
            return stacks.get(stacks.size() - 1);
        }

        public void push(int v){
            Stack last = getLastStack();
            if(last != null && !last.isFull()){
                last.push(v);
            }
            else {       // must creater new stack;
                Stack stack = new Stack(capacity);
                stack.push(v);
                stacks.add(stack);
            }
        }
        public int pop(){
            Stack last = getLastStack();
            if(last == null) throw new EmptyStackException();
            int v = last.pop();
            if(last.size == 0) stacks.remove(stacks.size() - 1);
            return v;
        }

        public int popAt(int index){
            return leftShift(index, true);
        }

        public int leftShift(int index, boolean removeTop){
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

        public boolean isEmpty(){
            Stack last = getLastStack();
            return last == null || last.isEmpty();
        }
    }
    public class Node{
        public Node above;
        public Node below;
        public int data;

        public Node(int data){
            this.data = data;
        }
        public Node(int data, Node above, Node below){
            this.data = data;
            this.above = above;
            this.below = below;
        }
    }

    public class Stack{
        private int capacity;
        public Node top, bottom;
        public int size = 0;

        public Stack(int capacity){
            this.capacity = capacity;
        }

        public boolean isFull(){
            return this.capacity == size;
        }
        public boolean isEmpty(){
            return this.size == 0;
        }

        public void join(Node above, Node below){
            if(above != null) above.below = below;
            if(below != null) below.above = above;
        }

        public boolean push(int v){
            if(size >= capacity) return false;
            size++;
            Node n = new Node(v);
            if(size == 1) bottom = n;
            join(n, top);
            top = n;
            return true;
        }

        public int pop(){
            if(top == null) return Integer.MAX_VALUE;   // Error output.
            Node t = top;
            top = top.below;
            size--;
            return t.data;
        }
        public int removeBottom(){
            if(bottom == null) return Integer.MAX_VALUE;
            Node b = bottom;
            bottom = bottom.above;
            if(bottom != null) bottom.below = null;
            size--;
            return b.data;
        }

    }

    public class MyQueue<T> {
        java.util.Stack<T> stackNewest, stackOldest;

        public MyQueue(){
            stackNewest = new java.util.Stack<T>();
            stackOldest = new java.util.Stack<T>();
        }

        public int size(){
            return stackNewest.size() + stackOldest.size();
        }

        public void add(T value){
            // Push onto stackNewest, which has the newest elements on top.
            stackNewest.push(value);
        }

        // Move elements from stackNewest into stackOldest. this is usually done so that we can do operations on stackOldest.
        public void shiftStacks(){
            if(stackOldest.isEmpty()){
                while(!stackNewest.isEmpty()){
                    stackOldest.push(stackNewest.pop());
                }
            }
        }

        public T peek(){
            shiftStacks();  // Ensure stackOldest has the current elements.
            return stackOldest.peek();
        }
        public T remove(){
            shiftStacks();              // Ensure stackOldest has the current elements.
            return stackOldest.pop();  // pop the oldest item.
        }
    }

    public static void sort(java.util.Stack<Integer> s){
        java.util.Stack<Integer> r = new java.util.Stack<Integer>();

        while(!s.isEmpty()){
            // insert each element in s in sorted order into r.
            int tmp = s.pop();
            while(!r.isEmpty() && r.peek() > tmp){
                s.push(r.pop());
            }
            r.push(tmp);
        }

        // Copy the elements from r back into s.
        while(!r.isEmpty()){
            s.push(r.pop());
        }                                                               // Algorithm O(n * n) time , O(n) space.
    }

    public abstract class Animal{
        private int order;
        protected String name;
        public Animal(String n){
            name = n;
        }
        public void setOrder(int ord){
            order = ord;
        }
        public int getOrder(){
            return order;
        }

        public boolean isOlderThan(Animal a){
            return this.order < a.getOrder();
        }
    }


    class AnimalQueue{
        LinkedList<Dog> dogs = new LinkedList<Dog>();
        LinkedList<Cat> cats = new LinkedList<Cat>();

        private int order = 0;          // acts as timestamp.

        public void enqueue(Animal a){
            a.setOrder(order++);

            if(a instanceof Dog) dogs.addLast((Dog) a);
            else if (a instanceof Cat) cats.addLast((Cat) a);
        }

        public Animal dequeueAny(){
            if(dogs.size() == 0){
                return dequeueCats();
            }
            else if(cats.size() == 0){
                return dequeueDogs();
            }

            Dog dog = dogs.peek();
            Cat cat = cats.peek();

            if(dog.isOlderThan(cat)){
                return dequeueDogs();
            }
            else{
                return dequeueCats();
            }
        }

        private Animal dequeueDogs() {
            return dogs.poll();
        }

        private Animal dequeueCats(){
            return cats.poll();
        }
    }

    public class Dog extends Animal{
        public Dog(String n){
            super(n);
        }
    }
    public class Cat extends Animal{
        public Cat(String n){
            super(n);
        }
    }

}
