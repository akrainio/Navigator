import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class main {
    public static void main(String args[]) throws IOException {
        BufferedImage image = loadImage();
        JFrame jFrame = new JFrame("Map");
        
        jFrame.setSize(1920, 1080);
        Container content = jFrame.getContentPane();
        content.setLayout(new BorderLayout());
        Container textFields = new Container();
        textFields.setLayout(new FlowLayout());
        textFields.add(new JLabel("Width: "));
        textFields.add(new JTextField(10));
        textFields.add(new JLabel("Height: "));
        textFields.add(new JTextField(10));
        content.add("North", textFields);

        Container center = new Container() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        content.add("Center", center);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
    private static BufferedImage loadImage() throws IOException {
        try (InputStream in = main.class.getResourceAsStream("mendocino.gif");) {
            return ImageIO.read(in);
        }
    }
}
