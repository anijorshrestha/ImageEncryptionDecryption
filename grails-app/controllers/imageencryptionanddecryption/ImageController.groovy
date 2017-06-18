package imageencryptionanddecryption
import encryption.AES
import encryption.Constants
import encryption.RSA
import grails.util.Holders
import org.apache.commons.io.FilenameUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.image.DataBuffer
import java.security.MessageDigest

class ImageController {

    def index() {}
    def renderFinalView = {
        redirect(controller: "encryption", action: "renderFinalView")
    }

    def save() {
        savePhotoToDisk(request.getFile('photo'))
        print("Hereee----")


    }

    def savePhotoToDisk(def f)

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
            int width = image.getWidth();
            println(width);

            int height = image.getHeight();
            println(height)
            int[][] pixel = new int[width][height];

            for (int i = 0; i < (height); i++) {

                for (int j = 0; j < (width); j++) {

//
                    int rgb = image.getRGB(i, j);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = (rgb & 0xFF);
                    pixel[i][j] = (r + g + b) / 3;

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
            println(single_array.length);
            ;
            String[] arra = new String[array_size];


                for (int i = 0; i < single_array.length; i++) {
                    arra[i] = String.valueOf(single_array[i]);
                }



//190, 186, 192, 189, 189, 199, 194, 182, 185, 189, 187, 196, 188, 200, 182, 210, 189, 203, 192, 202, 191, 210, 179, 214, 190, 190, 190, 187, 180, 186, 195, 198, 172, 189, 186, 179, 187, 191, 185, 185, 186, 177, 180, 189, 188, 182, 179, 176, 191, 184, 184, 189, 180, 185, 197, 187, 203, 197, 205, 199, 206, 218, 210, 212, 216, 206, 210, 201, 214, 203, 213, 213, 209, 214, 221

//            println ("Pixel String"+pixel[0]);

                //  println ("Pixel String"+s);
//
//                StringBuilder sb = new StringBuilder(pixel[0].length);
//                for (int d: pixel[0]) {
//                    sb.append(pixel);
//                }
//            println (sb.toString());

                // byte[] b = baos.toByteArray();
                //  println("original---"+b.length);
                RSA rsa = new RSA();

//           // println(Arrays.toString(b));
//        String [][]test=rsa.intToString(pixel[0])
//            println ("in string"+test);
////            println("rsa encrypt");
                String[] bs = rsa.encrypt(arra,array_size);
                // println(bs);
                //  byte [] ds= rsa.decrypt(bs)
                //    println(bs.length);
                //  println("encrypted--------"+bs)
                // println(b1)s
                // println("decrypted------"+b)
                // byte[] b2=RSA.dec(b);

//            byte[] b2 = new byte[b.length-620];
//            byte[] b1 = new byte[0];
//
//            for(int i=0; i<(b2.length); i++)
//                b2[i]=b[i+620];
//
//            b2=AES.encrypt(b2,k,10)
//            b1=new byte[b2.length+620];
//            for(int i=0; i<b1.length; i++) {
//                if(i<620) b1[i]=b[i];
//                else b1[i]=b2[i-620]; }
//
//            println "Before"
//            println "key == "+k
//            println "b------------------->" + b.length
//            println "b1------------------>" + b1.length
//            println "b2------------------>" + b2.length
                File inputFile
                String name
                if (f.originalFilename.indexOf(".") > 0)
                    name = f.originalFilename.substring(0, f.originalFilename.lastIndexOf("."));
                inputFile = new File(path + name + "-----encryptedimagersa.jpg");
                println(inputFile)
                FileOutputStream fos = new FileOutputStream(inputFile);
                fos.write(b);
                fos.flush();
                fos.close();
//            println "After encryption"
//            println "b------------------->" + b.length
//            println "b1------------------>" + b1.length
//            println "b2------------------>" + b2.length
//
//
//
////            for(int i=0; i<b2.length; i++)
////                b2[i]=b1[i+620];
////
////            b2=AES.decrypt(b2,k,10)
                byte[] ba = rsa.decrypt(bs);
                println(ba);
                println("decrypted---" + ba.length)
////            for(int i=0; i<b.length; i++) {
////                println b2.length + " ===== " + i
////                if(i<620) b[i]=b1[i];
////                else b[i]=b2[i-620]; }
////
                inputFile = new File(path + "---decryptedimagersa.jpg");
                fos = new FileOutputStream(inputFile);
//            println "after decrypt"
//            println "b------------------->" + b.length
//            println "b1------------------>" + b1.length
//            println "b2------------------>" + b2.length
                fos.write(ba);
                fos.flush();
                fos.close();
//        }
//    }
//    def saveD() {
//        savePhotoToDiskD(request.getFile('photo'))
//        print("Hereee----")
//
//
//    }
//
//    def savePhotoToDiskD(def f)
//
//    {
//        println("Now heree----")
//        Constants constants = new Constants();
//        constants.PHOTOS_DIR = Holders.getGrailsApplication().getMainContext().getResource("/").getFile().getAbsolutePath();
//
//        def okContentTypes = ['image/png', 'image/jpeg', 'image/jpg']
//        if (f.empty) {
//            print("not empty")
//            flash.message = 'file cannot be empty'
//            return
//        }
//        if (!okContentTypes.contains(f.contentType)) {
//            print("type")
//
//            flash.message = "Image must be one of: $okContentTypes"
//            return
//        }
//        print("1------")
//        def extension = FilenameUtils.getExtension(f.originalFilename)
//
//        def serverImagesDir = new File(encryption.Constants.PHOTOS_DIR)
//        String image_path= serverImagesDir.absolutePath +"/"+ f.originalFilename
//        if (serverImagesDir.exists()) {
//            File destinationFile = new File(serverImagesDir, f.originalFilename)
//            println(image_path)
//            f.transferTo(destinationFile)
////            println("convert")
//
//            // read "any" type of image (in this case a png file)
//            byte[] k=new byte[16];
//            try
//            {
//                println(params)
//                def key = params.user_key
//
//                //String text = "rojina";
//                MessageDigest msg = MessageDigest.getInstance("MD5");
//                msg.update(key.getBytes(), 0, key.length());
//                String digest1 = new BigInteger(1, msg.digest()).toString(16);
//                System.out.println("MD5: " + digest1.length());
//                System.out.println("MD5: " + digest1);
//
//                System.out.println("MD5: " + digest1.substring(0, 16));
//                k=digest1.substring(0, 16).bytes;
//            }
//            catch (Exception e){
//                println "Problem in keyGeneration!!!!!!!!";
//            }
//
////
//            String path = "/home/rojina/Desktop";
//            // File file = new File(image_path);  //delete this
//            File file = new File(image_path);  // give ur directory here okay
//
//            FileInputStream lenthstream = new FileInputStream(file);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//            byte[] buf = new byte[lenthstream.bytes.length];
//
//            FileInputStream fis = new FileInputStream(file);
//            for (int readNum; (readNum = fis.read(buf)) != -1;) {
//                bos.write(buf, 0, readNum);
//                System.out.println("read " + readNum + " bytes,");
//            }
//
//            byte[] b1 = bos.toByteArray();
//            byte[] b2 = new byte[b1.length - 620];
//            int length = b2.length;
//            byte[] b = new byte[0];
//
//
//            for (int i = 0; i < b2.length; i++){
//                b2[i] = b1[i + 620];
//            }
//            b2 = AES.decrypt(b2, k, 10)
//            b = new byte[b1.length-(length-b2.length)]
//            int count = 1;
//            for (int i = 0; i < b.length; i++) {
//                if (i < 620)
//                    b[i] = b1[i];
////                    else if(i > b2.length && b2.length<length)  {
////                        b[i] = b2[(i-count)-620]
////                        count ++
////                    }
//                else
//                    b[i] = b2[i-620];
//            }
//            File inputFile
//            String name
//            if (f.originalFilename.indexOf(".") > 0)
//                name = f.originalFilename.substring(0, f.originalFilename.lastIndexOf("."));
//            inputFile=new File(path+name+"Decryptedimage.jpg");
//            println(inputFile)
//            FileOutputStream fos = new FileOutputStream(inputFile);
//            fos.write(b);
//            fos.flush();
//            fos.close();
////                File inputFile = new File(path + "decrypted123123123image.jpg");
////                FileOutputStream fos = new FileOutputStream(inputFile);
////                fos.write(b);
////                fos.flush();
////                fos.close();
//
//
            }
        }
    }
