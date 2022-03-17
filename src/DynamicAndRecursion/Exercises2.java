package DynamicAndRecursion;
import java.util.*;
public class Exercises2 {

    // Triple steps
    public int countWays(int n){
        if(n == 0){
            return 1;
        }
        else if(n < 0){
            return 0;
        }
        else{
            return countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
        }
    }

    // Triple steps using memoization.
    public int countWays1(int n){
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return countWays1(n, memo);
    }
    private int countWays1(int n, int[] memo){
        if(n < 0){
            return 0;
        }
        else if(n == 0){
            return 1;
        }
        else if(memo[n] > -1){
            return memo[n];
        }
        else{
            memo[n] = countWays1(n - 1, memo) + countWays1(n - 2, memo) + countWays1(n - 3, memo);
            return memo[n];
        }
    }

    // Robot in grid.
    class Point{
        public int x, y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    ArrayList<Point> getPath(boolean[][] maze){
        if(maze == null || maze.length == 0) return null;
        ArrayList<Point> path = new ArrayList<>();
        if(getPath(maze, maze.length - 1, maze[0].length - 1, path)){
            return path;
        }
        return null;
    }

    public boolean getPath(boolean[][] maze, int row, int col, ArrayList<Point> path){
        if(col < 0 || row < 0 || !maze[row][col]){
            return false;
        }

        boolean isOrigin = (row == 0) && (col == 0);
        if(isOrigin || getPath(maze, row - 1, col, path) || getPath(maze, row, col - 1, path)){
            Point p = new Point(row, col);
            path.add(p);
            return true;
        }
        return false;
    }

    // cashing previous solution.
    public ArrayList<Point> getPath2(boolean[][] maze){
        if(maze == null || maze.length == 0) return null;

        HashSet<Point> failedPoints = new HashSet<>();
        ArrayList<Point> path = new ArrayList<>();
        if(getPath2(maze, maze.length - 1, maze[0].length - 1, path, failedPoints)){
            return path;
        }
        return null;
    }

    private boolean getPath2(boolean[][] maze, int row, int col, ArrayList<Point> path, HashSet<Point> failedPoints){
        if(row < 0 || col < 0 || !maze[row][col]){
            return false;
        }
        Point p = new Point(row, col);
        if(failedPoints.contains(new Point(row, col))){
            return false;
        }

        boolean isOrigin = (row == 0) && (col == 0);
        if(isOrigin || getPath2(maze, row - 1, col, path, failedPoints) || getPath2(maze, row, col - 1, path, failedPoints)){
            path.add(p);
            return true;
        }
        failedPoints.add(p);            // cache result.
        return false;
    }

    // Magic index.
    public int magicSlow(int[] array){
        for(int i = 0; i < array.length; i++){
            if(array[i] == i){
                return i;
            }
        }
        return -1;
    }

    // find magic index with distinct elements
    public int magicFast(int[] array){
        return magicFast(array, 0, array.length - 1);
    }
    private int magicFast(int[] array, int start, int end){
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
            return magicFast(array, mid + 1, start);
        }
    }

    // find magic index with duplicates.
    public int magicIndex(int[] array){
        return magicIndex(array, 0, array.length - 1);
    }
    private int magicIndex(int[] array, int start, int end){
        if(end < start) return -1;

        int midIndex = (start + end) / 2;
        int midValue = array[midIndex];

        if(midValue == midIndex){
            return midIndex;
        }

        // Search left.
        int leftIndex = Math.min(midIndex - 1, midValue);
        int left = magicFast(array, start, leftIndex);
        if(left >= 0){
            return left;
        }

        // Search right
        int rightIndex = Math.max(midIndex + 1, midValue);
        int right = magicFast(array, rightIndex, end);
        return right;
    }
    // Power set.
    public ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer> set, int index){
        ArrayList<ArrayList<Integer>> allSubsets;

        if(set.size() == index){    // add empty set.
            allSubsets = new ArrayList<ArrayList<Integer>>();
            allSubsets.add(new ArrayList<>());      // Empty set.
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
        return allSubsets;
    }

    public ArrayList<ArrayList<Integer>> getSubsets2(ArrayList<Integer> set, int index){
        int max = 1 << set.size();          // Compute 2^n
        ArrayList<ArrayList<Integer>> allSubsets = new ArrayList<ArrayList<Integer>>();
        for(int k = 0; k < max; k++){
            ArrayList<Integer> subset = convertIntToSet(k, set);
            allSubsets.add(subset);
        }
        return allSubsets;
    }

    private ArrayList<Integer> convertIntToSet(int x, ArrayList<Integer> set){
        ArrayList<Integer> subset = new ArrayList<>();
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
        int bigger = a > b ? a : b;
        int smaller = a > b ? b : a;

        return minProductHelper(smaller, bigger);
    }

    public int minProductHelper(int smaller, int bigger){
        if(smaller == 0){
            return 0;
        }
        else if(smaller == 1){
            return bigger;
        }

        int s = smaller >> 1;
        int side1 = minProductHelper(s, bigger);
        int side2 = side1;

        if(smaller % 2 == 1){
            side2 = minProduct(smaller - s, bigger);
        }
        return side1 + side2;
    }

    // Better solution.
    public int minProduct2(int a, int b){
        int bigger = a > b ? a : b;
        int smaller = a > b ? b : a;

        int[] memo = new int[smaller + 1];
        return minProductHelper2(smaller, bigger, memo);
    }

    private int minProductHelper2(int smaller, int bigger, int[] memo){
        if(smaller == 0){
            return 0;
        }
        else if(smaller == 1){
            return bigger;
        }
        else if(memo[smaller] > 0){
            return memo[smaller];
        }

        // Compute half. If uneven, compute other half.
        int s = smaller >> 1;
        int side1 = minProductHelper2(s, bigger, memo);
        int side2 = side1;

        if(smaller % 2 == 1){
            side2 = minProductHelper2(smaller - s, bigger, memo);
        }
        memo[smaller] = side1 + side2;
        return memo[smaller];
    }


    public int minProduct4(int a, int b){
        int smaller = a > b ? b : a;
        int bigger = a > b ? a : b;

        return minProductHelper4(smaller, bigger);
    }
    private int minProductHelper4(int smaller, int bigger){
        if(smaller == 0){
            return 0;
        }
        else if(smaller == 1){
            return bigger;
        }

        int s = smaller >> 1;   // divide by 2
        int halfProd = minProductHelper4(s, bigger);

        if(smaller % 2 == 0){
            return halfProd + halfProd;
        }
        else{
            return halfProd + halfProd + bigger;
        }
    }

    class Tower{
        private Stack<Integer> disks;
        private int index;
        public Tower(int i){
            disks = new Stack<Integer>();
            index = i;
        }

        public int index(){
            return index;
        }

        public void add(int d){
            if(!disks.isEmpty() && disks.peek() <= d){
                System.out.print("Error placing disk " + d);
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
            moveDisks(n - 1, buffer, destination);
            moveTopTo(destination);
            buffer.moveDisks(n - 1, destination, this);
        }
    }


    public ArrayList<String> getPerms(String str){
        if(str == null){
            return null;
        }

        ArrayList<String> permutations = new ArrayList<>();
        if(str.length() == 0){  // base case.
            permutations.add("");
            return permutations;
        }

        char first =str.charAt(0);      // get first char.
        String remainder = str.substring(1);        // remove first char.

        ArrayList<String> words = getPerms(remainder);
        for(String word : words){
            for(int j = 0; j < word.length(); j++){
                String s = insertCharAt(word, first ,j);
                permutations.add(s);
            }
        }
        return permutations;
    }

    private String insertCharAt(String word, char c, int i){
        String start = word.substring(0, i);
        String end = word.substring(i);
        return start + c + end;
    }


    public ArrayList<String> getPerms2(String remainder){
        int len = remainder.length();
        ArrayList<String> result = new ArrayList<>();

        // Base case
        if(len == 0){
            result.add("");
            return result;
        }

        for(int i = 0; i < len; i++){
            // remove char i and find permutations of other chars.
            String before = remainder.substring(0, i);
            String after = remainder.substring(i + 1, remainder.length());

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
    private void getPerms3(String prefix, String remainder, ArrayList<String> result){
        if(remainder.length() == 0){
            result.add(prefix);
        }

        int len = remainder.length();
        for(int i = 0; i < len; i++){
            String before = remainder.substring(0, i);
            String after = remainder.substring(i + 1, remainder.length());
            char c = remainder.charAt(i);
            getPerms3(prefix + c, before + after, result);
        }
    }


    // Permutations with duplicates.

    public ArrayList<String> printPerms5(String s){
        ArrayList<String> result = new ArrayList<>();

        HashMap<Character, Integer> map = buildCharFreq(s);
        printPerms5(map, "", s.length(), result);
        return result;
    }

    private HashMap<Character, Integer> buildCharFreq(String s){
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for(char c : s.toCharArray()){
            if(!map.containsKey(c)){
                map.put(c, 0);
            }
            map.put(c, map.get(c) + 1);
        }
        return map;
    }

    private void printPerms5(HashMap<Character, Integer> map, String prefix, int remaining, ArrayList<String> result){
        if(remaining == 0){
            result.add(prefix);
            return ;
        }

        for(Character c : map.keySet()){
            int count = map.get(c);
            if(count > 0){
                map.put(c, count - 1);
                printPerms5(map, prefix + c, remaining - 1, result);
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
                for(int i = 0; i < str.length(); i++){
                    if(str.charAt(i) == '('){
                        String s = insertInside(str, i);
                        set.add(s);
                    }
                }
                set.add("()" + str);
            }
        }
        return set;
    }

    private String insertInside(String s, int leftIndex){
        String left = s.substring(0, leftIndex + 1);
        String right = s.substring(leftIndex + 1, s.length());
        return left + "()" + right;
    }


    public void addParens(ArrayList<String> list, int leftRem, int rightRem, char[] str, int index){
        if(leftRem < 0 || rightRem < leftRem){
            return ;            // invalid state.
        }

        if(leftRem == 0 && rightRem == 0){
            list.add(String.copyValueOf(str));
        }
        else{
            str[index] = '(';           // add left and recurse.
            addParens(list, leftRem - 1, rightRem, str, index + 1);

            str[index] = ')';       // add right and recurse
            addParens(list, leftRem, rightRem - 1, str, index + 1);
        }
    }

    ArrayList<String> generateParens2(int count){
        char[] str = new char[count * 2];
        ArrayList<String> list = new ArrayList<>();
        addParens(list, count, count, str, 0);
        return list;
    }


    // Paint fill.
    enum Color{
        BLACK, WHITE, YELLOW, GREEN, RED
    }

    boolean PaintFill(Color[][] screen, int r, int c, Color nColor){
        if(screen[r][c] == nColor){
            return false;
        }
        return PaintFill(screen, r, c, screen[r][c], nColor);
    }

    boolean PaintFill(Color[][] screen, int r, int c, Color oColor, Color nColor){
        if(r < 0 || r >= screen.length || c < 0 || c >= screen[0].length){
            return false;
        }

        if(screen[r][c] == oColor){
            screen[r][c] = nColor;
            PaintFill(screen, r - 1, c, oColor, nColor); // up
            PaintFill(screen, r + 1, c, oColor, nColor); // down
            PaintFill(screen, r, c - 1, oColor, nColor); // left
            PaintFill(screen, r, c + 1, oColor, nColor); // right
        }
        return true;
    }


    public int makeChange(int amount, int[] denoms, int index){
        if(index >= denoms.length - 1){
            return 1;           // last denom.
        }
        int denomAmount = denoms[index];
        int ways = 0;
        for(int i = 0; denomAmount * i <= amount; i++){
            int amountRemaining = amount - denomAmount * i;
            ways += makeChange(amountRemaining, denoms, index + 1);
        }
        return ways;
    }

    int makeChange(int amount){
        int[] denoms = {25, 10, 5, 1};
        return makeChange(amount, denoms, 0);
    }

    // using memoization.
    public int makeChange2(int n){
        int[] denoms = {25, 10, 5, 1};
        int[][] map = new int[n + 1][denoms.length];        // precomputed vals.
        return makeChange2(n, denoms, 0, map);
    }

    private int makeChange2(int amount, int[] denoms, int index, int[][] map){
        if(map[amount][index] > 0){     // retrive value.
            return map[amount][index];
        }

        if(index >= denoms.length - 1){
            return 1;               // one denom remaining.
        }
        int denomAmount = denoms[index];
        int ways = 0;
        for(int i = 0; i * denomAmount <= amount; i++){
            int remainingAmount = amount - i * denomAmount;
            ways += makeChange2(remainingAmount, denoms, index + 1, map);
        }
        map[amount][index] = ways;
        return ways;
    }

    int GRID_SIZE = 8;

    public void placeQueens(int row, Integer[] columns, ArrayList<Integer[]> results){
        if(row == GRID_SIZE){
            results.add(columns.clone());
        }
        else{
            for(int col = 0; col < GRID_SIZE; col++){
                if(checkValid(columns, row, col)){
                    columns[row] = col;     // place Queen.
                    placeQueens(row + 1, columns, results);
                }
            }
        }
    }

    private boolean checkValid(Integer[] columns, int row1, int column1){
        for(int row2 = 0; row2 < row1; row2++){
            int column2 = columns[row2];

            if(column2 == column1){
                return false;
            }

            // check diagonals
            int columnDistance = Math.abs(column1 - column2);

            int rowDistance = row1 - row2;

            if(columnDistance == rowDistance){
                return false;
            }
        }
        return true;
    }

    class Box{
        int height, width, depth;

        public Box(int h, int w, int d){
            this.height = h;
            this.width = w;
            this.depth = d;
        }

        public boolean canBeAbove(Box y){
            return height < y.height && width < y.width && depth < y.depth;
        }
    }

    public class BoxComparator implements Comparator<Box>{
        @Override
        public int compare(Box x, Box y){
            return x.height - y.height;
        }
    }

    public int createStack(ArrayList<Box> boxes){
        // Sort in ascending order by height.
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
                maxHeight = Math.max(maxHeight, height);
            }
        }

        maxHeight += bottom.height;
        return maxHeight;
    }

    public int createStack2(ArrayList<Box> boxes){
        // Sort in ascending order by height.
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
        if(offset >= boxes.size()){
            return 0;           // Base case.
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

        // without this bottom.
        int heightWithoutBottom = createStack3(boxes, bottom, offset + 1, stackMap);
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

            // evaluate each side for each result;
            int leftTrue = countEval(left, true);
            int leftFalse = countEval(left, false);
            int rightFalse = countEval(right, false);
            int rightTrue = countEval(right, true);

            int total = (leftTrue + leftFalse) * (rightFalse + rightTrue);

            int totalTrue = 0;
            if(c == '^'){
                totalTrue = leftTrue * rightFalse + leftFalse * rightTrue;
            }
            else if(c == '&'){
                totalTrue = leftTrue * rightTrue;
            }
            else if(c == '|'){
                totalTrue = leftTrue * rightTrue + leftFalse * rightTrue + leftTrue * rightFalse;
            }

            int subways = result ? totalTrue : total - totalTrue;
            ways += subways;
        }
        return ways;
    }

    private boolean stringToBool(String c){
        return c.equals("1") ? true : false;
    }

    public int countEval(String s, boolean result, HashMap<String, Integer> memo){
        if(s.length() == 0) return 0;
        if(s.length() == 1) return stringToBool(s) == result ? 1 : 0;


        if(memo.containsKey(result + s)) return memo.get(s + result);

        int ways = 0;

        for(int i = 1; i < s.length(); i += 2){
            char c = s.charAt(i);

            String left = s.substring(0, i);
            String right = s.substring(i + 1, s.length());
            int leftTrue = countEval(left, true, memo);
            int leftFalse = countEval(left, false, memo);
            int rightTrue = countEval(right, true, memo);
            int rightFalse = countEval(right, false, memo);

            int total = (leftTrue + leftFalse) * (rightFalse + rightTrue);

            int totalTrue = 0;
            if(c == '^'){
                totalTrue = leftFalse * rightTrue + leftTrue * rightFalse;
            }
            else if(c == '&'){
                totalTrue = leftTrue * rightTrue;
            }
            else if(c == '|'){
                totalTrue = leftTrue * rightTrue + leftFalse * rightTrue + leftTrue * rightFalse;
            }

            int subway = result ? totalTrue : total - totalTrue;
            ways += subway;
        }
        memo.put(result + s, ways);
        return ways;
    }

}
