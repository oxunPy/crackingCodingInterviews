package Moderate;

import org.w3c.dom.Element;
import org.w3c.dom.ls.LSOutput;

import javax.print.attribute.Attribute;
import javax.print.attribute.IntegerSyntax;
import java.util.*;

public class Exercises {

    public void swapBits(int a, int b) {
        a = a - b;
        b = a + b;
        a = b - a;
    }

    public void swapBits2(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
    }

    public int getFrequency(String[] book, String word) {
        word = word.trim().toLowerCase();

        int count = 0;
        for (String w : book) {
            if (w.trim().toLowerCase().equals(word)) {
                count++;
            }
        }
        return count;
    }

    public HashMap<String, Integer> setupDictionary(String[] book) {
        HashMap<String, Integer> table = new HashMap<String, Integer>();
        for (String word : book) {
            word = word.toLowerCase();
            if (!word.trim().equals("")) {
                if (!table.containsKey(word)) {
                    table.put(word, 0);
                }
                table.put(word, table.get(word) + 1);
            }
        }
        return table;
    }

    private int getFrequencyTable(HashMap<String, Integer> table, String word) {
        if (table == null || word == null) {
            return -1;
        }
        word = word.toLowerCase();
        if (table.containsKey(word)) {
            return table.get(word);
        }
        return 0;
    }

    class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void setCoordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    class Line {
        double slope;
        double yintercept;

        public Line(Point start, Point end) {
            double deltaY = end.y - start.y;
            double deltaX = end.x - start.x;

            slope = deltaY / deltaX;
            yintercept = end.y - slope * end.x;     // start.y - slope * start.x;
        }
    }

    public boolean isBetween(Point start, Point between, Point end) {
        return isBetween(start.x, between.x, end.x) && isBetween(start.y, between.y, end.y);
    }

    public boolean isBetween(double start, double middle, double end) {
        if (start > end) {
            return end <= middle && middle <= start;
        } else {
            return start <= middle && middle <= end;
        }
    }

    public void swap(Point one, Point two) {
        double x = one.x;
        double y = one.y;
        one.setCoordinate(two.x, two.y);
        two.setCoordinate(x, y);
    }


    public Point intersection(Point start1, Point end1, Point start2, Point end2) {
        if (start1.x > end1.x) {
            swap(start1, end1);
        }
        if (start2.x > end2.x) {
            swap(start2, end2);
        }
        if (start1.x > start2.x) {
            swap(start1, start2);
            swap(end1, end2);
        }

        Line line1 = new Line(start1, end1);
        Line line2 = new Line(start2, end2);

        if (line1.slope == line2.slope) {
            if (line1.yintercept == line2.yintercept && isBetween(start1, start2, end1)) {
                return start2;
            }
            return null;
        }

        double x = (line1.yintercept - line2.yintercept) / (line1.slope - line2.slope);
        double y = line1.slope * x + line1.yintercept;

        Point intersect = new Point(x, y);

        if (isBetween(start1, intersect, end1) && isBetween(start2, intersect, end2)) {
            return intersect;
        }
        return null;
    }

    // if we know last move.
    enum Piece {
        EMPTY, RED, BLUE
    }

    public Piece hasWon(Piece[][] board, int row, int col) {
        if (board.length != board[0].length) return Piece.EMPTY;

        Piece piece = board[row][col];

        if (piece == Piece.EMPTY) return Piece.EMPTY;

        if (hasWonRow(board, row) || hasWonCol(board, col)) {
            return piece;
        }

        if (row == col && hasWonDiagonal(board, 1)) {
            return piece;
        }

        if (row == board.length - 1 - col && hasWonDiagonal(board, -1)) {
            return piece;
        }
        return Piece.EMPTY;
    }

    private boolean hasWonRow(Piece[][] board, int row) {
        for (int c = 1; c < board[row].length; c++) {
            if (board[row][c] != board[row][0]) {
                return false;
            }
        }
        return true;
    }

    private boolean hasWonCol(Piece[][] board, int col) {
        for (int r = 1; r < board.length; r++) {
            if (board[r][col] != board[0][col]) {
                return false;
            }
        }
        return true;
    }

    private boolean hasWonDiagonal(Piece[][] board, int direction) {
        int row = 0;
        int col = direction == 1 ? 0 : board.length - 1;

        Piece first = board[0][col];
        for (int i = 0; i < board.length; i++) {
            if (board[row][col] != first) {
                return false;
            }
            row += 1;
            col += direction;
        }
        return true;
    }


    public Piece hasWon(Piece[][] board) {
        for (int i = 0; i < board.length; i++) {
            // check rows.
            if (hasWinner(board[i][0], board[i][1], board[i][2])) {
                return board[i][0];
            }

            // check cols.
            if (hasWinner(board[0][i], board[1][i], board[2][i])) {
                return board[0][i];
            }
        }

        // check diagonal
        if (hasWinner(board[0][0], board[1][1], board[2][2])) {
            return board[0][0];
        }

        if (hasWinner(board[0][2], board[1][1], board[2][0])) {
            return board[1][1];
        }

        return Piece.EMPTY;
    }

    public boolean hasWinner(Piece p1, Piece p2, Piece p3) {
        if (p1 == Piece.EMPTY) {
            return false;
        }
        return p1 == p2 && p2 == p3;
    }


    // Solution 4 : Nested for-loops
    public Piece hasWon2(Piece[][] board) {
        int size = board.length;

        if (board[0].length != size) {
            return Piece.EMPTY;
        }
        Piece first;

        // check rows;
        for (int i = 0; i < size; i++) {
            first = board[i][0];
            if (first == Piece.EMPTY) continue;
            for (int j = 1; j < board[i].length; j++) {
                if (board[i][j] != first) {
                    break;
                } else if (j == size - 1) {
                    return first;
                }
            }
        }

        // check columns;
        for (int i = 0; i < size; i++) {
            first = board[0][i];
            if (first == Piece.EMPTY) continue;

            for (int j = 1; j < size; j++) {
                if (board[j][i] != first) {
                    break;
                } else if (j == size - 1) {         // last element.
                    return first;
                }
            }
        }

        // check diagonals.
        first = board[0][0];
        if (first != Piece.EMPTY) {
            for (int i = 1; i < size; i++) {
                if (board[i][i] != first) {
                    break;
                } else if (i == size - 1) {         // Last element.
                    return first;
                }
            }
        }

        first = board[0][size - 1];
        if (first != Piece.EMPTY) {
            for (int i = 1; i < size; i++) {
                if (board[i][size - i - 1] != first) {
                    break;
                } else if (i == size - 1) {         // Last element.
                    return first;
                }
            }
        }
        return Piece.EMPTY;
    }


    class Check {
        public int row, col;
        private int rowIncrement, colIncrement;

        public Check(int row, int col, int rowI, int colI) {
            this.row = row;
            this.col = col;
            rowIncrement = rowI;
            colIncrement = colI;
        }

        public void increment() {
            row += rowIncrement;
            col += colIncrement;
        }

        public boolean inbound(int size) {
            return row >= 0 && row < size && col >= 0 && col < size;
        }
    }

    public Piece hasWon3(Piece[][] board) {
        if (board.length != board[0].length) {
            return Piece.EMPTY;
        }
        int size = board.length;

        // Create a list of things to check.
        ArrayList<Check> instructions = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            instructions.add(new Check(0, i, 1, 0));
            instructions.add(new Check(i, 0, 0, 1));
        }

        instructions.add(new Check(0, 0, 1, 1));
        instructions.add(new Check(0, size - 1, 1, -1));

        // Check them.
        for (Check instr : instructions) {
            Piece winner = hasWon(board, instr);
            if (winner != Piece.EMPTY) {
                return winner;
            }
        }
        return Piece.EMPTY;
    }

    public Piece hasWon(Piece[][] board, Check instr) {
        Piece first = board[instr.row][instr.col];
        int size = board.length;
        while (instr.inbound(size)) {
            if (first != board[instr.row][instr.col]) {
                return Piece.EMPTY;
            }
            instr.increment();
        }
        return first;
    }


    class PositionIterator implements Iterator<Position> {
        private int rowIncrement, colIncrement, size;
        private Position current;

        public PositionIterator(Position p, int rowIncrement, int colIncrement, int size) {
            this.rowIncrement = rowIncrement;
            this.colIncrement = colIncrement;
            this.size = size;
            current = new Position(p.row - rowIncrement, p.col - colIncrement);
        }

        @Override
        public boolean hasNext() {
            return current.row + rowIncrement < size && current.col + colIncrement < size;
        }

        @Override
        public Position next() {
            current = new Position(current.row + rowIncrement, current.col + colIncrement);
            return current;
        }
    }

    class Position {
        public int row, col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }


    public Piece hasWon4(Piece[][] board) {
        if (board.length != board[0].length) return Piece.EMPTY;
        int size = board.length;

        ArrayList<PositionIterator> instructions = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            instructions.add(new PositionIterator(new Position(0, i), 1, 0, size));
            instructions.add(new PositionIterator(new Position(i, 0), 0, 1, size));
        }

        instructions.add(new PositionIterator(new Position(0, 0), 1, 1, size));
        instructions.add(new PositionIterator(new Position(0, size - 1), 1, -1, size));

        for (PositionIterator iterator : instructions) {
            Piece winner = hasWon(board, iterator);

            if (winner != Piece.EMPTY) {
                return winner;
            }
        }
        return Piece.EMPTY;
    }

    public Piece hasWon(Piece[][] board, PositionIterator iterator) {
        Position firstPosition = iterator.next();
        Piece first = board[firstPosition.row][firstPosition.col];

        while (iterator.hasNext()) {
            Position position = iterator.next();
            if (board[position.row][position.col] != first) {
                return Piece.EMPTY;
            }
        }
        return first;
    }

    public int factorsOf5(int i) {
        int count = 0;
        while (i % 5 == 0) {
            count++;
            i /= 5;
        }
        return count;
    }

    public int countFactZeros(int num) {
        int count = 0;
        for (int i = 2; i <= num; i++) {
            count += factorsOf5(i);
        }
        return count;
    }

    public int countFactZeros2(int num) {
        int count = 0;
        if (num < 0) {
            return -1;
        }

        for (int i = 5; num / i > 0; i *= 5) {
            count += num / i;
        }
        return count;
    }


    public int findSmallestDifference(int[] array1, int[] array2) {
        if (array1.length == 0 || array2.length == 0) {
            return -1;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < array1.length; i++) {
            for (int j = 0; j < array2.length; j++) {
                if (Math.abs(array1[i] - array2[j]) < min) {
                    min = Math.abs(array1[i] - array2[j]);
                }
            }
        }
        return min;
    }

    public int findSmallestDifference2(int[] array1, int[] array2) {
        Arrays.sort(array1);
        Arrays.sort(array2);

        int a = 0;
        int b = 0;

        int difference = Integer.MAX_VALUE;

        while (a < array1.length && b < array2.length) {
            if (Math.abs(array1[a] - array2[b]) < difference) {
                difference = Math.abs(array1[a] - array2[b]);
            }

            if (array1[a] < array2[b]) {
                a++;
            } else {
                b++;
            }
        }
        return difference;
    }


    private int flip(int bit) {
        return 1 ^ bit;
    }

    // return 1 if a is positive, and 0 if a is negative.
    private int sign(int a) {
        return flip(((a >> 31) & 0x1));
    }

    // this code almost word, it fails, unfortunately, if a = Int.MAX - 2, b = -15 when a - b overflows.
    public int getMaxNaive(int a, int b) {
        int k = sign(a - b);
        int q = flip(k);
        return a * k + b * q;
    }

    public int getMax(int a, int b) {

        int c = a - b;

        int sa = sign(a);       // if a >= 0 1 : 0
        int sb = sign(b);       // if b >= 0 1 : 0
        int sc = sign(c);       // a - b >= 0 ? 1 : 0


        int use_sign_of_a = sa ^ sb;
        int use_sign_of_c = flip(sa ^ sb);

        int k = use_sign_of_a * sa + use_sign_of_c * sc;
        int q = flip(k);

        return a * k + b * q;
    }

    private final String[] small = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven",
            "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventenn", "eighteen", "nineteen"};
    private final String[] tens = {"", "", "Twenty", "Thirty", "Fourty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private final String[] bigs = {"", "Thousand", "Million", "Billion"};
    String hundred = "hundred";
    String negative = "negative";

    public String convert(int num) {
        if (num == 0) {
            return small[0];
        } else if (num < 0) {
            return "negative" + convert(-1 * num);
        }

        LinkedList<String> parts = new LinkedList<>();
        int chunkCount = 0;

        while (num > 0) {
            if (num % 1000 != 0) {
                String chunk = convertChunk(num % 1000) + " " + bigs[chunkCount];
                parts.addFirst(chunk);
            }
            num /= 1000;        // shift chunk.
            chunkCount++;
        }
        return listToString(parts);
    }

    String convertChunk(int number) {
        LinkedList<String> parts = new LinkedList<>();

        // convert hundreds place
        if (number >= 100) {
            parts.addLast(small[number / 100]);
            parts.addLast(hundred);
            number %= 100;
        }

        // Convert tens place
        if (number >= 10 && number <= 19) {
            parts.addLast(small[number]);
        } else if (number >= 20) {
            parts.addLast(tens[number / 10]);
            number %= 10;
        }

        // Convert ones place.
        if (number >= 1 && number <= 9) {
            parts.addLast(small[number]);
        }
        return listToString(parts);
    }

    // Convert a linked list of strings to a string, dividing it up with spaces.
    public String listToString(LinkedList<String> parts) {
        StringBuilder sb = new StringBuilder();
        while (parts.size() > 1) {
            sb.append(parts.pop());
            sb.append(" ");
        }
        sb.append(parts.pop());
        return sb.toString();
    }

    public int negate(int a) {
        int neg = 0;
        int newSign = a < 0 ? 1 : 1;

        while (a != 0) {
            neg += newSign;
            a += newSign;
        }
        return neg;
    }

    public int minus(int a, int b) {
        return a + negate(b);
    }


    public int negate2(int a) {
        int neg = 0;
        int newSign = a > 0 ? -1 : 1;

        int delta = newSign;

        while (a != 0) {
            boolean differentSigns = (a + delta > 0) != (a > 0);

            if (a + delta != 0 && differentSigns) {
                delta = -1;
            }

            neg += delta;
            a += delta;
            delta += delta;         // Double the delta;
        }
        return delta;
    }

    public int multiply(int a, int b) {
        if (a < b) {
            return multiply(b, a);      // algorithm is faster if b < a;
        }
        int sum = 0;

        for (int i = Math.abs(b); i > 0; i = minus(i, 1)) {
            sum += a;
        }

        if (b < 0) {
            sum = negate2(sum);
        }
        return sum;
    }


    public int divide(int a, int b) throws java.lang.ArithmeticException {
        if (b == 0) {
            throw new java.lang.ArithmeticException();
        }

        int absa = Math.abs(a);
        int absb = Math.abs(b);

        int product = 0;
        int x = 0;

        while (product + absb <= absa) {          // Don't go past a
            product += absb;
            x++;
        }
        if ((a < 0 && b < 0) || (a > 0 && b > 0)) {
            return x;
        }
        return negate(x);
    }

    class Person {
        public int birth;
        public int death;

        public Person(int birth, int death) {
            this.birth = birth;
            this.death = death;
        }

        public int getBirthYear() {
            return birth;
        }

        public int getDeathYear() {
            return death;
        }
    }

    public int maxAliveYear(Person[] people, int min, int max) {
        int maxAlive = 0;
        int maxAliveYear = min;

        for (int year = min; year <= max; year++) {
            int alive = 0;
            for (Person person : people) {
                if (person.birth <= year && year <= person.death) {
                    alive++;
                }
            }
            if (alive > maxAlive) {
                maxAlive = alive;
                maxAliveYear = year;
            }
        }
        return maxAlive;
    }

    public int maxAliveYear2(Person[] people, int min, int max) {
        int[] years = createYearMap(people, min, max);
        int best = getMaxIndex(years);
        return best + min;
    }

    // add each person's year to yearMap
    public int[] createYearMap(Person[] people, int min, int max) {
        int[] years = new int[max - min + 1];

        for (Person person : people) {
            incrementRange(years, person.birth - min, person.death - min);
        }
        return years;
    }

    private void incrementRange(int[] values, int left, int right) {
        for (int i = left; i <= right; i++) {
            values[i]++;
        }
    }

    // Get index of largest element in array.
    private int getMaxIndex(int[] values) {
        int max = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] > values[max]) {
                max = i;
            }
        }
        return max;
    }

    public int maxAliveYear3(Person[] people, int min, int max) {
        int[] births = getSortedYears(people, true);
        int[] deaths = getSortedYears(people, false);


        int birthIndex = 0;
        int deathIndex = 0;
        int currentAlive = 0;
        int maxAlive = 0;
        int maxAliveYear = min;

        // Walk through array.
        while (birthIndex < births.length) {
            if (births[birthIndex] <= deathIndex) {
                currentAlive++;     // include birth

                if (currentAlive > maxAlive) {
                    maxAlive = currentAlive;
                    maxAliveYear = births[birthIndex];
                }
                birthIndex++;           // move birth index.
            } else if (births[birthIndex] > deathIndex) {
                currentAlive--; // include death.
                deathIndex++;       // move death Index;
            }
        }

        return maxAliveYear;
    }

    // integer array, then sort array.
    private int[] getSortedYears(Person[] people, boolean copyBirthYear) {
        int[] years = new int[people.length];

        for (int i = 0; i < people.length; i++) {
            years[i] = copyBirthYear ? people[i].birth : people[i].death;
        }
        Arrays.sort(years);
        return years;
    }

    public int maxAliveYear4(Person[] people, int min, int max) {

        // build population delta array.
        int[] populationDeltas = getPopulationDeltas(people, min, max);
        int maxAliveYear = getMaxAliveYear(populationDeltas);
        return maxAliveYear + min;
    }

    public int[] getPopulationDeltas(Person[] people, int min, int max) {
        int[] populationDeltas = new int[max - min + 2];
        for (Person person : people) {
            int birth = person.birth - min;
            populationDeltas[birth]++;

            int death = person.death - min;
            populationDeltas[death + 1]--;
        }
        return populationDeltas;
    }

    public int getMaxAliveYear(int[] populationDeltas) {
        int maxAliveYear = 0;
        int maxAlive = 0;
        int currentlyAlive = 0;

        for (int year = 0; year < populationDeltas.length; year++) {
            currentlyAlive += populationDeltas[year];
            if (currentlyAlive > maxAlive) {
                currentlyAlive = maxAlive;
                maxAliveYear = year;
            }
        }
        return maxAliveYear;
    }

    public HashSet<Integer> allLengths(int k, int shorter, int longer) {
        HashSet<Integer> lengths = new HashSet<>();
        getAllLengths(k, 0, shorter, longer, lengths);
        return lengths;
    }

    private void getAllLengths(int k, int total, int shorter, int longer, HashSet<Integer> lengths) {
        if (k == 0) {
            lengths.add(total);
            return;
        }

        getAllLengths(k - 1, total + shorter, shorter, longer, lengths);
        getAllLengths(k - 1, total + longer, shorter, longer, lengths);         // O(2 ^ k)
    }

    // using memoization.
    public HashSet<Integer> allLengths2(int k, int shorter, int longer) {
        HashSet<Integer> lengths = new HashSet<>();
        HashSet<String> visited = new HashSet<>();

        getAllLengths(k, 0, shorter, longer, visited, lengths);
        return lengths;
    }

    private void getAllLengths(int k, int total, int shorter, int longer, HashSet<String> visited, HashSet<Integer> lengths) {

        if (k == 0) {
            lengths.add(total);
            return;
        }
        String key = k + " " + total;

        if (visited.contains(key)) {
            return;
        }

        getAllLengths(k - 1, total + shorter, shorter, longer, visited, lengths);
        getAllLengths(k - 1, total + longer, shorter, longer, visited, lengths);
        visited.add(key);
    }


    public HashSet<Integer> allLengths3(int k, int shorter, int longer) {
        HashSet<Integer> lengths = new HashSet<>();
        for (int nShorter = 0; nShorter <= k; nShorter++) {
            int nLonger = k - nShorter;
            int length = nShorter * shorter + nLonger * longer;
            lengths.add(length);
        }
        return lengths;
    }


  /*  public void encode(Element root, StringBuilder sb){
        encode(root.getNameCode(), sb);

        for(Attribute a : root.attributes){
            encode(a, sb);
        }
        encode("0", sb);

        if(root.value != null && root.value != ""){
            encode(root.value, sb);
        }
        else{
            for(Element e : root.children){
                encode(e, sb);
            }
        }
        encode("0", sb);
    }
    public void encode(String v, StringBuilder sb){
        sb.append(v);
        sb.append(" ");
    }
    public void encode(Attribute attr, StringBuilder sb){
        encode(attr.getTagCode(), sb);
        encode(attr.value, sb);
    }

    public String encodeToString(Element root){
        StringBuilder sb = new StringBuilder();
        encode(root, sb);
        return sb.toString();
    }

*/

    // Bisect squares
    class Square {
        int left, right, top, bottom;
        int size;

        public Point middle() {
            return new Point((this.left + this.right) / 2.0, (this.top + this.bottom) / 2.0);
        }

        // return the point where the line segment connecting mid1 and mid2 intercepts the edge of square 1.
        // That is, draw a line from mid2 to mid1 and continue it out until the edge of square.
        public Point extend(Point mid1, Point mid2, double size) {
            // Find what direction the line mid2 -> mid1 size.
            double xdir = mid1.x < mid2.x ? -1 : 1;
            double ydir = mid1.y < mid2.y ? -1 : 1;

            // if mid1 and mid2 have the same x value, then the slop calculation will throw a divide by 0 exception.
            // So we compute this specially.
            if (mid1.x == mid2.x) {
                return new Point(mid1.x, mid1.y + ydir * size / 2.0);
            }

            double slope = (mid1.y - mid2.y) / (mid1.x - mid2.x);
            double x1 = 0;
            double y1 = 0;

            // Calculate the slope using the equation (y1 - y0) / (x1 - x0)


            if (Math.abs(slope) == 1) {
                x1 = mid1.x + xdir * size / 2.0;
                y1 = mid1.y + ydir * size / 2.0;
            } else if (Math.abs(slope) < 1) {       // shallow slope.
                x1 = mid1.x + xdir * size / 2.0;
                y1 = slope * (x1 - mid1.x) + mid1.y;
            } else {       // steep slope
                y1 = mid1.y + ydir * size / 2.0;
                x1 = (y1 - mid1.y) / slope + mid1.x;
            }

            return new Point(x1, y1);
        }

        public Line cut(Square other) {
            // Calculate where a line between each middle would colide with the edge of squares.
            Point p1 = extend(this.middle(), other.middle(), this.size);
            Point p2 = extend(this.middle(), other.middle(), -1 * this.size);
            Point p3 = extend(this.middle(), other.middle(), other.size);
            Point p4 = extend(this.middle(), other.middle(), -1 * other.size);

            // of above points, find start and end of lines. Start is farthest left ( with top most as a tie breaker)
            // and end is farthest right(with bottom most as a tie breaker).
            Point start = p1;
            Point end = p1;
            Point[] points = {p2, p3, p4};

            for (int i = 0; i < points.length; i++) {
                if (points[i].x < start.x || (points[i].x == start.x && points[i].y < start.y)) {
                    start = points[i];
                } else if (points[i].x > end.x || (points[i].x == end.x && points[i].y > end.y)) {
                    end = points[i];
                }
            }
            return new Line(start, end);
        }
    }

    public void findUnsortedSequence(int[] array) {
        // find left subsequence.
        int end_left = findEndOfLeftSubsequence(array);
        if (end_left >= array.length - 1) {
            return;        // Already sorted.
        }
        // find the start of right subsequence
        int start_right = findStartOfRightSubsequence(array);

        // get min and max index;
        int max_index = end_left;           // max of left side;
        int min_index = start_right;        // min of right side.

        for (int i = end_left + 1; i < start_right; i++) {
            if (array[i] < array[min_index]) min_index = i;
            if (array[i] > array[max_index]) max_index = i;
        }

        int left_index = shrinkLeft(array, min_index, end_left);
        int right_index = shrinkRight(array, max_index, start_right);

        System.out.println(left_index + " " + right_index);
    }

    public int findEndOfLeftSubsequence(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) return i - 1;
        }
        return array.length - 1;
    }

    public int findStartOfRightSubsequence(int[] array) {
        for (int i = array.length - 2; i >= 0; i--) {
            if (array[i] > array[i + 1]) return i + 1;
        }
        return 0;
    }

    public int shrinkLeft(int[] array, int min_index, int start) {
        int comp = array[min_index];

        for (int i = start - 1; i >= 0; i--) {
            if (array[i] <= comp) return i + 1;
        }
        return 0;
    }

    public int shrinkRight(int[] array, int max_index, int start) {
        int comp = array[max_index];

        for (int i = start; i < array.length; i++) {
            if (array[i] >= comp) return i - 1;
        }
        return array.length - 1;
    }

    // Contigious sequence
    public int getMaxSum(int[] array) {
        int maxSum = 0;
        int sum = 0;

        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            if (maxSum < sum) {
                maxSum = sum;
            } else if (sum < 0) {
                sum = 0;
            }
        }
        return maxSum;
    }


    public boolean doesMatch(String pattern, String value) {
        if (pattern.length() == 0) return value.length() == 0;

        int size = value.length();

        for (int mainSize = 0; mainSize <= size; mainSize++) {
            String main = value.substring(0, mainSize);
            for (int altStart = mainSize; altStart <= size; altStart++) {
                for (int altEnd = altStart; altEnd <= size; altEnd++) {
                    String alt = main.substring(altStart, altEnd);
                    String cand = buildFromPattern(pattern, main, alt);
                    if (cand.equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String buildFromPattern(String pattern, String main, String alt) {
        StringBuffer sb = new StringBuffer();

        char first = pattern.charAt(0);
        for (char c : pattern.toCharArray()) {
            if (c == first) {
                sb.append(main);
            } else {
                sb.append(alt);
            }
        }
        return sb.toString();
    }


    public boolean doesMatch2(String pattern, String value) {
        if (pattern.length() == 0) return value.length() == 0;

        char mainChar = pattern.charAt(0);
        char altChar = mainChar == 'a' ? 'b' : 'a';
        int size = value.length();

        int countOfMain = countOf(pattern, mainChar);
        int countOfAlt = pattern.length() - countOfMain;
        int firstAlt = pattern.indexOf(altChar);
        int maxMainSize = size / countOfMain;

        for (int mainSize = 0; mainSize <= maxMainSize; mainSize++) {
            int remainingLength = size - mainSize * countOfMain;
            String first = value.substring(0, mainSize);

            if (countOfAlt == 0 || remainingLength % countOfAlt == 0) {
                int altIndex = firstAlt * mainSize;
                int altSize = countOfAlt == 0 ? 0 : remainingLength / countOfAlt;
                String second = countOfAlt == 0 ? "" : value.substring(altIndex, altIndex + altSize);

                String cand = buildFromPattern(pattern, first, second);
                if (cand.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int countOf(String pattern, char c) {
        int count = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == c) count++;
        }
        return count;
    }

    // Optimized (Alternate).
    public boolean doesMatch3(String value, String pattern) {
        if (pattern.length() == 0) return value.length() == 0;

        char mainChar = pattern.charAt(0);
        char altChar = mainChar == 'a' ? 'b' : 'a';
        int size = value.length();

        int countOfMain = countOf(pattern, mainChar);
        int countOfAlt = pattern.length() - countOfMain;
        int firstAlt = pattern.indexOf(altChar);
        int maxMainSize = size / countOfMain;

        for (int mainSize = 0; mainSize <= maxMainSize; mainSize++) {
            int remainingLength = size - mainSize * countOfMain;

            if (countOfAlt == 0 || remainingLength % countOfAlt == 0) {
                int altIndex = firstAlt * mainSize;
                int altSize = countOfAlt == 0 ? 0 : remainingLength / countOfAlt;
                if (matches(pattern, value, mainSize, altSize, altIndex)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Iterate through pattern and value. At each character within pattern, check if this is the main String or
    // alternating string.Then check if the next set of characters in value match the original set of those characters.
    // (either main or alternating);

    public boolean matches(String pattern, String value, int mainSize, int altSize, int firstAlt) {
        int stringIndex = mainSize;

        for (int i = 1; i < pattern.length(); i++) {
            int size = pattern.charAt(i) == pattern.charAt(0) ? mainSize : altSize;
            int offset = pattern.charAt(i) == pattern.charAt(0) ? 0 : firstAlt;

            if (!isEqual(value, offset, stringIndex, size)) {
                return false;
            }
            stringIndex += size;
        }
        return true;
    }

    private boolean isEqual(String s1, int offset1, int offset2, int size) {
        for (int i = 0; i < size; i++) {
            if (s1.charAt(i + offset1) != s1.charAt(i + offset2)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> computePondSizes(int[][] land) {
        ArrayList<Integer> pondSizes = new ArrayList<>();

        for (int r = 0; r < land.length; r++) {
            for (int c = 0; c < land[r].length; c++) {
                if (land[r][c] == 0) {            // Optional, Would return anyway.
                    int size = computeSize(land, r, c);
                    pondSizes.add(size);
                }
            }
        }
        return pondSizes;
    }

    private int computeSize(int[][] land, int row, int col) {
        // if out of bounds or already visited.
        if (row < 0 || col < 0 || row >= land.length || col >= land[row].length || land[row][col] != 0) {
            return 0;
        }

        int size = 1;
        land[row][col] = -1;        // Mark unvisited;

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                size += computeSize(land, row + dr, col + dc);
            }
        }
        return size;
    }

    public ArrayList<Integer> computePondSizes2(int[][] land) {
        boolean[][] visited = new boolean[land.length][land[0].length];

        ArrayList<Integer> pondSizes = new ArrayList<>();
        for (int r = 0; r < land.length; r++) {
            for (int c = 0; c < land[r].length; c++) {
                if (land[r][c] == 0) {
                    int size = computeSize2(land, r, c, visited);
                    pondSizes.add(size);
                }
            }
        }
        return pondSizes;
    }

    private int computeSize2(int[][] land, int row, int col, boolean[][] visited) {
        if (row < 0 || row >= land.length || col >= land[row].length || col < 0 || visited[row][col]) {
            return 0;
        }

        int size = 1;
        visited[row][col] = true;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                size += computeSize2(land, row + dr, col + dc, visited);
            }
        }
        return size;
    }

    public ArrayList<String> getValidT9Words(String number, HashSet<String> wordList) {
        ArrayList<String> result = new ArrayList<>();
        getValidWords(number, 0, "", wordList, result);
        return result;
    }

    private void getValidWords(String number, int index, String prefix, HashSet<String> wordSet, ArrayList<String> results) {
        // if it's a complete word, print it.
        if (index == number.length() && wordSet.contains(prefix)) {
            results.add(prefix);
            return;
        }

        // get character that match this digit.
        char digit = number.charAt(index);
        char[] letters = getT9Chars(digit);

        if (letters != null) {
            for (char letter : letters) {
                getValidWords(number, index + 1, prefix + letter, wordSet, results);
            }
        }
    }

    private char[] getT9Chars(char digit) {
        if (!Character.isDigit(digit)) {
            return null;
        }
        int dig = Character.getNumericValue(digit) - Character.getNumericValue('0');
        return t9Letters[dig];
    }

    private final char[][] t9Letters = {null, null, {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}, {'j', 'k', 'l'}, {'m', 'n', 'o'},
            {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}};


    //         WORD LOOKUP
    public ArrayList<String> getValidT9Words(String number, HashMap<String, ArrayList<String>> dictionary) {
        return dictionary.get(number);
    }

    // PRECOMPUTATION
    // create hashtable that maps from a number to all words that have this numerical representation.
    private HashMap<String, ArrayList<String>> initializeDictionary(String[] words) {
        // Create a hash table that maps from a letter to the digits.
        HashMap<Character, Character> letterToNumberMap = createLetterToNumberMap();

        // Create word -> number map.
        HashMap<String, ArrayList<String>> wordToNumbers = new HashMap<String, ArrayList<String>>();
        for (String word : words) {
            String numbers = convertToT9(word, letterToNumberMap);
            if (!wordToNumbers.containsKey(numbers)) {
                wordToNumbers.put(numbers, new ArrayList<String>());
            }
            wordToNumbers.get(numbers).add(word);
        }
        return wordToNumbers;
    }

    private HashMap<Character, Character> createLetterToNumberMap() {

        HashMap<Character, Character> letterToNumberMap = new HashMap<>();

        for (int i = 0; i < t9Letters.length; i++) {
            char[] letters = t9Letters[i];

            if (letters != null) {
                for (char letter : letters) {
                    char c = Character.forDigit(i, 10);
                    letterToNumberMap.put(letter, c);
                }
            }
        }
        return letterToNumberMap;
    }

    // Convert from a string to its t9 representation.
    private String convertToT9(String word, HashMap<Character, Character> letterToNumberMap) {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (letterToNumberMap.containsKey(c)) {
                char digit = letterToNumberMap.get(c);
                sb.append(digit);
            }
        }
        return sb.toString();
    }
    // Naive approach.
    public int[] findSwapValues(int[] array1, int[] array2){
        int sum1 = sum(array1);
        int sum2 = sum(array2);

        for(int one : array1){
            for(int two : array2){
                int newSum1 = sum1 - one + two;
                int newSum2 = sum2 - two + one;

                if(newSum1 == newSum2){
                    int[] values = {one, two};
                    return values;
                }
            }
        }
        return null;
    }
    private int sum(int[] array){
        int sum = 0;
        for(int a : array) sum += a;
        return sum;
    }


    // Target approach.
    public int[] findSwapValues2(int[] array1, int[] array2){
        Integer target = getTarget(array1, array2);
        if(target == null) return null;

        for(int one : array1){
            for(int two : array2){
                if(one - two == target){
                    int[] values = {one, two};
                    return values;
                }
            }
        }
        return null;
    }

    private Integer getTarget(int[] array1, int[] array2){
        int sum1 = sum(array1);
        int sum2 = sum(array2);

        if( (sum1 - sum2) % 2 != 0){
            return null;
        }
        return (sum1 - sum2) / 2;
    }

    public int[] findSwapValues3(int[] array1, int[] array2){
        Integer target = getTarget(array1, array2);

        if(target == null) return null;

        return findDifference(array1, array2, target);
    }

    private int[] findDifference(int[] array1, int[] array2, int target){
        HashSet<Integer> contents2 = getContents(array2);

        for(int one : array1){
            int two = one - target;
            if(contents2.contains(two)){
                int[] values = {one, two};
                return values;
            }
        }
        return null;
    }
    private HashSet<Integer> getContents(int[] array){
        HashSet<Integer> set = new HashSet<>();
        for(int a : array){
            set.add(a);
        }
        return set;
    }

    public int[] findSwapValues4(int[] array1, int[] array2){
        Integer target = getTarget(array1, array2);
        if(target == null) return null;
        return findDifference2(array1, array2, target);
    }
    // if arrays are sorted, we can iterate through them to find an appropriate pair.This will require less space.
    private int[] findDifference2(int[] array1, int[] array2, int target){
        int a = 0;
        int b = 0;

        while(a < array1.length && b < array2.length){
            int difference = array1[a] - array2[b];

            // Compare difference to target. if difference is too small, then make it bigger
            // by moving a to a bigger value. if it is too big, then make it smaller by moving
            // b to a bigger value. if it's just right, return this pair.

            if(difference == target){
                int[] values = {array1[a], array2[b]};
                return values;
            }
            else if(difference < target){
                a++;
            }
            else{
                b++;
            }
        }
        return null;
    }






    class Grid{
        public boolean[][] grid;

        private Ant ant = new Ant();

        public Grid(){
            grid = new boolean[1][1];
        }

        // Copy old value into new array, with an (offset/shift) applied to the row and columns.
        private void copyWithShift(boolean[][] oldGrid, boolean[][] newGrid, int shiftRow, int shiftCol){
            for(int r = 0; r < oldGrid.length; r++){
                for(int c = 0; c < oldGrid[r].length; c++){
                    newGrid[r + shiftRow][c + shiftCol] = oldGrid[r][c];
                }
            }
        }

        // ensure that the given position will fit on the array.If necessary, double the size of the matrix, copy the old values over
        // and adjust the ant's position so that it's in a positive range.
        private void ensureFit(Position1 position){
            int shiftRow = 0;
            int shiftCol = 0;

            // Calculate the new number of rows.
            int numRows = grid.length;
            if(position.row < 0){
                shiftRow = numRows;
                numRows *= 2;
            }
            else if(position.row >= numRows){
                numRows *= 2;
            }

            // Calculate the new number of cols.
            int numCols = grid[0].length;
            if(position.col < 0){
                shiftCol = numCols;
                numCols *= 2;
            }
            else if(position.col >= numCols){
                numCols *= 2;
            }

            // Grow array, if necessary. shift ant's position too.
            if(numRows != grid.length || numCols != grid[0].length){
                boolean[][] newGrid = new boolean[numRows][numCols];
                copyWithShift(grid, newGrid, shiftRow, shiftCol);
                ant.adjustPosition(shiftRow, shiftCol);
                grid = newGrid;
            }
        }

        private void flip(Position1 position){
            int row = position.row;
            int col = position.col;

            grid[row][col]= grid[row][col] ? false : true;
        }

        // move ant
        public void move(){
            ant.turn(grid[ant.position.row][ant.position.col]);
            flip(ant.position);
            ant.move();
            ensureFit(ant.position);
        }

        // Print board.
        public String toString(){
            StringBuilder sb = new StringBuilder();
            for(int r = 0; r < grid.length; r++){
                for(int c = 0; c < grid[0].length; c++){
                    if(r == ant.position.row && c == ant.position.col){
                        sb.append(ant.orientation);
                    }
                    else if(grid[r][c]){
                        sb.append("X");
                    }
                    else{
                        sb.append("_");
                    }
                }
                sb.append("\n");
            }
            sb.append("Ant : " + ant.orientation + " \n");
            return sb.toString();
        }
    }


    class Ant{
        public Position1 position = new Position1(0, 0);
        public Orientation orientation = Orientation.right;

        public void turn(boolean clockwise){
            orientation = orientation.getTurn(clockwise);
        }

        public void move(){
            if(orientation == Orientation.left){
                position.col--;
            }
            else if(orientation == Orientation.right){
                position.col++;
            }
            else if(orientation == Orientation.up){
                position.row--;
            }
            else{   // orientation == Orientation.down
                position.row++;
            }
        }

        public void adjustPosition(int shiftRow, int shiftCol){
            position.row += shiftRow;
            position.col += shiftCol;
        }
    }

    private enum Orientation{
        left, up, right, down;

        public Orientation getTurn(boolean clockwise){
            if(this == left){
                return clockwise ? up : down;
            }
            else if(this == right){
                return clockwise ? down : up;
            }
            else if(this == up){
                return clockwise ? right : left;
            }
            else{
                return clockwise ? left : right;
            }
        }

        @Override
        public String toString(){
            if(this == left){
                return "\u2190";
            }
            else if(this == up){
                return "\u2191";
            }
            else if(this == right){
                return "\u2192";
            }
            else{
                return "\u2191";
            }
        }

    }

    class Position1{
        public int row;
        public int col;

        public Position1(int row, int col){
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o){
            if(o instanceof Position1){
                Position1 p = (Position1) o;
                return p.row == row && p.col == col;
            }
            return false;
        }
        @Override
        public int hashCode(){
            return (row * 31) ^ col;
        }
    }

    class Board{
        private HashSet<Position1> whites = new HashSet<>();
        private Ant ant = new Ant();
        private Position topLeftCorner = new Position(0, 0);
        private Position bottomRightCorner = new Position(0, 0);

        public Board(){}

        // Move end.
        public void move(){
            ant.turn(isWhite(ant.position.row, ant.position.col));// turn
            flip(ant.position); // flip
            ant.move();     // move
            ensureFit(ant.position);
        }

        // flip color of cells.
        private void flip(Position1 position){
            if(whites.contains(position)){
                whites.remove(position);
            }
            else{
                whites.add(new Position1(position.row, position.col));
            }
        }
        // Grow grid by tracking the most top-left and bottom-right positions.
        private void ensureFit(Position1 p){
            int row = p.row;
            int col = p.col;

            topLeftCorner.row = Math.min(topLeftCorner.row, row);
            topLeftCorner.col = Math.min(topLeftCorner.col, col);

            bottomRightCorner.row = Math.max(bottomRightCorner.row, row);
            bottomRightCorner.col = Math.max(bottomRightCorner.col, col);
        }

        // check if cell is white.
        public boolean isWhite(int row, int col){
            return whites.contains(new Position1(row, col));
        }

        // Print board.
        public String toString(){
            StringBuilder sb = new StringBuilder();

            int rowMin = topLeftCorner.row;
            int colMin = topLeftCorner.col;
            int rowMax=  bottomRightCorner.row;
            int colMax = bottomRightCorner.col;

            for(int r = rowMin; r <= rowMax; r++){
                for(int c = colMin; c <= colMax; c++){
                    if(r == ant.position.row && c == ant.position.col){
                        sb.append(ant.orientation);
                    }
                    else if(isWhite(r, c)){
                        sb.append("X");
                    }
                    else{
                        sb.append("_");
                    }
                }
                sb.append("\n");
            }
            sb.append("Ant : " + ant.orientation + " \n");
            return sb.toString();
        }
    }
//
//    private int rand5(){
//        Random r = new Random();
//        return r.nextInt(0,5);
//    }
//
//    public int rand7_1(){
//        int v = rand5() + rand5();
//        return v % 7;
//    }
//
//    // Second attempt : (Non deterministic number of calls)
//    public int rand7_2(){
//        while(true){
//            int num = 5 * rand5() + rand5();
//            if(num < 21){
//                return num % 7;
//            }
//        }
//    }
//
//    public int rand7(){
//        while(true){
//            int r1 = 2 * rand5();  // evens between [0,9)
//            int r2 = rand5();
//
//            if(r2 != 4){
//                int rand1 = r2 % 2;
//                int num = r1 + rand1;
//
//                if (num < 7){
//                    return num;
//                }
//            }
//        }
//    }

    class Pair{
        public int a, b;
        public Pair(int a, int b){
            this.a = a;
            this.b = b;
        }
    }


    public ArrayList<Pair> printPairResults(int[] array, int sum){          // this is brute force solution.
        ArrayList<Pair> result = new ArrayList<>();

        for(int i = 0; i < array.length; i++){
            for(int j = i; j < array.length; j++){
                if(array[i] + array[j] == sum){
                    result.add(new Pair(array[i], array[j]));
                }
            }
        }
        return result;
    }

    // optimized using hashTable
    public ArrayList<Pair> printPairResult(int[] array, int sum){
        ArrayList<Pair> result = new ArrayList<>();

        HashMap<Integer, Integer> unpairedCount = new HashMap<Integer, Integer>();
        for(int x : array){
            int complement = sum - x;
            if(unpairedCount.getOrDefault(complement, 0) > 0){
                result.add(new Pair(x, complement));
                adjustCounterBy(unpairedCount, complement, -1);
            }
            else{
                adjustCounterBy(unpairedCount, complement, 1);
            }
        }
        return result;
    }                       // this algorithm runs in O(n) time , O(n) space n is the number of array elements.

    private void adjustCounterBy(HashMap<Integer, Integer> counter, int key, int delta){
        counter.put(key, counter.getOrDefault(key, 0) + delta);
    }


    // Alternate solution.
    public void pairSum(int[] array, int sum){
        Arrays.sort(array);
        int first = 0;
        int last = array.length - 1;

        while(first < last){
            int s = array[first] + array[last];

            if(s == sum){
                System.out.println(array[first] + ", " + array[last]);
                first++;
                last--;
            }
            else if(s > sum){
                last--;
            }
            else{
                first++;
            }
        }
    }



















































































































}
