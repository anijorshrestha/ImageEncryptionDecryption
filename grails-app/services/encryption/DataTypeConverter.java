package encryption;

import java.math.BigInteger;

/**
 * Created by Sushant on 7/1/2017.
 */
public class DataTypeConverter {
    private int[] integers;
    private BigInteger[] bigIntegers;
    private String[] strings;
    private byte[] bytes;

    public byte[] bigToByteValue(BigInteger[] bigIntegersII, int array_Size){
        bytes = new byte[array_Size];
        for(int i = 0 ;  i < array_Size; i ++){
            bytes[i] = bigIntegersII[i].byteValue();
        }
        return bytes;
    }
}
