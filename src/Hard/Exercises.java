package Hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Exercises {
    // add without + operator.
    public int add(int a, int b){           // recursive adding
        if(b == 0){
            return a;
        }
        int sum = a ^ b;    //add without carrying
        int carry = (a & b) << 1;   // carry, but don't add

        return add(a, carry);
    }

    public int add2(int a, int b){
        while(b != 0){
            int sum = a ^ b;
            int carry = (a & b) << 1;
            a = sum;
            b = carry;
        }
        return a;
    }



    // Random number between lower and higher, inclusive
    private int rand(int lower, int higher){
        return lower +(int) Math.random() * (higher - lower + 1);
    }


    public int[] shuffleArrayRecursively(int[] cards, int i){

        if(i == 0){
            return cards;
        }

        shuffleArrayRecursively(cards, i - 1);      // shuffle earlier parts.
        int k = rand(0, i);     // pick random index to swap with.

        // Swap element k and i.
        int temp = cards[k];
        cards[k] = cards[i];
        cards[i] = temp;

        return cards;
    }

    public void shuffleArrayIteratively(int[] cards){
        for(int i = 0; i < cards.length; i++){
            int k = rand(0, i);
            int temp = cards[k];
            cards[k] = cards[i];
            cards[i] = temp;
        }
    }

//
//    public int[] pickMRecursively(int[] original, int m, int i){            // i is the size of original.
//        if(i + 1 == m){     // Base case
//            // return first m elements of original
//        }
//        else if(i + 1 > m){
//            int[] subset = pickMRecursively(original, m, i - 1);
//            int k = new Random().nextInt(0, i);// random value between 0 and i, inclusive
//
//            if(k < m){
//                subset[k] = original[i];
//            }
//            return subset;
//        }
//        return null;
//    }
//
//
//
//    public int[] pickMIteratively(int[] original, int m){
//        int[] subset = new int[m];
//
//
//        // Fill in subset array with first part of original array
//        for(int i = 0; i < m; i++){
//            subset[i] = original[i];
//        }
//
//        // Go through rest of original array.
//        for(int i = m; i < original.length; i++){
//            int k = new Random().nextInt(0, i);
//            if(k < m){
//                subset[k] = original[i];
//            }
//        }
//        return subset;
//    }

    public int findMissing(ArrayList<BitInteger> array){
        // start from the least significant bit, and work our way up.
        return findMissing(array, 0);
    }
    public int findMissing(ArrayList<BitInteger> input, int column){
        if(column == BitInteger.INTEGER_SIZE){
            return 0;
        }

        ArrayList<BitInteger> zeroBits = new ArrayList<>(input.size() / 2);
        ArrayList<BitInteger> oneBits = new ArrayList<>(input.size() / 2);

        for(BitInteger t : input){
            if(t.fetch(column) == 1){
                oneBits.add(t);
            }
            else{
                zeroBits.add(t);
            }
        }

        if(zeroBits.size() <= oneBits.size()){
            int v = findMissing(input, column + 1);
            return (v << 1) | 0;
        }
        else{
            int v = findMissing(input, column + 1);
            return (v << 1) | 1;
        }
    }


    public char[] findLongestSubarray(char[] array){

        for(int len = array.length; len > 1; len--){
            for(int i = 0; i <= array.length - len; i++){
                if(hasEqualLettersNumbers(array, i, i + len - 1)){
                    return extractSubarray(array, i, i + len - 1);
                }
            }
        }
        return null;
    }

    private boolean hasEqualLettersNumbers(char[] array, int start, int end){
        int counter = 0;

        for(int i = start; i <= end; i++){
            if(Character.isLetter(array[i])){
                counter++;
            }
            else{
                counter--;
            }
        }
        return counter == 0;
    }
    // return subarray between start and end (inclusive).
    public char[] extractSubarray(char[] array, int start, int end){
        char[] subarray = new char[end - start + 1];
        for(int i = start; i <= end; i++){
            subarray[i - start] = array[i];
        }
        return subarray;
    }

    public char[] findLongestSubarray2(char[] array){
        // Compute deltas between count of numbers and count of letters.
        int[] deltas = computeDeltaArray(array);

        // find pair in deltas with matching values and largest span.
        int[] match = findLongestMatch(deltas);

        // return the subarray. Note it starts one after initial occurence of this delta.
        return extractSubarray(array, match[0] + 1, match[1]);
    }

    // Compute deltas between count of numbers and count of leters.
    public int[] computeDeltaArray(char[] array){
        int delta = 0;
        int[] deltas = new int[array.length];

        for(int i = 0; i < array.length; i++){
            if(Character.isLetter(array[i])){
                delta++;
            }
            else{
                delta--;
            }
            deltas[i] = delta;
        }
        return deltas;
    }

    // find the matching pair of values in the deltas with the largest difference in indices.
    private int[] findLongestMatch(int[] deltas){
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int[] max = new int[2];

        for(int i = 0; i < deltas.length; i++){
            if(!map.containsKey(deltas[i])){
                map.put(deltas[i], i);
            }
            else{
                int match = map.get(deltas[i]);
                int distance = i- match;
                int longest = max[1] - max[0];
                if(distance > longest){
                    max[1] = i;
                    max[0] = match;
                }
            }
        }
        return max;
    }

    public int countNumberOf2sInRange(int n){
        int count = 0;
        for(int i = 1; i <= n; i++){
            count += countOf2(i);
        }
        return count;
    }

    private int countOf2(int num){
        if(num == 0){
            return 0;
        }
        int count = 0;
        return count + (num % 10 == 2 ? 1 : 0) + countOf2(num / 10);
    }


    private int countOf2sRangeAtDigit(int number, int d){
        int power10 = (int) Math.pow(10, d);
        int nextPower10 = power10 * 10;
        int right = number % power10;

        int roundDown = number - number % nextPower10;
        int roundUp = roundDown + nextPower10;

        int digit = (number / power10) % 10;

        if(digit < 2){
            return roundDown / 10;
        }
        else if(digit == 2){
            return roundDown / 10 + right + 1;
        }
        else{
            return roundUp / 10;
        }
    }

    public int count2sInRange(int number){
        int count = 0;
        int len = String.valueOf(number).length();

        for(int digit = 0; digit < len; digit++){
            count += countOf2sRangeAtDigit(number, digit);
        }
        return count;
    }




















































}
