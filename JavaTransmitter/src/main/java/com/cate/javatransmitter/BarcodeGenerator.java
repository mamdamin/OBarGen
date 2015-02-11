/*
 * The MIT License
 *
 * Copyright 2015 Amin.
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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.lang.Math;
import org.jtransforms.fft.FloatFFT_2D;
import java.awt.image.SampleModel;

/**
 *
 * @author Amin
 */
public class BarcodeGenerator {
    private byte[] inputData=null;
    private int width = 128;
    private int height = 128;
    
    
    public void setData(byte[] data){
        inputData = data;
    }

    public void setParams(){
        
    }
    

    //Take care of putting tiles together and DFT
    public BufferedImage modulateData(int[] data){
        int columns   = width;
        int rows  = height;
        ComplexMatrix tile = new ComplexMatrix(rows,columns);
        tile.clearData();
        int nRows = (int)(Math.sqrt((4*data.length)+1)-1)/4;
        //nRows = 4;
        tile = HermitianModulator.hermitianModulator(data, rows, columns, nRows);
        FloatFFT_2D fft;        
        fft = new FloatFFT_2D(rows,columns);
        fft.complexInverse(tile.complexData, false);
//        BufferedImage image = tile.getBufferedImage();
        WritableRaster dataRaster = tile.getRaster();
        BufferedImage image = createFrame(0,2,dataRaster);
        return image;
        //TODO Clipp Image
    }
    
    private BufferedImage createFrame(int cyclicPrefix, int finderScale, WritableRaster dataRaster){
        int finderSize = finderScale*9;
        int frameWidth  = width +2*cyclicPrefix+2*finderSize;
        int frameHeight = height+2*cyclicPrefix+2*finderSize;
        BufferedImage frame = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = frame.getRaster();
        
        BufferedImage finderImage = new BufferedImage(finderSize, finderSize, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D finderGraphic = finderImage.createGraphics();
        finderGraphic.setBackground(Color.black);
        finderGraphic.setPaint(Color.white);
        finderGraphic.fillRect(0, 0, finderSize, finderSize);
        finderGraphic.setPaint(Color.black);
        finderGraphic.fillRect(finderScale, finderScale, finderSize-2*finderScale, finderSize-2*finderScale);
        finderGraphic.setPaint(Color.white);
        finderGraphic.fillRect(2*finderScale, 2*finderScale, finderSize-4*finderScale, finderSize-4*finderScale);
        finderGraphic.setPaint(Color.black);
        finderGraphic.fillRect(3*finderScale, 3*finderScale, finderSize-6*finderScale, finderSize-6*finderScale);
        WritableRaster finderRaster = finderImage.getRaster();
        //Place Finder Patterns on the image
        raster.setRect(0, 0, finderRaster);
        raster.setRect(width +2*cyclicPrefix+finderSize, 0, finderRaster);
        raster.setRect(0, width +2*cyclicPrefix+finderSize, finderRaster);
        
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

    public void generateImage(){
        InputStream in = new ByteArrayInputStream(inputData);
        try {
            BufferedImage bImageFromConvert = ImageIO.read(in);
            ImageIO.write(bImageFromConvert, "bmp", new File("d:\\new-darksouls.bmp")); 
        } catch (IOException ex) {
            Logger.getLogger(BarcodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public byte[] bytesFromImage(){
        try {
            byte[] imageInByte;
            BufferedImage originalImage = ImageIO.read(new File("d:\\Mono.bmp"));
            
            // convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            return(imageInByte);
        } catch (IOException ex) {
            Logger.getLogger(BarcodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
