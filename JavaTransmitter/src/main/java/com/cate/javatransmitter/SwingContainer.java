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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SwingContainer {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   private JLabel msglabel;
   private JLabel picLabel;

   //private ActionListener action = 
   

   public SwingContainer(){
      prepareGUI();
   }

   /*public static void main(String[] args){
      SwingContainerDemo  swingContainerDemo = new SwingContainerDemo();  
      swingContainerDemo.showJPanelDemo();
   }*/

   private void prepareGUI(){
      mainFrame = new JFrame("Java OFDM Barcode Transmitter");
      picLabel = new JLabel();
      mainFrame.setSize(600,650);
      mainFrame.setLocation(2000, 100);
      mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.PAGE_AXIS));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      //headerLabel = new JLabel("", JLabel.CENTER);  
      headerLabel = new JLabel("Container in action: JPanel");
      headerLabel.setHorizontalTextPosition(JLabel.CENTER);
      statusLabel = new JLabel("",JLabel.CENTER);

      statusLabel.setSize(350,100);

      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      controlPanel.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      

      controlPanel.add(picLabel);
      mainFrame.setVisible(true);   
   }


//   public void showJPanelDemo(){
//      headerLabel.setText("Container in action: JPanel");
//
//      msglabel = new JLabel("Welcome to TutorialsPoint SWING Tutorial."
//         , JLabel.CENTER);
//      JPanel panel = new JPanel();
//      panel.setBackground(Color.magenta);
//      panel.setLayout(new FlowLayout());        
//      //panel.add(msglabel);
//      //controlPanel.add(panel);
//   }   


public void showBarcode(BufferedImage barcode){
//    BufferedImage img=null;
//    URL google = null;
//      try {
//             google = new URL("http://www.google.com/images/srpr/logo11w.png");
//        } 
//      catch (IOException e) {
//    // exception handler code here
//    // ...
//        }
//   
//      try{
//          
//        img = ImageIO.read(google);
//      
//      }
//      catch (IOException e) {
//          
//      }
   
//      int w = img.getWidth(null);
//      int h = img.getHeight(null);
      picLabel.setIcon(new ImageIcon(barcode));
//      BufferedImage myPicture1=null;
//       try {
//           myPicture1 = ImageIO.read(new File("c:\\logo11w.png"));
//       } catch (IOException ex) {
//           Logger.getLogger(SwingContainer.class.getName()).log(Level.SEVERE, null, ex);
//       }
//      BufferedImage myPicture2=null;
//       try {
//           myPicture2 = ImageIO.read(new File("c:\\erassm.gif"));
//       } catch (IOException ex) {
//           Logger.getLogger(SwingContainer.class.getName()).log(Level.SEVERE, null, ex);
//       }



//      Timer timer;
//      for (int i=0;i<100;i++){
//        picLabel.setIcon(new ImageIcon(barcode));
//          try {
//              Thread.sleep(100);
//          } catch (InterruptedException ex) {
//              Logger.getLogger(SwingContainer.class.getName()).log(Level.SEVERE, null, ex);
//          }
//        picLabel.setIcon(new ImageIcon(myPicture1));
//          try {
//              Thread.sleep(100);
//          } catch (InterruptedException ex) {
//              Logger.getLogger(SwingContainer.class.getName()).log(Level.SEVERE, null, ex);
//          }        
//        }
//    picLabel.setIcon(new ImageIcon(barcode));
    }
}