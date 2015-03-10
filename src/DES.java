
import java.util.ArrayList;

public class DES {

    /**
     * ATTRIBUTES
     */   
    //initial permutation
    private int[] IP = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9,  1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };

    //PC1 -> subkey permutation box
    private int[] PC1 = new int[] {
        57, 49, 41, 33, 25, 17, 9,
        1,  58, 50, 42, 34, 26, 18,
        10, 2,  59, 51, 43, 35, 27,
        19, 11, 3,  60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7,  62, 54, 46, 38, 30, 22,
        14, 6,  61, 53, 45, 37, 29,
        21, 13, 5,  28, 20, 12, 4
    };

    //PC2 -> subkey permutatian final box
    private int[] PC2 = new int[] {
        14, 17, 11, 24, 1,  5,
        3,  28, 15, 6,  21, 10,
        23, 19, 12, 4,  26, 8,
        16, 7,  27, 20, 13, 2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32
    };

    //expansion P-box
    private int[] exp = new int[] {
        32, 1,  2,  3,  4,  5,
        4,  5,  6,  7,  8,  9,
        8,  9,  10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1
    };

    //s-boxes
    private int[][] s1 = new int[][] {
        {14, 4, 14, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 10, 3, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };
    private int[][] s2 = new int[][] {
        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    };

    private int[][] s3 = new int[][] {
        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    };

    private int[][] s4 = new int[][] {
        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    };

    private int[][] s5 = new int[][] {
        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    };

    private int[][] s6 = new int[][] {
        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 10, 0, 8, 13}
    };

    private int[][] s7 = new int[][] {
        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    };

    private int[][] s8 = new int[][] {
        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 10, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    };

    ArrayList<int[][]> sBox;

    //straight permutation box after s-box substitution
    private int[] P = new int[] {
        16, 7,  20, 21,
        29, 12, 28, 17,
        1,  15, 23, 26,
        5,  18, 31, 10,
        2,  8,  24, 14,
        32, 27, 3,  9,
        19, 13, 30, 6,
        22, 11, 4,  25
    };

    /**
     * CONSTRUCTOR
     */
    DES() {
        sBox = new ArrayList<int[][]>();
        sBox.add(s1);
        sBox.add(s2);
        sBox.add(s3);
        sBox.add(s4);
        sBox.add(s5);
        sBox.add(s6);
        sBox.add(s7);
        sBox.add(s8);
    }

    /**
     * ACCESSOR
     */
    public int[] getIP() {
        return IP;
    }
    public int[] getPC1() {
        return PC1;
    }
    public int[] getPC2() {
        return PC2;
    }
    public int[][] getSBox(int sBoxNumber) {
        return sBox.get(sBoxNumber);
    }

    /**
     * MAIN METHOD
     */
    //f(Ri-1,Ki)
    public String DESFunction(String block, String roundKey) {
        String out = "";

        //P-box expansion
        String tempExp = permute(block, exp);
        
        //xor with round key
        String tempXOR = xor(tempExp, roundKey);

        //substitute s-box
        int sbox = 0; //<- sbox 1
        String sBoxRes = "";
        for(int i = 0; i < tempXOR.length(); i+=6) {
            String temp = tempXOR.charAt(i) + "" + tempXOR.charAt(i+5);
            int row = Integer.parseInt(temp, 2);
            temp = tempXOR.substring(i+1, i+5);
            int column = Integer.parseInt(temp, 2);
            sBoxRes += pad(Integer.toBinaryString(sBox.get(sbox)[row][column]));
            sbox++;
        }
        //straight permutation
        String spRes = permute(sBoxRes, P);

        out = spRes;

        return out;
    }
    public String[] split(String permutatedText) {
        String[] arr = new String[2];
        int halfIndex = permutatedText.length() / 2;
        arr[0] = permutatedText.substring(0, halfIndex);
        arr[1] = permutatedText.substring(halfIndex, permutatedText.length());
        return arr;
    }
    public String permute(String text, int[] permutationBox) {
        String out = "";
        if(text.length() > 64) {
            text = text.substring(0, 64);
        }
        for(int i = 0; i < permutationBox.length; i++) {
            out += text.charAt(permutationBox[i]-1);
        }
        return out;
    }
    public String[] rotShiftLeft(String text, int bits) {
        String[] out = new String[text.length()];
        for(int i = 0; i < text.length(); i++) {
            int shift = i - bits;
            if(shift < 0) {
                shift = text.length() + shift;
            }
            out[shift] = text.charAt(i) + "";
        }
        return out;
    }

    /**
     * OTHERS
     */
    //a xor b
    public String xor(String a, String b) {
        String res = "";
        for(int i = 0; i < a.length(); i++) {
            res += (Integer.parseInt(a.charAt(i)+"") ^ Integer.parseInt(b.charAt(i)+"")) + "";
        }
        return res;
    }
    public String pad(String binary) {
        String res = "";
        if(binary.length() < 4) {
            int padding = 4 - binary.length();
            for(int i = 0; i < padding; i++) {
                res += "0";
            }
        }
        res += binary;
        return res;
    }
    public String toByte(String text) {
        String bytePlainText = "";
        char[] arrByte = text.toCharArray();
        for(int i = 0; i < arrByte.length; i++) {
            int val = arrByte[i];
            String binaryString = Integer.toBinaryString(val);
            String add = "0";
            while(binaryString.length() < 8) {
                binaryString = add + binaryString;
            }
            bytePlainText += binaryString;
        }
        return bytePlainText;
    }
    
}
