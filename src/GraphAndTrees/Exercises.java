package GraphAndTrees;

import java.util.*;
public class Exercises {
    public class Node{
        int data;
        public State state;
        ArrayList<Node> neighbours;

        public Node(int data, ArrayList<Node> neighbours){
            this.data = data;
            this.neighbours = neighbours;
        }

        public ArrayList<Node> getAdjacent(){
            return neighbours;
        }
    }

    public class Graph{
        ArrayList<Node> edges;

        public Graph(ArrayList<Node> edges){
            this.edges = edges;
        }

        public ArrayList<Node> getNodes(){
            return edges;
        }
    }

    public static enum State{
        UNVISITED, VISITING, VISITED;
    }

    public static boolean search(Graph g, Node start, Node end){
        if(start == end) return true;

        // operates as queue.
        LinkedList<Node> q = new LinkedList<Node>();

        for(Node u : g.getNodes()){
            u.state = State.UNVISITED;
        }
        start.state = State.VISITING;
        q.add(start);
        Node u;

        while(!q.isEmpty()){
            u = q.removeFirst();        // dequeue.

            if(u != null){
                for(Node v : u.getAdjacent()){
                    if(v.state == State.UNVISITED){
                        if(v == end){
                            return true;
                        }
                        else{
                            v.state = State.VISITING;
                            q.add(v);
                        }
                    }
                }
            }
            u.state = State.VISITED;
        }
        return false;
    }

    public static class TreeNode{
        TreeNode left, right, parent;
        public int data, size;

        public TreeNode(int data){
            this.data = data;
        }
        public TreeNode (int data, TreeNode left, TreeNode right){
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }


    public static TreeNode createMinimalBinaryTree(int[] array){
        return createMinimalBinaryTree(array, 0, array.length - 1);
    }
    public static TreeNode createMinimalBinaryTree(int[] array, int start, int end){
        if(end < start){
            return null;
        }

        int mid = (start + end) / 2;
        TreeNode n = new TreeNode(array[mid]);
        n.left = createMinimalBinaryTree(array, 0, mid - 1);
        n.right = createMinimalBinaryTree(array, mid + 1, end);
        return n;
    }

    public static void createLevelLinkedList(TreeNode root, ArrayList<LinkedList<TreeNode>> lists, int level){
        if(root == null){
            return;             // base case;
        }

        LinkedList<TreeNode> list = null;
        if(lists.size() == level){
            list = new LinkedList<TreeNode>();
            // Levels are all traversed in order.So, if this is the first time we've visited level i,
            // we must have seen levels 0 through i - 1.therefore we can safely add the level at the end.
            lists.add(list);
        }
        else{
            list = lists.get(level);
        }

        list.add(root);
        createLevelLinkedList(root.left, lists, level + 1);
        createLevelLinkedList(root.right, lists, level + 1);
    }

    public static ArrayList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root){
        ArrayList<LinkedList<TreeNode>> lists = new ArrayList<LinkedList<TreeNode>>();
        createLevelLinkedList(root, lists, 0);
        return lists;
    }

    public static ArrayList<LinkedList<TreeNode>> createLevelLinkedList2(TreeNode root){
        ArrayList<LinkedList<TreeNode>> result = new ArrayList<LinkedList<TreeNode>>();
        // visit the root.
        LinkedList<TreeNode> current = new LinkedList<TreeNode>();
        if(root != null){
            current.add(root);
        }

        while(current.size() > 0){
            result.add(current);        // add previous level;
            LinkedList<TreeNode> parents = current;     // Go to next level.
            current = new LinkedList<TreeNode>();

            for(TreeNode parent : parents){
                if(parent.left != null){
                    current.addLast(parent.left);
                }
                if(parent.right != null){
                    current.addLast(parent.right);
                }
            }
        }
        return result;
    }


    // Balanced Trees.
    public static int getHeight(TreeNode node){
        if(node == null){
            return -1;          // Base case
        }
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    public static boolean isBalanced(TreeNode root){
        if(root == null){
            return true;            // Base case.
        }

        int heightDifference = Math.abs(getHeight(root.left) - getHeight(root.right));

        if(heightDifference > 1){
            return false;
        }
        else{       // Recurse
            return isBalanced(root.left) && isBalanced(root.right);
        }
    }


    public static int checkHeight(TreeNode root){
        if(root == null){
            return -1;
        }

        int leftHeight = checkHeight(root.left);
        if(leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;       // Pass error up.

        int rightHeight = checkHeight(root.right);
        if(rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;      // Pass error up.

        // check height difference
        int heightDifference = Math.abs(leftHeight - rightHeight);

        if(heightDifference > 1){
            return Integer.MIN_VALUE;           // Found error -> pass it back.
        }
        else{
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    public static boolean isBalanced2(TreeNode root){
        return checkHeight(root) != Integer.MIN_VALUE;
    }


    // Validate BST
    private static int index = 0;
    // this algorithm works for the tree with distinct elements.
    public static void copyBST(TreeNode root, int[] array){
        if(root == null) return;

        copyBST(root.left, array);
        array[index++] = root.data;
        copyBST(root.right, array);
    }

    public static boolean checkBST(TreeNode root){
        int[] array = new int[root.size];
        copyBST(root, array);

        for(int i = 1; i < array.length; ++i){
            if(array[i] <= array[i - 1]) return false;
        }
        return true;
    }

    static Integer last_printed = null;

    public static boolean checkBST2(TreeNode n){
        if(n == null) return true;

        // check / recurse left.
        if(!checkBST(n.left)) return false;

        // check current.
        if(last_printed != null && n.data <= last_printed){
            return false;
        }
        last_printed = n.data;

        // check / recurse right;
        if(!checkBST(n.right)) return false;

        return true;            // All good!
    }


    // Solution 2 : Min_Max solution.
    public static boolean checkBalanced2(TreeNode n){
        return checkBalanced2(n, null, null);
    }
    public static boolean checkBalanced2(TreeNode n, Integer min, Integer max){
        if(n == null){
            return true;
        }

        if((min != null && n.data <= min) || (max != null && n.data > max)){
            return false;
        }

        // check / recurse
        if(!checkBalanced2(n.left, min, n.data) || !checkBalanced2(n.right, n.data, max)){
           return false;
        }

        return true;        // All good!
    }


    public static TreeNode inorderSuccessor(TreeNode n){
        if(n == null){
            return null;
        }

        if(n.right != null){
            return leftMostChild(n.right);
        }
        else{
            TreeNode q = n;
            TreeNode x = q.parent;
            while(x != null && x.left != q){
                q = x;
                x = x.parent;
            }
            return x;
        }
    }

    public static TreeNode leftMostChild(TreeNode node){

        if(node == null) return null;

        while(node.left != null){
            node = node.left;
        }
        return node;
    }



    public Project[] findBuildOrder2(String[] projects, String[][] dependencies){
        Graph1 graph = buildGraph(projects, dependencies);
        return orderProjects(graph.getNodes());
    }

    public Graph1 buildGraph(String[] projects, String[][] dependencies){
        Graph1 graph = new Graph1();
        for(String project : projects){
            graph.getOrCreateNode(project);
        }

        for(String[] dependency : dependencies){
            String first = dependency[0];
            String second = dependency[1];
            graph.addEdge(first, second);
        }
        return graph;
    }
    // returns list of project a correct build order.
    public Project[] orderProjects(ArrayList<Project> projects){
        Project[] order = new Project[projects.size()];

        // add roots to the build order first.
        int endOfList = addNonDependent(order, projects, 0);

        int toBeProcessed = 0;
        while(toBeProcessed < 0){
            Project current = order[toBeProcessed];

            // We have a circular dependency since there are no remaining projects with zero dependencies.
            if(current == null){
                return null;
            }

            // remove myself as a dependency.
            ArrayList<Project> children = current.getChildren();

            for(Project child : children){
                child.decrementDependencies();
            }

            // add children that have no one depending on them;
            endOfList = addNonDependent(order, children, endOfList);
            toBeProcessed++;
        }
        return order;
    }

    public int addNonDependent(Project[] order, ArrayList<Project> projects, int offset){
        for(Project project : projects){
            if(project.getNumberDependencies() == 0){
                order[offset] = project;
                offset++;
            }
        }
        return offset;
    }

    public class Graph1{
        private ArrayList<Project> nodes = new ArrayList<Project>();
        private HashMap<String, Project> map = new HashMap<String, Project>();

        public Project getOrCreateNode(String name){
            if(!map.containsKey(name)){
                Project node = new Project(name);
                nodes.add(node);
                map.put(name, node);
            }
            return map.get(name);
        }

        public void addEdge(String startName, String endName){
            Project start = getOrCreateNode(startName);
            Project end = getOrCreateNode(endName);
            start.addNeighbour(end);
        }

        public ArrayList<Project> getNodes(){
            return nodes;
        }
    }


    public class Project{
        private ArrayList<Project> children = new ArrayList<Project>();
        private HashMap<String, Project> map = new HashMap<String, Project>();
        private String name;
        private int dependencies = 0;

        public Project(String n){
            name = n;
        }

        public void addNeighbour(Project node){
            if(!map.containsKey(node.getName())){
                children.add(node);
                node.incrementDependencies();
                map.put(node.getName(), node);
            }
        }

        public void incrementDependencies(){
            dependencies++;
        }
        public void decrementDependencies(){
            dependencies--;
        }
        public String getName(){
            return name;
        }
        public ArrayList<Project> getChildren(){
            return children;
        }
        public int getNumberDependencies(){
            return dependencies;
        }
    }

//
//    public Stack<Project1> findBuildOrder(String[] projects, String[][] dependencies){
//        Graph1 graph = buildGraph(projects, dependencies);
//        return orderProjects2(graph.getNodes());
//    }

    public Stack<Project1> orderProjects2(ArrayList<Project1> projects){
        Stack<Project1> stack = new Stack<Project1>();
        for(Project1 project : projects){
            if(project.getState() == State1.BLANK){
                if(!doDFS(project, stack)){
                    return null;
                }
            }
        }
        return stack;
    }

    public boolean doDFS(Project1 project, Stack<Project1> stack){
        if(project.getState() == State1.PARTIAL){
            return false;       // cycle.
        }

        if(project.getState() == State1.BLANK){
            project.setState(State1.PARTIAL);
            ArrayList<Project1> children = project.getChildren();
            for(Project1 child : children){
                if(!doDFS(child, stack)){
                    return false;
                }
            }
            project.setState(State1.COMPLETE);
            stack.push(project);
        }
        return true;
    }

    public class Graph2{
        ArrayList<Project1> nodes = new ArrayList<Project1>();
        HashMap<String, Project1> map = new HashMap<String, Project1>();

        public Project1 getOrCreateNode(String name){
            if(!map.containsKey(name)){
                Project1 node = new Project1(name);
                nodes.add(node);
                map.put(name, node);
            }
            return map.get(name);
        }

        public ArrayList<Project1> getNodes(){
            return nodes;
        }
    }


    public enum State1 {
        PARTIAL,COMPLETE, BLANK
    }
    public class Project1{
        private State1 state = State1.BLANK;
        private String name;
        private ArrayList<Project1> children;
        private HashMap<String, Project1> map = new HashMap<String, Project1>();


        public Project1(String n){
            name = n;
        }

        public State1 getState(){
            return state;
        }
        public void setState(State1 st){
            state = st;
        }

        public ArrayList<Project1> getChildren(){
            return children;
        }
        public String getName(){
            return name;
        }
    }


    public TreeNode commonAncestor(TreeNode p, TreeNode q){
        int delta = depth(p) - depth(q);        // get difference in depths.
        TreeNode first = delta > 0 ? q : p;     // get shallower node
        TreeNode second = delta > 0 ? p : q;    // get deeper node.

        second = goUpBy(second, Math.abs(delta));   // move deeper node.

        while(first != null && second != null && first != second){
            first = first.parent;
            second = second.parent;
        }
        return first == null || second == null ? null : first;
    }


    public TreeNode goUpBy(TreeNode node, int delta){
        while(delta > 0 && node != null){
            node = node.parent;
            delta--;
        }
        return node;
    }
    public int depth(TreeNode node){
        if(node == null){
            return -1;
        }
        return Math.max(depth(node.left), depth(node.right)) + 1;
    }
    // With links to parents.
    public TreeNode commonAncestor(TreeNode root, TreeNode p, TreeNode q){
        if(!covers(root, p) || !covers(root, q)){
            return null;
        }
        else if(covers(p, q)){
            return p;
        }
        else if(covers(q, p)){
            return q;
        }
        // Traverse upward until you find a node covers q.
        TreeNode sibling = getSibling(p);
        TreeNode parent = p.parent;
        while(!covers(sibling, q)){
            sibling = getSibling(sibling);
            parent = parent.parent;
        }
        return parent;
    }

    public boolean covers(TreeNode root, TreeNode p){
        if(root == null){
            return false;
        }
        if(root == p){
            return true;
        }
        return covers(root.left, p) || covers(root.right, p);
    }

    public TreeNode getSibling(TreeNode node){
        if(node == null || node.parent == null){
            return null;
        }
        TreeNode parent = node.parent;
        return parent.left == node ? parent.right : parent.left;
    }

    // Solution 3 : Without links to parents.
    public TreeNode commonAncestor2(TreeNode root, TreeNode p, TreeNode q){
        // Error check, one node is not in the tree.
        if(!covers(root, p) || !(covers(root, q))){
            return null;
        }

        return ancestorHelper(root, p, q);
    }
    public TreeNode ancestorHelper(TreeNode root, TreeNode p, TreeNode q){
        if(root == null || root == p || root == q){
            return root;
        }

        boolean pIsOnLeft = covers(root.left, p);
        boolean qIsOnLeft = covers(root.left, q);

        if(pIsOnLeft != qIsOnLeft){
            return root;            // Nodes are different sides.
        }

        TreeNode childSide = pIsOnLeft ? root.left : root.right;
        return ancestorHelper(childSide, p, q);
    }


    public TreeNode commonAncestor3(TreeNode root, TreeNode p, TreeNode q){
        if(root == null){
            return null;
        }
        if(root == p && root == q){
            return root;                // foound common ancestor.
        }

        TreeNode x = commonAncestor3(root.left, p, q);
        if(x != null && x != p && x != q){
            return x;                   // found ancestor.
        }

        TreeNode y = commonAncestor3(root.right, p, q);
        if(y != null && y != p && y != q){
            return y;
        }

        if(x != null && y != null){     // p and q found int diff. subtrees.
            return root;        // this is common ancestor.
        }
        else if(root == p || root == q){
            return root;
        }
        else{
            return x == null ? y : x;       // return non-null value.
        }
    }


    public class Result{
        public TreeNode node;
        public boolean isAncestor;

        public Result(TreeNode n, boolean isAnc){
            node = n;
            isAncestor = isAncestor;
        }
    }

    public TreeNode commonAncestor4(TreeNode root, TreeNode p, TreeNode q){
        Result r = commonAncestorHelper(root, p, q);
        if(r.isAncestor){
            return r.node;
        }
        return null;
    }

    public Result commonAncestorHelper(TreeNode root, TreeNode p, TreeNode q){
        if(root == null){
            return new Result(null, false);
        }
        if(root == p && root == q){
            return new Result(root, true);
        }

        Result rx = commonAncestorHelper(root.left, p, q);
        if(rx.isAncestor){
            return rx;      // found common ancestor
        }

        Result ry = commonAncestorHelper(root.right, p, q);
        if(ry.isAncestor){
            return ry;          // found common ancestor.
        }

        if(rx.node != null && ry.node != null){
            return new Result(root, true);      // This is common ancestor.
        }
        else if(root == p || root == q){
            // if we're currently at p or q, we also found one of those nodes in a subtree,
            // then this is truely an ancestor and the flag should be true.
            boolean isAncestor = rx.node != null || ry.node != null;
            return new Result(root, isAncestor);
        }
        else{
            return new Result(rx.node == null ? ry.node : rx.node, false);
        }
    }

    public ArrayList<LinkedList<Integer>> allSequences(TreeNode node){
        ArrayList<LinkedList<Integer>> result = new ArrayList<LinkedList<Integer>>();

        if(node == null){
            result.add(new LinkedList<Integer>());
            return result;
        }

        LinkedList<Integer> prefix = new LinkedList<Integer>();
        prefix.add(node.data);

        // recurse on left and right subtrees.
        ArrayList<LinkedList<Integer>> leftSeq = allSequences(node.left);
        ArrayList<LinkedList<Integer>> rightSeq = allSequences(node.right);

        for(LinkedList<Integer> left : leftSeq){
            for(LinkedList<Integer> right : rightSeq){
                ArrayList<LinkedList<Integer>> weaved = new ArrayList<LinkedList<Integer>>();
                weaveLists(left, right, prefix, weaved);
                result.addAll(weaved);
            }
        }
        return result;
    }

    public void weaveLists(LinkedList<Integer> first, LinkedList<Integer> second, LinkedList<Integer> prefix, ArrayList<LinkedList<Integer>> results){
        if(first.size() == 0 || second.size() == 0){
            LinkedList<Integer> weaved = (LinkedList<Integer>) prefix.clone();
            weaved.addAll(first);
            weaved.addAll(second);
            results.add(weaved);
            return;
        }

        int headFirst = first.removeFirst();
        prefix.addLast(headFirst);
        weaveLists(first, second, prefix, results);
        prefix.removeLast();
        first.addFirst(headFirst);

        int headSecond = second.removeFirst();
        prefix.addLast(headSecond);
        weaveLists(first, second, prefix, results);
        prefix.removeLast();
        second.addFirst(headSecond);
    }

    public boolean containsTree(TreeNode t1, TreeNode t2){
        StringBuilder string1 = new StringBuilder();
        StringBuilder string2 = new StringBuilder();

        getOrderString(t1, string1);
        getOrderString(t2, string2);

        return string1.indexOf(string2.toString()) != -1;
    }

    public void getOrderString(TreeNode node, StringBuilder sb){
        if(node == null){
            sb.append('X');         // add null indicator.
            return;
        }

        sb.append(node.data +" ");      // add root.
        getOrderString(node.left, sb);  // add left.
        getOrderString(node.right, sb); // add right.
    }


    public boolean containsTree2(TreeNode t1, TreeNode t2){
        if(t2 == null){
            return true;            // t2 is always a subtree.
        }
        return subTree(t1, t2);
    }

    public boolean subTree(TreeNode r1, TreeNode r2){
        if(r1 == null){
            return false;       // big tree is empty & subtree is not found.
        }
        else if(r1.data == r2.data && match(r1, r2)){
            return true;
        }
        return subTree(r1.left, r2) || subTree(r1.right, r2);
    }

    public boolean match(TreeNode r1, TreeNode r2){
        if(r1 == null && r2 == null){
            return true;        // nothing left in the subtee.
        }
        else if(r1 ==  null || r2 == null){
            return false;               // exactly tree is empty, therefore doesn't match.
        }
        else if(r1.data != r2.data){
            return false;
        }
        else{
            return match(r1.left, r2.left) && match(r1.right, r2.right);
        }
    }


    public class TreeNode3{
        private int data;
        public TreeNode3 left,right;
        private int size = 0;

        public TreeNode3(int d){
            data = d;
        }

        public TreeNode3 getRandomNode(){
            int leftSize = left == null ? 0 : left.size;
            Random random = new Random();
            int index = random.nextInt(size);
            if(index < leftSize){
                return left.getRandomNode();
            }
            else if(index == leftSize){
                return this;
            }
            else{
                return right.getRandomNode();
            }
        }

        public void insertInOrder(int d){
            if(d <= data){
                if(left == null){
                    left = new TreeNode3(d);
                }
                else{
                    left.insertInOrder(d);
                }
            }
            else{
                if(right == null){
                    right = new TreeNode3(d);
                }
                else{
                    right.insertInOrder(d);
                }
            }
            size++;
        }

        public int getSize(){
            return size;
        }
        public int getData(){
            return data;
        }
        public TreeNode3 find(int d){
            if(d == data){
                return this;
            }
            else if(d < data){
                return left != null ? left.find(d) : null;
            }
            else if(d > data){
                return right != null ? right.find(d) : null;
            }
            return null;
        }
    }               // In balanced tree algorithm runs in O(log(n)) time, where n is number of nodes in the tree.


    // Fast working. Random calls may be expensive.if we'd like, we can reduce the number of random calls substantially.
    public class Tree{
        TreeNode4 root = null;


        public int size(){
            return root != null ? root.size() : 0;
        }
        public TreeNode4 getRandomNode(){
            if(root == null) return null;

            Random random = new Random();
            int i = random.nextInt();

            return root.getIthNode(i);
        }
    }

    public class TreeNode4{
        public int data;
        public TreeNode4 left, right;
        int size = 0;
        public TreeNode4(int d){
            data = d;
            size = 1;
        }
        public TreeNode4 getIthNode(int i){
            int leftSize = left == null ? 0 : left.size();

            if(i < leftSize){
                return left.getIthNode(i);
            }
            else if(i == leftSize + 1){
                return this;
            }
            else{
                // Skipping leftSize + 1 nodes, so subtract them.
                return right.getIthNode(i - leftSize - 1);
            }
        }
        public int size(){
            return size;
        }
        public void insertInOrder(int d){
            if(d <= data){
                if(left == null){
                    left = new TreeNode4(d);
                }
                else{
                    left.insertInOrder(d);
                }
            }
            else{
                if(right == null){
                    right = new TreeNode4(d);
                }
                else{
                    right.insertInOrder(d);
                }
            }
        }

        public TreeNode4 find(int d){
            if(d == data){
                return this;
            }
            else if(d < data){
                return left != null ? left.find(d) : null;
            }
            else{
                return right != null ? right.find(d) : null;
            }
        }
    }

    public int countPathsWithSum(TreeNode root, int targetSum){
        if(root == null){
            return 0;
        }

        // Count paths with sum starting from the root.
        int pathsFromRoot = countPathsWithSumFromNode(root, targetSum, 0);

        // Try the nodes on the left and right.
        int pathsOnLeft = countPathsWithSum(root.left, targetSum);
        int pathsOnRight = countPathsWithSum(root.right, targetSum);
        return pathsFromRoot + pathsOnLeft + pathsOnRight;
    }
    // Return number of paths with this sum starting from this node.
    public int countPathsWithSumFromNode(TreeNode node, int targetSum, int currentSum){
        if(node == null){
            return 0;
        }
        currentSum += node.data;

        int totalPaths = 0;
        if(currentSum == targetSum){    // found the path from the root.
            totalPaths++;
        }
        totalPaths += countPathsWithSumFromNode(node.left, targetSum, currentSum);
        totalPaths += countPathsWithSumFromNode(node.right, targetSum, currentSum);
        return totalPaths;
    }

    public int countPathsWithSum2(TreeNode root, int targetSum){
        return countPathsWithSum2(root, targetSum, 0, new HashMap<Integer, Integer>());
    }

    public int countPathsWithSum2(TreeNode node, int targetSum, int runningSum, HashMap<Integer, Integer> pathCount){
        if(node == null){
            return 0;           // Base case.
        }

        // Count paths with sum ending at the current node.
        runningSum += node.data;
        int sum = runningSum - targetSum;
        int totalPaths = pathCount.containsKey(sum) ? pathCount.get(sum) : 0;

        // if the running sum equals targetSum, then one additional path starts at root.
        // Add in this path.
        if(runningSum == targetSum){
            totalPaths++;
        }

        // increment pathCount, recurse, then decrement pathCount.
        incrementHashTable(pathCount, runningSum, 1);
        totalPaths += countPathsWithSum2(node.left, targetSum, runningSum, pathCount);
        totalPaths += countPathsWithSum2(node.right, targetSum, runningSum, pathCount);
        incrementHashTable(pathCount, runningSum, -1);
        return totalPaths;
    }

    public void incrementHashTable(HashMap<Integer, Integer> hashTable, int key, int delta){
        int newCount = hashTable.getOrDefault(key, 0) + delta;
        if(newCount == 0){          // remove when zero to reduce space usage.
            hashTable.remove(key);
        }
        else{
            hashTable.put(key, newCount);
        }
    }


























































}
