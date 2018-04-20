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
    private int displaySize;
    private JFrame frame;
    private JButton resetBtn, saveBtn;
    private JComboBox<FractalGenerator> genSelector;
    private JImageDisplay img;
    private FractalGenerator fGen;
    private Rectangle2D.Double range;
    private int rowsRemaining;
    public FractalExplorer(int displaySize){
        this.displaySize = displaySize;
        range = new Rectangle2D.Double();
        fGen = new BurningShip();
        fGen.getInitialRange(range);
    }

    public void createAndShowGUI(){
        frame = new JFrame("Fractal Explorer");
        img = new JImageDisplay(displaySize,displaySize);
        resetBtn = new JButton("Reset Display");
        saveBtn = new JButton("Save Fractal");
        JLabel comboLabel = new JLabel("Select Fractal: ");
        genSelector = new JComboBox<>();
        JPanel northPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        resetBtn.setActionCommand("reset");
        saveBtn.setActionCommand("save");
        MouseHandler mHandler = new MouseHandler();
        ActionHandler aHandler = new ActionHandler();
        resetBtn.addActionListener(aHandler);
        saveBtn.addActionListener(aHandler);
        img.addMouseListener(mHandler);
        genSelector.addActionListener(aHandler);
        genSelector.addItem(new Mandelbrot());
        genSelector.addItem(new Tricorn());
        genSelector.addItem(new BurningShip());
        northPanel.add(comboLabel);
        northPanel.add(genSelector);
        buttonPanel.add(saveBtn);
        buttonPanel.add(resetBtn);
        frame.setLayout(new java.awt.BorderLayout());
        frame.add(northPanel,BorderLayout.NORTH);
        frame.add(img, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void enableUI(boolean val){
        resetBtn.setEnabled(val);
        saveBtn.setEnabled(val);
        genSelector.setEnabled(val);
    }



    private void drawFractal() {
        enableUI(false);
        rowsRemaining = displaySize;
        for (int y = 0; y < img.getHeight(); y++) {
            FractalWorker bgWorker = new FractalWorker(y);
            bgWorker.execute();
        }
    }

   private class ActionHandler implements ActionListener {
       @Override
       public void actionPerformed(ActionEvent e) {
           if (e.getSource() == genSelector) {
               fGen = (FractalGenerator) genSelector.getSelectedItem();
               fGen.getInitialRange(range);
               drawFractal();
           }
           else if (e.getActionCommand() == "reset") {
               fGen.getInitialRange(range);
               drawFractal();
           }
           else if (e.getActionCommand() == "save") {
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
           if (rowsRemaining != 0) return;
           double xCoord = FractalGenerator.getCoord(range.x,range.width,displaySize,e.getX());
           double yCoord = FractalGenerator.getCoord(range.y,range.height,displaySize,e.getY());
           fGen.recenterAndZoomRange(range,xCoord,yCoord,0.5);
           drawFractal();
       }
   }

   private class FractalWorker extends SwingWorker<Object,Object> {
        private int y;
        private int[] rgbColors;

        public FractalWorker(int y){
            this.y = y;
        }

        public Object doInBackground(){
            rgbColors = new int[displaySize];
            for (int x = 0; x<img.getWidth();x++){
                double xCoord = FractalGenerator.getCoord(range.x,range.x+range.width,displaySize,x);
                double yCoord = FractalGenerator.getCoord(range.y,range.y+range.height,displaySize,y);
                int numIters = fGen.numIterations(xCoord,yCoord);
                if (numIters == -1){
                    rgbColors[x] = 0;
                }
                else {
                    float hue = 0.5f + (float) numIters / 200f;
                    rgbColors[x] = Color.HSBtoRGB(hue, 1f, 1f);
                }
            }
            return null;
        }

        public void done(){
            for (int x = 0; x < img.getWidth(); x++){
                img.drawPixel(x,y,rgbColors[x]);
            }
            img.repaint(0,0,y,displaySize,1);
            rowsRemaining--;
            if (rowsRemaining == 0) enableUI(true);
        }
   }

    public static void main(String[] args){
        FractalExplorer fe = new FractalExplorer(800);
        fe.createAndShowGUI();
        fe.drawFractal();
    }
}
