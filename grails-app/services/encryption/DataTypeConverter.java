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

    public static byte[] bigToByteValue(BigInteger[] bigIntegersII){
        bytes = new byte[bigIntegersII.length];
        for(int i = 0 ;  i < bigIntegersII.length; i ++){
            bytes[i] = bigIntegersII[i].byteValue();
        }
        return bytes;
    }
}
