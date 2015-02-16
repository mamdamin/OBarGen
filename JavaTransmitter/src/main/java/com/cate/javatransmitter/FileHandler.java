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

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Read a File in defined chunks
 * @author Amin Motahari
 */
public class FileHandler {
    boolean isFinished;
    int nofChunks;

    private File inputFile;
    private Path inputPath;
    private int packetSize;
    private SeekableByteChannel sbc;
    private ByteBuffer buf;
    private int chunkCounter;
    
    public FileHandler(){
        this.inputPath = Paths.get("C:/","erassm.gif");
        this.packetSize = 4*1024;
        this.isFinished = false;
        this.chunkCounter = 0;
    }
    
    public byte[] nextChunk(){

        
            // Read the bytes with the proper encoding for this platform.  If
            // you skip this step, you might see something that looks like
            // Chinese characters when you expect Latin-style characters.
            //String encoding = System.getProperty("file.encoding");
        int bytesRead=packetSize;
        buf.clear();
        if(chunkCounter<nofChunks){
            chunkCounter++;
            try {    
                bytesRead = sbc.read(buf);
                System.out.println(bytesRead);
            }
            catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(chunkCounter == nofChunks){
                this.isFinished = true;
                try {
                    sbc.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return(Arrays.copyOf(buf.array(), bytesRead));                
}
    
    public void setFile(){
            //TODO add file selection
            this.inputPath = inputPath;
        try {
            this.sbc = Files.newByteChannel(inputPath, StandardOpenOption.READ);
            this.nofChunks = (int) Math.ceil((double)(sbc.size())/packetSize);
            System.out.println(nofChunks);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    this.chunkCounter = 0;
    buf = ByteBuffer.allocate(packetSize);        


//catch (IOException x) {
//    System.out.println("caught exception: " + x);        
//        return null;
//    }        
//            
}
        
    public void setPacketSize(int packetSize){
        this.packetSize = packetSize;
    }
    
}
