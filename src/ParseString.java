import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ParseString {
    public static ArrayList<String> parseString(String input){
        int numOpenParenthesis = 0;
        ArrayList<String> result = new ArrayList<>();

        for(int i = 0; i < input.length(); i++){
            boolean alphabeticChar = Character.isAlphabetic(input.charAt(i));
            if(input.charAt(i) == '('){
                numOpenParenthesis++;
            }
            else if(input.charAt(i) == ')'){
                numOpenParenthesis--;
            }
            else if(alphabeticChar){
                StringBuilder current = new StringBuilder();
                if(numOpenParenthesis > 1){
                    char[] temp = new char[numOpenParenthesis];
                    Arrays.fill(temp, '-');
                    temp[numOpenParenthesis - 1] = ' ';
                    current.append(new String(temp));
                }
                while(i < input.length() && Character.isAlphabetic(input.charAt(i))){
                    current.append(input.charAt(i));
                    i++;
                }
                i--;
                result.add(current.toString());
            }
        }
        return result;
    }


    // Bonus question.

    static class WordWrap{
        public int level;
        public boolean isWordAfterParenthesis;
        public String word;

        public WordWrap(String word){
            this.word = word;
        }
        public WordWrap(String word, int level){
            this.level = level;
            this.word = word;
        }
        public void setLevel(int level){
            this.level = level;
        }
        public int getLevel(){
            return this.level;
        }
        public void isWordAfterParenthesis(){
            isWordAfterParenthesis = true;
        }
        public boolean wordAfterParenthesis(){
            return isWordAfterParenthesis;
        }

        @Override
        public String toString(){
            return word;
        }
    }
    static class CompareString implements Comparator<WordWrap> {
        @Override
        public int compare(WordWrap w1, WordWrap w2){
            String s1 = w1.word;
            String s2 = w2.word;
            if(s1.startsWith("-")){
                s1 = clearedString(s1);
            }
            if(s2.startsWith("-")){
                s2 = clearedString(s2);
            }

            return s1.compareTo(s2);
        }
        private String clearedString(String s){
            int index = 0;
            while(!Character.isAlphabetic(s.charAt(index))){
                index++;
            }
            return s.substring(index);
        }
    }
    public static ArrayList<String> parseStringInSorted(String input){          // in this problem we will use recursion.

        ArrayList<WordWrap> wrappedWords = new ArrayList<>();
        int level = 0;

        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '('){
                if(!wrappedWords.isEmpty()){
                    wrappedWords.get(wrappedWords.size() - 1).isWordAfterParenthesis();
                }
                level++;
            }
            else if(input.charAt(i) == ')'){
                level--;
            }
            else if(Character.isAlphabetic(input.charAt(i))){
                StringBuilder word = new StringBuilder();
                if(level > 1){
                    char[] temp = new char[level];
                    Arrays.fill(temp, '-');
                    temp[level - 1] = ' ';
                    word.append(new String(temp));
                }
                while(i < input.length() && Character.isAlphabetic(input.charAt(i))){
                    word.append(input.charAt(i));
                    i++;
                }
                i--;
                WordWrap wrappedWord = new WordWrap(word.toString(), level);
                wrappedWords.add(wrappedWord);
            }
        }
        Collections.sort(wrappedWords, new Comparator<WordWrap>(){
            @Override
            public int compare(WordWrap w1, WordWrap w2){
                return Integer.compare(w1.level, w2.level);
            }
        });
//        for(int i = 0; i < wrappedWords.size(); i++){
//            System.out.println(wrappedWords.get(i).word + "   " + wrappedWords.get(i).wordAfterParenthesis());
//        }
        return sortWrappedWords(wrappedWords, 1);
    }


    private static ArrayList<String> sortWrappedWords(ArrayList<WordWrap> wrappedWords, int level){    // we recursively sort.
        ArrayList<String> result = new ArrayList<>();
        ArrayList<WordWrap> sortedLevel = getSortedLevel(wrappedWords, level, new CompareString());
        if(sortedLevel.isEmpty()){
            return new ArrayList<String>();
        }
        else{
            for(int i = 0; i < sortedLevel.size(); i++){
                if(sortedLevel.get(i).isWordAfterParenthesis){
                    result.add(sortedLevel.get(i).word);
                    ArrayList<String> nextLevel = sortWrappedWords(wrappedWords, level + 1);
                    result.addAll(nextLevel);
                }
                else{
                    result.add(sortedLevel.get(i).word);
                }
            }
        }
        return result;
    }

    private static int endOfSortedLevel = 0;
    private static ArrayList<WordWrap> getSortedLevel(ArrayList<WordWrap> wrappedWords, int level, Comparator<WordWrap> cmp){
        ArrayList<WordWrap> result = new ArrayList<>();

        while(endOfSortedLevel < wrappedWords.size() && wrappedWords.get(endOfSortedLevel).getLevel() == level){
            result.add(wrappedWords.get(endOfSortedLevel));
            endOfSortedLevel++;
        }
        Collections.sort(result, cmp);
        return result;
    }


    public static void main(String[] args) {
        String input = "(id,created,employee(id,firstname,employeeType(id), lastname),location)";
        System.out.println(parseString(input));
        System.out.println(parseStringInSorted(input));
    }
}
