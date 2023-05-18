package key.generator;

import java.math.BigInteger;
import java.util.Random;

import static key.generator.HexString.HEX_STRING;

public class KeyGenerator {
    private final Random random = new Random();

    /**
     *
     * @return BigInteger array with the private key at index 0 and the public key at index 1
     */
    public BigInteger[] generateKeyPair() {
        BigInteger[] array = new BigInteger[2];
        BigInteger g = new BigInteger("2");
        array[0] = randomBigInt();                  //Vorgabe von Aufgabe, Erzeuger 2
        array[1] = g.modPow(array[0], new BigInteger(HEX_STRING.getHexString(), 16));
        return array;
    }

    /**
     * Generates a random BigInteger with the same bit length as the given BigInteger
     * @return random BigInteger
     */
    public BigInteger randomBigInt() {
        BigInteger range = new BigInteger(HEX_STRING.getHexString(), 16);
        BigInteger result;
        do {
            result = new BigInteger(range.bitLength(), random);
        } while (result.compareTo(range) >= 0 || result.equals(new BigInteger("0")));

        return result;
    }

    /**
     *
     * @param publicKey  public key of the receiver
     * @param exponentA private key of the sender
     * @param msg message to encrypt, takes char value
     * @return encrypted message as BigInteger
     */

    public BigInteger generateY2(BigInteger publicKey, BigInteger exponentA, int msg) {
        //generiert Y2(verschlüsselt den text)
        BigInteger sharedKey = publicKey.modPow(exponentA, new BigInteger(HEX_STRING.getHexString(), 16));
        BigInteger encryptedMSG = sharedKey.multiply(new BigInteger(String.valueOf(msg)));
        return encryptedMSG.mod(new BigInteger(HEX_STRING.getHexString(), 16));
    }

    /**
     *
     * @param y1  public key of the sender
     * @param y2  encrypted message
     * @param privateKey private key of the receiver
     * @return  decrypted message as char
     */
    public char decrypt(BigInteger y1, BigInteger y2, BigInteger privateKey) {
        BigInteger shared = y1.modPow(privateKey, new BigInteger(HEX_STRING.getHexString(), 16));
        BigInteger message = shared.modInverse(new BigInteger(HEX_STRING.getHexString(), 16));
        message = message.multiply(y2);
        message = message.mod(new BigInteger(HEX_STRING.getHexString(), 16));
        return (char) message.intValue();
    }

}
