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
    private static BigInteger []darray= new BigInteger[5625];
    private  static int[] final_array = new int[5625];





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




    public BigInteger[] encrypt(String[] message, int arrayLength) throws IOException{
        for (int i = 0; i <arrayLength ; i++) {
            BigInteger bigInteger=new BigInteger(message[i]);
            array[i]=bigInteger.modPow(e, N);
        }
//        for (int i = 0; i <arrayLength ; i++) {
//            final_array[i]=darray[i].intValue();
//
//        }
        return darray;
    }

    protected Double getCorrelation(int arrayLength, String[] original_value){
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
            y[i]=array[i].intValue();
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

            ans=ans+(Math.abs(x[j]-y[j]));
            System.out.println(ans);

        }
        System.out.println(ans);
        double uaci=(ans/5625);
        System.out.println("uaci"+uaci);

        return uaci ;
    }
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

            ans=ans+(Math.abs(x[j]-y[j]));
            System.out.println(ans);

        }
        System.out.println(ans);
        double uaci=(ans/5625);
        System.out.println("uaci"+uaci);

        return uaci ;
    }

    public BigInteger[] decrypt(String[] message, int arrayLength) throws IOException{
        darray = new BigInteger[5625];

        for (int i = 0; i <array.length ; i++) {
            darray[i]=array[i].modPow(d, N);
        }
        return darray;
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

