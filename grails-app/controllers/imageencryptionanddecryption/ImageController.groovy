package imageencryptionanddecryption
import encryption.Constants
import encryption.DataTypeConverter
import encryption.RSA_Algorithm
import grails.util.Holders
import org.apache.commons.io.FilenameUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

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
        Constants constants = new Constants();
        constants.PHOTOS_DIR = Holders.getGrailsApplication().getMainContext().getResource("/").getFile().getAbsolutePath();
        def okContentTypes = ['image/png', 'image/jpeg', 'image/jpg']
        if (f.empty) {
            flash.message = 'file cannot be empty'
            return
        }
        if (!okContentTypes.contains(f.contentType)) {
            flash.message = "Image must be one of: $okContentTypes"
            return
        }
        def extension = FilenameUtils.getExtension(f.originalFilename)
        def serverImagesDir = new File(encryption.Constants.PHOTOS_DIR)
        String image_path = serverImagesDir.absolutePath + "/" + f.originalFilename
        if (serverImagesDir.exists()) {
            File destinationFile = new File(serverImagesDir, f.originalFilename)
            f.transferTo(destinationFile)
            BufferedImage image = ImageIO.read(new File(image_path));
            String path = "C:\\Users\\Sushant\\Desktop";          //////Change your here
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            int width = image.getWidth();
            int height = image.getHeight();
            int[][] pixel = new int[width][height];
            for (int i = 0; i < (height); i++) {
                for (int j = 0; j < (width); j++) {
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
            String[] arra = new String[array_size];
            for (int i = 0; i < single_array.length; i++) {
                arra[i] = String.valueOf(single_array[i]);
            }
            RSA_Algorithm rsa = new RSA_Algorithm();


            BigInteger[] encrypted_BigInt = rsa.encrypt(arra,array_size);

            byte[] encrypted_byte = DataTypeConverter.bigToByteValue(encrypted_BigInt);

            File inputFile
            String name
            inputFile = new File(path + name + "-----encryptedimagersa.jpg");
            FileOutputStream fos = new FileOutputStream(inputFile);
            fos.write(encrypted_byte);
            fos.flush();
            fos.close();

            BigInteger[] decrypted_BigInt = rsa.decrypt(encrypted_BigInt, array_size);

            byte[] decrypted_byte = DataTypeConverter.bigToByteValue(decrypted_BigInt);

            inputFile = new File(path + name + "-----decryptedimagersa.jpg");
            fos = new FileOutputStream(inputFile);
            fos.write(decrypted_byte);
            fos.flush();
            fos.close();

            Double correlation = rsa.getCorrelation(array_size)

        }
    }
}
