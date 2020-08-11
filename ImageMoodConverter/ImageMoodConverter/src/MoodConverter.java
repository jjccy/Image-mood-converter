
/**
 * 
 */

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.accessibility.Accessible;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;



/**
 * @author User
 *
 */
public class MoodConverter extends JPanel implements Accessible { // GUI + operations goes here

	/**
	 * @param args
	 */

	ImageSection imageSection;

	final int BUTTON_WIDTH = 100;
	final int BUTTON_HEIGHT = 100;
	public int visualizeCount = 0;
	public MoodConverter() throws IOException {
		imageSection = new ImageSection();
		imageSection.repaint();
		

		this.setPreferredSize(new Dimension(500, 800));
		this.setBorder(BorderFactory.createLineBorder(Color.black));

		
//		JPanel panel = new JPanel();
//
        BoxLayout panelLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(panelLayout);


		
		Button b1 = new Button("Add Image 1");
		Button b2 = new Button("Add Image 2");
		Button b3 = new Button("Transfer Mood");
		Button b4 = new Button("Save");
		try {
			b1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("resource/button1.png"))));
			b2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("resource/button2.png"))));
			b3.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("resource/button3.png"))));
			b4.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("resource/button4.png"))));
		}
		catch(Exception ex){

		}


		b1.setBorder(BorderFactory.createEmptyBorder());
		b1.setContentAreaFilled(false);

		b2.setBorder(BorderFactory.createEmptyBorder());
		b2.setContentAreaFilled(false);
		b3.setBorder(BorderFactory.createEmptyBorder());
		b3.setContentAreaFilled(false);
		b4.setBorder(BorderFactory.createEmptyBorder());
		b4.setContentAreaFilled(false);
		this.add(b1);
		this.add(b2);
		this.add(b3);
		this.add(b4);
//
		
//		guiSection.add(panel);
//
//		guiSection.pack();
//		guiSection.setVisible(true);
	}

	private class Button extends JButton {

		public Button(String type) {
			super();


			this.setActionCommand(type);
			this.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
			this.addMouseListener(new MouseDetector());
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));


		}
	}

	private class MouseDetector implements MouseListener { // mouse handler, all button action is here

		Button button;
		File file;
		BufferedImage img;

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			button = (Button) e.getSource();

			if (null != button.getActionCommand())
				switch (button.getActionCommand()) {

				case "Add Image 1":

					file = chooseFile();
					
					if (file != null) {
						try {
							img = ImageIO.read(file);
							imageSection.addInputImage(img);
							imageSection.repaint();
							visualize(img);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}					
					}
					

					break;
					
				case "Add Image 2":

					file = chooseFile();
					
					if (file != null) {
						try {
							img = ImageIO.read(file);
							imageSection.addReferenceImage(img);
							imageSection.repaint();
							visualize(img);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}						
					}			
					

					break;

				case "Transfer Mood":
					
					imageSection.MoodConvert();
					imageSection.repaint();
					visualize(imageSection.getResult());
					break;
					
				case "Save":
					
					if (imageSection.getResult() != null) {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Specify a file to save");  
						FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
						fileChooser.setFileFilter(filter);
						
						int userSelection = fileChooser.showSaveDialog(getParent());
						String location; 
						//String format; // "PNG" for example
						
						if (userSelection == JFileChooser.APPROVE_OPTION) {
						    File fileToSave = fileChooser.getSelectedFile();
						    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
						    
						    location = fileToSave.getAbsolutePath()  + ".jpg";
						    
						    try {
								ImageIO.write(imageSection.getResult(), "jpg", new File(location));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
						
					}
					else {
						System.out.println("nothing to save yet");
					}
					
					
					break;

				default:

					break;

				}

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
	protected File chooseFile() { // filerchooser function to choose files
		File result = null;

		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView());

		chooser.setDialogTitle("Select an Image");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(getParent());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			
			result = chooser.getSelectedFile();
			
		}

		return result;
	}
	
	protected void visualize(BufferedImage img) {
//		JFrame f = new JFrame("Visualize Image");
//		f.setSize(500, 500);
//		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//		f.add(new MoodVisualizar(img));
//		f.setVisible(true);

		if (visualizeCount == 0){
			MoodVisualizar placeholder = new MoodVisualizar(img);



		}


	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		try {
//
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//
//		}
//		catch (Exception e) {
//			// handle exception
//		}
//
//		MoodConverter moodconverter = new MoodConverter();
//	}

}
