package test;


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

import org.jtransforms.fft.FloatFFT_2D;

import test.SwingContainer;
import test.BarcodeGenerator;

/**
* @author Crunchify.com
*/
 
public class Test {
    private static GenericGF genericGF = new GenericGF(0x011D, 256, 0);
 
        // Tutorial: http://zxing.org/w/docs/javadoc/index.html
 
    public static void main(String[] args) throws ReedSolomonException {
        
        String myCodeText = "http://Crunchify.com/";
        String filePath = "/Users/amin/Documents/CrunchifyQR.png";
        int size = 125;
        String fileType = "png";
        File myFile = new File(filePath);
        int[] toEncode={255,0,0,0,0,0,0,0,0,0};
        int ecBytes=5;
        //  RS encoder
        ReedSolomonEncoder rsEncoder;
        ReedSolomonDecoder rsDecoder;
        rsEncoder = new ReedSolomonEncoder(genericGF);
        rsDecoder = new ReedSolomonDecoder(genericGF);
        System.out.println("Original data = " + Arrays.toString(toEncode));
        rsEncoder.encode(toEncode, ecBytes);
        toEncode[1]=toEncode[2]=4;
        System.out.println("Encoded  data = " + Arrays.toString(toEncode));
        rsDecoder.decode(toEncode,ecBytes);
        System.out.println("Decoded  data = " + Arrays.toString(toEncode));
        //2D FFT
        FloatFFT_2D fft;
        fft = new FloatFFT_2D(2,2);
        float[][] floats = {{1,1,1,1},{1,1,1,1}};
        fft.complexForward(floats);
        //System.out.println("\n Floats " + floats[0][0]);
        for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.print(floats[i][j]+" ");
                }
                System.out.println("");
            }
        System.out.println(floats.length);
        
        //show video
        
        SwingContainer  swingContainer = new SwingContainer();  
        swingContainer.showJPanelDemo();
        //swingContainer.showBarcode();
        
        // Generate Barcode
        BarcodeGenerator bGen = new BarcodeGenerator();
        byte[] bFI = bGen.bytesFromImage();
        bGen.setData(bFI);
        bGen.generateImage();
        
        //
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