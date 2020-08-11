import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MoodVisualizar extends JPanel {
	BufferedImage img;
	BufferedImage result;
	
	final int xmulti = 15;
	final int ymulti = 3;
	final int numMood = 24;
	
	double range = 1.0 / (double)(numMood);
	
	private double[] percentage;
	
	public MoodVisualizar (BufferedImage img) {
		super();
		
		this.img = img;
//		BoxLayout panelLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
//        this.setLayout(panelLayout);
//
//        JPanel panel = new JPanel();
        
        result = new BufferedImage(xmulti * numMood, ymulti * 100, img.getType()); 
        
        percentage = new double[numMood];
        
        for (double var : percentage) 
        { 
            var = 0.0;
        }
        
        checkMood();
        drawGraph();
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(result, 0, 0, this); // see javadoc for more info on the parameters            
    }
	
	private void checkMood() {
		
		double p = 1.0 / (double)(img.getWidth() * img.getHeight());
		
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				int rgb = img.getRGB(i, j);

				float[] hsb = new float[3];
				hsb = Color.RGBtoHSB(getRed(rgb), getGreen(rgb), getBlue(rgb), null);
				
				int location = 0;
				
				while (hsb[0] >= range * (location + 1)) {
					location++;
					
					//System.out.println(location + "  ");
				}
				
				location = Math.min(location, 23);
				
				percentage[location] += p;			
			}
		}
	}
	
	private void drawGraph() {
		for (int i = 0; i < result.getWidth(); i++) {
			for (int j = 0; j < result.getHeight(); j++) {
				
				int location = i / xmulti;
				
				if (percentage[location] * result.getHeight() >= result.getHeight() - j) {
					
					float hue = (float) (((location + 1) * range) - range / 2.0);
					
					int rgb = Color.HSBtoRGB(hue, 1f, 1f);		
					result.setRGB(i, j, rgb);
					
				}
				else {
					int rgb = Color.HSBtoRGB(0.5f, 0.5f, 0.5f);					
					result.setRGB(i, j, rgb);
				}
			}
		}
	
	}
	
	protected int getRed(int pixel) {
		return (new Color(pixel)).getRed();
	}

	protected int getGreen(int pixel) {
		return (new Color(pixel)).getGreen();
	}

	protected int getBlue(int pixel) {
		return (new Color(pixel)).getBlue();
	}
}
