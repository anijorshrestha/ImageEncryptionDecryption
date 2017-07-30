package encryption;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sushant on 7/30/2017.
 */
public class AES_Modifier {
    private static Map<String, Double> resultMap = new HashMap<String, Double>();
    private BigInteger[]array ;
    private BigInteger []darray;
    private  int[] encrypted_integer_array;
    private int[] encrypted_integer_arrayUaci;
    private  int[] decrypted_integer_array;
    public AES_Modifier(int height, int width){

    }
    public static void getCorrelations(String[] original_values, String path){
        BufferedImage img  = null;
        try {
            img = ImageIO.read(new File(path + "Encrypted.jpg"));
        } catch (IOException e) {
            System.out.print("=====================================" + "C:\\Users\\Sushant\\Desktop\\encryptionandde\\AES-Algorithm-Encrypted.jpg");
            e.printStackTrace();
        }
        int widths = img.getWidth();
        int heights = img.getHeight();
        int[][] pixels = new int[widths][heights];
        for (int i = 0; i < (heights); i++) {
            for (int j = 0; j < (widths); j++) {
////
                int rgb = img.getRGB(i, j);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int bl = (rgb & 0xFF);
                pixels[i][j] = (r + g + bl) / 3;
            }
        }
        int array_sizes = heights * widths;
        int[] single_arrays = new int[array_sizes];
        int positions = 0;
        for (int i = 0; i < (widths); i++) {
            for (int j = 0; j < (heights); j++) {
                single_arrays[positions++] = pixels[i][j];
            }
        }

        double nr=0,dr_1=0,dr_2=0,dr_3=0,dr=0;
        double []xx;
        double []yy;
        xx =new double[array_sizes];
        yy =new double[array_sizes];
        double []x=new double[array_sizes];
        double []y=new double[array_sizes];
        for (int i = 0; i <array_sizes ; i++) {
            x[i]=Integer.parseInt(original_values[i]);
        }
        for (int i = 0; i < array_sizes; i++) {
            y[i]=single_arrays[i];
        }

        double sum_y=0,sum_yy=0,sum_xy=0,sum_x=0,sum_xx=0;
        int i,n=array_sizes;
        for(i=0;i<array_sizes;i++)
        {
            xx[i]=x[i]*x[i];
            yy[i]=y[i]*y[i];
        }
        for(i=0;i<array_sizes;i++)
        {
            sum_x+=x[i];
            sum_y+=y[i];
            sum_xx+= xx[i];
            sum_yy+=yy[i];
            sum_xy+= x[i]*y[i];
        }
        nr=(n*sum_xy)-(sum_x*sum_y);
        double sum_x2=sum_x*sum_x;
        double sum_y2=sum_y*sum_y;
        dr_1=(n*sum_xx)-sum_x2;
        dr_2=(n*sum_yy)-sum_y2;
        dr_3=dr_1*dr_2;
        dr=Math.sqrt(dr_3);
        resultMap.put("aes_correlation",getCorrelation(nr,dr));
        resultMap.put("aes_npr",getNPR(x, y, array_sizes));

    }

    private static Double getCorrelation(double nr, double dr){
        double r=(nr/dr);
        String s = String.format("%.2f",r);
        r = Double.parseDouble(s);
        return Double.valueOf(r);
    }

    private static Double getNPR(double[]x , double[]y, int arrayLength){
        double count=0;
        for (int j = 0; j <arrayLength ; j++) {
            if (x[j]!=y[j]){
                count++;
            }
        }
        double np= (count/arrayLength);
        double npr=np*100;
        return Double.valueOf(npr);
    }
    public static Map<String, Double> getMap(){
        return resultMap;
    }

}
