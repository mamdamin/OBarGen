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

/**
    // Puts adjacent data bits which are differntially modulated on 
    // adjacent elements of a complex matrix;
 * @author Amin
 */
public class HermitianModulator {

    public static ComplexMatrix hermitianModulator(int[] data,int height,int width, int nRows){
        ComplexMatrix tile = new ComplexMatrix(height,width);
        tile.setElement(1,1,0,1);
        int dataIndex = 0;
        nRows++;    // First Row is reserved all zero 

        // Implement various lines on 1st tile
        for(int r=2;r<=nRows;r+=2){
            for(int j=1;j<r;j++){
                tile.setElement(r,j,0,data[dataIndex]);
                dataIndex++;
            }
        
            for(int i=r-1;i>0;i--){
                tile.setElement(i,r,0,data[dataIndex]);
                dataIndex++;
            }            
            
            for(int i=1;i<r+1;i++){
                tile.setElement(i,r+1,0,data[dataIndex]);
                dataIndex++;
            } 

            for(int j=r;j>0;j--){
                tile.setElement(r+1,j,0,data[dataIndex]);
                dataIndex++;
            }            
            
        }

        // Implement various lines on 2nd tile
        //Test
        System.out.println("NEXT");
        tile.setElement(1,width-1,0,1);
        for(int r=2;r<=nRows;r+=2){
            for(int j=1;j<r;j++){
                tile.setElement(r,width-j,0,data[dataIndex]);
                dataIndex++;
            }
        
            for(int i=r-1;i>0;i--){
                tile.setElement(i,width-r,0,data[dataIndex]);
                dataIndex++;
            }            
            
            for(int i=1;i<r+1;i++){
                tile.setElement(i,width-r-1,0,data[dataIndex]);
                dataIndex++;
            } 

            for(int j=r;j>0;j--){
                tile.setElement(r+1,width-j,0,data[dataIndex]);
                dataIndex++;
            }            
            
        }        
        return tile;
    }
}    

