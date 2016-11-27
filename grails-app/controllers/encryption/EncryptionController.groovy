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

    def index() {
        render(view: "test")


    }
    def encrypt(){





    }

    def XOR_Key(){
        println("XOR----")
        String [][] key = new  String[4][4];
        String [][] plaintext = new String[4][4];
        String[][] array_afterroundkey= new String[4][4];
        plaintext=[["54", "4F", "4E", "20"],[ "77", "6E", "69", "54"],[ "6F", "65", "6E", "77"],[ "20", "20", "65", "6F"]];

        key =[["54", "73", "20", "67"], ["68","20", "4B", "20", ],["61", "6D", "75", "46"],[ "74", "79", "6E", "75"]];

        println("KEY MATRIX");
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                print(key[i][j]);
            }
            println("----------");
        }
        println("AFTER ADD ROUND KEY");
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                array_afterroundkey[i][j]=Integer.toHexString( (Integer.parseInt((plaintext[i][j]).toLowerCase(), 16))^(Integer.parseInt((key[i][j]).toLowerCase(), 16)) );
                // array_afterroundkey[i][j][1]=Integer.valueOf((Plaintext[i][j][1]).toLowerCase(), 16).intValue()^Integer.valueOf((key[i][j]).toLowerCase(), 16).intValue();
                // array_afterroundkey[i][j][2]=0;
                if(array_afterroundkey[i][j].length()<2){
                    array_afterroundkey[i][j] = "0"+array_afterroundkey[i][j]
                }
                printf( array_afterroundkey[i][j]);
            }
            printf("---------");
        }
        substituteBytes(array_afterroundkey)
    }

    def substituteBytes(stateArray)
    {
        println("-----------------")
        println("subbyte")
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                print stateArray[i][j]
            }
            println " ----------------"
        }
        String [][] s_box = new  String[16][16];
        String [][] substituteArray= new String[4][4];
       // String [][] stateArray= new String[5][5][3];
        s_box=[["63", "7C", "77", "7B", "F2","6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76"],
               ["CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C","A4", "72", "C0"],
               ["B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15"],
               ["04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75"],
               ["09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84"],
               ["53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF"],
               ["D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8"],
               ["51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2"],
               ["CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73"],
               ["60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB"],
               ["E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79"],
               ["E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08"],
               ["BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A"],
               ["70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E"],
               ["E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF"],
               ["8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16"]];
        int values1=0,values2=0;
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                values1 = Integer.parseInt(stateArray[i][j].toString().charAt(0).toString().toLowerCase(),16);
                values2 = Integer.parseInt(stateArray[i][j].toString().charAt(1).toString().toLowerCase(),16);
                substituteArray[i][j] = s_box[values1][values2];
//                substituteArray[i][j]=s_box[Integer.parseInt((stateArray[i][j][0]), 16)][(Integer.parseInt((stateArray[i][j][1]), 16))];
               // substituteArray[i][j][1]=s_box[(Integer.parseInt((stateArray[i][j][0]).toLowerCase(), 16))][(Integer.parseInt((stateArray[i][j][1]).toLowerCase(), 16))];
              //  substituteArray[i][j][2]=NULL;
//                println "s = "  + substituteArray[i][j]
            }
//            println("---");
        }
        shiftRow(substituteArray)
    }

    def shiftRow(substituteArray){
        println("ShiftRow-------")
        String[][] shiftRows = new String [4][4];
        for(int i;i<4;i++){
            for(int j; j<4;j++){
                shiftRows[i][j]=substituteArray[i][(j+i)%4]
                print "m = " + shiftRows[i][j]
            }
            println " --------------------"
        }
        mixColumn(shiftRows)

    }

    def mixColumn(shiftRows){
        println("MixColumn--------")
        String [][] mixColumn = new String [4][4];
        int  [][] gF = new  int[4][4];
        gF=[["2","3","1","1"],["1","2","3","1"],["1","1","2","3"],["3","1","1","2"]];
        for(int i=0;i<=3;i++)
        {
            for(int j=0;j<=3;j++)
            {
                mixColumn[j][i]=gF[j][0]*(Integer.parseInt(shiftRows[i][0].toString().toLowerCase(),16))^gF[j][1]*(Integer.parseInt(shiftRows[i][1].toString().toLowerCase(),16))^gF[j][2]*(Integer.parseInt(shiftRows[i][2].toString().toLowerCase(),16))^gF[j][3]*(Integer.parseInt(shiftRows[i][3].toString().toLowerCase(),16));
            }
        }
        println(mixColumn)



    }




}
