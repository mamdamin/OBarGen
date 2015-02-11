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
import java.awt.image.WritableRaster;

/**
 *  Defined a finder pattern for later insertion on frame corners
 * @author Amin
 */
public class FinderPattern {
    public WritableRaster Raster;
    public int scale;
    public int size;
    public FinderPattern(int finderScale){
        int finderSize = 9* finderScale;
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
        Raster = finderImage.getRaster();
        this.scale = finderScale;
        this.size = finderSize;
    }
}
