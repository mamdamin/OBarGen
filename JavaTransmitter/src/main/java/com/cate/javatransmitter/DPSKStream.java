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

import java.util.Arrays;

/**
 * Create a stream of DPSK symbols from Byte array of data
 * @author Amin Motahari
 */
public class DPSKStream {
    int[] inputData;
    int[] outStream;
    int byteCounter;
    int bitCounter;
    private int length;
    public DPSKStream(){
        this.reset();
    }
    
    public final void reset(){
        length = 0;        
        this.byteCounter = 0;
        this.bitCounter = 0;
        inputData=null;
    }
            
    public int next(){
        return((bitCounter>=inputData.length*8)?0:100*((inputData[bitCounter/8]>>bitCounter++%8)&0x01)-50);
    }
    
    public void setData(int[] inputData) {
        this.reset();
        this.inputData = (inputData.length<=Integer.MAX_VALUE/8)?inputData:Arrays.copyOf(inputData, Integer.MAX_VALUE/8);
        this.length = inputData.length*8;
    }
    
}

