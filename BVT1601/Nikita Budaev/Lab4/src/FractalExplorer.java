import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class FractalExplorer {
    private int displaySize; //размер экрана
    private JComboBox<FractalGenerator> genSelecter;
    private ImageDisplay img;
    private FractalGenerator fGen;
    private Rectangle2D.Double range;

    public FractalExplorer(int displaySize){
        this.displaySize = displaySize;
        range = new Rectangle2D.Double();
        fGen = new Count();
        fGen.getInitialRange(range);
    }

    public void createAndShowGUI(){
        JFrame frame = new JFrame("Fractal Explorer");
        img = new ImageDisplay(displaySize,displaySize);
        JButton resetBtn = new JButton("Reset Display");
        JButton saveBtn = new JButton("Сохрнить Фрактал");
        JLabel comboLabel = new JLabel("Выбрать Фрактал: ");
        genSelecter = new JComboBox<>();
        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();
        resetBtn.setActionCommand("reset");
        saveBtn.setActionCommand("Сохранить");
        MouseHandler mHandler = new MouseHandler();
        ActionHandler aHandler = new ActionHandler();
        resetBtn.addActionListener(aHandler);
        saveBtn.addActionListener(aHandler);
        img.addMouseListener(mHandler);
        genSelecter.addActionListener(aHandler);
        genSelecter.addItem(new sweetcorn());
        genSelecter.addItem(new Corn());
        genSelecter.addItem(new Count());
        northPanel.add(comboLabel);
        northPanel.add(genSelecter);
        southPanel.add(saveBtn);
        southPanel.add(resetBtn);
        frame.setLayout(new java.awt.BorderLayout());
        frame.add(northPanel,BorderLayout.NORTH);
        frame.add(img, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal(){
        for (int x = 0; x<img.getWidth();x++){
            for (int y = 0; y<img.getHeight();y++){
                double xCoord = FractalGenerator.getCoord(range.x,range.x+range.width,displaySize,x);
                double yCoord = FractalGenerator.getCoord(range.y,range.y+range.height,displaySize,y);
                int numIters = fGen.numIterations(xCoord,yCoord);
                if (numIters == -1){
                    img.drawPixel(x,y,0);
                }
                else {
                    float hue = 0.5f + (float) numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    img.drawPixel(x, y, rgbColor);
                }
            }
        }
        img.repaint();
    }

    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == genSelecter) {
                fGen = (FractalGenerator) genSelecter.getSelectedItem();
                fGen.getInitialRange(range);
                drawFractal();
            }
            else if (e.getActionCommand() == "reset") {
                fGen.getInitialRange(range);
                drawFractal();
            }
            else if (e.getActionCommand() == "Сохранить") {
                JFileChooser chooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Pics", "png");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showSaveDialog(img) == JFileChooser.APPROVE_OPTION) {
                    try {
                        ImageIO.write(img.image,"png", chooser.getSelectedFile());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(img,e1.getMessage(), "Write error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        }
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            double xCoord = FractalGenerator.getCoord(range.x,range.width,displaySize,e.getX());
            double yCoord = FractalGenerator.getCoord(range.y,range.height,displaySize,e.getY());
            fGen.recenterAndZoomRange(range,xCoord,yCoord,0.5);
            drawFractal();
        }
    }

    public static void main(String[] args){
        FractalExplorer fe = new FractalExplorer(800);
        fe.createAndShowGUI();
        fe.drawFractal();
    }
}