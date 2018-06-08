package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

public class Main {

    // размеры дисплея в пикселях
    private int displaySize;

    // дисплей
    private JImageDisplay display;

    private FractalGenerator fractal;

    private Rectangle2D.Double range;

    //constructor
    public Main(int size){
        displaySize =size;

        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }

    //init Swing GUI
    public void createAndShowGUI(){
        JFrame myframe = new JFrame("Fractal");

        myframe.add(display, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset Display");


        resetButton.addActionListener((e)->{
            fractal.getInitialRange(range);
            drawFractal();
        });

        myframe.add(resetButton, BorderLayout.SOUTH);

        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myframe.pack();
        myframe.setVisible(true);
        myframe.setResizable(false);
    }

    //рисуем
    private void drawFractal(){
        for(int x = 0; x < displaySize; x++){
            for(int y = 0; y < displaySize; y++){

                double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize,x);
                double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);

                //compute number of iterations
                int iteration = fractal.numIterations(xCoord, yCoord);

                if(iteration == -1){
                    display.drawPixel(x, y, 0); //black
                } else {
                    float hue = 0.7f + (float)iteration/200f;
                    int rgbColor = Color.HSBtoRGB(hue,1f,1f);

                    display.drawPixel(x,y,rgbColor);
                }
            }
        }
        display.repaint();
    }

    //обработчик событий мыши
    private class MouseHandler extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e){
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x, range.x+range.width, displaySize, x);

            int y = e.getY();
            double yCoord = fractal.getCoord(range.y, range.y+range.height,displaySize,y);

            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

            drawFractal();

        }
    }
    public static void main(String[] args) {
	    Main explorer = new Main(600);
	    explorer.createAndShowGUI();
	    explorer.drawFractal();
    }
}
