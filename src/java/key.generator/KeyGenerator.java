package key.generator;

import java.math.BigInteger;
import java.util.Random;

import static key.generator.HexString.HEX_STRING;

public class KeyGenerator {
    private Random random = new Random();

    public BigInteger generateY1(){
        return null;
    }


    public BigInteger randomBigInt(BigInteger range){
        BigInteger result;
        do {
            result = new BigInteger(range.bitLength(), random);
        } while (result.compareTo(range) >= 0 || result.equals(new BigInteger("0")));

        return result;
    }


    public BigInteger generateY2(BigInteger y1, BigInteger exponentB,int msg){
        BigInteger sharedKey = y1.modPow(exponentB, new BigInteger(HEX_STRING.getHexString(),16));
        BigInteger encryptedMSG = sharedKey.multiply(new BigInteger(String.valueOf(msg)));
        return encryptedMSG.mod(new BigInteger(HEX_STRING.getHexString()));
    }

}
