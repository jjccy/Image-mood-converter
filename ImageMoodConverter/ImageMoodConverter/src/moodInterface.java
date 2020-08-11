import javax.swing.*;
import java.awt.*;

public class moodInterface extends JFrame {
    private static int windowW = 1920;
    private static int windowH = 1080;

    public moodInterface(String title){
        super(title);
//        this.setDefaultCloseOperation(3);
//        this.setSize(windowW, windowH);
//
//        this.setVisible(true);


    }
    public static void main(String[] args) {
		try {

		    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		}
		catch (Exception e) {
		   // handle exception
		}


        moodInterface app = new moodInterface("Our Mood Converter");

        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        app.setExtendedState(JFrame.MAXIMIZED_BOTH);
        app.setLayout(new BorderLayout());
        app.setVisible(true);

        JPanel container = new JPanel();

        try {
            MoodConverter guiControls = new MoodConverter();
            ImageSection imgSection = guiControls.imageSection;
            container.setLayout(new GridLayout(1,2));
            container.add(imgSection);
            container.add(guiControls);
        }
        catch(Exception ex){

        }

        app.add(container);
        app.repaint();
    }

}
