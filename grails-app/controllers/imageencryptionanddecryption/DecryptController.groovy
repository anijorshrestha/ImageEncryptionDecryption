package imageencryptionanddecryption

import encryption.AES
import encryption.Constants
import encryption.Encryption
import encryption.EncryptionController
import grails.util.Holders
import org.apache.commons.io.FilenameUtils

import javax.imageio.ImageIO
import javax.imageio.ImageReadParam
import javax.imageio.ImageReader
import javax.imageio.stream.ImageInputStream
import java.awt.Image
import java.awt.image.BufferedImage

/**
 * Created by anijor on 1/31/2017.
 */
class DecryptController {
    def index() {}

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
        println("2----------")
        if (serverImagesDir.exists()) {
            println("photoooo---")
            def fileName = "photo" + ".$extension"
            File destinationFile = new File(serverImagesDir, fileName)

            //  encryption.photoUrl = fileName

            f.transferTo(destinationFile)
            println("convert")

            // read "any" type of image (in this case a png file)
            byte[] k=new byte[16];
            try
            {
                k=[-10, 49, 59, -60, 75, -82, 23, 7, 2, 40, 107, -106, -23, 100, -119, -32];
            }
            catch (Exception e){
                println "Problem in keyGeneration!!!!!!!!";
            }

//            BufferedImage image = ImageIO.read(new File("C:\\Java_Projects\\AESAlgorithm\\web-app\\images\\springsource.PNG"));
//            BufferedImage image = ImageIO.read(new File("C:\\Users\\anijor\\Desktop\\mine.jpg"));
            String path = "C:\\Users\\anijor\\Desktop\\"
//            ByteArrayOutputStream baos=new ByteArrayOutputStream();
//            ImageIO.write(image, "jpg", baos);
            File file = new File("C:\\Users\\anijor\\Desktop\\mine.jpg");

            FileInputStream lenthstream = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] buf = new byte[lenthstream.bytes.length];

            FileInputStream fis = new FileInputStream(file);
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
            }

                byte[] b1 = bos.toByteArray();
                byte[] b2 = new byte[b1.length - 620];
                byte[] b = new byte[b2.length + 620];

//            for(int i=0; i<(b2.length); i++)
//                b2[i]=b[i+620];
//
//            b2=AES.encrypt(b2,k,10)
//            b1=new byte[b2.length+620];
//            for(int i=0; i<b1.length; i++) {
//                if(i<620) b1[i]=b[i];
//                else b1[i]=b2[i-620]; }
//
                println "Before"
                println "key == " + k
                println "b------------------->" + b.length
                println "b1------------------>" + b1.length
                println "b2------------------>" + b2.length

                for (int i = 0; i < b2.length; i++){
                    println "b2.leh == = == = =" + b2.length
                    b2[i] = b1[i + 620];
                }
                println "what the pop"
                b2 = AES.decrypt(b2, k, 10)
                println "what the pop 2<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>" + b2.length
                for (int i = 0; i < b.length; i++) {
                    println b2.length + "i = " + i
                    if (i < 620)
                        b[i] = b1[i];
                    else
                        b[i] = b2[i-620];
                }
                println "nope what the pop 3"
                File inputFile = new File(path + "decrypted123123123image.jpg");
                FileOutputStream fos = new FileOutputStream(inputFile);
                println "after decrypt"
                println "b------------------->" + b.length
                println "b1------------------>" + b1.length
                println "b2------------------>" + b2.length
                fos.write(b);
                fos.flush();
                fos.close();


        }
    }
}