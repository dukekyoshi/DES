package ver3;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        DES d = new DES();
        d.setMessage("the quick brown fox is jumping on the street so please don't tell Bob about this");
        d.setKey("password");
        d.initialize();
        d.encrypt();
        String cipher = binToHex(d.cipher);
        String plain = binToStr(d.decrypt());
        System.out.println(cipher);
        System.out.println(plain);
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
