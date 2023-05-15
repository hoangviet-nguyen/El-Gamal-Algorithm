import java.math.BigInteger;

import static key.generator.HexString.HEX_STRING;

public class main {

    public static void main(String[] args) {

        BigInteger test = new BigInteger(HEX_STRING.getHexString(), 16);
        System.out.println(test);
    }
}
