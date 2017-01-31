package imageencryptionanddecryption
import encryption.AES
import encryption.Constants
import grails.util.Holders
import org.apache.commons.io.FilenameUtils
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
                k=[-59, -15, -53, 94, -9, 33, 17, -18, -30, -20, -4, 105, 34, 114, -49, -100];
            }
            catch (Exception e){
                println "Problem in keyGeneration!!!!!!!!";
            }

//
            String path = "C:\\Users\\Sushant\\Desktop\\"
            File file = new File("C:\\Users\\Sushant\\Desktop\\mine.jpg");  //delete this
//            File file = new File("C:\\Users\\Sushant\\Desktop\\mine.jpg");   give ur directory here

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
                File inputFile = new File(path + "decrypted123123123image.jpg");
                FileOutputStream fos = new FileOutputStream(inputFile);
                fos.write(b);
                fos.flush();
                fos.close();


        }
    }
}