package ver3;

import java.util.ArrayList;

public class Encryption {

    /**
     * ATTRIBUTES
     */

    //PC1 -> subkey permutation box
    private int[] PC1 = {
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
    private int[] PC2 = {
        14, 17, 11, 24, 1,  5,
        3,  28, 15, 6,  21, 10,
        23, 19, 12, 4,  26, 8,
        16, 7,  27, 20, 13, 2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32
    };

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

    //expansion P-box
    private int[] exp = {
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
    private int[][] s1 = {
        {14, 4, 14, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 10, 3, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };
    private int[][] s2 = {
        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    };

    private int[][] s3 = {
        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    };

    private int[][] s4 = {
        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    };

    private int[][] s5 = {
        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    };

    private int[][] s6 = {
        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 10, 0, 8, 13}
    };

    private int[][] s7 = {
        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    };

    private int[][] s8 = {
        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 10, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    };

    //straight permutation box after s-box substitution
    private int[] P = {
        16, 7,  20, 21,
        29, 12, 28, 17,
        1,  15, 23, 26,
        5,  18, 31, 10,
        2,  8,  24, 14,
        32, 27, 3,  9,
        19, 13, 30, 6,
        22, 11, 4,  25
    };

    //final permutation
    private int[] IP1 = {
        40, 8,  48, 16, 56, 24, 64, 32,
        39, 7,  47, 15, 55, 23, 63, 31,
        38, 6,  46, 14, 54, 22, 62, 30,
        37, 5,  45, 13, 53, 21, 61, 29,
        36, 4,  44, 12, 52, 20, 60, 28,
        35, 3,  43, 11, 51, 19, 59, 27,
        34, 2,  42, 10, 50, 18, 58, 26,
        33, 1,  41, 9,  49, 17, 57, 25
    };

    String strMsg;
    String strKey;
    String msg;
    String key;
    String[] roundKey;
    String cipher;
    ArrayList<int[][]> sBox;
    String[] msgBlock;

    Encryption() {
        strMsg = "";
        strKey = "";
        msg = "";
        key = "";
        roundKey = new String[16];
        cipher = "";
        msgBlock = new String[1];

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

    public void encrypt() {
        createSubKey();
        String tempCipher = "";
        for(int block = 0; block < msgBlock.length; block++) {
            String init = initialPermutation(msgBlock[block]);
            String L0 = init.substring(0,init.length()/2);
            String R0 = init.substring(init.length()/2, init.length());

            //16 rounds
            String[] arr = {L0, R0};
            for(int i = 0; i < 16; i++) {
                arr = round(arr[0], arr[1], roundKey[i]);
            }

            tempCipher = arr[1] + arr[0];
            tempCipher = permute(tempCipher, IP1);
            cipher += tempCipher;
        }
    }

    public String getCipherText() {
        String cipherText = "";
        String[] temp = cipher.split("(?<=\\G.{4})");
        for(int i = 0; i < temp.length; i++) {
            cipherText += Integer.toHexString(Integer.parseInt(temp[i], 2));
        }
        return cipherText;
    }

    public String[] round(String left, String right, String rndKey) {
        String Ln = right;
        String Rn = xor(left, function(right, rndKey));
        String[] arr = {Ln, Rn};
        return arr;
    }

    public String function(String right, String rndKey) {
        String res = "";

        res = xor(rndKey, permute(right, exp));

        String[] B = res.split("(?<=\\G.{6})");
        String temp = "";
        for(int i = 0; i < sBox.size(); i++) {
            String index = B[i];
            int[][] sbox = sBox.get(i);
            int row = Integer.parseInt(index.charAt(0) + "" + index.charAt(index.length()-1) + "",2);
            int column = Integer.parseInt(index.substring(1, index.length()-1), 2);
            temp += String.format("%4s", Integer.toBinaryString(sbox[row][column])).replace(' ', '0');
        }

        res = permute(temp, P);

        return res;
    }

    public String initialPermutation(String msgblock) {
        String res = permute(msgblock, IP);
        return res;
    }

    public void createSubKey() {
        String K2 = permute(key, PC1);
        String left = K2.substring(0, K2.length()/2);
        String right = K2.substring(K2.length()/2, K2.length());
        for(int i = 0; i < 16 ; i++) {
            int shift = 2;
            int round = i + 1;
            if(round == 1 || round == 2 || round == 9 || round == 16) {
                shift = 1;
            }
            String leftN = leftShift(left, shift);
            String rightN = leftShift(right, shift);
            roundKey[i] = leftN+rightN;
            left = leftN;
            right = rightN;
        }

        for(int i = 0; i < roundKey.length; i++) {
            roundKey[i] = permute(roundKey[i], PC2);
        }
    }

    public String leftShift(String text, int bit) {
        String res = "";
        char[] ch = new char[text.length()];
        for(int i = 0; i < ch.length; i++) {
            int shift = (i-bit) < 0 ? text.length()+(i-bit) : (i-bit);
            ch[shift] = text.charAt(i);
        }
        res = new String(ch);
        return res;
    }

    public void initialize() {
        String tempMessage = "";
        for(int i = 0; i < strMsg.length(); i++) {
            tempMessage += String.format("%8s", Integer.toBinaryString(strMsg.charAt(i))).replace(' ', '0');
        }

        int padLength = (64 - (tempMessage.length() % 64)) / 8;
        String padding = "";
        for(int i = 0; i < padLength; i++) {
            padding += String.format("%8s", Integer.toBinaryString(32)).replace(' ', '0');
        }

        msg = tempMessage + padding;

        msgBlock = msg.split("(?<=\\G.{64})");

        for(int i = 0; i < strKey.length(); i++) {
            key += String.format("%8s", Integer.toBinaryString(strKey.charAt(i))).replace(' ', '0');
        }
    }
    public void setMessage(String m) {
        strMsg = m;
    }

    public void setKey(String k) {
        strKey = k;
    }

    public String permute(String text, int[] permutationBox) {
        String res = "";
        for(int i = 0; i < permutationBox.length; i++) {
            res += text.charAt(permutationBox[i]-1);
        }
        return res;
    }

    public String xor(String a, String b) {
        String res = "";
        for(int i = 0; i < a.length(); i++) {
            int temp1 = a.charAt(i)-48;
            int temp2 = b.charAt(i)-48;
            int xor = temp1 ^ temp2;
            res += xor;
        }
        return res;
    }
}
