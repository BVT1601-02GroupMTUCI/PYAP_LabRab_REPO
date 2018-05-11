import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;

public class JImageDisplay extends JComponent{

	private BufferedImage image;
	
	public JImageDisplay(int Width, int Height){
		image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		super.setPreferredSize(new java.awt.Dimension(Width, Height));
	}
	//�����, ������� ������������� ��� ������� ����������� ������� �����
	public void clearImage(){
		int arrLen;
		if (image.getHeight() > image.getWidth())
			arrLen = image.getHeight();
		else {                                
			arrLen = image.getWidth();
		}
		int[] col = new int[arrLen];
		image.setRGB(0, 0, image.getWidth() - 1, image.getHeight() - 1, col, 0, 0);
	}
	//�����, ������� ������������� ������������ ���� ��� �������
	public void drawPixel(int x, int y, int rgbColor)
	{
		image.setRGB(x, y, rgbColor);
	}
	//�����, ������� ������� �� ����� ������ �����������
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
	}
}