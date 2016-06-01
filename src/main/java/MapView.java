import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

/**
 * Created by Andrei on 5/31/2016.
 */
public class MapView extends Component {
    private final BufferedImage image;
    private final Point dragStart = new Point();
    private final Point offset = new Point();
    private double zoomLevel = 1;

    public MapView(BufferedImage image) {
        this.image = image;
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point delta = new Point(dragStart.x - e.getX(), dragStart.y - e.getY());
                offset.x -= delta.x;
                offset.y -= delta.y;
                repaint();
                dragStart.setLocation(e.getX(), e.getY());
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStart.setLocation(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }
        });
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                zoomLevel -= (e.getWheelRotation()) * .1;
                if (zoomLevel < .5) zoomLevel = .5;
                if (zoomLevel > 1.5) zoomLevel = 1.5;
//                offset.x += (image.getWidth() - image.getWidth() * zoomLevel) / 2;
//                offset.y += (image.getHeight() - image.getHeight() * zoomLevel) / 2;
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, offset.x, offset.y, (int) (image.getWidth() * zoomLevel),
                (int) (image.getHeight() * zoomLevel), null);
    }


}
