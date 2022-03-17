package SortingAndSearching;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;
public class Exercises {
    public void merge(int[] a, int[] b, int lastA, int lastB){
        int indexA = lastA - 1;         // index of last element in array A
        int indexB = lastB - 1;         // index of last element in array B
        int indexMerged = lastA + lastB - 1;    // end of merged Array.


        while(indexB >= 0){
            // end of a greater that end of b
            if(indexA >= 0 && a[indexA] > b[indexB]){
                a[indexMerged] = a[indexA];     // copy element.
                indexA--;
            }
            else{
                a[indexMerged] = b[indexB];     // copy element.
                indexB--;
            }
            indexMerged--;          // move indices.
        }
    }


    class AnagramComparator implements Comparator<String> {
        public String sortedChars(String s){
            char[] content = s.toCharArray();
            Arrays.sort(content);
            return new String(content);
        }

        @Override
        public int compare(String s1, String s2){
            return sortedChars(s1).compareTo(sortedChars(s2));
        }
    }

    public void sort(String[] array){
        HashMap<String, ArrayList<String>> mapList = new HashMap<String, ArrayList<String>>();


        // group words by anagram.
        for(String s : array){
            String key = sortedChars(s);
            if(!mapList.containsKey(key)){
                mapList.put(key, new ArrayList<String>());
            }
            mapList.get(key).add(s);
        }

        // Convert hashTable to array.
        int index = 0;
        for(String key : mapList.keySet()){
            ArrayList<String> list = mapList.get(key);

            for(String t : list){
                array[index] = t;
                index++;
            }
        }
    }

    private String sortedChars(String s){
        char[] content = s.toCharArray();
        Arrays.sort(content);
        return new String(content);
    }


    public int search(int[] array, int left, int right, int x){

        if(right < left){
            return -1;
        }

        int mid = left + right / 2;

        if(array[mid] == x){
            return mid;
        }

        if(array[left] < array[mid]){
            if(x >= array[left] && x < array[mid]){
                return search(array, left, mid - 1, x);
            }
            else{
                return search(array, mid + 1, right, x);
            }
        }

        else if(array[left] > array[mid]){
            if(x >= array[left] && x < array[mid]){
                return search(array, mid + 1, right, x);
            }
            else{
                return search(array, left, mid - 1, x);
            }
        }

        else if(array[left] == array[mid]){
            if(array[mid] != array[right]){
                return search(array, mid + 1, right, x);
            }
            else{
                int result = search(array, left, mid - 1, x);

                if(result == -1){
                    return search(array, mid + 1, right, x);
                }
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

    public int binarySearch(Listy list, int value, int low, int high){
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


    * */

    public int sparseSearch(String[] strings, int first, int last, String str){
        if(first > last){
            return -1;
        }

        // Move mid to the middle.
        int mid = (first + last) / 2;

        // if mid is empty, find closest non-empty string.
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
                left--;
                right++;
            }
        }

        // Check for string, and recurse if necessary.
        if(strings[mid].equals(str)){
            return mid;
        }
        else if(strings[mid].compareTo(str) < 0){       // Search right.
            return sparseSearch(strings, mid + 1, last, str);
        }
        else{   // Search left.
            return sparseSearch(strings, first, mid - 1, str);
        }
    }




























}
