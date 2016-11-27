package imageencryptionanddecryption

import encryption.AES
import encryption.Constants
import encryption.Encryption
import encryption.EncryptionController
import grails.util.Holders
import org.apache.commons.io.FilenameUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class ImageController {

    def index() {}

    def save() {
        savePhotoToDisk(request.getFile('photo'))
        print("Hereee----")


    }

    def savePhotoToDisk(def f)

    {
        byte[] k=new byte[16];
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
        print(serverImagesDir);
        Encryption encryption = new Encryption();
        AES aes = new AES();
        EncryptionController encryptionController = new EncryptionController();
        println("2----------")
        if (serverImagesDir.exists()) {
            println("photoooo---")
            def fileName = "photo" + ".$extension"
            File destinationFile = new File(serverImagesDir, fileName)

            //  encryption.photoUrl = fileName

            f.transferTo(destinationFile)
            println("convert")

            // read "any" type of image (in this case a png file)
            BufferedImage image = ImageIO.read(new File("D:\\ImageEncryptionAndDecryption\\web-app\\photo.jpg"));
            int height = image.getHeight();
            int width = image.getWidth();
            int pixel;
//
            //  write it to byte array in-memory (jpg format)

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(image, "png", b);

            // do whatever with the array...
            byte[] jpgByteArray = b.toByteArray();
            byte[] b2 = new byte[jpgByteArray.length - 620];
            byte[] b1 = new byte[0];
            println(jpgByteArray)
            println(b2);
            for (int i = 0; i < b2.length; i++) {
                b2[i] = b[i + 620];
                b2= aes.encrypt(b2,k,10)
                t=Math.round((AES.t2-AES.t1)/(1000000));
                b1=new byte[b2.length+620];
                for(int i=0; i<b1.length; i++) {
                    if(i<620) b1[i]=b[i];
                    else b1[i]=b2[i-620]; }
                inputFile=new File(jFileChooser2.getSelectedFile()+"/encryptedimage.jpg");
                FileOutputStream fos = new FileOutputStream(inputFile);
                fos.write(b1);
                fos.flush();
                fos.close();
                for(int i=0; i<b2.length; i++)
                    b2[i]=b1[i+620];
                if(jRadioButton2.isSelected()==true) b2=AES.decrypt(b2,k,4);
                else b2=AES.decrypt(b2,k,10);
                t=Math.round((AES.t2-AES.t1)/(1000000));
                jTextField2.setText(String.valueOf(t)+"ms");
                for(int i=0; i<b.length; i++) {
                    if(i<620) b[i]=b1[i];
                    else b[i]=b2[i-620]; }
                inputFile=new File(jFileChooser2.getSelectedFile()+"/decryptedimage.jpg");
                fos = new FileOutputStream(inputFile);
                fos.write(b);
                fos.flush();
                fos.close();



            }

            // println(jpgByteArray)
            // convert it to a String with 0s and 1s


        }
    }
}