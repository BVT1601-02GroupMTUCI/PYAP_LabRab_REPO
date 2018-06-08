package com.company;

import java.awt.geom.Rectangle2D;

//Mandelbrot
public class Mandelbrot extends FractalGenerator{

    //number of maximum iterations
    public static final int MAX_ITERATIONS = 2000;

    //constructor
    public void getInitialRange(Rectangle2D.Double range){
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    //реализация Mandelbrot fractal
    public int numIterations(double x, double y){
        int iteration = 0;

        double zreal = 0;
        double zimaginary = 0;

        //Zn = Zn - 1^2 + c
        while(iteration < MAX_ITERATIONS && zreal*zreal + zimaginary * zimaginary < 4){
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginaryUpdated = 2 * zreal * zimaginary + y;
            zreal = zrealUpdated;
            zimaginary = zimaginaryUpdated;
            iteration++;
        }

        if(iteration == MAX_ITERATIONS){
            return -1;
        }

        return iteration;
    }


}
