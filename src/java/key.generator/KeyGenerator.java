package key.generator;

import java.math.BigInteger;
import java.util.Random;

public class KeyGenerator {
    private Random random = new Random();

    public BigInteger generateY1(){
        return null;
    }


    public BigInteger randomBigInt(BigInteger range){
        BigInteger result;
        do {
            result = new BigInteger(range.subtract(new BigInteger("1")).bitLength(), random);
        } while (result.compareTo(range) > 0);

        return result;
    }

}
