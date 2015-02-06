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

/**
 *
 * @author Amin
 */
public class BarcodeGenerator {
    private byte[] inputData=null;
    private int width;
    private int height;
    
    
    public void setData(byte[] data){
        inputData = data;
    }

    public void setParams(){
        
    }
    

    //Take care of putting tiles together and DFT
    public BufferedImage modulateData(int[] data){
        int columns   = 512;
        int rows  = 512;
        ComplexMatrix tile = new ComplexMatrix(rows,columns);
        tile.clearData();
        int nRows = (int)(Math.sqrt((4*data.length)+1)-1)/4;
        //nRows = 4;
        tile = HermitianModulator.hermitianModulator(data, rows, columns, nRows);
        FloatFFT_2D fft;        
        fft = new FloatFFT_2D(rows,columns);
        fft.complexForward(tile.complexData);
        BufferedImage image = tile.getBufferedImage();
        return image;
        //TODO Clipp Image
    }
    
    private int[][] createFrame(int rows,int columns){
        int finderSize = 9;
        int[][] finderPattern = new int[finderSize][finderSize];
        int[][] imageFrame = new int[rows][columns];
        
        
    return null;
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
