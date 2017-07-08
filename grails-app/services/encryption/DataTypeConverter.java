package encryption;

import java.math.BigInteger;

/**
 * Created by Sushant on 7/1/2017.
 */
public class DataTypeConverter {
    private static int[] integers;
    private static BigInteger[] bigIntegers;
    private static String[] strings;
    private static byte[] bytes;

    public static byte[] bigToByteValue(BigInteger[] bigIntegersII, int array_Size){
        bytes = new byte[array_Size];
        for(int i = 0 ;  i < array_Size; i ++){
          //  System.out.println("bigIntegersII = " + bigIntegersII[i]);
            bytes[i] = bigIntegersII[i].byteValue();
        }
        return bytes;
    }
}
