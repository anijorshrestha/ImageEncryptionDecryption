package encryption

import grails.util.Holders
import org.apache.commons.io.FilenameUtils

import javax.imageio.ImageIO
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.awt.image.Raster
import java.nio.charset.StandardCharsets


class EncryptionController {

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
        print(serverImagesDir);
        Encryption encryption = new Encryption();
        println("2----------")
        if (serverImagesDir.exists()) {
            println("photoooo---")
            def fileName = "photo" + ".$extension"
            File destinationFile = new File(serverImagesDir, fileName)

            //  encryption.photoUrl = fileName

            f.transferTo(destinationFile)
            println("convert")

            // read "any" type of image (in this case a png file)
            BufferedImage image = ImageIO.read(new File("D:\\ImageEncryptionAndDecryption\\web-app\\photo.PNG"));
            int height = image.getHeight();
            int width = image.getWidth();
            int pixel;
//            int k=1;
//            for (int i = 0; i < height; i++) {
//                for (int j = 0; j < width; j++) {
//                    System.out.println("x,y: " + j + ", " + i);
//                    pixel = image.getRGB(j, i);
//                    k++;
//                    println(k)
//
//                    System.out.println("");
//                    int alpha = (pixel >> 24) & 0xff;
//                    int red = (pixel >> 16) & 0xff;
//                    int green = (pixel >> 8) & 0xff;
//                    int blue = (pixel) & 0xff;
//                    println(alpha)
//                    println(red)
//                    println(green)
//                    println(blue)
//                    System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
//                }
//            }


            Raster raster = image.getData();
            int[][] pixels = new int[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    pixels[x][y] = raster.getSample(x, y, 0);


                }
            }
            print(pixels)

//            return pixels;
            redirect(action: XOR_Key())

            // write it to byte array in-memory (jpg format)
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

    def XOR_Key(){
        println("XOR----")
        String [][] key = new  String[4][4];
        String [][] plaintext = new String[4][4];
        String[][] array_afterroundkey= new String[4][4];
        plaintext=[["54", "77", "6F", "20"],[ "4F", "6E", "65", "20"],[ "4E", "69", "6E", "65"],[ "20", "54", "77", "6F"]];

        key =[["54", "73", "20", "67"], ["68","20", "4B", "20", ],["61", "6D", "75", "46"],[ "74", "79", "6E", "75"]];

        println("KEY MATRIX");
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                print(key[i][j]);
            }
            println();
        }
        println("AFTER ADD ROUND KEY");
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                array_afterroundkey[i][j]=Integer.toHexString( (Integer.parseInt((plaintext[i][j]).toLowerCase(), 16))^(Integer.parseInt((key[i][j]).toLowerCase(), 16)) );
                // array_afterroundkey[i][j][1]=Integer.valueOf((Plaintext[i][j][1]).toLowerCase(), 16).intValue()^Integer.valueOf((key[i][j]).toLowerCase(), 16).intValue();
                // array_afterroundkey[i][j][2]=0;
                printf( array_afterroundkey[i][j]);
            }
            printf("\n");
        }
        redirect(action: substituteBytes(array_afterroundkey))
    }

    def substituteBytes(stateArray)
    {
        println("subbyte")
        println(stateArray)
        String [][] s_box = new  String[16][16][3];
        String [][] substituteArray= new String[5][5][3];
       // String [][] stateArray= new String[5][5][3];
        s_box=["63", "7C", "77", "7B", "F2","6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76",
        "CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C","A4", "72", "C0",
        "B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15",
        "04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75",
        "09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84",
        "53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF",
        "D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8",
        "51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2",
        "CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73",
        "60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB",
        "E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79",
        "E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08",
        "BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A",
        "70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E",
        "E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF",
        "8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16"];
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                substituteArray[i][j]=s_box[Integer.parseInt((stateArray[i][j][0]).toLowerCase(), 16)][(Integer.parseInt((stateArray[i][j][1]).toLowerCase(), 16))];
               // substituteArray[i][j][1]=s_box[(Integer.parseInt((stateArray[i][j][0]).toLowerCase(), 16))][(Integer.parseInt((stateArray[i][j][1]).toLowerCase(), 16))];
              //  substituteArray[i][j][2]=NULL;
                printf( substituteArray[i][j]);
            }
            printf("\n");
        }
    }



}
