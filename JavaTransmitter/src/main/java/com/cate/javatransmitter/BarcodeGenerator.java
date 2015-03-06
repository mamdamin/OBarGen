/*
 * The MIT License
 *
 * Copyright 2015 Amin Motahari.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.cate.javatransmitter;

import java.awt.image.BufferedImage;
import java.awt.image.*;
import org.jtransforms.fft.FloatFFT_2D;

/**
 *
 * @author Amin
 */
public class BarcodeGenerator {
    private int[] inputData=null;
    private int width = 128;
    private int height = 128;
    private FinderPattern finPat;   // Finder Pattern
    //private final BufferedImage barcodeImage = null;
    private DPSKStreamer dPSKStream;
    
    public BarcodeGenerator(){
        dPSKStream = new DPSKStreamer();
    }
    
    public void setData(int[] data){
        inputData = data;
    }

    public void setParams(int height,int width,int finderScale){
        this.width = width;
        this.height= height;
        finPat = new FinderPattern(finderScale);
    }
    
    //Take care of putting tiles together and DFT
    public BufferedImage modulateData(){
        //int columns   = width;
        //int rows  = height;
        ComplexMatrix ofdmFrame = new ComplexMatrix(height,width);
        ofdmFrame.clearData();
        int nRows = (int)Math.ceil((Math.sqrt((16*inputData.length)+1)-1)/2);
        if(nRows%2==1)nRows++;
        //System.out.println(nRows);
        dPSKStream.setData(inputData);
        
        ofdmFrame = HermitianModulator.hermitianModulator(dPSKStream, height, width, nRows);
        FloatFFT_2D fft;        
        fft = new FloatFFT_2D(height,width);
        fft.complexInverse(ofdmFrame.complexData, false);
//        BufferedImage image = ofdmFrame.getBufferedImage();
        WritableRaster dataRaster = ofdmFrame.getRaster();
        BufferedImage image = createFrame(0,dataRaster);
        return image;
        //TODO Clipp Image
    }
    
    private BufferedImage createFrame(int cyclicPrefix, WritableRaster dataRaster){
        int finderSize = finPat.size;
        int frameWidth  = width +2*cyclicPrefix+2*finderSize;
        int frameHeight = height+2*cyclicPrefix+2*finderSize;
        BufferedImage frame = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = frame.getRaster();

        //Place Finder Patterns on the image
        raster.setRect(0, 0, finPat.Raster);
        raster.setRect(width +2*cyclicPrefix+finderSize, 0, finPat.Raster);
        raster.setRect(0, width +2*cyclicPrefix+finderSize, finPat.Raster);
        
        //Place OFDM data
        raster.setRect(cyclicPrefix+finderSize, cyclicPrefix+finderSize, dataRaster);
        
        //Add CyclicPrefix
        if (cyclicPrefix>0){
            //Upper CycleicPrefix
            raster.setRect(cyclicPrefix+finderSize,finderSize,dataRaster.createChild(0, height - cyclicPrefix, width, cyclicPrefix, 0, 0, null));
            //Lower CycleicPrefix
            raster.setRect(cyclicPrefix+finderSize,finderSize+cyclicPrefix+height,dataRaster.createChild(0, 0, width, cyclicPrefix, 0, 0, null));
            //Left CycleicPrefix
            raster.setRect(finderSize,finderSize+cyclicPrefix,dataRaster.createChild(width-cyclicPrefix, 0, cyclicPrefix, height, 0, 0, null));        
            //Right CycleicPrefix

            //Corner Squares
            raster.setRect(finderSize+cyclicPrefix+width,finderSize+cyclicPrefix,dataRaster.createChild(0, 0, cyclicPrefix, height, 0, 0, null));                
            //UpperLeftCorner CycleicPrefix
            raster.setRect(finderSize,finderSize,dataRaster.createChild(width-cyclicPrefix, height-cyclicPrefix, cyclicPrefix, cyclicPrefix, 0, 0, null));        
            //LowerLeftCorner CycleicPrefix
            raster.setRect(finderSize,finderSize+cyclicPrefix+height,dataRaster.createChild(width-cyclicPrefix, 0, cyclicPrefix, cyclicPrefix, 0, 0, null));        
            //UpperRightCorner CycleicPrefix
            raster.setRect(finderSize+cyclicPrefix+width,finderSize,dataRaster.createChild(0,height-cyclicPrefix, cyclicPrefix, cyclicPrefix, 0, 0, null));        
            //LowerRightCorner CycleicPrefix
            raster.setRect(finderSize+cyclicPrefix+width,finderSize+cyclicPrefix+height,dataRaster.createChild(0,0, cyclicPrefix, cyclicPrefix, 0, 0, null));                
        }        
        
        
    return frame;
    }
    
    public int getFrameCapacity(){
        return((int) (width*height/100));
    }
            

//    public void generateImage(){
//        InputStream in = new ByteArrayInputStream(inputData);
//        try {
//            BufferedImage bImageFromConvert = ImageIO.read(in);
//            ImageIO.write(bImageFromConvert, "bmp", new File("d:\\new-darksouls.bmp")); 
//        } catch (IOException ex) {
//            Logger.getLogger(BarcodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    public byte[] bytesFromImage(){
//        try {
//            byte[] imageInByte;
//            BufferedImage originalImage = ImageIO.read(new File("d:\\Mono.bmp"));
//            
//            // convert BufferedImage to byte array
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(originalImage, "png", baos);
//            baos.flush();
//            imageInByte = baos.toByteArray();
//            baos.close();
//            return(imageInByte);
//        } catch (IOException ex) {
//            Logger.getLogger(BarcodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
}
