package LinkedList;
import java.util.*;
import java.util.Stack;
public class Exercises2 {
    class LinkedListNode{
        public int data;
        public LinkedListNode next;

        public LinkedListNode(int data){
            this.data = data;
        }
        public LinkedListNode(int data, LinkedListNode next){
            this.data = data;
            this.next = next;
        }
        public LinkedListNode(){}

        public void setNext(LinkedListNode node){
            next = node;
        }
    }
    public void deleteDuplicates(LinkedListNode n){
        HashSet<Integer> set = new HashSet<>();
        LinkedListNode prev = null;

        while(n != null){
            if(set.contains(n.data)){
                prev.next = n.next;
            }
            else{
                set.add(n.data);
                prev = n;
            }
        }
    }

    // No buffer allowed
    public void deleteDuplicates2(LinkedListNode head){
        LinkedListNode current = head;

        while(current != null){
            LinkedListNode runner = current;

            while(runner.next != null){
                if(runner.next.data == current.data){
                    runner.next = runner.next.next;
                }
                else{
                    runner = runner.next;
                }
            }
            current = current.next;
        }
    }

    // return Kth to last.
    /* recursive solution.*/
    public int printKthToLast(LinkedListNode head, int k){
        if(head == null){
            return 0;
        }

        int index = printKthToLast(head, k) + 1;
        if(index == k){
            System.out.println("Kth node from last is " + head.data);
        }
        return index;
    }

    class Index{
        public int value;
        public Index(int i){
            value = i;
        }
        public Index(){}
    }


    public LinkedListNode kthToLast(LinkedListNode head, int k){
        Index index = new Index();
        return kthToLast(head, k, index);
    }
    private LinkedListNode kthToLast(LinkedListNode head, int k, Index index){
        if(head == null){
            return null;
        }
        LinkedListNode node = kthToLast(head.next, k, index);
        index.value = index.value + 1;
        if(index.value == k){
            return head;
        }
        return node;
    }

    // Iterative solution.
    LinkedListNode printNthToLast(LinkedListNode head, int k){
        LinkedListNode p1 = head;
        LinkedListNode p2 = head;

        // move p1 k nodes into the list.
        for(int i = 0; i < k; i++){
            if(p1 == null){
                return null;            // out of bounds.
            }
            p1 = p1.next;
        }

        // move them at the same pace. When p1 hits the end, p2 will be at the right element.
        while(p1 != null){
            p2 = p2.next;
            p1 = p1.next;
        }
        return p2;
    }


    public boolean deleteNode(LinkedListNode n){
        if(n == null || n.next == null){
            return false;       // failure.
        }
        LinkedListNode next = n.next;
        n.data = next.data;
        n.next = next.next;
        return true;
    }


    LinkedListNode partition(LinkedListNode node, int x){
        LinkedListNode beforeStart = null;
        LinkedListNode beforeEnd = null;
        LinkedListNode afterStart = null;
        LinkedListNode afterEnd = null;

        while(node != null){
            LinkedListNode next = null;
            node.next = null;
            if(node.data < x){
                if(beforeStart == null){
                    beforeStart = node;
                    beforeEnd = beforeStart;
                }else{
                    beforeEnd.next = node;
                    beforeEnd = node;
                }
            }
            else{
                if(afterStart == null){
                    afterStart = node;
                    afterEnd = afterStart;
                }
                else{
                    afterEnd.next = node;
                    afterEnd = node;
                }
            }
            node = next;
        }

        if(beforeStart == null) return afterStart;

        beforeEnd.next = afterStart;
        return beforeStart;
    }


    public LinkedListNode partition2(LinkedListNode node, int x){
        LinkedListNode head = node;
        LinkedListNode tail = node;

        while(node != null){
            LinkedListNode next = node.next;

            if(node.data < x){
                node.next = head;
                head = node;
            }
            else{
                tail.next = node;
                tail = node;
            }
            node = next;
        }
        return head;
    }

    public LinkedListNode addLists(LinkedListNode list1, LinkedListNode list2, int carry){
        if(list1 == null && list2 == null && carry == 0){
            return null;
        }

        LinkedListNode result = new LinkedListNode();
        int value = carry;

        if(list1 != null){
            value += list1.data;
        }

        if(list2 != null){
            value += list2.data;
        }

        result.data = value % 10;
        // recurse
        if(list1 != null || list2 != null){
            LinkedListNode more = addLists(list1.next, list2.next, value > 10 ? 1 : 0);
            result.setNext(more);
        }
        return result;
    }

    class PartialSum{
        LinkedListNode sum = null;
        public int carry = 0;
    }


    public LinkedListNode addLists2(LinkedListNode l1, LinkedListNode l2){
        int len1 = length(l1);
        int len2 = length(l2);

        if(len1 > len2){
            l2 = padList(l2, len1 - len2);
        }
        else if(len1 < len2){
            l1 = padList(l1, len2 - len1);
        }

        // add Lists.
        PartialSum sum = addListsHelper(l1, l2);

        if(sum.carry == 0){
            return sum.sum;
        }
        else{
            LinkedListNode result = insertBefore(sum.sum, sum.carry);
            return result;
        }
    }

    private PartialSum addListsHelper(LinkedListNode l1, LinkedListNode l2){
        if(l1 == null && l2 == null){
            PartialSum sum = new PartialSum();
            return sum;
        }

        // add smaller digits recursively
        PartialSum sum = addListsHelper(l1.next, l2.next);

        // add carry to the current data.
        int val = l1.data + l2.data + sum.carry;

        // insert sum of current digits.
        LinkedListNode full_result = insertBefore(sum.sum, val % 10);

        // return sum so far, and the carry value.
        sum.sum = full_result;
        sum.carry = val / 10;
        return sum;
    }

    public LinkedListNode padList(LinkedListNode list, int padding){
        LinkedListNode head = list;
        for(int i = 0; i < padding; i++){
            head = insertBefore(head, 0);
        }
        return head;
    }

    private LinkedListNode insertBefore(LinkedListNode list, int data){
        LinkedListNode node = new LinkedListNode(data);
        if(list != null){
            node.next = list;
        }
        return node;
    }

    private int length(LinkedListNode head){
        int length = 0;

        LinkedListNode curr = head;

        while(curr != null){
            length++;
            curr = curr.next;
        }
        return length;
    }


    public boolean isPalindrome(LinkedListNode head){
        LinkedListNode reversed = reverseAndClone(head);
        return isEqual(head, reversed);
    }
    private LinkedListNode reverseAndClone(LinkedListNode node){
        LinkedListNode head = null;

        while(node != null){
            LinkedListNode n = new LinkedListNode(node.data);
            n.next = head;
            head = n;
            node = node.next;
        }
        return head;
    }
    private boolean isEqual(LinkedListNode one, LinkedListNode two){
        while(one != null && two != null){
            if(one.data != two.data){
                return false;
            }
            one = one.next;
            two = two.next;
        }
        return true;
    }

    public boolean isPalindrome1(LinkedListNode head){
        LinkedListNode fast = head;
        LinkedListNode slow = head;

        Stack<Integer> stack = new Stack<Integer>();

        while(fast != null && fast.next != null){
            stack.push(slow.data);
            slow = slow.next;
            fast = fast.next;
        }

        if(fast != null){
            slow = slow.next;
        }

        while(slow != null){
            int top = stack.pop().intValue();

            if(top != slow.data){
                return false;
            }
            slow = slow.next;
        }
        return true;
    }

    class Result{
        public LinkedListNode node;
        public boolean result;

        public Result(LinkedListNode n, boolean res){
            node = n;
            result = res;
        }
    }

    public boolean isPalindrome2(LinkedListNode head){
        int length = lengthOfList(head);
        Result p = isPalindromeRecursive(head, length);
        return p.result;
    }

    private Result isPalindromeRecursive(LinkedListNode head, int length){
        if(head == null || length <= 0){
            return new Result(head, true);
        }
        else if(length == 1){       // Odd number of nodes.
            return new Result(head.next, true);
        }

        // recurse on subList
        Result res = isPalindromeRecursive(head.next, length - 2);

        // if child calls are not a palindrome, pass back up a failure.
        if(!res.result || res.node == null){
            return res;
        }

        res.result = (head.data == res.node.data);

        // return corresponding node.
        res.node = res.node.next;

        return res;
    }
    private int lengthOfList(LinkedListNode n){
        int size = 0;

        while(n != null){
            size++;
            n = n.next;
        }
        return size;
    }


    public LinkedListNode findIntersection(LinkedListNode l1, LinkedListNode l2){
        if(l1 == null || l2 == null){
            return null;
        }

        Result1 result1 = getTailAndSize(l1);
        Result1 result2 = getTailAndSize(l2);

        if(result1.tail != result2.tail){
            return null;
        }

        LinkedListNode shorter = result1.size < result2.size ? l1 : l2;
        LinkedListNode longer = result1.size < result2.size ? l2 : l1;

        longer = getKthNode(longer, Math.abs(result1.size - result2.size));

        while(shorter != longer){
            shorter = shorter.next;
            longer = longer.next;
        }
        // return either one.
        return longer;
    }

    private LinkedListNode getKthNode(LinkedListNode node, int k){
        LinkedListNode current = node;
        while(k > 0 && current != null){
            current = current.next;
            k--;
        }
        return current;
    }
    class Result1{
        public LinkedListNode tail;
        public int size;

        public Result1(LinkedListNode tail, int size){
            this.tail = tail;
            this.size = size;
        }
    }

    private Result1 getTailAndSize(LinkedListNode list){
        if(list == null) return null;

        int size = 1;
        LinkedListNode current = list;
        while(current.next != null){
            size++;
            current = current.next;
        }
        return new Result1(current, size);
    }


    public LinkedListNode findBeginning(LinkedListNode head){
        LinkedListNode slow = head;
        LinkedListNode fast = head;


        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next;

            if(slow == fast){
                break;              // Collision.
            }
        }

        slow = head;
        // Move slow to head. Keep fast at meeting point. Each are k steps far from the Loop start.
        // if they move at the same pace, they must meet at loop start.

        while(slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        // Both now points to the start of the loop.
        return fast;
    }


    class FixedMultiStack {
        private int numberOfStacks = 3;
        public int[] values;
        public int[] sizes;
        private int stackCapacity;

        public FixedMultiStack(int stackSize) {
            stackCapacity = stackSize;
            values = new int[numberOfStacks * stackSize];
            sizes = new int[numberOfStacks];
        }

        // push value onto stack
        public void push(int stackNum, int value) throws StackOverflowError {
            // check we have a space for next number.
            if (isFull(stackNum)) {
                throw new StackOverflowError();
            }
            // Increment stack pointer, then update top value.
            sizes[stackNum]++;
            values[indexOfTop(stackNum)] = value;
        }
        public int pop(int stackNum) throws EmptyStackException{
            if(isEmpty(stackNum)){
                throw new EmptyStackException();
            }
            int topIndex = indexOfTop(stackNum);
            int value = values[topIndex];       // get top.
            values[topIndex] = 0;       // Clear
            sizes[stackNum]--;      // Shrink
            return value;
        }
        private int indexOfTop(int stackNum) {
            int offset = stackNum * stackCapacity;
            int size = sizes[stackNum];
            return offset + size - 1;
        }

        private boolean isFull(int stackNum){
            return sizes[stackNum] == stackCapacity;
        }
        private boolean isEmpty(int stackNum){
            return sizes[stackNum] == 0;
        }
    }

    public class MultiStack{
        private class StackInfo{
            public int size, capacity, start;

            public StackInfo(int start, int capacity){
                this.start = start;
                this.capacity = capacity;
            }

            public boolean isWithinStackCapacity(int index){
                if(index < 0 || index >= values.length){
                    return false;
                }

                // if index wraps around, adjust it.
                int contiguousIndex = index < start ? index + values.length : index;
                int end = start + capacity;
                return index <= contiguousIndex && contiguousIndex < end;
            }

            public int lastCapacityIndex(){
                return adjustIndex(start + capacity - 1);
            }
            public int lastElementIndex(){
                return adjustIndex(start + size - 1);
            }

            public boolean isFull(){
                return size == capacity;
            }
            public boolean isEmpty(){
                return size == 0;
            }
        }
        StackInfo[] info;
        int[] values;

        public MultiStack(int numberOfStacks, int defaultSize){
            // Create metadata for all the stacks.
            info = new StackInfo[numberOfStacks];

            for(int i = 0; i < numberOfStacks; i++){
                info[i] = new StackInfo(i * defaultSize, defaultSize);
            }
            values = new int[numberOfStacks * defaultSize];
        }

        public void push(int stackNum, int value) throws StackOverflowError{
            if(allStacksAreFull()){
                throw new StackOverflowError();
            }

            // if this stack is full, expand it.
            StackInfo stack = info[stackNum];

            if(stack.isFull()){
                expand(stackNum);
            }

            // find the index of the top element in the array + 1, and increment the stack pointer.
            stack.size++;
            values[stack.lastElementIndex()] = value;
        }

        // remove value from stack.
        public int pop(int stackNum) throws EmptyStackException{
            StackInfo stack = info[stackNum];

            if(stack.isEmpty()){
                throw new EmptyStackException();
            }

            // remove last element.
            int value = values[stack.lastElementIndex()];   // retrieve last element.
            values[stack.lastElementIndex()] = 0;       // clear
            stack.size--;           // Shrink the size.
            return value;
        }

        // get top element of stack.
        public int peek(int stackNum){
            StackInfo stack = info[stackNum];
            return values[stack.lastElementIndex()];
        }

        // Shift items in stack over by one element. if we have available capacity, then we'll end up shrinking the stack by one
        // element.if we don't have available capacity, then we'll need to shift next stack over too.
        private void shift(int stackNum){
            System.out.println("/// Shifting " + stackNum);

            StackInfo stack = info[stackNum];

            if(stack.size >= stack.capacity){
                int nextStack = (stackNum + 1) % info.length;
                shift(nextStack);
                stack.capacity++;
            }

            int index = stack.lastCapacityIndex();
            // Shift all elements in stack over by one.
            while(stack.isWithinStackCapacity(index)){
                values[index] = values[previousIndex(index)];
                index = previousIndex(index);
            }
            // adjust stack data.
            values[stack.start] = 0;        // clear first element of the stack.
            stack.start = nextIndex(stack.start);       // move start.
            stack.capacity--;           // Shrink capacity
        }

        // Expanding stack by shifting over other stacks.
        public void expand(int stackNum){
            shift((stackNum + 1) % info.length);
            info[stackNum].capacity++;
        }

        public int numberOfElements(){
            int size = 0;
            for(StackInfo sd : info){
                size += sd.size;
            }
            return size;
        }

        public boolean allStacksAreFull(){
            return numberOfElements() == values.length;
        }

        private int adjustIndex(int index){
            int max = values.length;
            return ((index % max) + max) % max;
        }
        private int nextIndex(int index){
            return adjustIndex(index + 1);
        }
        private int previousIndex(int index){
            return adjustIndex(index - 1);
        }
    }


    public class StackWithMin extends Stack<NodeWithMin> {
        public void push(int value){
            int newMin = Math.min(min(), value);
            super.push(new NodeWithMin(value, newMin));
        }


        public int min(){
            if(this.isEmpty()){
                return Integer.MAX_VALUE;           // Error value.
            }
            else{
                return peek().min;
            }
        }

    }

    class NodeWithMin{
        public int value;
        public int min;

        public NodeWithMin(int v, int m){
            value = v;
            min = m;
        }
    }




    public class StackWithMin2 extends Stack<Integer>{
        Stack<Integer> s2;

        public StackWithMin2(){
            s2 = new Stack<Integer>();
        }

        public void push(int value){
            if(value < min()){
                s2.push(value);
            }
            else{
                super.push(value);
            }
        }

        public Integer pop(){
            if(super.isEmpty()){
                return Integer.MAX_VALUE;       // Error value.
            }
            else{
                int value = super.pop();
                if(value == s2.peek()){
                    s2.pop();
                }
                return value;
            }
        }
        public Integer min(){
            if(s2.isEmpty()){
                return Integer.MAX_VALUE;
            }
            else{
                return s2.peek();
            }
        }
    }




































}
