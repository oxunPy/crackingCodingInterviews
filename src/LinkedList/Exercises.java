package LinkedList;
import java.util.*;

public class Exercises {

    public static class ListNode{
        public Integer data;
        public ListNode next;

        public ListNode(int data, ListNode next){
            this.data = data;
            this.next = next;
        }
        public ListNode(int data){
            this.data = data;
        }
    }

    public static void deleteDups(ListNode n){
        HashSet<Integer> set = new HashSet<Integer>();
        ListNode previous = null;

        while(n != null){
            if(set.contains(n.data)){
                previous.next = n.next;
            }
            else{
                set.add(n.data);
                previous = n;
            }
            n = n.next;
        }
    } // O(n) time complexity, O(n) space complexity.

    public static void deleteDups2(ListNode head){     // O(1) space
        ListNode current = head;

        while(current != null){
            // remove all future nodes that have the same value.
            ListNode runner = current;
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
    }   // O(n * n ) time complexity, O(1) space complexity.


    public static int printKToLastNode(ListNode head, int k){
        if(head == null){
            return 0;
        }

        int index = printKToLastNode(head.next, k) + 1;
        if(index == k){
            System.out.println(k + "th to last node is " + head.data);
        }
        return index;
    }

    public static class Index{
        public int value = 0;
    }

    public static ListNode kthLastNode(ListNode head, int k){
        Index idx = new Index();
        return kthLastNode(head, k, idx);
    }
    public static ListNode kthLastNode(ListNode head, int k, Index idx){
        if(head == null){
            return null;
        }

        ListNode node = kthLastNode(head.next, k, idx);
        idx.value = idx.value + 1;
        if(idx.value == k){
            return head;
        }
        return head;
    }

    // Solution 3 : Iterative.
    public static ListNode nthNodeFromLast(ListNode head, int n){
        ListNode p1 = head, p2 = head;

        // move p1 pointer n nodes into the list.
        for(int i = 0; i < n; ++i){
            if(p1 == null) return null;
            p1 = p1.next;
        }

        // Move them at the same pace. When p1 hits the end, p2 will be at the right element.
        while(p1 != null){
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }

    // delete middle node.
    public static boolean deleteNode(ListNode node){

        if(node == null || node.next == null){
            return false;           // Failure
        }

        ListNode next = node.next;
        node.data = next.data;
        node.next = next.next;
        return true;
    }

    public static ListNode partition(ListNode node, int x){
        ListNode beforeStart = null;
        ListNode beforeEnd = null;
        ListNode afterStart = null;
        ListNode afterEnd = null;

        // Partition list.

        while(node != null){
            ListNode next = node.next;
            node.next = null;

            if(node.data < x){
                if(beforeStart == null){
                    beforeStart = node;
                    beforeEnd = beforeStart;
                }
                else{
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
                    afterStart = node;
                }
            }
            node = next;
        }

        if(beforeStart == null){
            return afterStart;
        }

        beforeEnd.next = afterStart;
        return beforeStart;
    }

    public static ListNode partition2(ListNode node, int x){
        ListNode head = node;
        ListNode tail = node;

        while(node != null){
            ListNode next = node.next;

            if(node.data < x){
                node.next = head;
                head = node;
            }
            else{
                tail.next = node;
                tail = node;
            }
        }
        tail.next = null;
        // head has changed so we need to return it to the user.
        return head;
    }







    public static ListNode addLists(ListNode l1, ListNode l2, int carry){
        if(l1 == null && l2 == null && carry == 0){
            return null;
        }

        ListNode result = new ListNode(0);

        int value = carry;
        if(l1 != null){
            value += l1.data;
        }

        if(l2 != null){
            value += l2.data;
        }

        result.data = value % 10;
        // recurse here/
        if(l1 != null || l2 != null){
            ListNode more = addLists(l1 == null ? null : l1.next, l2 == null ? null : l2.next, value >= 10 ? 1 : 0);
            result.next = more;
        }
        return result;
    }

    public static class PartialSum{
        public ListNode sum = null;
        public int carry = 0;

        public PartialSum(ListNode sum, int carry){
            this.sum = sum;
            this.carry = carry;
        }
        public PartialSum(){}
    }

    public static ListNode addLists(ListNode l1, ListNode l2){
        int len1 = length(l1);
        int len2 = length(l2);

        if(len1 < len2){
            l1 = padList(l1, len2 - len1);
        }
        else{
            l2 = padList(l2, len1 - len2);
        }

        // Add lists.
        PartialSum sum = addListsHelper(l1, l2);

        if(sum.carry == 0){
            return sum.sum;
        }
        else{
            ListNode result = insertBefore(sum.sum, sum.carry);
            return result;
        }
    }

    public static PartialSum addListsHelper(ListNode l1, ListNode l2){
        if(l1 == null && l2 == null){
            PartialSum sum = new PartialSum();
            return sum;
        }

        // add smaller digits recursively.
        PartialSum sum = addListsHelper(l1.next, l2.next);

        // add carry to current data.
        int val = sum.carry + l1.data + l2.data;

        // insert sum of current digits.
        ListNode fullResult = insertBefore(sum.sum, val % 10);

        // return sum so far, and the carry value.
        sum.sum = fullResult;
        sum.carry = val / 10;
        return sum;
    }

    public static ListNode padList(ListNode l, int padding){
        ListNode head = l;
        for(int i = 0; i < padding; ++i){
            head = insertBefore(l, 0);
        }
        return head;
    }

    public static ListNode insertBefore(ListNode list, int data){
        ListNode node = new ListNode(data);

        if(list != null){
            node.next = list;
        }
        return node;
    }

    private static int length(ListNode head){
        ListNode temp = head;
        int len = 0;
        while(temp != null){
            temp = temp.next;
            len++;
        }
        return len;
    }


    public static boolean isPalindrome(ListNode head){
        ListNode reversed = reverseAndClone(head);
        return isEqual(head, reversed);
    }

    private static ListNode reverseAndClone(ListNode node){
        ListNode head = null;

        while(node != null){
            ListNode n = new ListNode(node.data);   // clone
            n.next = head;
            head = n;
            node = node.next;
        }
        return head;
    }

    private static boolean isEqual(ListNode one, ListNode two){
        while(one != null && two != null){
            if(one.data != two.data){
                return false;
            }
            one = one.next;
            two = two.next;
        }
        return true;
    }
    // Iterative approach 2.
    public static boolean palindrome(ListNode head){
        ListNode fast = head;
        ListNode slow = head;

        Stack<Integer> stack = new Stack<Integer>();

        // push elements from first half of linked list onto stack. When fast runner (which is moving at 2x speed)
        // reaches the end of the linked list, then we know we're at the middle.
        while(fast != null && fast.next != null){
            stack.push(slow.data);
            fast = fast.next.next;
            slow = slow.next;
        }

        // has odd number of elements, so skip the middle element.
        if(fast != null){
            slow = slow.next;
        }

        while(slow != null){
            int top = stack.pop().intValue();

            // if values are different, then it's not a palindrome.
            if(top != slow.data){
                return false;
            }
        }
        return true;
    }

    // Recursive approach 3.
    public static class Result{
        public ListNode node;
        public boolean result;

        public Result(ListNode node, boolean result){
            this.node = node;
            this.result = result;
        }
    }

    public static boolean isPalindrome1(ListNode head){
        int length = lengthOfList(head);
        Result p = isPalindromeRecurse(head, length);
        return p.result;
    }

    public static Result isPalindromeRecurse(ListNode head, int length){
        if(length == 1 || head == null){        // Odd number of nodes;
            return new Result(head.next, true);
        }
        if(length <= 0){
            return new Result(head, true);      // Even number of nodes.
        }
        // Recurse on subList.
        Result res = isPalindromeRecurse(head.next, length - 2);

        // if child calls are not a palindrome, pass back up a failure.
        if(!res.result || res.node == null){
            return res;
        }
        // check if matches corresponding node on other side.
        res.result = (head.data == res.node.data);
        // return corresponding node.
        res.node = res.node.next;

        return res;
    }


    public static int lengthOfList(ListNode n){
        int size = 0;
        while(n != null){
            size++;
            n = n.next;
        }
        return size;
    }

    public static ListNode findIntersection(ListNode list1, ListNode list2){
        if(list1 == null || list2 == null) return null;

        // Get tail and sizes.
        Result2 res1 = getSizeAndTail(list1);
        Result2 res2 = getSizeAndTail(list2);


        // Set pointers to the start of each linked list.
        ListNode shorter = res1.size < res2.size ? list1 : list2;
        ListNode longer = res1.size > res2.size ? list1 : list2;

        longer = getKthNode(longer, Math.abs(res1.size - res2.size));

        while(shorter != longer){
            shorter = shorter.next;
            longer = longer.next;
        }

        // Return either one.
        return longer;
    }

    public static class Result2{
        public ListNode tail;
        public int size;

        public Result2(ListNode tail, int size){
            this.tail = tail;
            this.size = size;
        }
    }

    public static Result2 getSizeAndTail(ListNode list){
        if(list == null) return null;

        int size = 1;
        ListNode current = list;
        while(current.next != null){
            current = current.next;
            size++;
        }
        return new Result2(current, size);
    }

    public static ListNode getKthNode(ListNode head, int k){
        ListNode current = head;

        while(k > 0 && current != null){
            current = current.next;
            k--;
        }
        return current;
    }

    public static ListNode findBeginning(ListNode head){
        ListNode slow = head;
        ListNode fast = head;

        // Find the meeting point. this will LOOPING_SIZE - k steps into the linked list.
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast){
                break;
            }
        }

        // Error check - no meeting point, and therefore no loop.
        if(fast == null || fast.next == null){
            return null;
        }

        // Move slow to head. Keep fast at meeting point. Each are k steps from the Loop Start.if they move at the same pace,
        // they must meet at loop start.
        slow = head;
        while(slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        // Both now point to the start of the loop.
        return fast;
    }


























}
