/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

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
   //private ActionListener action = 
   

   public SwingContainer(){
      prepareGUI();
   }

   /*public static void main(String[] args){
      SwingContainerDemo  swingContainerDemo = new SwingContainerDemo();  
      swingContainerDemo.showJPanelDemo();
   }*/

   private void prepareGUI(){
      mainFrame = new JFrame("Java Swing Examples");
      mainFrame.setSize(600,600);
      mainFrame.setLocation(2000, 100);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("", JLabel.CENTER);        
      statusLabel = new JLabel("",JLabel.CENTER);

      statusLabel.setSize(350,100);

      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);
   
   }


   public void showJPanelDemo(){
      headerLabel.setText("Container in action: JPanel");      
      msglabel = new JLabel("Welcome to TutorialsPoint SWING Tutorial."
         , JLabel.CENTER);
      JPanel panel = new JPanel();
      panel.setBackground(Color.magenta);
      panel.setLayout(new FlowLayout());        
      panel.add(msglabel);
      controlPanel.add(panel);
   }   


public void showBarcode(){
    BufferedImage img=null;
    URL google = null;
      try {
             google = new URL("http://www.google.com/images/srpr/logo11w.png");
        } 
      catch (IOException e) {
    // exception handler code here
    // ...
        }
   
      try{
          
        img = ImageIO.read(google);
      
      }
      catch (IOException e) {
          
      }
   
      int w = img.getWidth(null);
      int h = img.getHeight(null);
     
      BufferedImage myPicture1=null;
       try {
           myPicture1 = ImageIO.read(new File("c:\\logo11w.png"));
       } catch (IOException ex) {
           Logger.getLogger(SwingContainer.class.getName()).log(Level.SEVERE, null, ex);
       }
      BufferedImage myPicture2=null;
       try {
           myPicture2 = ImageIO.read(new File("c:\\erassm.gif"));
       } catch (IOException ex) {
           Logger.getLogger(SwingContainer.class.getName()).log(Level.SEVERE, null, ex);
       }
      JLabel picLabel = new JLabel("hi",new ImageIcon(myPicture1),JLabel.CENTER);

      controlPanel.add(picLabel);

      mainFrame.setVisible(true);
      Timer timer;
      for (int i=0;i<100;i++){
        picLabel.setIcon(new ImageIcon(myPicture2));
          try {
              Thread.sleep(100);
          } catch (InterruptedException ex) {
              Logger.getLogger(SwingContainer.class.getName()).log(Level.SEVERE, null, ex);
          }
        picLabel.setIcon(new ImageIcon(myPicture1));
          try {
              Thread.sleep(100);
          } catch (InterruptedException ex) {
              Logger.getLogger(SwingContainer.class.getName()).log(Level.SEVERE, null, ex);
          }        
        }
    }
}