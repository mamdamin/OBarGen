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
import com.cate.javatransmitter.ComplexMatrix;

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
    
    // Puts adjacent data bits which are differntially modulated on 
    // adjacent elements of a complex matrix;
    private ComplexMatrix modulateATile(int[] data,int height,int width){
        ComplexMatrix tile = new ComplexMatrix(height,width);
        tile.setElement(0,0,0,1);
        int dataIndex = 0; 
        // Implement various lines on a tile
        for(int r=1;r<width;r+=2){
            for(int j=0;j<r;j++){
                tile.setElement(r,j,0,data[dataIndex]);
                dataIndex++;
            }
        
            for(int i=r-1;i>=0;i--){
                tile.setElement(i,r,0,data[dataIndex]);
                dataIndex++;
            }            
            
            for(int i=0;i<r+1;i++){
                tile.setElement(i,r+1,0,data[dataIndex]);
                dataIndex++;
            } 

            for(int j=r;j>=0;j--){
                tile.setElement(r+1,j,0,data[dataIndex]);
                dataIndex++;
            }            
            
        }
        return tile;
    }
    //Take care of putting tiles together and DFT
    public void modulateData(int[] data){
        int columns   = 5;
        int rows  = 5;
        ComplexMatrix tile = new ComplexMatrix(rows,columns);
        tile.clearData();
        tile = this.modulateATile(data, rows, columns);
        columns = 3;
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
