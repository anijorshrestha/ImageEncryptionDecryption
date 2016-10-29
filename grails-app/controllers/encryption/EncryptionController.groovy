package encryption

import grails.util.Holders
import org.apache.commons.io.FilenameUtils

import javax.imageio.ImageIO
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.awt.image.Raster


class EncryptionController {

    def index() { }

    def save(){
        savePhotoToDisk(request.getFile('photo'))
        print("Hereee----")


    }
    def savePhotoToDisk(def f)

    {
        println("Now heree----")
        Constants constants= new Constants();
        constants.PHOTOS_DIR= Holders.getGrailsApplication().getMainContext().getResource("/").getFile().getAbsolutePath();

        def okContentTypes = ['image/png', 'image/jpeg', 'image/jpg']
        if (f.empty) {
            print("not empty")
            flash.message = 'file cannot be empty'
            return
        }
        if (!okContentTypes.contains(f.contentType)){
            print("type")

            flash.message = "Image must be one of: $okContentTypes"
            return
        }
        print("1------")
        def extension = FilenameUtils.getExtension(f.originalFilename)

        def serverImagesDir = new File(encryption.Constants.PHOTOS_DIR)
        print(serverImagesDir);
        Encryption encryption=new Encryption();
        println("2----------")
        if(serverImagesDir.exists()) {
            println("photoooo---")
            def fileName = "photo" + ".$extension"
            File destinationFile = new File(serverImagesDir, fileName)

          //  encryption.photoUrl = fileName

            f.transferTo(destinationFile)
            println("convert")

            // read "any" type of image (in this case a png file)
            BufferedImage image = ImageIO.read(new File("D:\\ImageEncryptionAndDecryption\\web-app\\photo.PNG"));
            int height= image.getHeight();
            int width= image.getWidth();
            int pixel;
            int k=1;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    System.out.println("x,y: " + j + ", " + i);
                    pixel = image.getRGB(j, i);
                    k++;
                    println(k)

                    System.out.println("");
                    int alpha = (pixel >> 24) & 0xff;
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;
                    println(alpha)
                    println(red)
                    println(green)
                    println(blue)
                    System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
                }
            }



//            Raster raster= image.getData();
//            int[][] pixels=new int[width][height];
//            for (int x=0;x<width;x++)
//            {
//                for(int y=0;y<height;y++)
//                {
//                    pixels[x][y]=raster.getSample(x,y,0);
//
//
//                }
//            }
//            print(pixels)
//
//
//            return pixels;


          //  write it to byte array in-memory (jpg format)
//            ByteArrayOutputStream b = new ByteArrayOutputStream();
//            ImageIO.write(image, "png", b);
//
//            // do whatever with the array...
//            byte[] jpgByteArray = b.toByteArray();
//            println(jpgByteArray)
//            // convert it to a String with 0s and 1s
//            StringBuilder sb = new StringBuilder();
//
//            for (byte by : jpgByteArray)
//                sb.append(Integer.toBinaryString(by & 0xFF));
//
//
//            System.out.println(sb.toString());



        }

    }



}
