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
 *
 * @author Amin
 */
public class ComplexMatrix {
    int width;
    int height;
    public int[][] complexData;
    public ComplexMatrix(int height,int width){
        width = this.width;
        height= this.height;
        complexData = new int[height][2*width];
    }
    
    public void setElement(int i,int j, int real, int imag){
        this.complexData[i][j]   = real;
        this.complexData[i][j]   = imag;
    }
    
    public void clearData(){
        for(int i=0;i<height;i++)
            for(int j=0;j<2*width;j++)
                this.complexData[i][j] = 0;
    }
}
