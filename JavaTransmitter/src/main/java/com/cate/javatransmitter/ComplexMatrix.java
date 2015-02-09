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
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.lang.Math;

/**
 *
 * @author Amin
 */
public class ComplexMatrix {
    int width;
    int height;
    public float[][] complexData;
//    private final BufferedImage imageData;
    private final int[] realData;
    
    public ComplexMatrix(int height,int width){
        this.width = width;
        this.height= height;
        complexData = new float[height][2*width];
//        imageData = new BufferedImage(height,width,BufferedImage.TYPE_BYTE_GRAY);
        realData = new int[height*width];
        
    }
    
    public void setElement(int i,int j, int real, int imag){
        try {
            this.complexData[i][2*j] = real;
            this.complexData[i][2*j+1] = imag;
            //Test
            i = height - i;
            j = width  - j;
            this.complexData[i][2*j] = real;
            this.complexData[i][2*j+1] = -imag;
            
            System.out.println(Integer.toString(i)+","+Integer.toString(j)+"="+Integer.toString(-imag));
        } catch (Exception e) {
        }
    }
    
    public void clearData(){
        for(int i=0;i<height;i++)
            for(int j=0;j<2*width;j++)
                this.complexData[i][j] = 0;
    }

    /**
        Mirror a ComplexMatrix around diagonal
     */
    public void mirror(){
        float tmp1, tmp2;
        for(int i=0;i<height;i++)
            for(int j=i;j<width;j++){
                tmp1 = this.complexData[i][j];
                tmp2 = this.complexData[j][i];
                this.complexData[i][j] = tmp2;
                this.complexData[j][i] = tmp1;
            }
    }
    
    public BufferedImage getBufferedImage(){
        //color c = new Color();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);        
        WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0, 0, width, height, this.getRealData());
        image.setData(raster);
    return image;
    }
    
    // Convert IFFT output into 1D Real Data for Raster
    private int[] getRealData(){
        int counter= 0;
        for(int i=0;i<height;i++)
            for(int j=0;j<width;j++)
                realData[counter++] = Math.abs((byte) this.complexData[i][2*j]);
        return realData;
}
    
}