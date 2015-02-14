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
 * gets an input byte array and returns differential modulated bit array
 * @author Amin
 */
public class DPSKModulator {
    public static int[] DPSKModulator(int[] inputData){
    int[] outputData = new int[inputData.length*8];
    int currentByte;
    int currentBit;
    int oldBit = 0x01;
        for(int i=0; i<inputData.length;i++){
            currentByte = (byte) inputData[i];
            for(int j=0;j<8;j++){
                currentBit = (currentByte ^ oldBit) & 0x01;
                oldBit = currentBit;
                currentByte = (byte) (currentByte>>1);
                outputData[i*8+j]=oldBit;
            }
        }
        
    return outputData;
        
    }
}
