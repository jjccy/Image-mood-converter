//

//**********************************************************/
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;


class ImageSection extends JPanel {// Image section + calculation + other image releated goes here
	
	private BufferedImage inputImage;
	private BufferedImage referenceImage;
	private BufferedImage outputImage;

	public BufferedImage visualize1;
	public BufferedImage visualize2;

	private double[] percentage;
	double range = 1.0 / (double)(24);
	
	public ImageSection() {
//		this.setTitle("Mood Converter Image Section");
//		this.setVisible(true);
		this.setPreferredSize(new Dimension(950,980));
		percentage = new double[24];

		for (double var : percentage)
		{
			var = 0.0;
		}
		
//		// Anonymous inner-class listener to terminate program
//		this.addWindowListener(new WindowAdapter() {// anonymous class definition
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);// terminate the program
//			}// end windowClosing()
//		}// end WindowAdapter
//		);// end addWindowListener
		
	}
	
	public BufferedImage combineImages(BufferedImage src1, BufferedImage src2, Operations op) {

		if (src1.getType() != src2.getType()) {
			System.out.println("Source Images should be of the same type");
			return null;
		}
		
		int useW = Math.min(src1.getWidth(),  src2.getWidth());
		int useH = Math.min(src1.getHeight(),  src2.getHeight());
		
		BufferedImage result = new BufferedImage(useW, useH, src1.getType());

		switch (op) {

		case multiply:
			for (int i = 0; i < result.getWidth(); i++) {
				for (int j = 0; j < result.getHeight(); j++) {
					int rgb1 = src1.getRGB(i, j);
					int rgb2 = src2.getRGB(i, j);

					result.setRGB(i, j,
							makeRGB(clip(getRed(rgb2) * getRed(rgb1) / 255.0),
									clip(getGreen(rgb2) * getGreen(rgb1) / 255.0),
									clip(getBlue(rgb2) * getBlue(rgb1) / 255.0)));

				}
			}

			break;

		case add:
			for (int i = 0; i < result.getWidth(); i++) {
				for (int j = 0; j < result.getHeight(); j++) {
					int rgb1 = src1.getRGB(i, j);
					int rgb2 = src2.getRGB(i, j);

					result.setRGB(i, j, 
							makeRGB(clip(getRed(rgb2) + getRed(rgb1)),
									clip(getGreen(rgb2) + getGreen(rgb1)), 
									clip(getBlue(rgb2) + getBlue(rgb1))));

				}
			}

			break;

		case subtract:
			for (int i = 0; i < result.getWidth(); i++) {
				for (int j = 0; j < result.getHeight(); j++) {
					int rgb1 = src1.getRGB(i, j);
					int rgb2 = src2.getRGB(i, j);

					result.setRGB(i, j, 
							makeRGB(Math.abs(getRed(rgb2) - getRed(rgb1)),
									Math.abs(getGreen(rgb2) - getGreen(rgb1)), 
									Math.abs(getBlue(rgb2) - getBlue(rgb1))));

				}
			}

			break;

		default:
			break;
		}

		return result;
	}
	
	
	private int clip(double v) {
		v = v > 255 ? 255 : v;
		v = v < 0 ? 0 : v;
		return (int) v;
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

	protected int makeRGB(int r, int g, int b) {
		return new Color(r, g, b).getRGB();
	}
	
	public void paint(Graphics g) {
		this.paintComponent(g);
		
		int w1 = (int) (4.5 * 70);
		int h1 = (int) (3.5 * 70);
		
		g.setColor(Color.BLACK);
		Font f1 = new Font("Verdana", Font.PLAIN, 13);
		g.setFont(f1);
		
		g.drawImage(inputImage, 30, 40, w1, h1, this);
		g.drawString("Input Image", 30, 10);

//		if(visualize1 != null){
//
//
//		}
		if (inputImage != null) {
			g.drawImage(drawGraph(inputImage), 30 + w1 + 30, 40, w1, h1, this );
			percentage = new double[24];
		}
		g.drawString("Input Image Visualizer", 30+w1+30, 10);

		g.drawImage(referenceImage, 30, 30+h1+80, w1, h1, this);
		g.drawString("Reference Image", 30, h1+80);

		if (referenceImage != null) {
			g.drawImage(drawGraph(referenceImage), 30 + w1 + 30, 30+h1+80, w1, h1, this );
			percentage = new double[24];
		}
		g.drawString("Reference Image Visualizer", 30+w1+30, h1+80);

		g.drawImage(outputImage, 30, 30+h1+400, w1, h1, this);
		g.drawString("Output Image", 30, h1+400);

		if (outputImage != null) {
			g.drawImage(drawGraph(outputImage), 30 + w1 + 30, 30+h1+400, w1, h1, this );
			percentage = new double[24];
		}
		g.drawString("Output Image Visualizer", 30+w1+30, h1+400);
		
//		g.drawImage(outputImage, 30, 50 + h1 + 180, w1, h1, this);
//		g.drawString("Output Image", 30, 50 + h1 + 170);



	}
	
	public void addInputImage(BufferedImage img) {
		inputImage = img;
	}
	
	public void addReferenceImage(BufferedImage img)  {
		referenceImage = img;
	}
	
	public BufferedImage getResult() {
		return outputImage;
	}
	
	public void MoodConvert() {
		if (inputImage != null && referenceImage != null) {
			outputImage = combineImages(inputImage, referenceImage, Operations.multiply);
		}
		else {
			System.out.println("Please input all the images first");
		}
	}

	private void checkMood(BufferedImage img) {

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


	private BufferedImage drawGraph(BufferedImage img) {
		checkMood(img);
		BufferedImage result = new BufferedImage(15 * 24, 3 * 100, img.getType());
		for (int i = 0; i < result.getWidth(); i++) {
			for (int j = 0; j < result.getHeight(); j++) {

				int location = i / 15;

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
		return result;

	}
	
}