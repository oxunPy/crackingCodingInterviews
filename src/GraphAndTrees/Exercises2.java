package GraphAndTrees;
import java.util.*;
public class Exercises2 {

    // route between nodes.
    enum State{
        VISITING, UNVISITED, VISITED
    }

    class Graph{
        ArrayList<Node> nodes = new ArrayList<Node>();

        public ArrayList<Node> getNodes(){
            return nodes;
        }
    }

    class Node{
        public int data;
        public ArrayList<Node> edges;
        public State state;
        public Node(int data){
            this.data = data;
        }

        public ArrayList<Node> getAdjacent(){
            return edges;
        }
    }

    public boolean search(Graph g, Node start, Node end){
        if(start == end){
            return true;
        }
        // Operates as queue
        LinkedList<Node> q = new LinkedList<>();

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

    class TreeNode{
        public int data;
        public TreeNode left, right, parent;
        public int size;
        public TreeNode(int data){
            this.data = data;
        }
        public TreeNode(int data, TreeNode left, TreeNode right){
            this.data = data;
            this.left = left;
            this.right = right;
        }
        public void setLeft(TreeNode left){
            this.left = left;
        }
        public TreeNode getLeft(){
            return this.left;
        }
        public void setRight(TreeNode right){
            this.right = right;
        }
        public TreeNode getRight(){
            return right;
        }
    }
    // creating minimal binary-search tree.
    public TreeNode createMinimalBST(int[] array){
        return createMinimalBST(array, 0, array.length - 1);
    }
    private TreeNode createMinimalBST(int[] array, int start, int end){
        if(end < start){
            return null;
        }

        int mid = (start + end) / 2;
        TreeNode n = new TreeNode(array[mid]);
        n.left = createMinimalBST(array, start, mid - 1);
        n.right = createMinimalBST(array, mid + 1, end);
        return n;
    }

    // List of Depths.          // recursive solution.
    public void createLevelLinkedList(TreeNode root, ArrayList<LinkedList<TreeNode>> lists, int level){
        if(root == null){
            return;
        }

        LinkedList<TreeNode> list = null;
        if(lists.size() == level){          //Level not contained in list.
            list = new LinkedList<>();
            lists.add(list);
        }
        else{
            list = lists.get(level);
        }

        list.add(root);
        createLevelLinkedList(root.left, lists, level + 1);
        createLevelLinkedList(root.right, lists, level + 1);
    }

    ArrayList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root){
        ArrayList<LinkedList<TreeNode>> lists = new ArrayList<>();
        createLevelLinkedList(root, lists, 0);
        return lists;
    }

    ArrayList<LinkedList<TreeNode>> createLevelLinkedList2(TreeNode root){
        ArrayList<LinkedList<TreeNode>> result = new ArrayList<>();

        LinkedList<TreeNode> current = new LinkedList<>();
        if(root != null){
            current.add(root);
        }

        while(current.size()  > 0){
            result.add(current);

            LinkedList<TreeNode> parents = current;         // go to next level.
            current = new LinkedList<>();           // next level nodes.

            for(TreeNode parent : parents){
                if(parent.left != null){
                    current.add(parent.left);
                }
                if(parent.right != null){
                    current.add(parent.right);
                }
            }
        }
        return result;
    }


    // check binary tree for balanced.
    public int getHeight1(TreeNode node){
        if(node == null){
            return -1;      // base case.
        }
        int leftHeight = getHeight1(node.left);
        int rightHeight = getHeight1(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public int getHeight(TreeNode node){
        if(node == null){
            return -1;
        }

        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    public boolean isBalanced(TreeNode root){
        if(root == null){
            return true;            // base case.
        }

        int heightDifference = getHeight(root.left) - getHeight(root.right);

        if(Math.abs(heightDifference) > 1){
            return false;
        }
        else{       // Recurse
            return isBalanced(root.left) && isBalanced(root.right);
        }
    }

    public int checkHeight(TreeNode root){
        if(root == null){
            return -1;
        }

        int leftHeight = checkHeight(root.left);
        if(leftHeight == Integer.MIN_VALUE){            // pass error up.
            return leftHeight;
        }

        int rightHeight = checkHeight(root.right);
        if(rightHeight == Integer.MIN_VALUE){           // pass error up.
            return rightHeight;
        }

        int heightDifference = leftHeight - rightHeight;
        if(Math.abs(heightDifference) > 1){
            return Integer.MIN_VALUE;           // found error, pass it back.
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public boolean isBalanced2(TreeNode root){
        return checkHeight(root) != Integer.MIN_VALUE;
    }


// valid binary search tree. first approach traverse in - order , then check the result array is sorted
    public boolean checkBST(TreeNode root){
        int[] array = new int[root.size];

        copyBST(root, array);
        for(int i = 1; i < array.length; i++){
            if(array[i] < array[i - 1]){
                return false;
            }
        }
        return true;
    }
    int index = 0;
    private void copyBST1(TreeNode root, int[] array){
        if(root != null){
            copyBST1(root.left, array);
            array[index] = root.data;
            index++;
            copyBST1(root.right, array);
        }
    }
    private void copyBST(TreeNode root, int[] array){
        if(root == null) return ;
        copyBST(root.left, array);
        array[index] = root.data;
        index++;
        copyBST(root.right, array);
    }

    // array is not necessary, because We neven use it other than compare to last element to the previous element.
    Integer last_printed = null;
    boolean checkBST1(TreeNode n){
        if(n == null){
            return true;
        }
        // Check / recurse left.
        if(!checkBST1(n.left)){
            return false;
        }

        // check current
        if(last_printed != null && last_printed >= n.data){
            return false;
        }
        last_printed = n.data;
        // Check / recurse right.
        if(checkBST1(n.right)){
            return false;
        }

        return true;        // All good!
    }

    public boolean checkBST3(TreeNode n){
        return checkBST3(n, null, null);
    }
    private boolean checkBST3(TreeNode n, Integer min, Integer max){
        if(n == null){
            return true;
        }

        if((min != null && n.data <= min) || (max != null && n.data > max)){
            return false;
        }

        // recurse left.
        if(!checkBST3(n.left, min, n.data) || !checkBST3(n.right, n.data, max)){
            return false;
        }
        return true;
    }

    public TreeNode inorderSuccessor(TreeNode n){
        if(n == null){
            return null;
        }

        // found right children -> return leftMost node of rightSubtree
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

    private TreeNode leftMostChild(TreeNode n){
        if(n == null){
            return null;
        }
        while(n.left != null){
            n = n.left;
        }
        return n;
    }


    public Project[] findBuildOrder(String[] projects, String[][] dependencies){
        Graph1 graph = buildGraph(projects, dependencies);
        return orderProjects(graph.getNodes());
    }

    private Graph1 buildGraph(String[] projects, String[][] dependencies){
        Graph1 graph = new Graph1();

        for(String project : projects){
            graph.addNode(project);
        }

        for(String[] dependency : dependencies){
            String first = dependency[0];
            String second = dependency[1];

            graph.addEdge(first, second);
        }

        return graph;
    }

    private Project[] orderProjects(ArrayList<Project> projects){
        Project[] order = new Project[projects.size()];

        int endOfList = addNonDependent(order, projects, 0);

        int toBeProcessed = 0;

        while(toBeProcessed < order.length){
            Project current = order[toBeProcessed];

            if(current == null){
                return null;
            }
            ArrayList<Project> children = current.getChildren();
            for(Project child : children){
                child.decrementDependencies();
            }

            endOfList = addNonDependent(order, projects, endOfList);
            toBeProcessed++;
        }
        return order;
    }

    private int addNonDependent(Project[] order, ArrayList<Project> nodes, int offset){

        for(Project project : nodes){
            if(project.getDependencies() == 0){
                order[offset] = project;
                offset++;
            }
        }
        return offset;
    }


    public class Graph1{
        public ArrayList<Project> nodes;
        public HashMap<String, Project> map = new HashMap<String, Project>();

        public Project getOrCreateNode(String name){
            if(!map.containsKey(name)){
                Project node = new Project(name);
                map.put(name, node);
                nodes.add(node);
            }
            return map.get(name);
        }
        public void addNode(String name){
            if(!map.containsKey(name)){
                Project node = new Project(name);
                nodes.add(node);
                map.put(name, node);
            }
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
    public static class Project{
        private ArrayList<Project> children;
        private int dependencies = 0;
        private HashMap<String, Project> map = new HashMap<String, Project>();
        private String name;

        public enum State{
            COMPLETE, PARTIAL, BLANK;
        }
        public State state = State.BLANK;

        public Project(String n){
            name = n;
        }

        public void addNeighbour(Project node){
            if(!map.containsKey(node.getName())){
                map.put(node.getName(), node);
                children.add(node);
                node.incrementDependencies();
            }
        }

        public void incrementDependencies(){
            dependencies++;
        }
        public void decrementDependencies(){
            dependencies--;
        }

        public ArrayList<Project> getChildren(){
            return children;
        }
        public String getName(){
            return name;
        }
        public int getDependencies(){
            return dependencies;
        }

        public State getState(){
            return state;
        }
        public void setState(State state){
            this.state = state;
        }
    }

    Stack<Project> orderProject2(ArrayList<Project> projects){
        Stack<Project> stack = new Stack<Project>();

        for(Project project : projects){
            if(project.getState() == Project.State.BLANK){
                if(!doDFS(project, stack)){
                    return null;
                }
            }
        }
        return stack;
    }

    public boolean doDFS(Project project, Stack<Project> stack){
        if(project.state == Project.State.PARTIAL){
            return false;
        }

        if(project.state == Project.State.BLANK){
            project.state = Project.State.PARTIAL;
            ArrayList<Project> children = project.getChildren();
            for(Project child : children){
                if(!doDFS(child, stack)){
                    return false;
                }
            }
            project.setState(Project.State.COMPLETE);
            stack.push(project);
        }
        return true;            // All good!
    }

    // First common ancestor
    public TreeNode commonAncestor(TreeNode p, TreeNode q){
        int delta = depth(p) - depth(q);

        TreeNode first = delta > 0 ? q : p; // get shallower node.
        TreeNode second = delta > 0 ? p : q ; // get deeper node.

        second = goUpBy(second, Math.abs(delta));       // move deeper node up.

        while(first != second && first != null && second != null){
            first = first.parent;
            second = second.parent;
        }
        // return either node.
        return first;
    }
    private int depth(TreeNode node){
        int depth = 0;
        while(node != null){
            depth++;
            node = node.parent;
        }
        return depth;
    }

    private TreeNode goUpBy(TreeNode node, int delta){
        while(node != null && delta > 0){
            node = node.parent;
            delta--;
        }
        return node;
    }

    public TreeNode commonAncestor2(TreeNode root, TreeNode p, TreeNode q){
        if(!covers(root, p) || !covers(root, q)){
            return null;
        }
        else if(covers(p, q)){
            return p;
        }
        else if(covers(q, p)){
            return q;
        }

        TreeNode sibling = getSibling(p);
        TreeNode parent = p.parent;

        while(!covers(sibling, q)){
            sibling = getSibling(parent);
            parent = parent.parent;
        }
        return parent;
    }
    private TreeNode getSibling(TreeNode node){
        if(node == null || node.parent == null){
            return null;
        }
        TreeNode parent = node.parent;
        return parent.left == node ? parent.right : parent.left;
    }

    public boolean covers(TreeNode root, TreeNode node){
        if(root == null){
            return false;
        }
        if(root == node){
            return true;
        }
        return covers(root.left, node) || covers(root.right, node);
    }

    // without link to parent.
    public TreeNode commonAncestor4(TreeNode root, TreeNode p, TreeNode q){
        if(!covers(root, p) || !covers(root, q)){
            return null;
        }
        return commonAncestorHelper(root, p, q);
    }

    private TreeNode commonAncestorHelper(TreeNode root, TreeNode p, TreeNode q){
        if(root == null || root == p || root == q){
            return root;
        }

        boolean pIsOnLeft = covers(root.left, p);
        boolean qIsOnLeft = covers(root.right, q);

        if(pIsOnLeft != qIsOnLeft){
            return root;
        }
        TreeNode childSide = pIsOnLeft ? root.left : root.right;
        return commonAncestorHelper(childSide, p, q);
    }

    public TreeNode commonAncestor5(TreeNode root, TreeNode p, TreeNode q){
        if(root == null){
            return null;
        }
        else if(root == p && root == q){
            return root;
        }

        TreeNode x = commonAncestor5(root.left, p, q);          // searching left.
        if(x != null && x != p && x != q){      // already found ancestor
            return x;
        }

        TreeNode y = commonAncestor5(root.right, p, q);
        if(y != null && y != p && y != q){          // already found ancestor.
            return y;
        }

        if(x != null && y != null){         // p and q found in diff. subtrees
            return root;            // this is common ancestor.
        }
        else if(root == p || root == q){
            return root;
        }
        else{
            return x == null ? y : x;       // return the non-null value.
        }
    }

    class Result{
        public TreeNode node;
        public boolean isAncestor;

        public Result(TreeNode n, boolean isAnc){
            node = n;
            isAncestor = isAnc;
        }
    }

    public TreeNode commonAncestor6(TreeNode root, TreeNode p, TreeNode q){
        Result  res = commonAncHelper(root, p, q);
        if(res.isAncestor){
            return res.node;
        }
        return null;
    }
    private Result commonAncHelper(TreeNode root, TreeNode p, TreeNode q){
        if(root == null){
            return new Result(null, false);
        }

        if(root == p && root == q){
            return new Result(root, true);
        }

        Result rx = commonAncHelper(root.left, p, q);
        if(rx.isAncestor){          // found common ancestor.
            return rx;
        }

        Result ry = commonAncHelper(root.right, p, q);
        if(ry.isAncestor){      // found common ancestor.
            return ry;
        }

        if(rx.node != null && ry.node != null){
            return new Result(root, true);      // This is the common ancestor.
        }
        else if(root == p || root == q){
            // if we're currently at p or q, and we also found one of those nodes in a subtree, then this is truly ancestor.

            boolean isAncestor = rx.node != null || ry.node != null;
            return new Result(root, isAncestor);
        }
        else{
            return rx.node == null ? ry : rx;
        }
    }

    public ArrayList<LinkedList<Integer>> allSequences(TreeNode node){
        ArrayList<LinkedList<Integer>> result = new ArrayList<>();

        if(node == null){
            result.add(new LinkedList<>());
            return result;
        }

        LinkedList<Integer> prefix = new LinkedList<>();
        prefix.add(node.data);

        // recurse on left and right subtrees.
        ArrayList<LinkedList<Integer>> leftSeq = allSequences(node.left);       // all sequences in left.
        ArrayList<LinkedList<Integer>> rightSeq = allSequences(node.right);     // all sequences in right.

        // weave together each list from left and right sides.
        for(LinkedList<Integer> left : leftSeq){
            for(LinkedList<Integer> right : rightSeq){
                ArrayList<LinkedList<Integer>> weaved = new ArrayList<LinkedList<Integer>>();
                weaveLists(left, right, weaved, prefix);
                result.addAll(weaved);
            }
        }
        return result;
    }

    private void weaveLists(LinkedList<Integer> first, LinkedList<Integer> second, ArrayList<LinkedList<Integer>> results, LinkedList<Integer> prefix){
        if(first.size() == 0 || second.size() == 0){
            LinkedList<Integer> result = (LinkedList<Integer>) prefix.clone();
            result.addAll(first);
            result.addAll(second);
            results.add(result);
            return;
        }

        int headFirst = first.removeFirst();
        prefix.addLast(headFirst);
        weaveLists(first, second, results, prefix);
        first.addFirst(headFirst);
        prefix.removeLast();

        int headSecond = second.removeFirst();
        prefix.addLast(headSecond);
        weaveLists(first, second, results, prefix);
        second.addFirst(headSecond);
        prefix.removeLast();
    }

    public boolean containsTree(TreeNode t1, TreeNode t2){
        StringBuilder string1 = new StringBuilder();
        StringBuilder string2 = new StringBuilder();

        getOrderString(t1, string1);
        getOrderString(t2, string2);

        return string1.indexOf(string2.toString()) != -1;
    }

    private void getOrderString(TreeNode node, StringBuilder sb){
        if(node == null){
            sb.append("X");         // null indicator.
            return;
        }

        sb.append(node.data);           // add node data;
        getOrderString(node.left, sb);
        getOrderString(node.right, sb);
    }


    public boolean containsTree2(TreeNode t1, TreeNode t2){
        if(t2 == null) return true;         // empty tree is always a subtree.
        return subTree(t1, t2);
    }

    public boolean subTree(TreeNode t1, TreeNode t2){
        if(t1 == null){
            return false;
        }

        if(t1 == t2 && matchTree(t1, t2)){
            return true;
        }
        return subTree(t1.left, t2) || subTree(t1.right, t2);
    }
    private boolean matchTree(TreeNode t1, TreeNode t2){
        if(t1 == null && t2 == null){
            return true;
        }
        else if(t1 == null || t2 == null){
            return false;
        }
        return t1.data == t2.data && matchTree(t1.left, t2.left) && matchTree(t1.right, t2.right);
    }

    // building binary search tree from scratch.
    class TreeNode1{
        private int data;
        private TreeNode1 left;
        private TreeNode1 right;
        private int size;

        public TreeNode1(int d){
            data = d;
            size = 1;
        }

        public TreeNode1 getRandomNode(){
            int leftSize = left != null ? left.size() : 0;
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
                    left = new TreeNode1(d);
                }
                else{
                    left.insertInOrder(d);
                }
            }
            else{
                if(right == null){
                    right = new TreeNode1(d);
                }
                else{
                    right.insertInOrder(d);
                }
            }
        }

        public int size(){
            return size;
        }
        public int data(){
            return data;
        }

        public TreeNode1 find(int d){
            if(data == d){
                return this;
            }
            else if(d <= data){
                return left != null ? left.find(d) : null;
            }
            else{
                return right != null ? right.find(d) : null;
            }
        }
    }


    class Tree{
        TreeNode2 root = null;

        public int size(){
            return root == null ? 0 : root.size();
        }

        public TreeNode2 getRandomNode(){
            if(root == null){
                return null;
            }
            Random random = new Random();
            int i = random.nextInt(size());
            return root.getIthNode(i);
        }

        public void insert(int d){
            if(root == null){
                root = new TreeNode2(d);
            }
            else{
                root.insertInOrder(d);
            }
        }
    }

    class TreeNode2{
        private int data;
        private TreeNode2 left;
        private TreeNode2 right;
        private int size;

        public TreeNode2(int d){
            data = d;
        }


        public int size(){
            return size;
        }

        public TreeNode2 getIthNode(int i){
            int leftSize = left == null ? 0 : left.size();

            if(i == leftSize){
                return this;
            }
            else if(i > leftSize){
                return right.getIthNode(i - leftSize - 1);
            }
            else{
                return left.getIthNode(i);
            }
        }

        public void insertInOrder(int d){
            if(d <= data){
                if(left == null){
                    left = new TreeNode2(d);
                }
                else{
                    left.insertInOrder(d);
                }
            }
            else{
                if(right == null){
                    right = new TreeNode2(d);
                }
                else{
                    right.insertInOrder(d);
                }
            }
        }
    }



    public int countPathWithSum(TreeNode root, int target){
        if(root == null){
            return 0;
        }

        int pathsFromRoot = countPathsWithSumFromRoot(root, target, 0);


        // Try the nodes on the left and right.
        int pathsOnLeft = countPathWithSum(root.left, target);
        int pathsOnRight = countPathWithSum(root.right, target);

        return pathsFromRoot + pathsOnLeft + pathsOnRight;
    }

    private int countPathsWithSumFromRoot(TreeNode node, int target, int currentSum){
        if(node == null){
            return 0;
        }

        currentSum += node.data;

        int totalPaths = 0;
        if(currentSum == target){           // Found a path from a root.
            totalPaths++;
        }

        totalPaths += countPathsWithSumFromRoot(node.left, target, currentSum);
        totalPaths += countPathsWithSumFromRoot(node.right, target, currentSum);
        return totalPaths;
    }

    public int  countPathsWithSum2(TreeNode root, int targetSum){
        return countPathsWithSum2(root, targetSum, 0, new HashMap<Integer, Integer>());
    }

    private int countPathsWithSum2(TreeNode node, int targetSum, int runningSum, HashMap<Integer, Integer> pathCount){
        if(node == null){
            return 0;           // Base case.
        }

        // Count paths with sum ending at the current node.
        runningSum += node.data;
        int sum = runningSum - targetSum;
        int totalPaths  = pathCount.getOrDefault(sum, 0);

        // if runningSum equals targetSum, then one additional path starting at root.
        if(runningSum == targetSum){
            totalPaths++;
        }

        incrementHashTable(pathCount, sum, 1);
        totalPaths += countPathsWithSum2(node.left, targetSum, runningSum, pathCount);
        totalPaths += countPathsWithSum2(node.right, targetSum, runningSum, pathCount);
        incrementHashTable(pathCount, sum, -1);
        return totalPaths;
    }
    private void incrementHashTable(HashMap<Integer, Integer> hashTable, int key, int delta){
        int newCount = hashTable.get(key) + delta;
        if(newCount == 0){          // remove when zero to reduce space usage.
            hashTable.remove(key);
        }
        else{
            hashTable.put(key, newCount);
        }
    }



























}
