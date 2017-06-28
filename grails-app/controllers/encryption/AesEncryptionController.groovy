package encryption

import grails.util.Holders
import org.apache.commons.io.FilenameUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.security.MessageDigest

class AesEncryptionController {

    def index() {}
    def renderFinalView = {
        render(view:"main");
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

        String image_path= serverImagesDir.absolutePath +"\\"+ f.originalFilename
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
            byte[] k=new byte[16];
            try
            {
//                k=AES.keygeneration();
               // println(params)
                def key = params.user_key

                //String text = "rojina";
                MessageDigest msg = MessageDigest.getInstance("MD5");
                msg.update(key.getBytes(), 0, key.length());
                String digest1 = new BigInteger(1, msg.digest()).toString(16);
                System.out.println("MD5: " + digest1.length());
                System.out.println("MD5: " + digest1);

                System.out.println("MD5: " + digest1.substring(0, 16));
                k=digest1.substring(0, 16).bytes;
            }
            catch (Exception e){
                println "Problem in keyGeneration!!!!!!!!";
            }



            // BufferedImage image = ImageIO.read(new File(image_path));   //delete this one
            BufferedImage image = ImageIO.read(new File(image_path));//  Change hereeeeee your directory ok
            String path = "C:\\Users\\anijor\\Desktop\\"
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] b = baos.toByteArray();
            byte[] b2 = new byte[b.length-620];
            byte[] b1 = new byte[0];

            for(int i=0; i<(b2.length); i++)
                b2[i]=b[i+620];

            b2=AES.encrypt(b2,k,10)
            b1=new byte[b2.length+620];
            for(int i=0; i<b1.length; i++) {
                if(i<620) b1[i]=b[i];
                else b1[i]=b2[i-620]; }

            println "Before"
            println "key == "+k
            println "b------------------->" + b.length
            println "b1------------------>" + b1.length
            println "b2------------------>" + b2.length
            File inputFile
            String name
            if (f.originalFilename.indexOf(".") > 0)
                name = f.originalFilename.substring(0, f.originalFilename.lastIndexOf("."));
            inputFile=new File(path+name+"encryptedimage.jpg");
            println(inputFile)
            FileOutputStream fos = new FileOutputStream(inputFile);
            fos.write(b1);
            fos.flush();
            fos.close();
            println "After encryption"
            println "b------------------->" + b.length
            println "b1------------------>" + b1.length
            println "b2------------------>" + b2.length



//            for(int i=0; i<b2.length; i++)
//                b2[i]=b1[i+620];
//
//            b2=AES.decrypt(b2,k,10)
//            for(int i=0; i<b.length; i++) {
//                println b2.length + " ===== " + i
//                if(i<620) b[i]=b1[i];
//                else b[i]=b2[i-620]; }
//
//            inputFile=new File(path+"decryptedimage.jpg");
//            fos = new FileOutputStream(inputFile);
//            println "after decrypt"
//            println "b------------------->" + b.length
//            println "b1------------------>" + b1.length
//            println "b2------------------>" + b2.length
//            fos.write(b);
//            fos.flush();
//            fos.close();
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
        String image_path= serverImagesDir.absolutePath +"\\\\"+ f.originalFilename
        if (serverImagesDir.exists()) {
            File destinationFile = new File(serverImagesDir, f.originalFilename)
            println(image_path)
            f.transferTo(destinationFile)
//            println("convert")

            // read "any" type of image (in this case a png file)
            byte[] k=new byte[16];
            try
            {
                println(params)
                def key = params.user_key

                //String text = "rojina";
                MessageDigest msg = MessageDigest.getInstance("MD5");
                msg.update(key.getBytes(), 0, key.length());
                String digest1 = new BigInteger(1, msg.digest()).toString(16);
                System.out.println("MD5: " + digest1.length());
                System.out.println("MD5: " + digest1);

                System.out.println("MD5: " + digest1.substring(0, 16));
                k=digest1.substring(0, 16).bytes;
            }
            catch (Exception e){
                println "Problem in keyGeneration!!!!!!!!";
            }

//
            String path = "C:\\Users\\anijor\\Desktop\\"
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


            for (int i = 0; i < b2.length; i++){
                b2[i] = b1[i + 620];
            }
            b2 = AES.decrypt(b2, k, 10)
            b = new byte[b1.length-(length-b2.length)]
            int count = 1;
            for (int i = 0; i < b.length; i++) {
                if (i < 620)
                    b[i] = b1[i];
//                    else if(i > b2.length && b2.length<length)  {
//                        b[i] = b2[(i-count)-620]
//                        count ++
//                    }
                else
                    b[i] = b2[i-620];
            }
            File inputFile
            String name
            if (f.originalFilename.indexOf(".") > 0)
                name = f.originalFilename.substring(0, f.originalFilename.lastIndexOf("."));
            inputFile=new File(path+name+"Decryptedimage.jpg");
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