package encryption;


import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;


public class RSA_Algorithm

{

    private static BigInteger p;

    private static BigInteger q;

    private static BigInteger N;

    private static BigInteger phi;

    private static BigInteger e;

    private static BigInteger d;

    private int   bitlength = 1024;

    private Random     r;

    private static BigInteger []array= new BigInteger[5625];
    private  static int[] final_array = new int[5625];





    public RSA_Algorithm()

    {


        System.out.println("here");
//
//        r = new Random();
//
//        p = BigInteger.probablePrime(bitlength, r);
//
//        System.out.println("p"+p);
//
//        q = BigInteger.probablePrime(bitlength, r);
//        System.out.println("q"+q);

        //  N = p.multiply(q);
        N = new BigInteger("86609");

        // System.out.println("n length"+n.bit);
//
//        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // e = BigInteger.probablePrime(bitlength / 2, r);
        e=new BigInteger("17");

//        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
//
//        {
//
//            e.add(BigInteger.ONE);
//
//        }

        //   d = e.modInverse(phi);
        d= new BigInteger("65777");

    }


//    @Override
//    public String toString() {
//        return String;
//    }

    public RSA_Algorithm(BigInteger e, BigInteger d, BigInteger N)

    {

        this.e = e;
        System.out.println("public key"+e);

        this.d = d;
        System.out.println("private key"+ d);

        this.N = N;

    }



    // Encrypt message
//    public static byte[] encrypt(byte[] in){
//
////        Nb = 4;
////        Nk = key.length/4;
////        Nr = Nk + 6;
//
//
//        int lenght=0;
//        byte[] padding = new byte[1];
//        int i;
//        lenght = 16 - in.length % 16;
//        padding = new byte[lenght];
//        padding[0] = (byte) 0x80;
//
//        for (i = 1; i < lenght; i++)
//            padding[i] = 0;
//
//        byte[] tmp = new byte[in.length + lenght];
//        byte[] bloc = new byte[16];
//
//
//      //  w = generateSubkeys(key);
//
//        int count = 0;
//
//        for (i = 0; i < in.length + lenght; i++) {
//            if (i > 0 && i % 16 == 0) {
//                bloc = encryptBloc(bloc);
//                System.out.println(Arrays.toString(bloc));
//              //  arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
//                System.arraycopy(bloc, 0, tmp, i - 16, bloc.length);
//            }
//            if (i < in.length)
//                bloc[i % 16] = in[i];
//            else{
//                bloc[i % 16] = padding[count % 16];
//                count++;
//            }
//        }
//        if(bloc.length == 16){
//            bloc = encryptBloc(bloc);
//            System.arraycopy(bloc, 0, tmp, i - 16, bloc.length);
//        }
//
//        return tmp;

//    }


//
//
//        for (int i = 0; i <arra.length ; i++) {
//    BigInteger bigInteger=new BigInteger(arra[i]);
//    array[i]=bigInteger.modPow(p, q);
//    //
//    //  System.out.println("enc----"+array[i]);
//}
//        for (int i = 0; i <array.length ; i++) {
//    //BigInteger bigInteger=new BigInteger(arra[i]);
//    array[i]=array[i].modPow(d,q);
//    //System.out.println("denc----"+array[i]);


    public static String encrypt(String[] message, int arrayLength) throws IOException

    {
        //System.out.println("e----"+e);
        // System.out.println("d------"+d);

        System.out.println(message[1]);
//        for (int i = 0; i <5625 ; i++) {
//            System.out.println(msg[i]);
//      so
//        }
        System.out.println(arrayLength);
        System.out.println("message----------"+ message);

        for (int i = 0; i <arrayLength ; i++) {
            //System.out.println(message[i]);
            BigInteger bigInteger=new BigInteger(message[i]);
            //System.out.println(bigInteger);
            array[i]=bigInteger.modPow(e, N);
            //
            // System.out.println("enc----"+array[i]);

        }
        ;

        for (int i = 0; i <array.length ; i++) {
            //BigInteger bigInteger=new BigInteger(arra[i]);
            array[i]=array[i].modPow(d,N);
            //System.out.println("denc----"+array[i]);
        }

        for (int i = 0; i <arrayLength ; i++) {
            System.out.println("original--"+message[i]+"decrypted--"+array[i]);

        }

        for (int i = 0; i <arrayLength ; i++) {

            final_array[i]=array[i].intValue();
            System.out.println("original--"+message[i]+"decrypted--"+array[i]+"dec int--"+final_array[i]);

        }

//
//        BufferedImage b = new BufferedImage(75, 75, BufferedImage.TYPE_BYTE_GRAY);
//        for(int x = 0; x<75; x++){
//            for(int y = 0; y<i75; y++){
//                int sum = (int) imagePixels.get(x, y);
//                Color color = new Color(sum, sum, sum);
//
//                b.setRGB(y, x, color.getRGB());
//            }
//        }



//        byte []b=(array.toString()).getBytes();
//        System.out.println((array.toString()).getBytes());
//
//        File inputFile;
//        String name;
//        inputFile = new File("-----encryptedimagersa.jpg");
//        println(inputFile);
//        FileOutputStream fos = new FileOutputStream(inputFile);
//        fos.write(b);
//        fos.flush();
//        fos.close();


        return "test" ;
    }



    // Decrypt message

//    public static byte[] decrypt(byte[] message)
//
//    {
//        System.out.println("e---------2"+e);
//        System.out.println("d--------2"+d);
//        return (new BigInteger(message)).modPow(d, N).toByteArray();
//
//    }

//    private static String[][] intToString(int[][] encrypted)
//
//    {
//        System.out.println(encrypted);
//
//        String[][] test= new String[75][75];
//
//        for(int i = 0; i < 75; i++){
//
//            for(int j = 0; j < 75; j++) {
//
//                test[i][j]= String.valueOf(encrypted[i][j]);
//            }
//
//            }
//        System.out.println(test);
//
//        return test;
//
//    }
}

