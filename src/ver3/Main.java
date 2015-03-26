package ver3;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Encryption e = new Encryption();
        System.out.println("Enter message: ");
        String msg = sc.nextLine();
        e.setMessage(msg);

        System.out.print("Enter password: ");
        String pswd = sc.nextLine();
        e.setKey(pswd);

        e.initialize();
        e.encrypt();

        String cipher = e.getCipherText();
        System.out.println(cipher);

        Decryption d = new Decryption();
        System.out.print("Enter encrypted message: ");
        String enMsg = sc.nextLine();
        d.setCipher(enMsg);

        System.out.print("Enter password: ");
        String pass = sc.nextLine();
        d.setKey(pass);

        String plainMsg = d.decrypt();
        System.out.println("Message: " + binToStr(plainMsg).replace(" ", ""));

        sc.close();
    }

    public static BigInteger hex2decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        BigInteger val = BigInteger.ZERO;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = val.multiply(new BigInteger("16"));
            val = val.add(new BigInteger(d+""));
        }
        return val;
    }

    public static String binToHex(String bin) {
        String hex = "";
        String[] temp = bin.split("(?<=\\G.{4})");
        for(int i = 0; i < temp.length; i++) {
            hex += Integer.toHexString(Integer.parseInt(temp[i], 2)).toUpperCase();
        }
        return hex;
    }

    public static String binToStr(String bin) {
        String text = "";
        String[] temp = bin.split("(?<=\\G.{8})");
        for(int i = 0; i < temp.length; i++) {
            text += (char)Integer.parseInt(temp[i], 2);
        }
        return text;
    }
}
