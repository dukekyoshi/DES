package ver2;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String message = "0000000100100011010001010110011110001001101010111100110111101111";
        String key = "0001001100110100010101110111100110011011101111001101111111110001";

        DES d = new DES(message, key);
//        d.createRoundKey();
//        d.round();
//        System.out.println(format(d.getRoundKey(16)));
//        System.out.println(format(d.getMessage()));
//        System.out.println(format(arrToString(d.initialPermutation())));

    }

    public static String format(String inputText) {
        String out = "";
        inputText = inputText.replaceAll("\\s+", "");
        for(int i = 0; i < inputText.length(); i++) {
            if(i != 0 && (i+1) % 4 == 0) {
                out += inputText.charAt(i) + " ";
            } else {
                out += inputText.charAt(i);
            }
        }
        return out;
    }

    public static String arrToString(int[] obj) {
        String res = "";
        for(int i = 0; i < obj.length; i++) {
            res += obj[i] + "";
        }
        return res;
    }

}
