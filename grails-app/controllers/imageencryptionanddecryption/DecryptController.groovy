package imageencryptionanddecryption
import encryption.AES
import encryption.Constants
import encryption.RSA_Algorithm
import grails.util.Holders
import org.apache.commons.io.FilenameUtils
import javax.imageio.ImageIO
import java.awt.Image
import java.awt.image.BufferedImage
import java.awt.image.DataBuffer
import java.security.MessageDigest
class DecryptController {
    def index() {}
    def renderFinalView = {
        redirect(controller: "encryption", action: "renderFinalView")
    }

    def save() {
        savePhotoToDisk(request.getFile('photo'))
        print("Hereee----")
    }

    def savePhotoToDisk(def f) {
        println("Now heree----")
        Constants constants = new Constants();
        constants.PHOTOS_DIR = Holders.getGrailsApplication().getMainContext().getResource("/").getFile().getAbsolutePath();
        def okContentTypes = ['image/png', 'image/jpeg', 'image/jpg']
        if (f.empty) {
            print("not empty")
            flash.message = 'file cannot be empty'
            return
        }
        if (!okContentTypes.contains(f.contentType)) {
            print("type")
            flash.message = "Image must be one of: $okContentTypes"
            return
        }
        print("1------")
        def extension = FilenameUtils.getExtension(f.originalFilename)
        def serverImagesDir = new File(encryption.Constants.PHOTOS_DIR)
        String image_path = serverImagesDir.absolutePath + "/" + f.originalFilename
        print(image_path);
        println("2----------")
        if (serverImagesDir.exists()) {
            println("photoooo---")
//            def fileName = "photo" + ".$extension"
            File destinationFile = new File(serverImagesDir, f.originalFilename)
            //  encryption.photoUrl = fileName
            f.transferTo(destinationFile)
//            println("convert")
            // read "any" type of image (in this case a png file)
            byte[] k = new byte[16];
            try {
//                k=AES.keygeneration();
                println(params)
                def key = params.user_key
                //String text = "rojina";
                MessageDigest msg = MessageDigest.getInstance("MD5");
                msg.update(key.getBytes(), 0, key.length());
                String digest1 = new BigInteger(1, msg.digest()).toString(16);
                System.out.println("MD5: " + digest1.length());
                System.out.println("MD5: " + digest1);
                System.out.println("MD5: " + digest1.substring(0, 16));
                k = digest1.substring(0, 16).bytes;
            }
            catch (Exception e) {
                println "Problem in keyGeneration!!!!!!!!";
            }
            println(image_path);
            // BufferedImage image = ImageIO.read(new File(image_path));   //delete this one
            BufferedImage image = ImageIO.read(new File(image_path));//  Change hereeeeee your directory ok
            String path = "/home/rojina/Desktop";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] b = baos.toByteArray();
            byte[] b2 = new byte[b.length - 620];
            byte[] b1 = new byte[0];
            int width = image.getWidth();
            println(width);
            int height = image.getHeight();
            println(height)
            int[][] pixel = new int[width][height];
            for (int i = 0; i < (height); i++) {
                for (int j = 0; j < (width); j++) {
//                    DataBuffer dataBuffer = image.getRaster().getDataBuffer();
//                    int grayLevel = dataBuffer.getElem(i * image.getWidth() + j);
//
//                    pixel[i][j] = grayLevel * 1.0;
////
                    int rgb = image.getRGB(i, j);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int bl = (rgb & 0xFF);
//                    println("r-"+r+"g-"+g+"b-"+bl);
                    pixel[i][j] = (r + g + bl) / 3;
                }
            }
            println(pixel.length);
            int array_size = height * width;
            int[] single_array = new int[array_size];
            int position = 0;
            for (int i = 0; i < (width); i++) {
                for (int j = 0; j < (height); j++) {
                    single_array[position++] = pixel[i][j];
                }
            }
            for (int i = 0; i <single_array.length ; i++) {
                println(single_array[i]);
            }


            for (int i = 0; i < (b2.length); i++)
                b2[i] = b[i + 620];

            b2 = AES.encrypt(b2, k, 10)
            b1 = new byte[b2.length + 620];
            for (int i = 0; i < b1.length; i++) {
                if (i < 620) b1[i] = b[i];
                else b1[i] = b2[i - 620];
            }

            println "Before"
            println "key == " + k
            println "b------------------->" + b.length
            println "b1------------------>" + b1.length
            println "b2------------------>" + b2.length
            File inputFile
            String name
            if (f.originalFilename.indexOf(".") > 0)
                name = f.originalFilename.substring(0, f.originalFilename.lastIndexOf("."));
            inputFile = new File(path + name + "encryptedimage.jpg");
            println(inputFile)
            FileOutputStream fos = new FileOutputStream(inputFile);
            fos.write(b1);
            fos.flush();
            fos.close();

            ;


            println "After encryption"
            println "b------------------->" + b.length
            println "b1------------------>" + b1.length
            println "b2------------------>" + b2.length

            for(int i=0; i<b2.length; i++)
                b2[i]=b1[i+620];

            b2=AES.decrypt(b2,k,10)
            for(int i=0; i<b.length; i++) {
               // println b2.length + " ===== " + i
                if(i<620) b[i]=b1[i];
                else b[i]=b2[i-620]; }

            inputFile=new File(path+"decryptedimage.jpg");
            println(inputFile)
            fos = new FileOutputStream(inputFile);
            println "after decrypt"
            println "b------------------->" + b.length
            println "b1------------------>" + b1.length
            println "b2------------------>" + b2.length
            fos.write(b);
            fos.flush();
            fos.close();
            println("dec img")
            BufferedImage img = null;

            try
            {
                img = ImageIO.read(new File("/home/rojina/Desktopagencryptedimage.jpg"));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            int widths = img.getWidth();
            println(widths);
            int heights = img.getHeight();
            println(heights)
            int[][] pixels = new int[widths][heights];
            for (int i = 0; i < (heights); i++) {
                for (int j = 0; j < (widths); j++) {
//                    DataBuffer dataBuffer = image.getRaster().getDataBuffer();
//                    int grayLevel = dataBuffer.getElem(i * image.getWidth() + j);
//
//                    pixel[i][j] = grayLevel * 1.0;
////
                    int rgb = img.getRGB(i, j);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int bl = (rgb & 0xFF);
//                    println("r-"+r+"g-"+g+"b-"+bl);
                    pixels[i][j] = (r + g + bl) / 3;
                }
            }
            println(pixel.length);
            int array_sizes = heights * widths;
            int[] single_arrays = new int[array_sizes];
            int positions = 0;
            for (int i = 0; i < (widths); i++) {
                for (int j = 0; j < (heights); j++) {
                    single_arrays[positions++] = pixels[i][j];
                }
            }
            for (int i = 0; i <single_arrays.length ; i++) {
                println(single_arrays[i]+"--"+single_array[i]);
            }

            println("src img")

            double r,nr=0,dr_1=0,dr_2=0,dr_3=0,dr=0;
            double []xx;
            double []xy;
            double []yy;
            xx =new double[array_sizes];
            xy =new double[array_sizes];
            yy =new double[array_sizes];
            double []x=new double[array_sizes];
            double []y=new double[array_sizes];
            for (int i = 0; i <array_sizes ; i++) {
                x[i]=single_array[i];
            }
            for (int i = 0; i < array_sizes; i++) {
                y[i]=single_arrays[i];
            }
            for (int i = 0; i <array_sizes ; i++) {
                System.out.println("org:"+x[i]+"enc:"+y[i]);
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
            r=(nr/dr);
            String s = String.format("%.2f",r);
            r = Double.parseDouble(s);
            System.out.println("Total Numbers:"+n+"\nCorrelation Coefficient:"+r);
            double count=0;
            for (int j = 0; j <array_sizes ; j++) {
                if (x[j]!=y[j]){
                    count++;
                }
            }
            System.out.println(count);
            double npr= (count/array_sizes)* 100;
            System.out.println(npr);
            double ans=0
            for (int j = 0; j <array_sizes ; j++) {

                ans=ans+(Math.abs(x[j]-y[j]));
                System.out.println(ans);

            }
            System.out.println(ans);
            double uac= (ans/5625);
            println (uac);
            double ucai=uac*100;
            System.out.println("uaci"+ucai);
        }

    }

    def saveD() {
        savePhotoToDiskD(request.getFile('photo'))
        print("Hereee----")


    }

    def savePhotoToDiskD(def f)

    {
        println("Now heree----")
        Constants constants = new Constants();
        constants.PHOTOS_DIR = Holders.getGrailsApplication().getMainContext().getResource("/").getFile().getAbsolutePath();

        def okContentTypes = ['image/png', 'image/jpeg', 'image/jpg']
        if (f.empty) {
            print("not empty")
            flash.message = 'file cannot be empty'
            return
        }
        if (!okContentTypes.contains(f.contentType)) {
            print("type")

            flash.message = "Image must be one of: $okContentTypes"
            return
        }
        print("1------")
        def extension = FilenameUtils.getExtension(f.originalFilename)

        def serverImagesDir = new File(encryption.Constants.PHOTOS_DIR)
        String image_path = serverImagesDir.absolutePath + "\\\\" + f.originalFilename
        if (serverImagesDir.exists()) {
            File destinationFile = new File(serverImagesDir, f.originalFilename)
            println(image_path)
            f.transferTo(destinationFile)
//            println("convert")

            // read "any" type of image (in this case a png file)
            byte[] k = new byte[16];
            try {
                println(params)
                def key = params.user_key

                //String text = "rojina";
                MessageDigest msg = MessageDigest.getInstance("MD5");
                msg.update(key.getBytes(), 0, key.length());
                String digest1 = new BigInteger(1, msg.digest()).toString(16);
                System.out.println("MD5: " + digest1.length());
                System.out.println("MD5: " + digest1);

                System.out.println("MD5: " + digest1.substring(0, 16));
                k = digest1.substring(0, 16).bytes;
            }
            catch (Exception e) {
                println "Problem in keyGeneration!!!!!!!!";
            }

//
            String path = "/home/rojina/Desktop";
            // File file = new File(image_path);  //delete this
            File file = new File(image_path);  // give ur directory here okay

            FileInputStream lenthstream = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] buf = new byte[lenthstream.bytes.length];

            FileInputStream fis = new FileInputStream(file);
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
            }

            byte[] b1 = bos.toByteArray();
            byte[] b2 = new byte[b1.length - 620];
            int length = b2.length;
            byte[] b = new byte[0];


            for (int i = 0; i < b2.length; i++) {
                b2[i] = b1[i + 620];
            }
            b2 = AES.decrypt(b2, k, 10)
            b = new byte[b1.length - (length - b2.length)]
            int count = 1;
            for (int i = 0; i < b.length; i++) {
                if (i < 620)
                    b[i] = b1[i];
//                    else if(i > b2.length && b2.length<length)  {
//                        b[i] = b2[(i-count)-620]
//                        count ++
//                    }
                else
                    b[i] = b2[i - 620];
            }
            File inputFile
            String name
            if (f.originalFilename.indexOf(".") > 0)
                name = f.originalFilename.substring(0, f.originalFilename.lastIndexOf("."));
            inputFile = new File(path + name + "Decryptedimage.jpg");
            println(inputFile)
            FileOutputStream fos = new FileOutputStream(inputFile);
            fos.write(b);
            fos.flush();
            fos.close();
//                File inputFile = new File(path + "decrypted123123123image.jpg");
//                FileOutputStream fos = new FileOutputStream(inputFile);
//                fos.write(b);
//                fos.flush();
//                fos.close();


        }
    }
}