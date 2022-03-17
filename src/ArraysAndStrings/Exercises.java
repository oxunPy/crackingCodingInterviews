package ArraysAndStrings;
import java.util.*;

public class Exercises {
    public static boolean isUniqueChars(String str){
        if(str.length() > 128){
            return false;
        }
        boolean[] char_set = new boolean[128];
        for(char c : str.toCharArray()){
            if(char_set[c]){
                return false;
            }
            char_set[c] = true;
        }
        return true;
    }
    public static boolean isUniqueChars2(String str){
        int checker = 0;
        for(int i = 0; i < str.length(); ++i){
            int val = str.charAt(i) - 'a';
            if((checker & (1 << val)) > 0){
                return false;
            }
            checker |= (1 << val);
        }
        return true;
    }

    private static String sort(String s){
        char[] content = s.toCharArray();
        Arrays.sort(content);
        return new String(content);
    }
    public static boolean permutation(String s, String t){
        if(s.length() != t.length()){
            return false;
        }

        return sort(s).equals(sort(t));
    }

    // Check if the two strings have identical character counts.
    public static boolean permuatation2(String s, String t){
        if(s.length() != t.length()){
            return false;
        }

        int[] letter = new int[128];        // Assumption.
        char[] s_array = s.toCharArray();

        for(char c : s_array){      // count number of each char in s.
            letter[c]++;
        }

        for(int i = 0; i < t.length(); ++i){
            int c = (int) t.charAt(i);
            letter[c]--;

            if(letter[c] < 0){
                return false;
            }
        }
        return true;
    }

    public static void replaceSpaces(char[] str, int trueLength){
        int spaceCount = 0, index, i = 0;

        for(i = 0; i < trueLength; ++i){
            if(str[i] == ' '){
                spaceCount++;
            }
        }
        index = trueLength + spaceCount * 2;
        if(trueLength < str.length) str[trueLength] = '\0'; // End array

        for(i = trueLength - 1; i >= 0; --i){
            if(str[i] == ' '){
                str[index - 1] = '0';
                str[index - 2] = '2';
                str[index - 3] = '%';
                index = index - 3;
            }
            else{
                str[index - 1] = str[i];
                index--;
            }
        }
    }

    // Solution - 1;
    public static boolean permutationOfPalindrome(String phrase){
        int[] table = buildCharFrequencyTable(phrase);
        return checkMaxOneOdd(table);
    }

    public static boolean checkMaxOneOdd(int[] table){
        boolean foundOdd = false;

        for(int x : table){
            if(x % 2 == 1){
                if(foundOdd) return false;
                foundOdd = true;
            }
        }
        return true;
    }

    public static int getCharNumber(Character c){
        int a = Character.getNumericValue('a');
        int z = Character.getNumericValue('z');
        int val = Character.getNumericValue(c);
        if(val >= a && val <= z){
            return val - a;
        }
        return -1;
    }

    public static int[] buildCharFrequencyTable(String phrase){
        int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a' + 1)];
        for(char c : phrase.toCharArray()){
            int x = getCharNumber(c);

            if(x != -1){
                table[x]++;
            }
        }
        return table;
    }

    // Solution - 2;
    public static boolean isPermutationOfPalindrome(String phrase){
        int countOdd = 0;
        int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a') + 1];

        for(char c : phrase.toCharArray()){
            int x = getCharNumber(c);

            if(x != -1){
                table[x]++;
                if(table[x] % 2 == 1){
                    countOdd++;
                }
                else{
                    countOdd--;
                }
            }
        }
        return countOdd <= 1;
    }

    // Solution - 3;
    public static boolean isPalindromePermutation2(String phrase){
        int bitVector = createBitVector(phrase);
        return checkExactlyOneBit(bitVector);
    }

    public static int createBitVector(String phrase){
        int bitVector = 0;

        for(char c : phrase.toCharArray()){
            int x = getCharNumber(c);
            bitVector = toggle(bitVector, x);
        }
        return bitVector;
    }

    public static int toggle(int bitVector, int index){
        if(index < 0){
            return bitVector;
        }
        int mask = 1 << index;

        if((bitVector & mask) == 0){
            bitVector |= mask;
        }
        else{
            bitVector &= ~mask;
        }
        return bitVector;
    }

    public static boolean checkExactlyOneBit(int bitVector){
        return (bitVector & (bitVector - 1)) == 0;
    }

    public static boolean oneEditAway(String first, String second){
        if(first.length() == second.length()){
            return oneEditReplace(first, second);
        }
        else if(first.length() + 1 == second.length()){
            return oneEditReplace(first, second);
        }
        else if(first.length() == second.length() + 1){
            return oneEditReplace(second, first);
        }
        return false;
    }

    public static boolean oneEditReplace(String first, String second){
        boolean foundDifference = false;

        for(int i = 0; i < first.length(); i++){
            if(first.charAt(i) != second.charAt(i)){
                if(foundDifference){
                    return false;
                }
                foundDifference = true;
            }
        }
        return true;
    }
    public static boolean oneEditInsert(String first, String second){
        int index1 = 0;
        int index2 = 0;

        while(index1 < first.length() && index2 < second.length()){
            if(first.charAt(index1) != second.charAt(index2)){
                if(index1 != index2){
                    return false;
                }
                index1++;
            }
            else{
                index1++;
                index2++;
            }
        }
        return true;
    }

    public static boolean oneEditAway2(String first, String second){
        if(Math.abs(first.length() - second.length()) > 1){
            return false;
        }

        // Get shorter and longer strings.
        String s1 = first.length() < second.length() ? first : second;
        String s2 = first.length() > second.length() ? first : second;

        int index1 = 0;
        int index2 = 0;
        boolean foundDifference = false;

        while(index1 < s1.length() && index2 < s2.length()){
            if(s1.charAt(index1) != s2.charAt(index2)){
                // Ensure this is first difference found.
                if(foundDifference){
                    return false;
                }
                foundDifference = true;

                if(s1.length() == s2.length()){     // On replace, move shorter pointer.
                    index1++;
                }
            }
            else{
                index1++;       // if matching move shorter pointer.
            }
            index2++;           // Always move pointer for longer string.
        }
        return true;
    }

    // Solution 1 :
    public static String compressBad(String str){
        String compressedString = "";
        int countConsecutive = 0;

        for(int i = 0; i < str.length(); ++i){
            countConsecutive++;

            if(i + 1 == str.length() || str.charAt(i) != str.charAt(i + 1)){
                compressedString += str.charAt(i) + countConsecutive;
                countConsecutive = 0;
            }
        }
        return compressedString.length() < str.length() ? compressedString : str;
    }


    // Solution 2 :
    public static String compress1(String str){
        StringBuilder compressed = new StringBuilder();
        int countConsecutive = 0;
        for(int i = 0; i < str.length(); ++i){
            countConsecutive++;

            if(i == str.length() || str.charAt(i) != str.charAt(i + 1)){
                compressed.append(str.charAt(i));
                compressed.append(countConsecutive);
                countConsecutive = 0;
            }
        }
        return compressed.length() < str.length() ? compressed.toString() : str;
    }

    // Solution 3 :
    public static String compress(String str){
        // check final length and input string if it would be longer.
        int finalLength = countCompression(str);
        if(finalLength >= str.length()) return str;

        StringBuilder compressed = new StringBuilder(finalLength);      // initial capacity.
        int countConsecutive = 0;

        for(int i = 0; i < str.length(); ++i){
            countConsecutive++;

            if(i + 1 == str.length() || str.charAt(i) != str.charAt(i + 1)){
                compressed.append(str.charAt(i));
                compressed.append(countConsecutive);
                countConsecutive = 0;
            }
        }
        return compressed.toString();
    }

    private static int countCompression(String str){
        int compressedLength = 0;
        int countConsecutive = 0;

        for(int i = 0; i < str.length(); ++i){
            countConsecutive++;

            if(i + 1 == str.length() || str.charAt(i) != str.charAt(i + 1)){
                compressedLength += 1 + String.valueOf(countConsecutive).length();
                countConsecutive = 0;
            }
        }
        return compressedLength;
    }


    public static boolean rotate(int[][] matrix){
        if(matrix.length == 0 || matrix.length != matrix[0].length) return false;
        int n = matrix.length;
        for(int layer = 0; layer < n / 2; layer++){
            int first = layer;
            int last = n - 1 - layer;

            for(int i = first; i < last; i++){
                int offset = i - first;

                int top = matrix[first][i];

                // left -> top
                matrix[first][i] = matrix[last - offset][first];
                // bottom -> left
                matrix[last - offset][first] = matrix[last][last - offset];
                // right -> bottom
                matrix[last][last - offset] = matrix[i][last];
                // top -> bottom
                matrix[i][last] = top;
            }
        }
        return true;
    }


    public static void setZeroes(int[][] matrix){
        boolean[] row = new boolean[matrix.length];
        boolean[] coloumn = new boolean[matrix[0].length];

        for(int i = 0; i < matrix.length; ++i){
            for(int j = 0; j < matrix[0].length; ++j){
                if(matrix[i][j] == 0){
                    row[i] = true;
                    coloumn[j] = true;
                }
            }
        }
        //nullify row
        for(int i = 0; i < row.length; ++i){
            if(row[i]) nullifyRow(matrix, i);
        }
        // nullify col
        for(int j = 0; j < coloumn.length; ++j){
            if(coloumn[j]) nullifyCol(matrix, j);
        }

    }

    public static void nullifyRow(int[][] matrix, int row){
        for(int j = 0; j < matrix[0].length; ++j){
            matrix[row][j] = 0;
        }
    }
    public static void nullifyCol(int[][] matrix, int col){
        for(int i = 0; i < matrix.length; ++i){
            matrix[i][col] = 0;
        }
    }

    public static void setZeroes2(int[][] matrix){
        boolean rowHasZero = false, colHasZero = false;

        // check first row has a zero.
        for(int j = 0; j < matrix[0].length; j++){
            if(matrix[0][j] == 0){
                rowHasZero = true;
                break;
            }
        }

        // check first col has a zero.
        for(int i = 0; i < matrix.length; i++){
            if(matrix[i][0] == 0){
                colHasZero = true;
            }
        }

        // check for zeros in the rest of the array.
        for(int i = 1; i < matrix.length; i++){
            for(int j = 1; j < matrix[0].length; j++){
                if(matrix[i][j] == 0){
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // Nullify rows based values in first coloumn.
        for(int i = 1; i < matrix.length; ++i){
            if(matrix[i][0] == 0)
            {
                nullifyRow(matrix, i);
            }
        }

        // Nullify cols based on values in first rows.
        for(int j = 1; j < matrix[0].length; j++){
            if(matrix[0][j] == 0){
                nullifyCol(matrix, j);
            }
        }

        if(rowHasZero){
            nullifyRow(matrix, 0);
        }
        if(colHasZero){
            nullifyCol(matrix, 0);
        }
    }

    public static boolean rotation(String s1, String s2){
        int len = s1.length();

        if(len == s2.length() && len > 0){
            // Concatenate s1 and s1 within new buffer.
            String s1s1 = s1 + s1;
            return isSubstring(s1s1, s2);
        }
        return false;
    }
    private static boolean isSubstring(String source, String target){
        if(source.length() < target.length()){
            return false;
        }
        for(int i = 0; i < source.length() - target.length(); i++){
            if(source.charAt(i) == target.charAt(0) && source.substring(i, i + target.length()).equals(target)){
                return true;
            }
        }
        return false;
    }






























}
