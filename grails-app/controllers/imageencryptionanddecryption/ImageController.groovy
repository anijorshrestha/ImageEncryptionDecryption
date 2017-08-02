package imageencryptionanddecryption

import encryption.AES
import encryption.AES_Modifier
import encryption.Constants
import encryption.RSA_Algorithm
import grails.util.Holders
import org.apache.commons.io.FilenameUtils

import javax.imageio.ImageIO
import java.awt.Color
import java.awt.image.BufferedImage
import java.security.MessageDigest

class ImageController {
    def rsa_map, aes_map
    def index() {}
    def renderFinalView = {
        redirect(controller: "encryption", action: "renderFinalView")
    }
    def save() {
        savePhotoToDisk(request.getFile('photo'),params.user_key)
        print("Hereee----")
    }
    def savePhotoToDisk(def f, String user_key)
    {
        Constants constants = new Constants();
        constants.PHOTOS_DIR = Holders.getGrailsApplication().getMainContext().getResource("/").getFile().getAbsolutePath()+"/images/";
        def okContentTypes = ['image/png', 'image/jpeg', 'image/jpg']
        if (f.empty) {
            flash.message = 'file cannot be empty'
            return
        }
        if (!okContentTypes.contains(f.contentType)) {
            flash.message = "Image must be one of: $okContentTypes"
            return
        }
        def serverImagesDir = new File(encryption.Constants.PHOTOS_DIR)
        String image_path = serverImagesDir.absolutePath + "/" + f.originalFilename

        ///////////////////////////////////////////////////////////////////////////////////////////
        File file = new File("/home/rojina/Desktop/imageAES_RSA/ImageEncryptionDecryption/web-app/images/");

        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                System.out.println(myFile);
                if (!myFile.isDirectory()) {
                    myFile.delete();
                }
            }
        }

        //////////////////////////////////////////////////////////////////////////////////////////


        if (serverImagesDir.exists()) {
            File destinationFile = new File(serverImagesDir, f.originalFilename)
            f.transferTo(destinationFile)

            ///////////////////////////////////   Reading Image   //////////////////////////////////////////////////
            BufferedImage image = ImageIO.read(new File(image_path));
            String path = serverImagesDir.absolutePath +"/"
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            int width = image.getWidth();
            int height = image.getHeight();
            int[][] pixel = new int[height][width];

            ////////////////////////////////////  Changing to GrayScaleImage  /////////////////////////////////////
            for (int i = 0; i < (height); i++) {
                for (int j = 0; j < (width); j++) {
                    int rgb = image.getRGB(i, j);
                    int r = (rgb >> 16) & 0xFF;
//                    println "r = $r"
                    int g = (rgb >> 8) & 0xFF;
//                    println "g = $g"
                    int b = (rgb & 0xFF);
//                    println "b = $b"
                    pixel[i][j] = (r + g + b) / 3;
//                    pixel[i][j] = (r + g + b) / 3;
                }
            }
            int array_size = height * width;
            int[] single_array = new int[array_size];
            int position = 0;
            for (int i = 0; i < (height); i++) {
                for (int j = 0; j < (width); j++) {
                    single_array[position++] = pixel[i][j];
                }
            }
            String[] arra = new String[array_size];
            for (int i = 0; i < single_array.length; i++) {
                arra[i] = String.valueOf(single_array[i]);
            }
            aes_map = aESpart(baos,path+"AES-Algorithm-", arra, user_key)
            println "aes_correlation ==== " + aes_map.get("aes_correlation")
            println " aes_npr ==== " + aes_map.get("aes_npr")
            rsa_map = rsaPart(arra, height, width, path)

            println " rsa_correlation ==== " + rsa_map.get("rsa_correlation")
            println "rsa_npr ==== " + rsa_map.get("rsa_npr")
//            double uaci = AES_Modifier.getCorrelation(path)
//            println "uaci ====================================== $uaci"
        }
        render(view: "save", model: [aes_map: aes_map, rsa_map: rsa_map, original_photo:(f.originalFilename)])
    }

    def rsaPart(String[] arra, int height, int width, String path){




        /////////////////////////////////////////// Encrypting and decrypting ////////////////////////////////////////////
        RSA_Algorithm rsa = new RSA_Algorithm();
        rsa.setImage(width, height)
        rsa.setArray()
        rsa.setPath(path)


        //////////////////////////////////////////////   Encryption     ///////////////////////////////////////////////
        def encrypted_BigInt = rsa.encrypt(arra,height*width);

        ////////////////////////////////////////    Decrypweb-apption  //////////////////////////////////////////////

        def decrypted_BigInt = rsa.decrypt(height*width,arra);

//            encrypted_BigInt.un
        return rsa.getMap()

    }

    def aESpart(ByteArrayOutputStream baos, String path, String[] arra, String key){

        byte[] k=new byte[16];
        try
        {
//                k=AES.keygeneration();
            // println(params)

            //String text = "rojina";
            MessageDigest msg = MessageDigest.getInstance("MD5");

            msg.update(key.getBytes(), 0, key.length());
            String digest1 = new BigInteger(1, msg.digest()).toString(16);
//            System.out.println("MD5: " + digest1.length());
//            System.out.println("MD5: " + digest1);

//            System.out.println("MD5: " + digest1.substring(0, 16));
            k=digest1.substring(0, 16).bytes;
        }
        catch (Exception e){
            println "Problem in keyGeneration!!!!!!!!";
        }

        long lStartTime = System.currentTimeMillis();
        byte[] b = baos.toByteArray();
        byte[] b2 = new byte[b.length-620];
        byte[] b1 = new byte[0];

        for(int i=0; i<(b2.length); i++)
            b2[i]=b[i+620];

        b2=AES.encrypt(b2,k,10)
        long lEndTime = System.currentTimeMillis();
        b1=new byte[b2.length+620];
        for(int i=0; i<b1.length; i++) {
            if(i<620) b1[i]=b[i];
            else b1[i]=b2[i-620]; }


        File inputFile

//        if (f.originalFilename.indexOf(".") > 0)
//            name = f.originalFilename.substring(0, f.originalFilename.lastIndexOf("."));
        inputFile=new File(path+"Encrypted.jpg");
        FileOutputStream fos = new FileOutputStream(inputFile);
        fos.write(b1);
        fos.flush();
        fos.close();
        long output = (lEndTime - lStartTime);

        ////////////////---Decrypt/////////////////////////////////

        lStartTime = System.currentTimeMillis()

        for(int i=0; i<b2.length; i++)
            b2[i]=b1[i+620];

        b2=AES.decrypt(b2,k,10)
        lEndTime = System.currentTimeMillis();
        for(int i=0; i<b.length; i++) {
            if(i<620) b[i]=b1[i];
            else b[i]=b2[i-620]; }

        inputFile=new File(path+"Decrypted.jpg");
        fos = new FileOutputStream(inputFile);
        fos.write(b);
        fos.flush();
        fos.close();

        //time elapsed
        long output_decrypt = (lEndTime - lStartTime);


        path = path + "Encrypted.jpg"
        AES_Modifier.getCorrelations(arra, path);
        Map<String, Double> resultMap = AES_Modifier.getMap()
        resultMap.put("aes_encryption_time", Double.parseDouble(String.valueOf(output)));
        resultMap.put("aes_decryption_time", Double.parseDouble(String.valueOf(output_decrypt)));
        return resultMap

    }
}
