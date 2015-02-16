/*
 * The MIT License
 *
 * Copyright 2015 Amin Motahari <mamdamin at gmail.com>.
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
package com.cate.javatransmitter.DifferentialModulators;

import java.util.HashMap;

/**
 *
 * @author Amin Motahari <mamdamin at gmail.com>
 */
public class DifferentialByteModem {
    private final HashMap<Integer, Integer> difMap = new HashMap<>();
    private int carry;
    
    // setup the HashMap for differential Modulation
    public DifferentialByteModem(){
        this.reset(1);
        int[] bitArray = new int[8];
        int diffByte;
        int oldBit;
        int newBit;
        for(int k = 0 ; k<2 ; k++)
            for(int i = 0; i<256; i++){
                diffByte = 0;
                bitArray = convert2Array(i);
                oldBit = k;
                for(int j=0;j<8;j++){
                    newBit = ((bitArray[j]^oldBit)&0x01);
                    diffByte += (newBit<<j);
                    oldBit = newBit;
                }
                difMap.put(k*256+i, diffByte);
                //System.out.println(Integer.toBinaryString(i)+" for k = "+k+"=>"+ Integer.toBinaryString(diffByte));
           }
    }
    // Get Differential Value for a Byte and keep the carry bit for next calculation
    public int getDifByte(int data){    
        int difData = difMap.get(carry<<8+data);
        this.carry = (difData>>8);
        return difData;
    }
    
    // Convert a Byte into 8 element array
    private int[] convert2Array(int byteData){
        int[] output = new int[8];
        for(int i = 0; i<8;i++)
            output[i]=(byteData>>i) & 0x01;
        return output;
    }
    
    public final void reset(int carry){
        this.carry = carry;
    }
}


