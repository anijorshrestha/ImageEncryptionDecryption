package encryption;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;


public class RSA_Algorithm

{

    private static BigInteger p;

    private static int image_widht;
    private static int image_height;

    private static BigInteger q;

    private static BigInteger N;

    private static BigInteger phi;

    private static BigInteger e;

    private static BigInteger d;

    private int   bitlength = 1024;

    private Random     r;

    private static BigInteger []array= new BigInteger[5625];
    private static BigInteger []darray= new BigInteger[5625];
    private  static int[] encrypted_integer_array = new int[5625];
    private  static int[] encrypted_integer_arrayUaci = new int[5625];
    private  static int[] decrypted_integer_array = new int[5625];
    double ri,nr=0,dr_1=0,dr_2=0,dr_3=0,dr=0;
    double xx[],xy[],yy[];





    public RSA_Algorithm()

    {
        N = new BigInteger("86609");
        e=new BigInteger("17");
        d= new BigInteger("65777");
    }

    public RSA_Algorithm(BigInteger e, BigInteger d, BigInteger N)

    {

        this.e = e;
        System.out.println("public key"+e);

        this.d = d;
        System.out.println("private key"+ d);

        this.N = N;

    }




    public int[] encrypt(String[] message, int arrayLength) throws IOException{
        for (int i = 0; i <arrayLength ; i++) {
            BigInteger bigInteger=new BigInteger(message[i]);
            array[i]=bigInteger.modPow(e, N);
            encrypted_integer_array[i] = array[i].intValue();
        }
        writeImageInFolder(encrypted_integer_array,"Encrypted");
        return encrypted_integer_array;
    }
//
//    protected Double getCorrelation(int arrayLength, String[] original_value){


//        double ans=0;
//        for (int j = 0; j <arrayLength ; j++) {
//
//            ans=ans+(Math.abs(x[j]-y[j]));
//            System.out.println(ans);
//
//        }
//        System.out.println(ans);
//        double uaci=(ans/5625);
//        System.out.println("uaci"+uaci);
//
//        return uaci ;
//    }
    protected Double getCorrelation(int arrayLength, String[] original_value, BigInteger[] encrypted_value){
        double r,nr=0,dr_1=0,dr_2=0,dr_3=0,dr=0;
        double xx[],xy[],yy[];
        xx =new double[arrayLength];
        xy =new double[arrayLength];
        yy =new double[arrayLength];
        double x[]=new double[arrayLength];
        double y[]=new double[arrayLength];
        for (int i = 0; i <arrayLength ; i++) {
            x[i]=Integer.parseInt(original_value[i]);
        }
        for (int i = 0; i < arrayLength; i++) {
            y[i]=encrypted_value[i].intValue();
        }
        for (int i = 0; i <arrayLength ; i++) {
            System.out.println("org:"+x[i]+"enc:"+y[i]);
        }

        double sum_y=0,sum_yy=0,sum_xy=0,sum_x=0,sum_xx=0;
        int i,n=arrayLength;
        for(i=0;i<n;i++)
        {
            xx[i]=x[i]*x[i];
            yy[i]=y[i]*y[i];
        }
        for(i=0;i<n;i++)
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
        r=(nr/dr);
        String s = String.format("%.2f",r);
        r = Double.parseDouble(s);
        System.out.println("Total Numbers:"+n+"\nCorrelation Coefficient:"+r);
        double count=0;
        for (int j = 0; j <arrayLength ; j++) {
            if (x[j]!=y[j]){
                count++;
            }
        }
        System.out.println(count);
        System.out.println(arrayLength);
        double np= (count/arrayLength);
        System.out.println(np);
        double npr=np*100;
        System.out.println(npr);
        double ans=0;
        for (int j = 0; j <arrayLength ; j++) {

            ans=ans+((Math.abs(x[j]-y[j]))/255);
            System.out.println(ans);

        }
        System.out.println(ans);
        double uaci=(ans/5625);
        System.out.println("uaci"+uaci);

        return uaci ;
    }

    public int[] decrypt() throws IOException{
        darray = new BigInteger[5625];

        for (int i = 0; i <array.length ; i++) {
            darray[i]=array[i].modPow(d, N);
            decrypted_integer_array[i] = darray[i].intValue();
        }
        writeImageInFolder(decrypted_integer_array, "Decrypted");
        return decrypted_integer_array;
    }

    public void writeImageInFolder(int[] inputArray, String name) throws IOException {
        BufferedImage img = new BufferedImage(image_widht, image_height, BufferedImage.TYPE_INT_BGR);
        int i = 0;
        System.out.println("From");
        for (int x = 0; x < image_height ; x++) {           //++i and i++
            for (int y = 0; y < image_widht; y++) {
//                int rgb=inputArray[i++] ;
//                int rgb=inputArray[i++] ;  (rgb >> 8) & 0xFF
                int rgb= ((inputArray[i] << 16) | 0xFF) + ((inputArray[i] << 8) | 0xFF) + (inputArray[i] | 0xFF) ;
//                rgb /= 3;
                System.out.println(inputArray[i++] + " ==== rgb = " + rgb);
//                System.out.println(inputArray[i] + " ==== rgb = " + rgb);
                img.setRGB(x, y, rgb);
//                img.setRGB(x, y, inputArray[i++]);
            }
        }

        File outputFile = new File("C:/Users/Sushant/Desktop/encryptionandde/RSA-Algorithm-"+name);
        ImageIO.write(img, "jpg", outputFile);

    }

    public void setImage(int width, int height){
        this.image_widht = width;
        this.image_height = height;
    }
}

