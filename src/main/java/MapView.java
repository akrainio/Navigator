import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class MapView extends Component {
    private BufferedImage image;
    private final Point offset = new Point();
    private double zoomLevel = 1;
    private final Point markerPosition;

    public MapView(BufferedImage image) {
        this.image = image;
        MouseController mouseController = new MouseController();
        addMouseMotionListener(mouseController);
        addMouseListener(mouseController);
        addMouseWheelListener(mouseController);
        markerPosition = new Point((image.getWidth() / 2), (image.getHeight() / 2));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, offset.x, offset.y, (int) (image.getWidth() * zoomLevel),
                (int) (image.getHeight() * zoomLevel), null);
        Point p = imageToScreen(markerPosition);
        g.setColor(Color.red);
        ((Graphics2D) g).setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        g.drawOval(p.x, p.y, 30, 30);
    }

    public void setLatitude(double v) {
        markerPosition.y = (int) v;
        repaint();
    }

    public void setLongitude(double v) {
        markerPosition.x = (int) v;
        repaint();
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    private Point imageToScreen(Point imagePoint) {
        int newX = (int) (imagePoint.x * zoomLevel) + offset.x;
        int newY = (int) (imagePoint.y * zoomLevel) + offset.y;
        return new Point(newX, newY);
    }

    public class MouseController extends MouseAdapter {
        private final Point dragStart = new Point();

        @Override
        public void mousePressed(MouseEvent e) {
            dragStart.setLocation(e.getX(), e.getY());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point delta = new Point(dragStart.x - e.getX(), dragStart.y - e.getY());
            offset.x -= delta.x;
            offset.y -= delta.y;
            repaint();
            dragStart.setLocation(e.getX(), e.getY());
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            double zoomChange = (e.getWheelRotation() * .1);
            //if (e.getWheelRotation() > 0)
            zoomLevel -= (zoomChange);
            if (zoomLevel < .5) {
                zoomLevel = .5;
                return;
            }
            if (zoomLevel > 3) {
                zoomLevel = 3;
                return;
            }
            offset.x += e.getX() * zoomChange;
            offset.y += e.getY() * zoomChange;
            repaint();
        }
    }
}
