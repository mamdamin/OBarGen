package com.cate.javatransmitter;


import java.util.Arrays; 
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
 
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SystemUtils;

import org.jtransforms.fft.FloatFFT_2D;

/**
* @author Crunchify.com
*/
 
public class Test {
    private static GenericGF genericGF = new GenericGF(0x011D, 256, 1);
 
        // Tutorial: http://zxing.org/w/docs/javadoc/index.html
 
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws ReedSolomonException, UnsupportedEncodingException {
        
        String myCodeText = "http://Crunchify.com/";
        String filePath = "/Users/amin/Documents/CrunchifyQR.png";
        int size = 125;
        String fileType = "png";
        File myFile = new File(filePath);
        int ecBytes=64;
        //generate random packet
        int nOfBytes = 256;
        int[] testEncode=new int[nOfBytes];
        Random rand = new Random();
        for(int i=0;i<nOfBytes-ecBytes;i++)
            testEncode[i] = rand.nextInt(256);
        
        //  RS encoder
        ReedSolomonEncoder rsEncoder;
        ReedSolomonDecoder rsDecoder;
        rsEncoder = new ReedSolomonEncoder(genericGF);
        rsDecoder = new ReedSolomonDecoder(genericGF);
        System.out.println("Original data = " + Arrays.toString(testEncode));
        rsEncoder.encode(testEncode, ecBytes);
        testEncode[1]=testEncode[2]=testEncode[3]=testEncode[4]=testEncode[5]=testEncode[6]=testEncode[7]=testEncode[8]=4;
        System.out.println("Encoded  data = " + Arrays.toString(testEncode));
        rsDecoder.decode(testEncode,ecBytes);
        System.out.println("Decoded  data = " + Arrays.toString(testEncode));
//        int[] byteStream = {0b00101101,129};
        //int[] bitStream = DPSKModulator.DPSKModulator(byteStream);
//        DPSKStream bitStream = new DPSKStream();
//        bitStream.setData(byteStream);
//        for(int i=0; i<byteStream.length*8+10;i++)            
//            System.out.println(Integer.toString(i)+" th bit is " + Integer.toString(bitStream.next()));
        
        //2D FFT
//        FloatFFT_2D fft;
//        fft = new FloatFFT_2D(2,2);
//        float[][] floats = {{1,1,1,1},{1,1,1,1}};
//        fft.complexForward(floats);
//        //System.out.println("\n Floats " + floats[0][0]);
//        for (int i = 0; i < 2; i++) {
//                for (int j = 0; j < 4; j++) {
//                    System.out.print(floats[i][j]+" ");
//                }
//                System.out.println("");
//            }
//        System.out.println(floats.length);
        
        //show video
        
        SwingContainer  swingContainer = new SwingContainer();  
        //swingContainer.showJPanelDemo();
        
        
        // Generate Barcode
        BarcodeGenerator bGen = new BarcodeGenerator();
//        byte[] bFI = bGen.bytesFromImage();
//        bGen.setData(bFI);
//        bGen.generateImage();
        //int[] toEncode1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        int[] toEncode1 = new int[16192];   //Not greater than this for 512x512
        for(int i = 0;i<toEncode1.length;i++)
            toEncode1[i] = rand.nextInt(255);
        
        bGen.setParams(512, 512, 2);
        Path inputPath;
        if(SystemUtils.IS_OS_WINDOWS==true)
            inputPath = Paths.get("C:\\Users\\Public\\Pictures\\Sample Pictures","Penguins.jpg");
        else
            inputPath = Paths.get("/Users/Amin/Desktop","erassm.gif");
       
        FileHandler fH = new FileHandler();
        fH.setPacketSize(nOfBytes-ecBytes);
        fH.setFile(inputPath);
        int[] toEncode = new int[nOfBytes];
        int[] dataOfFrame = new int[bGen.getFrameCapacity()];
        int chunksInFrame = dataOfFrame.length/nOfBytes;
        int chunkCounter  = 0;
        while(!fH.isFinished){
            if(chunkCounter++ <= chunksInFrame){
                toEncode = fH.nextIntChunk();
                rsEncoder.encode(toEncode, ecBytes);
                //Arrays.
                //System.out.println(Arrays.toString(toEncode));
            }
            else{
                System.out.println("Ready to send Frame"+chunkCounter);
                bGen.setData(toEncode);
                swingContainer.showBarcode(bGen.modulateData());                
                chunkCounter  = 0;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }                   
            }
            //rsEncoder.encode(testEncode, ecBytes);
            //System.out.println((toEncode, StandardCharsets.UTF_8));
        }
        //System.out.println(((byte) -63) & 0xFF);
        
            //System.out.println(new String(fH.nextChunk(), StandardCharsets.UTF_8));
        System.exit(0);
         
 
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        int[] newToEncode1 = toEncode1.clone();
        for(int i = 0; i<100; i++){
            for(int x=0; x<=toEncode1.length-1;x++)
                 newToEncode1[(x+i) % toEncode1.length ] = toEncode1[x];
            toEncode1 = newToEncode1.clone();
            bGen.setData(toEncode1);
            swingContainer.showBarcode(bGen.modulateData());
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }            
            //System.out.println("Frame number: "+i);
            
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);        //
        //System.out.println("FFT Data = " + Arrays.toString(floats));
        /*
        try {
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText,BarcodeFormat.QR_CODE, size, size, hintMap);
            int CrunchifyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                    BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
 
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);
 
            for (int i = 0; i < CrunchifyWidth; i++) {
                for (int j = 0; j < CrunchifyWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, fileType, myFile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
                */
        System.out.println("\n\nYou have successfully created QR Code.");
    }      
}