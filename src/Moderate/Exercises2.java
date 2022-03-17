package Moderate;

import java.util.*;

public class Exercises2 {
    // swapping numbers without exta memory.
    public void swap(int a, int b) {
        a = a - b;
        b = a + b;
        a = b - a;
    }

    public void swap2(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
    }

    // word frequencies
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

    // Solution : Repetitive queries.
    HashMap<String, Integer> setupDictionary(String[] book) {
        HashMap<String, Integer> table = new HashMap<>();
        for (String word : book) {
            word = word.toLowerCase();
            if (word.trim() != "") {
                if (!table.containsKey(word)) {
                    table.put(word, 0);
                }
                table.put(word, table.get(word) + 1);
            }
        }
        return table;
    }

    public int getFrequency(HashMap<String, Integer> table, String word) {
        if (table == null || word == null) return -1;

        word = word.toLowerCase();
        if (table.containsKey(word)) {
            return table.get(word);
        }
        return 0;
    }

    class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void setLocation(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }


    class Line {
        public double slope, yintercept;

        public Line(double slope, double yintercept) {
            this.slope = slope;
            this.yintercept = yintercept;
        }

        public Line(Point start, Point end) {
            double deltaY = end.y - start.y;
            double deltaX = end.x - start.x;

            slope = deltaY / deltaX;        // Will be infinity(not exception) when deltaX = 0;
            yintercept = end.y - slope * end.x;
        }
    }

    public Point intersection(Point start1, Point end1, Point start2, Point end2) {
        if (start1.x > end1.x) {
            swap(start1, end1);
        } else if (start2.x > end2.x) {
            swap(start2, end2);
        } else if (start1.x > start2.x) {
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

        double x = (line2.yintercept - line1.yintercept) / (line1.slope - line2.slope);
        double y = x * line1.slope + line1.yintercept;

        Point intersection = new Point(x, y);

        if (isBetween(start1, intersection, end1) && isBetween(start2, intersection, end2)) {
            return intersection;
        }
        return null;
    }

    private boolean isBetween(double start, double middle, double end) {
        if (start > end) {
            return end <= middle && middle <= start;
        } else {
            return start <= middle && middle <= end;
        }
    }

    private boolean isBetween(Point start, Point middle, Point end) {
        return isBetween(start.x, middle.x, end.x) && isBetween(start.y, middle.y, end.y);
    }

    private void swap(Point one, Point two) {
        double x = one.x;
        double y = one.y;
        one.setLocation(two.x, two.y);
        two.setLocation(x, y);
    }


    // Tic tac win
    // Solution1 hasWon is called many times.
    enum Piece {
        EMPTY, RED, BLUE
    }

    Piece hasWon1(int board) {
//        return winnerHashTable[board];
        return Piece.RED;                   // FOR SIMPLICITY
    }

    public int convertBoardToInt(Piece[][] board) {
        int sum = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int value = board[i][j].ordinal();
                sum = sum * 3 + value;
            }
        }
        return sum;
    }

    // Solution 2 : what if we know last move.
    Piece hasWon2(Piece[][] board, int row, int col) {
        if (board.length != board[0].length) return Piece.EMPTY;

        Piece piece = board[row][col];

        if (piece == Piece.EMPTY) return Piece.EMPTY;

        if (hasWonRow(board, row) || hasWonCol(board, col)) {
            return piece;
        }
        if (row == col && hasWonDiagonal(board, 1)) {
            return piece;
        }

        if (row == (board.length - 1 - col) && hasWonDiagonal(board, -1)) {
            return piece;
        }
        return Piece.EMPTY;
    }

    boolean hasWonRow(Piece[][] board, int row) {
        for (int c = 1; c < board[row].length; c++) {
            if (board[row][0] != board[row][c]) {
                return false;
            }
        }
        return true;
    }

    boolean hasWonCol(Piece[][] board, int col) {
        for (int r = 1; r < board.length; r++) {
            if (board[r][col] != board[0][col]) {
                return false;
            }
        }
        return true;
    }

    boolean hasWonDiagonal(Piece[][] board, int direction) {
        int row = 0;
        int col = direction > 0 ? 0 : board[row].length - 1;

        Piece first = board[row][col];
        for (int i = 0; i < board.length; i++) {
            if (board[row][col] != first) {
                return false;
            }
            row += 1;
            col += direction;
        }
        return true;
    }

    // Designing for just a 3x3 board.
    public Piece hasWon3(Piece[][] board) {
        for (int i = 0; i < board.length; i++) {
            // check rows
            if (hasWinner(board[i][0], board[i][1], board[i][2])) {
                return board[i][0];
            }
            // check cols
            if (hasWinner(board[0][i], board[1][i], board[2][i])) {
                return board[0][i];
            }
        }

        // check diagonals.
        if (hasWinner(board[0][0], board[1][1], board[2][2])) {
            return board[0][0];
        }
        if (hasWinner(board[0][2], board[1][1], board[2][0])) {
            return board[0][2];
        }
        return Piece.EMPTY;
    }

    boolean hasWinner(Piece p1, Piece p2, Piece p3) {
        if (p1 == Piece.EMPTY) {
            return false;
        }
        return p1 == p2 && p2 == p3;
    }


    // Designing for an NxN board.
    Piece hasWon4(Piece[][] board) {
        int size = board.length;
        if (board[0].length != size) return Piece.EMPTY;

        Piece first;

        // check rows.
        for (int i = 0; i < size; i++) {
            first = board[i][0];
            if (first == Piece.EMPTY) continue;
            for (int j = 1; j < size; j++) {
                if (board[i][j] != first) {
                    break;
                } else if (j == size - 1) { // Last element.
                    return first;
                }
            }
        }

        // check cols.
        for (int i = 0; i < size; i++) {
            first = board[0][i];
            if (first == Piece.EMPTY) continue;
            for (int j = 1; j < size; j++) {
                if (board[j][i] != first) {
                    break;
                } else if (j == size - 1) { // Last element
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
                } else if (i == size - 1) { // Last element.
                    return first;
                }
            }
        }

        first = board[0][size - 1];
        if (first != Piece.EMPTY) {
            for (int i = 1; i < size; i++) {
                if (board[i][size - 1 - i] != first) {
                    break;
                } else if (i == size - 1) {
                    return first;
                }
            }
        }
        return Piece.EMPTY;
    }


    // Increment and decrement functions.
    class Check {
        public int row, col;
        private int rowIncrement, colIncrement;

        public Check(int row, int col, int rowIncrement, int colIncrement) {
            this.row = row;
            this.col = col;
            this.rowIncrement = rowIncrement;
            this.colIncrement = colIncrement;
        }

        public void increment() {
            row += rowIncrement;
            col += colIncrement;
        }

        public boolean inBounds(int size) {
            return row >= 0 && col >= 0 && row < size && col < size;
        }
    }


    public Piece hasWon5(Piece[][] board) {
        if (board.length != board[0].length) return Piece.EMPTY;
        int size = board.length;

        // Create list of things to check.
        ArrayList<Check> instructions = new ArrayList<Check>();
        for (int i = 0; i < size; i++) {
            instructions.add(new Check(i, 0, 0, 1));
            instructions.add(new Check(0, i, 1, 0));
        }
        instructions.add(new Check(0, 0, 1, 1));
        instructions.add(new Check(0, size - 1, 1, -1));

        // check them
        for (Check instr : instructions) {
            Piece winner = hasWon(board, instr);
            if (winner != Piece.EMPTY) {
                return winner;
            }
        }
        return Piece.EMPTY;
    }

    Piece hasWon(Piece[][] board, Check instr) {
        Piece first = board[instr.row][instr.col];

        while (instr.inBounds(board.length)) {
            if (board[instr.row][instr.col] != first) {
                return Piece.EMPTY;
            }
            instr.increment();
        }
        return first;
    }


    // Iterator.
    class PositionIterator implements Iterator<Position> {
        private int rowIncrement, colIncrement, size;
        public Position current;

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


    public Piece hasWon(Piece[][] board) {
        if (board.length != board[0].length) return Piece.EMPTY;
        int size = board.length;
        ArrayList<PositionIterator> instructions = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            instructions.add(new PositionIterator(new Position(i, 0), 0, 1, size));
            instructions.add(new PositionIterator(new Position(0, i), 1, 0, size));
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
        Position firstPosition = iterator.current;
        Piece first = board[firstPosition.row][firstPosition.col];
        while (iterator.hasNext()) {
            Position position = iterator.next();
            if (first != board[position.row][position.col]) {
                return Piece.EMPTY;
            }
        }
        return first;
    }


    int countFactZeros2(int num) {
        int count = 0;

        for (int i = 2; i <= num; i++) {
            count += factorOf5(i);
        }
        return count;
    }

    private int factorOf5(int num) {
        int count = 0;
        while (num % 5 == 0) {
            count++;
            num /= 5;
        }
        return count;
    }

    int countFactZeros(int num) {
        int count = 0;
        if (num < 0) {
            return -1;
        }
        for (int i = 5; num / i > 0; i *= 5) {
            count += num / i;
        }
        return count;
    }


    //Smallest Difference
    public int findDifference(int[] array1, int[] array2) {
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

    // Optimal approach is to sort array.
    int findSmallestDifference(int[] array1, int[] array2) {
        Arrays.sort(array1);
        Arrays.sort(array2);
        int a = 0;
        int b = 0;

        int difference = Integer.MAX_VALUE;

        while (a < array1.length && b < array2.length) {
            if (Math.abs(array1[a] - array2[b]) < difference) {
                difference = Math.abs(array1[a] - array2[b]);
            }

            // Move smaller
            if (array1[a] < array2[b]) {
                a++;
            } else {
                b++;
            }
        }
        return difference;
    }

    int flip(int bit) {
        return 1 ^ bit;         // flipping bit 1 -> 0, 0 -> 1
    }

    // return 1 if a is positive, and 0 if a is negative.
    int sign(int a) {
        return flip((a >> 31) & 0x1);
    }


    int getMaxNaive(int a, int b) {
        int k = sign(a - b);
        int q = flip(k);
        return a * k + b * q;
    }

    // what if a - b may overflow.
    int getMax(int a, int b) {
        int c = a - b;

        int sa = sign(a);           // if a >= 0 1 else 0
        int sb = sign(b);           // if b >= 0 1 else 0
        int sc = sign(c);           // depends on whether overflow or not.

        // if a and b have different sign k = sign(a)
        int use_sign_a = sa ^ sb;

        int use_sign_c = flip(sa ^ sb);

        int k = use_sign_a * sa + use_sign_c * sc;
        int q = flip(k);
        return a * k + b * q;
    }

    String[] smalls = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven",
            "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
            "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy",
            "Eighty", "Ninety"};
    String[] bigs = {"", "Thousand", "Million", "Billion"};

    String hundred = "hundred";
    String negative = "Negative";

    String convert(int num) {
        if (num == 0) {
            return smalls[0];
        } else if (num < 0) {
            return negative + " " + convert(-1 * num);
        }

        LinkedList<String> parts = new LinkedList<>();
        int chunkCount = 0;

        while (num > 0) {
            if (num % 1000 != 0) {
                String chunk = convertChunk(num % 1000) + " " + bigs[chunkCount];
                parts.addFirst(chunk);
            }
            num /= 1000;        // shift chunk
            chunkCount++;
        }
        return listToString(parts);
    }

    String convertChunk(int num) {
        LinkedList<String> parts = new LinkedList<>();

        // Convert hundreds place.
        if (num >= 100) {
            parts.addLast(smalls[num / 100]);
            parts.addLast(hundred);
            num %= 100;
        }

        // Convert tens place.
        if (num >= 10 && num <= 19) {
            parts.addLast(smalls[num]);
        } else if (num >= 20) {
            parts.addLast(tens[num / 10]);
            num %= 10;
        }

        // Convert ones place
        if (num >= 1 && num <= 9) {
            parts.addLast(smalls[num]);
        }
        return listToString(parts);
    }

    String listToString(LinkedList<String> parts) {
        StringBuilder sb = new StringBuilder();
        while (parts.size() > 1) {
            sb.append(parts.pop());
            sb.append(" ");
        }
        sb.append(parts.pop());
        return sb.toString();
    }


    // use only add operation.
    int negate1(int a) {
        int neg = 0;

        int newSign = a > 0 ? -1 : 1;

        while (a != 0) {
            a += newSign;
            neg += newSign;
        }
        return neg;
    }

    int minus(int a, int b) {
        return a + negate(b);
    }

    int negate(int a) {
        int neg = 0;
        int newSign = a > 0 ? -1 : 1;
        int delta = newSign;
        while (a != 0) {
            boolean differentSign = (a + delta > 0) != (a > 0);
            if (a + delta != 0 && differentSign) {        // if delta is too big reset it.
                delta = newSign;
            }

            neg += delta;
            a += delta;
            delta += delta;     // double the delta.
        }
        return neg;
    }

    int multiply(int a, int b) {
        if (a < b) {
            return multiply(b, a);  // algorithm is faster if b < a
        }

        int sum = 0;
        for (int i = abs(b); i > 0; i = minus(i, 1)) {
            sum += a;
        }
        if (b < 0) {
            return negate(sum);
        }
        return sum;
    }

    int abs(int a) {
        if (a < 0) {
            return negate(a);
        }
        return a;
    }

    int divide(int a, int b) throws java.lang.ArithmeticException {
        if (b == 0) {
            throw new java.lang.ArithmeticException();
        }

        int absa = abs(a);
        int absb = abs(b);

        int product = 0;
        int x = 0;

        while (product + absb <= absa) {      // don't go past a
            product += absb;
            x++;
        }

        if ((a < 0 && b < 0) || a > 0 && b > 0) {
            return x;
        } else {
            return negate(x);
        }
    }


    class Person {
        public int birth;
        public int death;

        public Person(int birth, int death) {
            this.birth = birth;
            this.death = death;
        }
    }

    // brute force approach.
    int maxAliveYear2(Person[] people, int min, int max) {
        int maxAlive = 0;
        int maxAliveYear = min;

        for (int year = min; year <= max; year++) {
            int alive = 0;

            for (Person p : people) {
                if (p.birth <= year && year <= p.death) {
                    alive++;
                }
            }

            if (alive > maxAlive) {
                maxAlive = alive;
                maxAliveYear = year;
            }
        }
        return maxAliveYear;
    }

    // slightly brute force approach.
    int maxAliveYear1(Person[] people, int min, int max) {
        int[] years = createYearMap(people, min, max);
        int best = getMaxIndex(years);
        return best + min;
    }

    // add each person's years to a year map.
    int[] createYearMap(Person[] people, int min, int max) {
        int[] years = new int[max - min + 1];
        for (Person person : people) {
            incrementRange(years, person.birth - min, person.death - min);
        }
        return years;
    }

    void incrementRange(int[] values, int left, int right) {
        for (int i = left; i <= right; i++) {
            values[i]++;
        }
    }

    int getMaxIndex(int[] values) {
        int max = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] > values[max]) {
                max = i;
            }
        }
        return max;
    }

    // More optimal
    int maxAliveYear3(Person[] people, int min, int max) {
        int[] births = getSortedYears(people, true);
        int[] deaths = getSortedYears(people, false);

        int birthIndex = 0;
        int deathIndex = 0;
        int currentAlive = 0;
        int maxAlive = 0;
        int maxAliveYear = min;


        //Walk through arrays.
        while (birthIndex < births.length) {
            if (births[birthIndex] < deaths[deathIndex]) {
                currentAlive++;     // include birth.

                if (currentAlive > maxAlive) {
                    maxAlive = currentAlive;
                    maxAliveYear = births[birthIndex];
                }
                birthIndex++;       // move birth index.
            } else if (births[birthIndex] > deaths[deathIndex]) {
                currentAlive--;     // include death.
                deathIndex++;       // move death index.
            }
        }
        return maxAliveYear;
    }

    int[] getSortedYears(Person[] people, boolean copyBirthYear) {
        int[] years = new int[people.length];
        for (int i = 0; i < people.length; i++) {
            years[i] = copyBirthYear ? people[i].birth : people[i].death;
        }
        Arrays.sort(years);
        return years;
    }


    // More optimal (maybe).
    int maxAliveYear(Person[] people, int min, int max) {
        // Build population deltas.
        int[] populationDeltas = getPopulationDeltas(people, min, max);
        int maxAliveYear = getMaxAliveYear(populationDeltas);
        return maxAliveYear + min;
    }

    int[] getPopulationDeltas(Person[] people, int min, int max) {
        int[] populationDeltas = new int[max - min + 2];

        for (Person person : people) {
            int birth = person.birth - min;
            populationDeltas[birth]++;

            int death = person.death - min;
            populationDeltas[death + 1]--;
        }
        return populationDeltas;
    }

    int getMaxAliveYear(int[] deltas) {
        int maxAliveYear = 0;
        int maxAlive = 0;
        int currentAlive = 0;

        for (int year = 0; year < deltas.length; year++) {
            currentAlive += deltas[year];
            if (currentAlive > maxAlive) {
                maxAlive = currentAlive;
                maxAliveYear = year;
            }
        }
        return maxAliveYear;
    }


    HashSet<Integer> allLength(int k, int shorter, int longer) {
        HashSet<Integer> lengths = new HashSet<>();
        getAllLengths(k, 0, shorter, longer, lengths);
        return lengths;
    }

    public void getAllLengths(int k, int total, int shorter, int longer, HashSet<Integer> lengths) {
        if (k == 0) {
            lengths.add(total);
            return;
        }

        getAllLengths(k - 1, total + shorter, shorter, longer, lengths);
        getAllLengths(k - 1, total + longer, shorter, longer, lengths);
    }

    HashSet<Integer> allLengths(int k, int shorter, int longer) {
        HashSet<Integer> lengths = new HashSet<>();
        HashSet<String> visited = new HashSet<>();
        getAllLengths(k, 0, shorter, longer, visited, lengths);
        return lengths;
    }

    public void getAllLengths(int k, int total, int shorter, int longer, HashSet<String> visited, HashSet<Integer> lengths) {

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


    // Optimal solution.
    HashSet<Integer> allLengths2(int k, int shorter, int longer) {
        HashSet<Integer> lengths = new HashSet<>();
        for (int nShorter = 0; nShorter <= k; nShorter++) {
            int nLonger = k - nShorter;
            int length = nShorter * shorter + nLonger * longer;
            lengths.add(length);
        }
        return lengths;
    }


    class Result {
        public int hits = 0;
        public int pseudoHits = 0;


        public String toString() {
            return "(" + hits + ", " + pseudoHits + ")";
        }
    }

    int code(char c) {
        switch (c) {
            case 'B':
                return 0;
            case 'G':
                return 1;
            case 'R':
                return 2;
            case 'Y':
                return 3;
            default:
                return -1;
        }
    }

    int MAX_COLORS = 4;

    Result estimate(String guess, String solution) {
        if (guess.length() != solution.length()) {
            return null;
        }

        Result res = new Result();
        int[] frequencies = new int[MAX_COLORS];

        // compute hits and build frequency table.
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == solution.charAt(i)) {
                res.hits++;
            } else {
                int code = code(solution.charAt(i));
                frequencies[code]++;
            }
        }

        // Compute pseudo-hits
        for (int i = 0; i < guess.length(); i++) {
            int code = code(guess.charAt(i));
            if (code >= 0 && frequencies[code] > 0 && guess.charAt(i) != solution.charAt(i)) {
                res.pseudoHits++;
                frequencies[code]--;
            }
        }
        return res;
    }


    void findUnsortedSequence(int[] array) {
        // find left sequence.
        int end_left = findEndOfLeftSequence(array);
        if (end_left >= array.length - 1) {
            return;     // already sorted.
        }

        // find start of right sequence.
        int start_right = findStartOfRightSequence(array);

        // get min and max.
        int max_index = end_left;       // max of left side
        int min_index = start_right;    // min of right side

        for (int i = end_left + 1; i < start_right; i++) {
            if (array[i] < array[min_index]) min_index = i;
            if (array[i] > array[max_index]) max_index = i;
        }


        // slide left until less than array[min_index]
        int left_index = shrinkLeft(array, min_index, end_left);

        // slide right until greater than array[max_index]
        int right_index = shrinkRight(array, max_index, start_right);

        System.out.println(left_index + " " + right_index);
    }

    public int findEndOfLeftSequence(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return i - 1;
            }
        }
        return array.length - 1;
    }

    public int findStartOfRightSequence(int[] array) {
        for (int i = array.length - 2; i >= 0; i--) {
            if (array[i] > array[i + 1]) {
                return i + 1;
            }
        }
        return 0;
    }

    int shrinkLeft(int[] array, int min_index, int start) {
        int comp = array[min_index];
        for (int i = start - 1; i >= 0; i--) {
            if (array[i] >= comp) {
                return i + 1;
            }
        }
        return 0;
    }

    int shrinkRight(int[] array, int max_index, int start) {
        int comp = array[max_index];
        for (int i = start; i < array.length; i++) {
            if (array[i] <= comp) {
                return i - 1;
            }
        }
        return array.length - 1;
    }

    int getMaxSum(int[] a) {
        int maxSum = 0;
        int sum = 0;

        for (int i = 0; i < a.length; i++) {
            sum += a[i];

            if (maxSum < sum) {
                maxSum = sum;
            }
            if (sum < 0) {
                sum = 0;
            }
        }
        return maxSum;
    }

    public boolean doesMatch3(String pattern, String value) {     // brute force approach
        if (pattern.length() == 0) return value.length() == 0;

        int size = value.length();

        for (int mainSize = 0; mainSize < size; mainSize++) {
            String main = value.substring(0, mainSize);

            for (int altStart = mainSize; altStart <= size; altStart++) {
                for (int altEnd = altStart; altEnd <= size; altEnd++) {
                    String alt = value.substring(altStart, altEnd);
                    String cand = buildFromPattern(pattern, main, alt);
                    if (cand.equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }                       // this algorithm runs O(n^4) where n is the given length of pattern.

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
            int remainingLength = value.length() - mainSize * countOfMain;
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
        return false;           // this algorithm takes O(n ^ 2) time complexity.
    }

    public int countOf(String pattern, char c) {
        int count = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == c) count++;
        }
        return count;
    }

    //Optimized;
    public boolean doesMatch(String pattern, String value) {
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

    public boolean isEqual(String s1, int offset1, int offset2, int size) {
        for (int i = 0; i < size; i++) {
            if (s1.charAt(offset1 + i) != s1.charAt(offset2 + i)) {
                return false;
            }
        }
        return true;
    }


    ArrayList<Integer> computePondSizes(int[][] lands) {
        ArrayList<Integer> pondSizes = new ArrayList<>();

        for (int r = 0; r < lands.length; r++) {
            for (int c = 0; c < lands[0].length; c++) {
                if (lands[r][c] == 0) {   // Optional would return anyway.
                    int size = computeSize(lands, r, c);
                    pondSizes.add(size);
                }
            }
        }
        return pondSizes;
    }

    int computeSize(int[][] lands, int r, int c) {
        if (r < 0 || r >= lands.length || c < 0 || c > lands[0].length || lands[r][c] != 0) {
            return 0;
        }

        int size = 1;
        lands[r][c] = -1;       // Mark visited.

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                size += computeSize(lands, r + dr, c + dc);
            }
        }
        return size;
    }


    public ArrayList<Integer> computePondSizes2(int[][] land) {
        boolean[][] visited = new boolean[land.length][land[0].length];
        ArrayList<Integer> pondSizes = new ArrayList<>();

        for (int r = 0; r < land.length; r++) {
            for (int c = 0; c < land[0].length; c++) {
                if (land[r][c] == 0) {
                    int size = computeSize(land, r, c, visited);
                    pondSizes.add(size);
                }
            }
        }
        return pondSizes;
    }

    private int computeSize(int[][] land, int r, int c, boolean[][] visited) {
        if (r < 0 || r >= land.length || c < 0 || c >= land[0].length || visited[r][c] || land[r][c] != 0) {
            return 0;
        }

        int size = 1;
        visited[r][c] = true;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                size += computeSize(land, r + dr, c + dc);
            }
        }
        return size;
    }

    ArrayList<String> getValidT9Words(String number, HashSet<String> wordList) {
        ArrayList<String> result = new ArrayList<>();
        getValidWords(number, 0, "", wordList, result);
        return result;
    }

    public void getValidWords(String number, int index, String prefix, HashSet<String> wordSet, ArrayList<String> results) {
        if (index == number.length() && wordSet.contains(prefix)) {
            results.add(prefix);
            return;
        }

        char digit = number.charAt(index);
        char[] letters = getT9Chars(digit);

        if (letters != null) {
            for (char letter : letters) {
                getValidWords(number, index + 1, prefix + letter, wordSet, results);
            }
        }
    }

    char[] getT9Chars(char digit) {
        if (!Character.isDigit(digit)) {
            return null;
        }

        int dig = Character.getNumericValue(digit) - Character.getNumericValue('0');
        return t9Letters[dig];
    }

    /* Mapping of digits to letters. */
    char[][] t9Letters = {null, null,
            {'a', 'b', 'c'},
            {'d', 'e', 'f'},
            {'g', 'h', 'i'},
            {'j', 'k', 'l'},
            {'m', 'n', 'o'},
            {'p', 'q', 'r', 's'},
            {'t', 'u', 'v'},
            {'w', 'x', 'y', 'z'}
    };



}




