package encryption;
//package atnf.atoms.mon.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Simple RSA public key encryption algorithm implementation.
 * <P>
 * Taken from "Paj's" website:
 * <TT>http://pajhome.org.uk/crypt/rsa/implementation.html</TT>
 * <P>
 * Adapted by David Brodrick
 */
public class RSA {
    private BigInteger n, d, e;

    private int bitlen = 1024;

    /** Create an instance that can encrypt using someone elses public key. */
    public RSA(BigInteger newn, BigInteger newe) {
        n = newn;
        e = newe;
    }

    /** Create an instance that can both encrypt and decrypt. */
    public RSA(int bits) {
        bitlen = bits;
        Random r = new Random();
        BigInteger p = new BigInteger("15370255671907611947");
        BigInteger q = new BigInteger("14986774026322099447");
        n = p.multiply(q);
        System.out.println("q = " + n);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
                .subtract(BigInteger.ONE));
        e = new BigInteger("3");
        while (m.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        d = e.modInverse(m);
        System.out.println("d = " + d);
    }

    public RSA(){
        n = new BigInteger("86609");
        e=new BigInteger("17");
        d= new BigInteger("65777");
    }

    /** Encrypt the given plaintext message. */
    public synchronized String encrypt(String message) {
        return (new BigInteger(message.getBytes())).modPow(e, n).toString();
    }

    /** Encrypt the given plaintext message. */
    public synchronized BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    /** Decrypt the given ciphertext message. */
    public synchronized String decrypt(String message) {
        return new String((new BigInteger(message)).modPow(d, n).toByteArray());
    }

    /** Decrypt the given ciphertext message. */
    public synchronized BigInteger decrypt(BigInteger message) {
        return message.modPow(d, n);
    }

    /** Generate a new public and private key set. */
    public synchronized void generateKeys() {
        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bitlen / 2, 100, r);
        BigInteger q = new BigInteger(bitlen / 2, 100, r);
        n = p.multiply(q);
        n = new BigInteger("86609");
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
                .subtract(BigInteger.ONE));
        e = new BigInteger("17");
        d = new BigInteger("65777");
        System.out.println("d = " + d);
    }

    /** Return the modulus. */
    public synchronized BigInteger getN() {
        return n;
    }

    /** Return the public key. */
    public synchronized BigInteger getE() {
        return e;
    }

    /** Trivial test program. */
    public static void main(String[] args) {
        RSA rsa = new RSA(128);

        String text1 = "I am horny";
        System.out.println("Plaintext: " + text1);
        BigInteger plaintext = new BigInteger(text1.getBytes());

        BigInteger ciphertext = rsa.encrypt(plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("Ciphertext: " + ciphertext.toString());
        plaintext = rsa.decrypt(ciphertext);

        String text2 = new String(plaintext.toByteArray());
        System.out.println("Plaintext: " + text2);
    }
}