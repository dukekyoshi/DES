
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String text = "0000000100100011010001010110011110001001101010111100110111101111"; //message block
        String key  = "0001001100110100010101110111100110011011101111001101111111110001"; //original 64 bit key

        DES d = new DES();
        //create 16 round keys
        String keyP = d.permute(key, d.getPC1());
        String[] arrKey = d.split(keyP);
        String[] roundKey = new String[16];
        for(int i = 0; i < roundKey.length; i++) {
            int shift = 2;  //round shift
            int round = i + 1;
            if(round == 1 || round == 2 || round == 9 || round == 16) {
                shift = 1;
            }
            String[] left = d.rotShiftLeft(arrKey[0], shift);
            String[] right = d.rotShiftLeft(arrKey[1], shift);
            roundKey[i] = arrToString(left) + arrToString(right);
            roundKey[i] = d.permute(roundKey[i], d.getPC2());
            arrKey[0] = arrToString(left);
            arrKey[1] = arrToString(right);
        }

        //initial permutation
        String IPBlock = d.permute(text, d.getIP());
        //split the block to 32-bit each
        String[] arr = d.split(IPBlock);
        String res = d.DESFunction(arr[1], roundKey[0]);
        

        //printing
        System.out.println("M  = " + format(text));
        System.out.println("K  = " + format(key));
        System.out.println();
        System.out.println("left:" + format(arr[0]));
        System.out.println("right:" + format(arr[1]));
        System.out.println(format(res));
//        System.out.println("right = " + format(d.DESFunction(arr[1],roundKey[0])));

//        System.out.println("K+ = " + format(keyP));
//        System.out.println();
//        System.out.println("M+ = " + format(temp));
//        System.out.println("L  = " + format(arr[0]));
//        System.out.println("R  = " + format(arr[1]));

        sc.close();
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

    public static String arrToString(Object[] obj) {
        String res = "";
        for(int i = 0; i < obj.length; i++) {
            res += obj[i].toString();
        }
        return res;
    }

}
