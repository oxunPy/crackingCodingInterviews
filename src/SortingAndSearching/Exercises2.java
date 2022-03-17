package SortingAndSearching;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Exercises2 {
    public void merge(int[] A, int[] B, int lastA, int lastB){
        int indexA = lastA - 1;     // index of last element in A
        int indexB = lastB - 1;     // index of last element in B
        int indexMerged = lastA + lastB - 1; // end of merged.


        while(indexB >= 0){

            if(indexA >= 0 && A[indexA] > B[indexB]){
                A[indexMerged] = A[indexA];     // copy element.
                indexA--;
            }
            else{
                A[indexMerged] = B[indexB];     // copy element.
                indexB--;
            }
            indexMerged--;      // move indices.
        }
    }


    class AnagramComparator implements Comparator<String> {
        public String sortedChars(String s){
            char[] content = s.toCharArray();
            Arrays.sort(content);
            return new String(content);
        }

        @Override
        public int compare(String s1, String s2) {
            return sortedChars(s1).compareTo(sortedChars(s2));
        }
    }

    public void sort(String[] array){
        HashMap<String, ArrayList<String>> mapList = new HashMap<>();

        for(String s : array){
            String key = sortChars(s);
            if(!mapList.containsKey(key)){
                mapList.put(key, new ArrayList<String>());
            }
            mapList.get(key).add(s);
        }

        // convert hashTable to array.
        int index = 0;
        for(String key : mapList.keySet()){
            ArrayList<String> list = mapList.get(key);
            for(String s : list){
                array[index] = s;
                index++;
            }
        }
    }

    private String sortChars(String s){
        char[] content = s.toCharArray();
        Arrays.sort(content);
        return new String(content);
    }


    public int search(int[] A, int left, int right, int x){
        int mid = (left + right) / 2;
        if(x == A[mid]){        // Found element.
            return mid;
        }

        if(right < left){
            return -1;
        }

        if(A[left] < A[mid]){           // Left is normally ordered.
            if(x >= A[left] && x < A[mid]){
                return search(A, left, mid - 1, x);
            }
            else{
                return search(A, mid + 1, right, x);        // x > left and x > mid
            }
        }
        else if(A[mid] < A[left]){          // right is normally ordered.
            if(x > A[mid] && x <= A[right]){
                return search(A, mid + 1, right, x);        // search right
            }
            else{
                return search(A, left, mid - 1, x);         // search left
            }
        }
        else if(A[mid] == A[left]){         // Left or right half all repeats.
            if(A[mid] != A[right]){
                return search(A, mid + 1, right, x);
            }
            else{       // Else we have to search both halves
                int result = search(A, left, mid - 1, x);      // search left.
                if(result != -1){
                    return result;
                }
                return search(A, mid + 1, right, x);           // search right.
            }
        }
        return -1;
    }

    /*
        public int search(Listy list, int value){
            int index = 1;

            while(list.elementAt(index) != -1 && list.elementAt(index) < value){
                index *= 2;
            }

            return binarySearch(list, value, index / 2, index);
        }

        private int binarySearch(Listy list, int value, int low, int high){
            int mid;

            while(low <= high){
                mid = (low + high) / 2;

               int middle = list.elementAt(mid);
               if(middle > value || middle == -1){
                   high = mid - 1;
               }
               else if(middle < value){
                   low = mid + 1;
               }
               else{
                   return mid;
               }
           }
           return -1;
       }
    */


    public int search(String[] strings, String str, int first, int last){
        if(first > last) return -1;

        // move mid to the middle.
        int mid = (first + last) / 2;

        if(strings[mid].isEmpty()){
            int left = mid - 1;
            int right = mid + 1;

            while(true){
                if(left < first && right > last){
                    return -1;
                }
                else if(right <= last && !strings[right].isEmpty()){
                    mid = right;
                    break;
                }
                else if(left >= first && !strings[left].isEmpty()){
                    mid = left;
                    break;
                }
                right++;
                left--;
            }
        }
        if(str.equals(strings[mid])){
            return mid;             // Found it!
        }
        else if(strings[mid].compareTo(str) < 0){       // search right
            return search(strings, str, mid + 1, last);
        }
        else{
            return search(strings, str, first, mid - 1);        // search left.
        }
    }

    public int search(String[] strings, String str){
        if(strings == null || str == null || str == ""){
            return -1;
        }
        return search(strings, str, 0, strings.length - 1);
    }


    // Missing int.
    long numberOfInts = (long) Integer.MAX_VALUE + 1;
    byte[] bitField = new byte[(int) (numberOfInts / 8)];
    String filename = "adsfasdfasdf";

    public void findOpenNumber() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader(filename));
        while(in.hasNext()){
            int n = in.nextInt();
            // Find the corresponding number in the bitField by using the OR operator to
            // set the nth bit of a byte
            bitField[n / 8] |= (1 << ((n % 8)));
        }

        for(int i = 0; i < bitField.length; i++){
            for(int j = 0; j < 8; j++){
                if((bitField[i] & (1 << j)) != 0){
                    System.out.println(i * 8 + j);
                }
            }
        }
    }

    // Find duplicates
    public void checkDuplicates(int[] array){
        BitSet bs = new BitSet(32000);

        for(int i = 0; i < array.length; i++){
            int num = array[i];
            int num0 = num - 1; // bitset starts at 0, number starts at 1.

            if(bs.get(num0)){
                System.out.println(num);
            }
            else{
                bs.set(num0);
            }
        }
    }

    class BitSet{
        int[] bitset;

        public BitSet(int size){
            bitset = new int[(size >> 5) + 1];      // divide by 32
        }

        boolean get(int pos){
            int wordNumber = pos >> 32;     // divide by 32
            int bitNumber = (pos & 0x1F);  // mod 32
            return (bitset[wordNumber] & (1 << bitNumber)) != 0;
        }

        void set(int pos){
            int wordNumber = pos >> 32;     // divide by 32
            int bitNumber = (pos & 0x1F);   // mod 32
            bitset[wordNumber] |= 1 << bitNumber;
        }
    }

    public boolean findElement2(int[][] matrix, int element){
        int row = 0;
        int col = matrix[row].length - 1;

        while(row < matrix.length && col >= 0){
            if(element == matrix[row][col]){
                return true;
            }
            else if(element > matrix[row][col]){
                row++;
            }
            else {
                col--;
            }
        }
        return false;
    }

    // Solution 2 .binary Search.
    class Coordinate implements Cloneable{
        public int row, col;

        public Coordinate(int r, int c){
            row = r;
            col = c;
        }

        public boolean inbounds(int[][] matrix){
            return row >= 0 && row < matrix.length && col >= 0 && col < matrix[0].length;
        }

        public boolean isBefore(Coordinate p){
            return row <= p.row && col <= p.col;
        }
        public Object clone(){
            return new Coordinate(row, col);
        }
        public void setToAverage(Coordinate min, Coordinate max){
            row = (min.row + max.row) / 2;
            col = (min.col + max.col) / 2;
        }
    }


    Coordinate findElement(int[][] matrix, int x){
        Coordinate origin = new Coordinate(0, 0);
        Coordinate dest = new Coordinate(matrix.length, matrix[0].length);
        return findElement(matrix, origin, dest, x);
    }

    Coordinate findElement(int[][] matrix, Coordinate origin, Coordinate dest, int x){
        if(!origin.inbounds(matrix) || !dest.inbounds(matrix)){
            return null;
        }

        if(matrix[origin.row][origin.col] == x){
            return origin;
        }
        else if(!origin.isBefore(dest)){
            return null;
        }

        Coordinate start = (Coordinate) origin.clone();
        int diagDist = Math.min(dest.row - origin.row, dest.col - origin.col);
        Coordinate end = new Coordinate(start.row + diagDist, start.col + diagDist);
        Coordinate p = new Coordinate(0, 0);

        // do binary search on the diagonal, looking for the first element > x;
        while(start.isBefore(end)){
            p.setToAverage(start, end);         // mid point.

            if(x > matrix[p.row][p.col]){
                start.row = p.row + 1;
                start.col = p.col + 1;
            }
            else{
                end.row = p.row - 1;
                end.col = p.col - 1;
            }
        }

        // Split the grid into quadrants.Search the bottom left and the top right.
        return partitionAndSearch(matrix, origin, dest, start, x);
    }

    Coordinate partitionAndSearch(int[][] matrix, Coordinate origin, Coordinate dest, Coordinate pivot, int x){
        Coordinate lowerLeftOrigin = new Coordinate(pivot.row, origin.col);
        Coordinate lowerLeftDest = new Coordinate(dest.row, pivot.col - 1);
        Coordinate upperRightOrigin = new Coordinate(origin.row, pivot.col);
        Coordinate upperRightDest = new Coordinate(pivot.row - 1, dest.col);

        Coordinate lowerLeft = findElement(matrix, lowerLeftOrigin, lowerLeftDest, x);
        if(lowerLeft == null){
            return findElement(matrix, upperRightOrigin, upperRightDest, x);
        }
        return lowerLeft;
    }


    class RankNode{
        public int left_size = 0;
        public RankNode left, right;
        public int data;

        public RankNode(int d){
             data = d;
        }

        public void insert(int d){
            if(d <= data){
                if(left != null) left.insert(d);
                else left = new RankNode(d);
                left_size++;
            }
            else{
                if(right != null) right.insert(d);
                else right = new RankNode(d);
            }
        }

        public int getRank(int d){
            if(d == data){
                return left_size;
            }
            else if(d < data){
                if(left == null) return -1;
                else return left.getRank(d);
            }
            else{
                int right_size = right == null ? -1 : right.getRank(d);
                if(right_size == -1){
                    return -1;
                }
                else return left_size + 1 + right_size;
            }
        }
    }

    public void sortValleyPeak(int[] array){
        Arrays.sort(array);

        for(int i = 1; i < array.length; i += 2){
            swap(array, i - 1, i);
        }
    }
    private void swap(int[] array, int left, int right){
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }                   // O(nlog(n)) time comp.


    // Optimal solution.
    public void sortValleyPeak2(int[] array){
        for(int i = 1; i < array.length; i += 2){
            int biggestIndex = maxIndex(array, i - 1, i, i + 1);
            if(i != biggestIndex){
                swap(array, i, biggestIndex);
            }
        }
    }

    private int maxIndex(int[] array, int a, int b, int c){
        int len = array.length;

        int aValue = a >= 0 && a < len ? array[a] : Integer.MIN_VALUE;
        int bValue = b >= 0 && b < len ? array[b] : Integer.MIN_VALUE;
        int cValue = c >= 0 && c < len ? array[c] : Integer.MIN_VALUE;

        int max = Math.max(aValue, Math.max(bValue, cValue));

        if(aValue == max) return a;
        else if(bValue == max) return b;
        else return c;
    }       // this algorithm takes O(n) time.






























}
