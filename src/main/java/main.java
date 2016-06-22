import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.DoubleConsumer;

public class main {
    public static void main(String args[]) throws IOException {
        BufferedImage image = loadImage();
        JFrame jFrame = new JFrame("Map");
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = defaultToolkit.getScreenSize();
        jFrame.setSize(screenSize.width / 2, screenSize.height / 2);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Container content = jFrame.getContentPane();
        content.setLayout(new BorderLayout());
        Container textFields = new Container();
        textFields.setLayout(new FlowLayout());
        MapView mv = new MapView(image);
        textFields.add(new JLabel("Longitude: "));
        textFields.add(numberField(mv::setLongitude));
        textFields.add(new JLabel("Latitude: "));
        textFields.add(numberField(mv::setLatitude));
        content.add("North", textFields);
        content.add("Center", mv);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
    private static BufferedImage loadImage() throws IOException {
        try (InputStream in = main.class.getResourceAsStream("mendocino.gif");) {
            return ImageIO.read(in);
        }
    }
    private static JTextField numberField(DoubleConsumer onChange) {
        JTextField field = new JTextField("0", 10);
        field.addActionListener(e -> {
            double value = Double.parseDouble(field.getText());
            onChange.accept(value);
        });
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                double value = Double.parseDouble(field.getText());
                onChange.accept(value);
            }
        });
        return field;
    }

}
