package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

// JImageDisplay shows our fractal
public class JImageDisplay extends JComponent {

    private BufferedImage displayImage;

    //constructor
    public JImageDisplay(int width, int height){
        displayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Dimension imageDimension = new Dimension(width, height);
        super.setPreferredSize(imageDimension);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(displayImage, 0, 0, displayImage.getWidth(), displayImage.getHeight(), null);
    }

    public void clearImage(){
        int[] blankArray = new int[getWidth() * getHeight()];
        displayImage.setRGB(0,0,getWidth(),getHeight(),blankArray,0,1);
    }
    public void drawPixel(int x, int y, int rgbColor){
        displayImage.setRGB(x,y,rgbColor);
    }
}
