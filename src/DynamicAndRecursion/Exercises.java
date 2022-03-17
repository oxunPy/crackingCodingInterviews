package DynamicAndRecursion;

import java.sql.SQLOutput;
import java.util.*;

public class Exercises {
    public int countWays(int n){
        if(n < 0){
            return 0;
        }
        else if(n == 0){
            return 0;
        }
        return countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
    }

    // memoization solution.
    public int countWays2(int n){
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return countWays2(n, memo);
    }
    public int countWays2(int n, int[] memo){
        if(n < 0){
            return 0;
        }
        else if(n == 0){
            return 1;
        }

        else if(memo[n] > -1){
            return memo[n];
        }

        memo[n] = countWays2(n - 1, memo) + countWays2(n - 2, memo) + countWays2(n - 3, memo);
        return memo[n];
    }
    static class Point{
        int x, y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString(){
            StringBuilder res = new StringBuilder(5);
            res.append("(" + x);
            res.append(",");
            res.append(y + ")");
            return res.toString();
        }
        @Override
        public boolean equals(Object obj){
            if(obj == null || !(obj instanceof Point)){
                return false;
            }
            if(obj == this){
                return true;
            }
            Point that = (Point) obj;
            return this.x == that.x && this.y == that.y;
        }

        @Override
        public int hashCode(){
            return Objects.hash(x, y);
        }
    }


    public ArrayList<Point> getPath(boolean[][] maze){
        if(maze == null || maze.length == 0) return null;

        ArrayList<Point> path = new ArrayList<>();
        if(getPath(maze, maze.length - 1, maze[0].length - 1, path)){
            return path;
        }
        return null;
    }

    public boolean getPath(boolean[][] maze, int row, int col, ArrayList<Point> path){
        if(col < 0 || row < 0 || !maze[row][col]) {
            return false;
        }

        boolean isAtOrigin = (row == 0) && (col == 0);

        if(isAtOrigin || getPath(maze, row - 1, col, path) || getPath(maze, row, col - 1, path)){
            Point p = new Point(row, col);
            path.add(p);
            return true;                // top-down approach.
        }
        return false;
    }

    // using dynamic programming.
    public static ArrayList<Point> getPath2(boolean[][] maze){
        if(maze == null || maze.length == 0) return null;

        ArrayList<Point> path = new ArrayList<>();
        HashSet<Point> failedPoints = new HashSet<>();

        if(getPath2(maze, maze.length - 1, maze[0].length - 1, path, failedPoints)){
            System.out.println(failedPoints);
            return path;
        }
        return null;
    }

    public static  boolean getPath2(boolean[][] maze, int row, int col, ArrayList<Point> path, HashSet<Point> failedPoints){
        // if out of bounds or not available, return.
        if(row < 0 || col < 0 || !maze[row][col]){
            return false;
        }
        Point p = new Point(row, col);
        // we have already visited this cell, return.
        if(failedPoints.contains(p)){
            return false;
        }

        boolean isAtOrigin = (row == 0) && (col == 0);

        // if there is path from start to my currrent location, add my location.
        if(isAtOrigin || getPath2(maze, row - 1, col, path, failedPoints) || getPath2(maze, row, col - 1, path, failedPoints)){
            path.add(p);
            return true;
        }
        failedPoints.add(p);            // Cache result;
        return false;
    }

    public int magicFast(int[] array){
        return magicFast(array, 0, array.length - 1);
    }
    private int magicFast(int[] array, int start, int end){         // if all elements are distinct
        if(end < start){
             return -1;
        }

        int mid = (start + end) / 2;

        if(array[mid] == mid){
            return mid;
        }
        else if(array[mid] > mid){
            return magicFast(array, start, mid - 1);
        }
        else{
            return magicFast(array, mid + 1, end);
        }
    }


    public int magicFast2(int[] array, int start, int end){
        if(start > end){
            return -1;
        }

        int midIndex = (start + end) / 2;
        int midValue = array[midIndex];

        if(midValue == midIndex){
            return midValue;
        }

        // Search left.
        int leftIndex = Math.min(midIndex - 1, midValue);
        int left = magicFast(array, start, leftIndex);
        if(left >= 0){
            return left;
        }

        // Search right.
        int rightIndex = Math.max(midIndex + 1, midValue);
        int right = magicFast2(array, rightIndex, end);
        return right;
    }

    public ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer> set, int index){
        ArrayList<ArrayList<Integer>> allSubsets;
        if(index == set.size()){    //  base case: add empty set.
            allSubsets = new ArrayList<ArrayList<Integer>>();
            allSubsets.add(new ArrayList<Integer>());       // Empty set.
        }
        else{
            allSubsets = getSubsets(set, index + 1);
            int item = set.get(index);
            ArrayList<ArrayList<Integer>> moreSubsets = new ArrayList<ArrayList<Integer>>();

            for(ArrayList<Integer> subset : allSubsets){
                ArrayList<Integer> newSubset = new ArrayList<>();
                newSubset.addAll(subset);
                newSubset.add(item);
                moreSubsets.add(newSubset);
            }
            allSubsets.addAll(moreSubsets);
        }
        return allSubsets;          // this solution will be O(n * pow(2, n)) in time and space.
    }

    public ArrayList<ArrayList<Integer>> getSubset2(ArrayList<Integer> set){
        ArrayList<ArrayList<Integer>> allSubsets = new ArrayList<ArrayList<Integer>>();

        int max = 1 << set.size();      // compute 2 ^ n;
        for(int k = 0; k < max; k++){
            ArrayList<Integer> subset = convertIntToSet(set, k);
            allSubsets.add(subset);
        }
        return allSubsets;
    }

    private ArrayList<Integer> convertIntToSet(ArrayList<Integer> set, int x){
        ArrayList<Integer> subset = new ArrayList<Integer>();
        int index = 0;
        for(int k = x; k > 0; k >>= 1){
            if((k & 1) == 1){
                subset.add(set.get(index));
            }
            index++;
        }
        return subset;
    }


    public int minProduct(int a, int b){
        int smaller = a > b ? b : a;
        int bigger = a > b ? a : b;

        return minProductHelper(smaller, bigger);
    }

    private int minProductHelper(int smaller, int bigger){
        // base cases
        if(smaller == 0){
            return 0;               // 0 x bigger = 0;
        }
        else if(smaller == 1){
            return bigger;          // 1 x bigger = bigger;
        }

        // Compute half. if uneven, compute other half. If even, double it.
        int s = smaller >> 1;       // s = smaller / 2;
        int side1 = minProduct(s, bigger);
        int side2 = side1;

        if(smaller % 2 == 1){
            side2 = minProductHelper(smaller - s, bigger);
        }
        return side1 + side2;
    }

    public int minProduct(int smaller, int bigger, int[] memo){
        if(smaller == 0){
            return 0;
        }
        else if(smaller == 1){
            return bigger;
        }
        else if(memo[smaller] > 0){
            return memo[smaller];
        }

        // Compute half. If uneven, compute other half. If even, double it.
        int s = smaller >> 1;           // Divide by 2;
        int side1 = minProduct(s, bigger, memo);
        int side2 = side1;

        if(smaller % 2 == 1){
            side2 = minProduct(smaller - s, bigger, memo);
        }
        memo[smaller] = side1 + side2;
        return memo[smaller];
    }

    public int minProduct2(int a, int b){
        int smaller = a > b ? b : a;
        int bigger = a > b ? a : b;

        return minProduct2Helper(smaller, bigger);
    }

    public int minProduct2Helper(int smaller, int bigger){
        if(smaller == 0){
            return 0;
        }
        else if(smaller == 1){
            return bigger;
        }
        int s = smaller >> 1;
        int side1 = minProduct2Helper(s, bigger);
        int side2 = side1;

        if(smaller % 2 == 1){
            return side1 + side2 + bigger;
        }
        return side1 + side2;
    }

    static class Tower{
        private Stack<Integer> disks;
        private int index;              // top index;

        public Tower(int i){
            disks = new Stack<Integer>();
            index = i;
        }

        public int index(){
            return index;
        }

        public void add(int d){
            if(!disks.isEmpty() && disks.peek() <= d){
                System.out.println("Error placing disk " + d);
            }
            else{
                disks.push(d);
            }
        }

        public void moveTopTo(Tower t){
            int top = disks.pop();
            t.add(top);
        }
        public void moveDisks(int n, Tower destination, Tower buffer){
            if(n > 0){
                moveDisks(n - 1, buffer, destination);
                moveTopTo(destination);
                buffer.moveDisks(n - 1, destination, this);
            }
        }
    }

    public ArrayList<String> getPerms(String str){
        if(str == null) {
            return null;
        }
        ArrayList<String> permutations = new ArrayList<String>();

        if(str.length() == 0){      // base case.
            permutations.add("");
            return permutations;
        }

        char first = str.charAt(0);     // get the first char.
        String remainder = str.substring(1);       // remove the first char.

        ArrayList<String> words = getPerms(remainder);

        for(String word : words){
            for(int j = 0; j <= word.length(); j++){
                String s = insertCharAt(word, first, j);
                permutations.add(s);
            }
        }
        return permutations;
    }
    // Insert char c at index i in word.
    public String insertCharAt(String word, char c, int i){
        String start = word.substring(0, i);
        String end = word.substring(i);
        return start + c + end;
    }

    public ArrayList<String> getPerms2(String remainder){
        int len = remainder.length();
        ArrayList<String> result = new ArrayList<>();

        // Base case.
        if(len == 0){
            result.add("");         // be sure to return empty string.
            return result;
        }

        for(int i = 0; i < len; i++){
            // remove char i and find permutations of remaining chars.
            String before = remainder.substring(0, i);
            String after = remainder.substring(i + 1, len);
            ArrayList<String> partial = getPerms2(before + after);

            // prepend char i to each permutations.
            for(String s : partial){
                result.add(remainder.charAt(i) + s);
            }
        }
        return result;
    }

    public ArrayList<String> getPerms3(String str){
        ArrayList<String> result = new ArrayList<>();
        getPerms3("", str, result);
        return result;
    }

    public void getPerms3(String prefix, String remainder, ArrayList<String> result){
        if(remainder.length() == 0){
            result.add(prefix);
        }

        int len = remainder.length();
        for(int i = 0; i < len; i++){
            String before = remainder.substring(0, i);
            String after = remainder.substring(i + 1, len);
            char c = remainder.charAt(i);
            getPerms3(prefix + c, before + after, result);
        }
    }

    public static ArrayList<String> printPerm(String s){
        ArrayList<String> result = new ArrayList<>();
        HashMap<Character, Integer> map = buildFreqTable(s);
        printPerm(map, "", s.length(), result);
        return result;
    }
    private static HashMap<Character, Integer> buildFreqTable(String s){
        HashMap<Character, Integer> table = new HashMap<Character, Integer>();
        for(char c : s.toCharArray()){
            if(!table.containsKey(c)){
                table.put(c, 0);
            }
            table.put(c, table.get(c) + 1);
        }
        return table;
    }

    private static void printPerm(HashMap<Character, Integer> map, String prefix, int remaining, ArrayList<String> result){
        // Base case : permutation has been completed.
        if(remaining == 0){
            result.add(prefix);
            return ;
        }

        for(Character c : map.keySet()){
            int count = map.get(c);
            if(count > 0){
                map.put(c, count - 1);
                printPerm(map, prefix + c, remaining - 1, result);
                map.put(c, count);
            }
        }
    }


    public Set<String> generateParens(int remaining){
        Set<String> set = new HashSet<>();

        if(remaining == 0){
            set.add("");
        }
        else{
            Set<String> prev = generateParens(remaining - 1);
            for(String str : prev){
                for(int i = 0; i < str.length(); ++i){
                    if(str.charAt(i) == '('){
                        String s = insertInside(str, i);
                        // add s to set if it's not already in there. Note : HashSet automatically check for duplicates
                        // before adding, so an explicit check is not necessary.
                        set.add(s);
                    }
                }
                set.add("()" + str);
            }
        }
        return set;
    }
    public String insertInside(String s, int leftIndex){
        String left = s.substring(0, leftIndex + 1);
        String right = s.substring(leftIndex + 1, s.length());
        return left + "()" + right;
    }

    public void addParen(ArrayList<String> list, int leftRem, int rightRem, char[] str, int index){
        if(leftRem < 0 || rightRem < leftRem){      // invalid state.
            return;
        }

        else if(leftRem == 0 && rightRem == 0){      // Out of left and right parentheses.
            list.add(String.copyValueOf(str));
        }
        else{
            // add left and recurse
            str[index] = '(';
            addParen(list, leftRem - 1, rightRem, str, index + 1);

            // add right and recurse.
            str[index] = ')';
            addParen(list, leftRem, rightRem - 1, str, index + 1);
        }
    }
    public ArrayList<String> generateParens2(int n){
        char[] str = new char[n * 2];
        ArrayList<String> list = new ArrayList<>();
        addParen(list, n, n, str, 0);
        return list;
    }

    enum Color{
        BLACK, WHITE, RED, YELLOW, GREEN,
    }

    public boolean paintFill(Color[][] screen, int r, int c, Color nColor){
        if(screen[r][c] == nColor){
            return false;
        }
        return PaintFill(screen, r, c, screen[r][c], nColor);
    }
    private boolean PaintFill(Color[][] screen, int r, int c, Color oColor, Color nColor){
        if(r < 0 || r >= screen.length || c < 0 || c >= screen[0].length){
            return false;
        }

        if(screen[r][c] == oColor){
            screen[r][c] = nColor;
            PaintFill(screen, r - 1, c, oColor, nColor);        // up
            PaintFill(screen, r + 1, c, oColor, nColor);        // down7
            PaintFill(screen, r, c - 1, oColor, nColor);        // left
            PaintFill(screen, r, c + 1, oColor, nColor);        // right
        }
        return true;
    }

    public int makeChange(int n){
        int[] denoms = {25, 10, 5, 1};
        return makeChange(n, denoms, 0);
    }
    private int makeChange(int amount, int[] denoms, int index){
        if(index >= denoms.length - 1){     //last denom.
            return 1;
        }
        int denomAmount = denoms[index];
        int ways = 0;

        for(int i = 0; i * denomAmount <= amount; i++){
            int amountRemaining = amount -  i * denomAmount;
            ways += makeChange(amountRemaining, denoms, index + 1);
        }
        return ways;
    }

    // optimizing previous algorithm, by storing previously computed values. We'll need to store a mapping from
    // each pair (amount, index)    to the precomputed result.
    public int makeChange2(int n){
        int[] denoms = {25, 10, 5, 1};
        int[][] map = new int[n + 1][denoms.length];    // precomputed vals.
        return makeChange2(n, denoms, 0, map);
    }

    public int makeChange2(int amount, int[] denoms, int index, int[][] map){
        if(map[amount][index] > 0){         //retrieve value.
            return map[amount][index];
        }

        if(index >= denoms.length - 1) return 1;        // one denom remaining.

        int denomAmount = denoms[index];
        int ways = 0;
        for(int i = 0; i * denomAmount <= amount; i++){
            int remainingAmount = amount - i * denomAmount;
            ways += makeChange2(remainingAmount, denoms, index + 1, map);
        }
        map[amount][index] = ways;
        return ways;
    }

    private static final int GRID_SIZE = 8;

    public void placeQueens(int row, Integer[] columns, ArrayList<Integer[]> results){
        if(row == GRID_SIZE){
            results.add(columns.clone());
        }
        else{
            for(int col = 0; col < GRID_SIZE; col++){
                if(checkValid(columns, row, col)){
                    columns[row] = col;     // Place queens
                    placeQueens(row + 1, columns, results);
                }
            }
        }
    }

    // Check if (row1, col1) is valid spot for a queue by checking if there is a queen in the same column,
    // or diagonal. We don't need to check it for queens in the same row because the calling placeQueens only attempts
    // to place one queen at a time. We know this is row empty.
    private boolean checkValid(Integer[] columns, int row1, int column1){
        for(int row2 = 0; row2 < row1; row2++){
            int column2 = columns[row2];
            // check (row2, col2) invalidates (row1, col1) as a queen spot.

            // check if rows have a queen in the same column.
            if(column1 == column2){
                return false;
            }

            // check diagonals : if the distance between columns equals distance between rows, then they are in the same diagonal.
            int columnDistance = Math.abs(column1 - column2);

            int rowDistance = row1 - row2;      // row1 > row2, so no need for abs.

            if(rowDistance == columnDistance){
                return false;
            }
        }
        return true;
    }

    public int createStack(ArrayList<Box> boxes){
        // Sort in decdending order by height.
        Collections.sort(boxes, new BoxComparator());
        int maxHeight = 0;

        for(int i = 0; i < boxes.size(); i++){
            int height = createStack(boxes, i);
            maxHeight = Math.max(maxHeight, height);
        }
        return maxHeight;
    }

    private int createStack(ArrayList<Box> boxes, int bottomIndex){
        Box bottom = boxes.get(bottomIndex);
        int maxHeight = 0;
        for(int i = bottomIndex + 1; i < boxes.size(); i++){
            if(boxes.get(i).canBeAbove(bottom)){
                int height = createStack(boxes, i);
                maxHeight = Math.max(height, maxHeight);
            }
        }
        maxHeight += bottom.height;
        return maxHeight;
    }


    class Box{
        int width, height, depth;
        public Box(int x, int y, int z){
            this.width = x;
            this.height = y;
            this.depth = z;
        }

        public boolean canBeAbove(Box b){
            return this.width > b.width && this.depth > b.depth;
        }
    }

    class BoxComparator implements Comparator<Box>{
        @Override
        public int compare(Box x, Box y){
            return y.height - x.height;
        }
    }

    public int createStack2(ArrayList<Box> boxes){
        Collections.sort(boxes, new BoxComparator());

        int maxHeight = 0;
        int[] stackMap = new int[boxes.size()];

        for(int i = 0; i < boxes.size(); i++){
            int height = createStack2(boxes, i, stackMap);
            maxHeight = Math.max(height, maxHeight);
        }
        return maxHeight;
    }

    private int createStack2(ArrayList<Box> boxes, int bottomIndex, int[] stackMap){
        if(bottomIndex < boxes.size() && stackMap[bottomIndex] > 0){
            return stackMap[bottomIndex];
        }

        Box bottom = boxes.get(bottomIndex);
        int maxHeight = 0;
        for(int i = bottomIndex + 1; i < boxes.size(); i++){
            if(boxes.get(i).canBeAbove(bottom)){
                int height = createStack2(boxes, i, stackMap);
                maxHeight = Math.max(height, maxHeight);
            }
        }
        maxHeight += bottom.height;
        stackMap[bottomIndex] = maxHeight;
        return maxHeight;
    }

    public int createStack3(ArrayList<Box> boxes){
        Collections.sort(boxes, new BoxComparator());
        int[] stackMap = new int[boxes.size()];
        return createStack3(boxes, null, 0, stackMap);
    }

    private int createStack3(ArrayList<Box> boxes, Box bottom, int offset, int[] stackMap){
        if(offset >= boxes.size()){     // Base case
            return 0;
        }

        // height with this bottom.
        Box newBottom = boxes.get(offset);
        int heightWithBottom = 0;

        if(bottom == null || newBottom.canBeAbove(bottom)){
            if(stackMap[offset] == 0){
                stackMap[offset] = createStack3(boxes, newBottom, offset + 1, stackMap);
                stackMap[offset] += newBottom.height;
            }
            heightWithBottom = stackMap[offset];
        }

        // without this bottom/
        int heightWithoutBottom = createStack3(boxes, bottom, offset + 1, stackMap);

        // return better of two options.
        return Math.max(heightWithBottom, heightWithoutBottom);
    }

    public int countEval(String s, boolean result){
        if(s.length() == 0) return 0;
        if(s.length() == 1) return stringToBool(s) == result ? 1 : 0;

        int ways = 0;

        for(int i = 1; i < s.length(); i += 2){
            char c = s.charAt(i);
            String left = s.substring(0, i);
            String right = s.substring(i + 1, s.length());

            // evaulate each side for each result.
            int leftTrue = countEval(left, true);
            int leftFalse = countEval(left, false);
            int rightTrue = countEval(right, true);
            int rightFalse = countEval(right, false);

            int total = (leftTrue + leftFalse) * (rightTrue + rightFalse);

            int totalTrue = 0;

            if(c == '^'){       // required one true and one false.
                totalTrue = leftTrue * rightFalse + leftFalse * rightTrue;
            }
            else if(c == '&'){          // required both true
                totalTrue = leftTrue * rightTrue;
            }
            else if(c == '|'){      // required anything but not both false.
                totalTrue = leftTrue * rightTrue + leftTrue * rightFalse + leftFalse * rightTrue;
            }
            int subWays = result ? totalTrue : total - totalTrue;
            ways += subWays;
        }
        return ways;
    }

    private boolean stringToBool(String c){
        return c.equals("1") ? true : false;
    }

    public int countEval(String s, boolean result, HashMap<String, Integer> memo){
        if(s.length() == 0) return 0;
        if(s.length() == 1) return stringToBool(s) ? 1 : 0;
        if(memo.containsKey(result + s)){
            return memo.get(result + s);
        }
        int ways = 0;

        for(int i = 1; i < s.length(); i += 2){
            char c = s.charAt(i);

            String left = s.substring(0, i);
            String right = s.substring(i + 1, s.length());

            int leftTrue = countEval(left, true, memo);
            int leftFalse = countEval(left, false, memo);
            int rightTrue = countEval(right, true, memo);
            int rightFalse = countEval(right, false, memo);

            int total = (leftTrue + leftFalse) * (rightTrue + rightFalse);
            int totalTrue = 0;
            if(c == '^'){
                totalTrue = leftTrue * rightFalse + leftFalse * rightTrue;
            }
            else if(c == '&'){
                totalTrue = leftTrue * rightTrue;
            }
            else if(c == '|'){
                totalTrue = leftTrue * rightTrue + leftTrue * rightFalse + leftFalse * rightTrue;
            }

            int subWays = result ? totalTrue : total - totalTrue;
            ways += subWays;
        }
        memo.put(result + s, ways);
        return ways;
    }

































    public static void main(String[] args) {
        boolean[][] maze = {
                            {true, true, false, true, true, true, true},
                            {true, true, true, false, true, true, true},
                            {false, true, false, true, true, false, true},
                            {true, true, true, true, true, true, true},
                            {true, true, false, true, true, true, true},
                            };

        System.out.println(getPath2(maze));
        Tower[] towers = new Tower[3];
        for(int i = 0; i < towers.length; i++){
            towers[i] = new Tower(i);
        }
        for(int i = 3; i > 0; i--){
            towers[0].add(i);
        }

        towers[0].moveDisks(3, towers[2], towers[1]);
        System.out.println(towers[0].disks);
        System.out.println(towers[1].disks);
        System.out.println(towers[2].disks);



    }
























}
