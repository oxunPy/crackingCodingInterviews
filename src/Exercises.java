import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Exercises {
    // Question1
    public static Integer findOneAfterMax1(ArrayList<Integer> A){

        if(A.isEmpty() || A.size() < 2){
            return null;
        }

        int max = Integer.MIN_VALUE;
        int afterMax = Integer.MIN_VALUE;

        for(int a : A){
            if(a > afterMax){
                if(a > max){
                    afterMax = max;
                    max = a;
                }
                else{
                    afterMax = a;
                }
            }
        }
        return afterMax;
    }
    public static Integer findOneAfterMax2(ArrayList<Integer> A){
        if(A.isEmpty() || A.size() < 2){
            return null;
        }
        Collections.sort(A);
        return A.get(A.size() - 2);
    }
    // using priority queue
    public static Integer findOneAfterMax3(ArrayList<Integer> A){
        if(A.isEmpty() || A.size() < 2){
            return null;
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(2, Collections.reverseOrder());
        for(int a : A){
            maxHeap.add(a);

            if(maxHeap.size() >= 2 && maxHeap.peek() < a){
//                maxHeap.remove();
                maxHeap.add(a);
            }
        }
        maxHeap.poll();
        return maxHeap.peek();
    }



    // Question2

    static String[] phoneMnemonic = {"", "", "abc","def","ghi","jkl","mno","pqrs","tuv","xyz","tuv","wxyz"};

    public static ArrayList<String> getAllPhoneMnemonic(String number){
        if(number.isEmpty()){
            return null;
        }
        ArrayList<String> allMnemonics = new ArrayList<>();
        getAllPhoneMnemonicHelper(number,new char[number.length()], 0, allMnemonics);
        return allMnemonics;
    }

    public static void getAllPhoneMnemonicHelper(String number,char[] prefix, int index, ArrayList<String> allMnemonics){

        if(index == number.length()){
            allMnemonics.add(new String(prefix));
            return;
        }

        for(int i = 0; i < phoneMnemonic[(number.charAt(index) - '0')].length(); i++){
            prefix[index] = phoneMnemonic[number.charAt(index) - '0'].charAt(i);
            getAllPhoneMnemonicHelper(number, prefix, index + 1, allMnemonics);
        }
    }



    // Question3
    static class Rectangle{
        public Point A, B, C, D;

        public Rectangle(Point A, Point B, Point C, Point D){
            this.A = A;
            this.B = B;
            this.C = C;
            this.D = D;

            normalizePointA();
            normalizePointB();
            normalizePointC();
//            normalizePointD();
        }

        public void normalizePointA(){
            if(B.y <= A.y && B.x <= A.x){
                A.swap(B);
            }
            if(C.y <= A.y && C.x <= A.x){
                A.swap(C);
            }
            if(D.y <= A.y && D.x <= A.x){
                A.swap(D);
            }
        }

        public void normalizePointB(){
            if(C.y >= B.y && C.x <= B.x){
                B.swap(C);
            }
            if(D.y >= B.y && D.x <= B.x){
                B.swap(D);
            }
        }
        public void normalizePointC(){
            if(D.y > C.y && D.x >= C.x){
                C.swap(D);
            }
        }

        public int intersectionArea(Rectangle that){

            if(this.contains(that)){
                return that.getSurface();
            }
            else if(that.contains(this)){
                return this.getSurface();
            }
            else if(!isIntersect(that)) return 0;

            Point _A = new Point(Math.max(A.x, that.A.x), Math.max(A.y, that.A.y));
            Point _B = new Point(Math.max(B.x, that.B.x), Math.min(B.y, that.B.y));
            Point _D = new Point(Math.min(D.x, that.D.x), Math.max(D.y, that.D.y));

            int width = _B.y - _A.y;
            int height = _D.x - _A.x;
            return width * height;
        }

        public boolean isIntersect(Rectangle that){
            return A.x + getWidth() > that.A.x && A.y + getHeight() > that.A.y &&
                    that.A.x + that.getWidth() > A.x && that.A.y + that.getHeight() > A.y;
        }

        public boolean contains(Rectangle rect){
            boolean result = this.isIntersect(rect) && A.isLessCoordinates(rect.A);
            return result;
        }
        public int getHeight(){
            return B.y - A.y;
        }
        public int getWidth(){
            return D.x - A.x;
        }
        public int getSurface(){
            return getHeight() * getWidth();
        }
    }
    static class Point{
        public int x, y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public void setPoints(int x, int y){
            this.x = x;
            this.y = y;
        }

        public void swap(Point that){
            int x_copy = x;
            int y_copy = y;
            setPoints(that.x, that.y);
            that.setPoints(x_copy, y_copy);
        }

        public boolean isLessCoordinates(Point p){
            return x <= p.x && y <= p.y;
        }

        @Override
        public String toString(){
            return String.valueOf(x) + " " + String.valueOf(y);
        }
    }


    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(2);
        list.add(-56);
        list.add(39);
        list.add(45);
        list.add(100);
        list.add(56);
        list.add(57);
//        list.add(13);
//        System.out.println(findOneAfterMax1(list));
//        System.out.println(findOneAfterMax2(list));
//        System.out.println(findOneAfterMax3(list));

//        System.out.println(getAllPhoneMnemonic("2473"));

        Rectangle rectangle1 = new Rectangle(new Point(1,10), new Point(1,3), new Point(6,3), new Point(6,10));
        Rectangle rectangle2 = new Rectangle(new Point(3,0), new Point(15,7), new Point(3,7), new Point(15,0));
//        System.out.println(rectangle1.isIntersect(rectangle2));
        System.out.println(rectangle1.intersectionArea(rectangle2));

//
//        System.out.println(rectangle1.A);
//        System.out.println(rectangle1.B);
//        System.out.println(rectangle1.C);
//        System.out.println(rectangle1.D);


//        System.out.println();
//
//        System.out.println(rectangle2.A);
//        System.out.println(rectangle2.B);
//        System.out.println(rectangle2.C);
//        System.out.println(rectangle2.D);
//

        Rectangle rectangle3 = new Rectangle(new Point(8,15), new Point(2,15), new Point(2,10), new Point(8,10));
        Rectangle rectangle4 = new Rectangle(new Point(20,3), new Point(20,8), new Point(4,8), new Point(4,3));

//        System.out.println(rectangle3.isIntersect(rectangle4));
        System.out.println(rectangle3.intersectionArea(rectangle4));

        Rectangle rectangle5 = new Rectangle(new Point(6,7), new Point(6,5), new Point(15,5), new Point(15,7));
        Rectangle rectangle6 = new Rectangle(new Point(20,3), new Point(20,8), new Point(4,8), new Point(4,3));

        System.out.println(rectangle5.intersectionArea(rectangle6));

        Rectangle rectangle7 = new Rectangle(new Point(0,0), new Point(0,4), new Point(4,4), new Point(4,0));
        Rectangle rectangle8 = new Rectangle(new Point(-1,2), new Point(-1,3), new Point(1,2), new Point(1,3));

        System.out.println(rectangle7.intersectionArea(rectangle8));

    }

}
