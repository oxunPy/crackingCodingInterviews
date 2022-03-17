package ArraysAndStrings;

import java.util.Arrays;

public class Exercises2 {
    public boolean isUniqueChars(String str) {
        if (str.length() > 128) {
            return false;
        }

        boolean[] char_set = new boolean[128];
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i);
            if (char_set[val]) return false;

            char_set[val] = true;
        }
        return true;
    }

    // reducing space usage by a factor of 8 by using bit vector.
    public boolean isUniqueChars2(String str) {
        int checker = 0;
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i) - 'a';

            if ((checker & (1 << val)) > 0) {
                return false;
            }
            checker |= (1 << val);
        }
        return true;
    }

    // check permutation of other.
    public String sort(String s) {
        char[] contents = s.toCharArray();
        Arrays.sort(contents);
        return new String(contents);
    }

    public boolean permutation(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        return sort(s).equals(sort(t));
    }


    // check if the two strings have identical character counts.
    public boolean permutation2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] letters = new int[128];       // Assumption
        char[] s_array = s.toCharArray();
        for (char c : s_array) {
            letters[c]++;           // count number of each char in s.
        }

        for (int i = 0; i < t.length(); i++) {
            int c = (int) t.charAt(i);
            letters[c]--;
            if (letters[c] < 0) {
                return false;
            }
        }
        return true;
    }


    public void replaceSpaces(char[] str, int trueLength) {
        int spaceCount = 0, index, i = 0;

        for (i = 0; i < trueLength; i++) {
            if (str[i] == ' ') {
                spaceCount++;
            }
        }
        index = trueLength + spaceCount * 2;

        if (trueLength < str.length) str[trueLength] = '\0';        // End array

        for (i = trueLength - 1; i >= 0; i--) {
            if (str[i] == ' ') {
                str[index] = '%';
                str[index - 1] = '0';
                str[index - 2] = '2';
                index -= 3;
            } else {
                str[index] = str[i];
                index -= 1;
            }
        }
    }

    public boolean isPermutationOfPalindrome(String phrase) {
        int[] table = buildCharFrequencyTable(phrase);
        return checkMaxOneOdd(table);
    }

    private boolean checkMaxOneOdd(int[] table) {
        boolean foundOdd = false;

        for (int a : table) {
            if (a % 2 == 1) {
                if (foundOdd) {
                    return false;
                }
                foundOdd = true;
            }
        }
        return true;
    }

    public int getCharNumber(Character c) {
        int a = Character.getNumericValue('a');
        int z = Character.getNumericValue('z');
        int val = Character.getNumericValue(c);

        if (a <= val && val <= z) {
            return val - a;
        }
        return -1;
    }

    private int[] buildCharFrequencyTable(String phrase) {
        int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a') + 1];
        for (char c : phrase.toCharArray()) {
            int x = getCharNumber(c);

            if (x != -1) {
                table[x]++;
            }
        }
        return table;
    }               // O(n) time.

    public boolean isPermutationPalindrome3(String phrase) {
        int bitVector = createBitVector(phrase);
        return bitVector == 0 || checkExactlyOneBitSet(bitVector);
    }

    private int createBitVector(String phrase) {
        int bitVector = 0;

        for (char c : phrase.toCharArray()) {
            int x = getCharNumber(c);
            bitVector = toggle(bitVector, x);
        }
        return bitVector;
    }

    private int toggle(int bitVector, int x) {
        if (x == -1) {
            return bitVector;
        }
        int mask = 1 << x;

        if ((bitVector & mask) == 0) {
            bitVector |= mask;
        } else {
            bitVector &= ~mask;
        }
        return bitVector;
    }

    private boolean checkExactlyOneBitSet(int bitVector) {
        return (bitVector & (bitVector - 1)) == 0;
    }


    public boolean oneEditAway(String first, String second) {
        if (first.length() != second.length()) {
            return oneEditReplace(first, second);
        } else if (first.length() + 1 == second.length()) {
            return oneEditInsert(first, second);
        } else if (first.length() == second.length() + 1) {
            return oneEditInsert(second, first);
        }
        return false;
    }

    private boolean oneEditInsert(String s1, String s2) {
        int index1 = 0;
        int index2 = 0;

        while (index1 < s1.length() && index2 < s2.length()) {
            if (s1.charAt(index1) != s2.charAt(index2)) {
                if (index1 != index2) {
                    return false;
                }
                index2++;
            } else {
                index1++;
                index2++;
            }
        }
        return true;
    }

    private boolean oneEditReplace(String first, String second) {
        boolean foundDifference = false;

        for (int i = 0; i < first.length(); i++) {
            if (first.charAt(i) != second.charAt(i)) {
                if (foundDifference) {
                    return false;
                }
                foundDifference = true;
            }
        }
        return true;
    }

    public boolean oneEditAway2(String first, String second) {
        //Lengths checks.
        if (Math.abs(first.length() - second.length()) > 1) {
            return false;
        }

        // Get shorter and longer string.
        String s1 = first.length() < second.length() ? first : second;
        String s2 = first.length() < second.length() ? second : first;

        int index1 = 0;
        int index2 = 0;

        boolean foundDifference = false;

        while (index1 < s1.length() && index2 < s2.length()) {
            if (s1.charAt(index1) != s2.charAt(index2)) {

                if (foundDifference) return false;
                foundDifference = true;

                if (s1.length() == s2.length()) {         // on replace move shorter pointer.
                    index1++;
                }
            } else {
                index1++;
            }
            index2++;
        }
        return true;
    }


    public String compressBad(String str) {
        String compressedString = "";
        int countConsecutive = 0;

        for (int i = 0; i < str.length(); i++) {
            countConsecutive++;

            if (i + 1 >= str.length() || str.charAt(i + 1) != str.charAt(i)) {
                compressedString += str.charAt(i);
                compressedString += countConsecutive;
                countConsecutive = 0;
            }
        }
        return compressedString;            // time complexity is O(p + k * k)
    }

    // using StringBuilder
    public String compress(String str) {
        StringBuilder compressedString = new StringBuilder();
        int countConsecutive = 0;

        for (int i = 0; i < str.length(); i++) {
            countConsecutive++;

            if (i + 1 == str.length() || str.charAt(i + 1) != str.charAt(i)) {
                compressedString.append(str.charAt(i));
                compressedString.append(countConsecutive);
                countConsecutive = 0;
            }
        }
        return compressedString.toString();
    }

    public String compress2(String str) {
        int finalLength = countCompression(str);
        if (finalLength > str.length()) {
            return str;
        }

        StringBuilder compressed = new StringBuilder();
        int countConsecutive = 0;
        for (int i = 0; i < str.length(); i++) {
            countConsecutive++;

            if (i + 1 == str.length() || str.charAt(i + 1) != str.charAt(i)) {
                compressed.append(str.charAt(i));
                compressed.append(countConsecutive);
                countConsecutive = 0;
            }
        }
        return compressed.toString();
    }

    private int countCompression(String str) {
        int compressedLength = 0;
        int countConsecutive = 0;

        for (int i = 0; i < str.length(); i++) {
            countConsecutive++;

            if (i + 1 == str.length() || str.charAt(i + 1) != str.charAt(i)) {
                compressedLength += 1 + String.valueOf(countConsecutive).length();
                countConsecutive = 0;
            }
        }
        return compressedLength;
    }

    public boolean rotate(int[][] matrix){
        if(matrix.length == 0 || matrix.length != matrix[0].length){
            return false;
        }
        int n = matrix.length;

        for(int layer = 0; layer < n / 2; layer++){
            int first = layer;
            int last = n - 1 - layer;

            for(int i = first; i < last; i++){
                int offset = i - first;

                int top = matrix[first][i];     // save top.

                // left - top
                matrix[first][i] = matrix[last - offset][first];
                // bottom - left
                matrix[last - offset][first] = matrix[last][last - offset];
                // right - bottom
                matrix[last][last - offset] = matrix[i][last];
                // top - right
                matrix[i][last] = top;  // saved top.
            }
        }
        return true;
    }


    // String rotation
    public boolean isRotation(String s1, String s2){
        int len = s1.length();

        if(len == s2.length() && len > 0){
            // Concatenate s1 and s1 within new buffer
            String s1s1 = s1 + s1;
            return s1s1.indexOf(s2) > 0;
        }
        return false;
    }

    public void setZeros(int[][] matrix){
        boolean[] row = new boolean[matrix.length];
        boolean[] column = new boolean[matrix[0].length];

        // Store the row and column index with value zero
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j] == 0){
                    row[i] = true;
                    column[j] = true;
                }
            }
        }

        // Nullify rows
        for(int i = 0; i < row.length; i++){
            if(row[i]) nullifyRow(matrix, i);
        }

        // Nullify cols
        for(int j = 0; j < column.length; j++){
            if(column[j]) nullifyCol(matrix, j);
        }
    }
    private void nullifyRow(int[][] matrix, int row){
        for(int col = 0; col < matrix[row].length; col++){
            matrix[row][col] = 0;
        }
    }
    private void nullifyCol(int[][] matrix, int col){
        for(int row = 0; row < matrix.length; row++){
            matrix[row][col] = 0;
        }
    }


    public void setZeroes(int[][] matrix){
        boolean rowHasZero = false;
        boolean colHasZero = false;

        for(int row = 0; row < matrix.length; row++){
            if(matrix[row][0] == 0){
                colHasZero = true;
                break;
            }
        }

        for(int col = 0; col < matrix[0].length; col++){
            if(matrix[0][col] == 0){
                rowHasZero = true;
                break;
            }
        }

        for(int i = 1; i < matrix.length; i++){
            for(int j = 1; j < matrix[0].length; j++){
                if(matrix[i][j] == 0){
                    matrix[i][0] = 0;
                    matrix[i][j] = 0;
                }
            }
        }

        // nullify rows based on first col
        for(int i = 1; i < matrix.length; i++){
            if(matrix[i][0] == 0){
                nullifyRow(matrix, i);
            }
        }

        for(int j = 1; j < matrix.length; j++){
            if(matrix[0][j] == 0){
                nullifyCol(matrix, j);
            }
        }

        if(rowHasZero) nullifyRow(matrix, 0);
        if(colHasZero) nullifyCol(matrix, 0);
    }















































}
