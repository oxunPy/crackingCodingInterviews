package BitManipulation;

import java.util.ArrayList;

public class Exercises {
    public int updateBits(int n, int m, int i, int j){
        // Create mask for clear bits i through j in n. Example i = 2 and j = 4. Result should be 111000011.
        // For simplicity, we'll use just 8 bits for the example.

        int allOne = ~0;    // will equal sequence of all 1s

        // 1s before position j, then 0s. left = 111100000
        int left = allOne << (j + 1);

        // 1s after position i. right = 000011.
        int right = (1 << i) - 1;

        // All 1s, except for 0s between i and j. mask = 11100011.
        int mask = left | right;

        // Clear j through i then put m there.
        int n_cleared = n & mask;
        int m_shifted = m << i;
        return n_cleared | m_shifted;
    }


    public String printBinary(double num){
        if(num >= 1 || num < 0){
            return "ERROR";
        }
        StringBuilder binary =  new StringBuilder();
        binary.append(".");
        while(num > 0){
            // Setting a limit on length : 32 characters.
            if(binary.length() >= 32){
                return "ERROR";
            }

            double r = num * 2;
            if(r >= 1){
                binary.append(1);
                num = r - 1;
            }
            else{
                binary.append(0);
                num = r;
            }
        }
        return binary.toString();
    }

    public String printBinary2(double num){
        if(num >= 1 || num < 0){
            return "ERROR";
        }
        StringBuilder binary = new StringBuilder();
        double frac = 0.5;
        binary.append(".");
        while(num > 0){
            // Setting limit on length : 32 characters.
            if(binary.length() > 32){
                return "ERROR";
            }
            if(num >= frac){
                binary.append(1);
                num -= frac;
            }
            else{
                binary.append(0);
            }
            frac /= 2;
        }
        return binary.toString();
    }


    public int longestSequence(int n){
        if(n == -1){
            return Integer.BYTES * 8;
        }
        ArrayList<Integer> sequences = getAlternatingSequences(n);
        return findLongestSequence(sequences);
    }
    // returns list of sizes of the sequences. The sequence starts off with the
    // number of 0s (which might be zero). Then alternates with the count of each value.
    public ArrayList<Integer> getAlternatingSequences(int n){
        ArrayList<Integer> sequences = new ArrayList<>();
        int searchingFor = 0;
        int counter = 0;

        for(int i = 0; i < Integer.BYTES * 8; i++){
            if((n & 1) != searchingFor){
                sequences.add(counter);
                searchingFor = n & 1;         // Flipping 0 to 1 or 1 to 0
                counter = 0;
            }
            counter++;
            n >>>= 1;
        }
        sequences.add(counter);

        return sequences;
    }
    // Given the lengths of alternating sequences of 0s and 1s, find the longest one we can build.
    public int findLongestSequence(ArrayList<Integer> seq){
        int maxSeq = 1;

        for(int i = 0; i < seq.size(); i += 2){
            int zeroSeq = seq.get(i);
            int oneSeqRight = i - 1 >= 0 ? seq.get(i - 1) : 0;
            int oneSeqLeft = i + 1 < seq.size() ? seq.get(i + 1) : 0;

            int thisSeq = 0;
            if(zeroSeq == 1){           // Can merge.
                thisSeq = 1 + oneSeqLeft + oneSeqRight;
            }
            else if(zeroSeq > 1){       // Just add a zero to either side.
                thisSeq = 1 + Math.max(oneSeqLeft, oneSeqRight);
            }
            else if(zeroSeq == 0){      // No zero take either side.
                thisSeq = Math.max(oneSeqLeft, oneSeqRight);
            }
            maxSeq = Math.max(maxSeq, thisSeq);
        }
        return maxSeq;
    }

    public int flipBit(int a){
        // if all 1s, this is already the longest sequence.
        if(a == ~0){
            return Integer.BYTES * 8;
        }

        int currentLength = 0;
        int previousLength = 0;
        int maxLength = 1;          //We can always have a sequence of at least one 1.

        while(a != 0){
            if((a & 1) == 1){       // current bit is 1.
                currentLength++;
            }
            else if((a & 1) == 0){  // current bit is 0.
                previousLength = (a & 2) == 0 ? 0 : currentLength;
                currentLength = 0;
            }
            maxLength = Math.max(previousLength + currentLength + 1, maxLength);
            a >>>= 1;
        }
        return maxLength;
    }


    public int getNext(int n){

        // Compute c0 and c1
        int c = n;
        int c0 = 0;
        int c1 = 0;

        while((c & 1) == 0 && c != 0){
            c0++;
            c >>= 1;
        }

        while((c & 1) == 1 && c != 0){
            c1++;
            c >>= 1;
        }

        // Error : if n == 11100000 then there is no bigger number with the same number of 1s.
        if(c0 + c1 == 31 || c0 + c1 == 0){
            return -1;
        }

        int p = c0 + c1;    // position of rightmost non-trailing zero.

        n |= (1 << p);      // Flip rightmost non - trailing zero.
        n &= ~((1 << p) - 1);   // Clear all bits to the right of p.
        n |= (1 << (c1 - 1)) - 1;   // Insert (c1 - 1) ones on the right.
        return n;
    }



    public int getPrev(int n){
        int c = n;
        int c0 = 0;
        int c1 = 0;

        while((c & 1) == 1 && c != 0){
            c >>>= 1;
            c1++;
        }

        while((c & 1) == 0 && c != 0){
            c >>>= 1;
            c0++;
        }
        if(c == 0){
            return -1;
        }

        int p = c0 + c1;        // position of rightmost non-trailing one.
        // clears from p bit onwards.
        n &= (~0 << (p + 1));

        int mask = 1 << (c1 + 1) - 1;       // sequence of c1 + 1 1s.
        n |= mask << (c0 - 1);
        return n;
    }

    public int bitSwapRequired(int a, int b){
        int count = 0;
        for(int c = a ^ b; c != 0; c >>= 1){
            count += c & 1;
        }
        return count;
    }

    public int bitSwapRequired2(int a, int b){
        int count = 0;
        for(int c = a ^ b; c != 0; c &= (c - 1)){
            count++;
        }
        return count;
    }

    public int swapOddEvenBits(int x){
        return ((x & 0xaaaaaaaa) >>> 1) | ((x & 0x55555555) << 1);
    }

    public void drawLine(byte[] screen, int width, int x1, int x2, int y){
        int start_offset = x1 % 8;
        int first_full_byte = x1 / 8;

        if(start_offset != 0){
            first_full_byte++;
        }

        int end_offset = x2 % 8;
        int last_full_byte = x2 / 8;

        if(end_offset != 7){
            last_full_byte--;
        }

        // Set full_bytes
        for(int b = first_full_byte; b <= last_full_byte; b++){
            screen[(width / 8) * y + b] = (byte) 0xFF;
        }

        // Create mask for start and end of line.
        byte start_mask = (byte) (0xFF >> start_offset);
        byte end_mask = (byte) ~(0xFF >> (end_offset + 1));

        // set start and end of line.
        if((x1 / 8) == (x2 / 8)){       // x1 and x2 are in same byte.
            byte mask = (byte) (start_mask & end_mask);
            screen[(width / 8) * y + (x1 / 8)] |= mask;
        }
        else{
            if(start_offset != 0){
                int byte_number = (width / 8) * y + first_full_byte - 1;
                screen[byte_number] |= start_mask;
            }

            if(end_offset != 7){
                int byte_number = (width / 8) * y + last_full_byte + 1;
                screen[byte_number] |= end_mask;
            }
        }
    }


    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(0xFF));
    }






























}
