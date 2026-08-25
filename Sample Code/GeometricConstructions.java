import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Lecture 5 Demonstration:
 * 1. Two-point diagonal anchor rectangle construction: min(x1,x2), min(y1,y2), |x2-x1|, |y2-y1|
 * 2. Three-point non-collinear triangle construction via drawPolygon
 * 3. Object-Oriented Swing architecture (JFrame container, custom JPanel, super.paintComponent(g))
 * 4. Anonymous inner class for event handling (JButton action listener)
 */
public class GeometricConstructions extends JPanel {

    private boolean showFill = false;

    public GeometricConstructions() {
        // Default constructor
    }

    public void toggleFill() {
        this.showFill = !this.showFill;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. Rectangle constructed from two diagonal anchor points: P1(50, 60) and P2(250, 180)
        int x1 = 50, y1 = 60;
        int x2 = 250, y2 = 180;

        int rectX = Math.min(x1, x2);
        int rectY = Math.min(y1, y2);
        int rectWidth = Math.abs(x2 - x1);
        int rectHeight = Math.abs(y2 - y1);

        g.setColor(Color.BLUE);
        if (showFill) {
            g.fillRect(rectX, rectY, rectWidth, rectHeight);
        } else {
            g.drawRect(rectX, rectY, rectWidth, rectHeight);
        }

        // Draw diagonal anchor points
        g.setColor(Color.RED);
        g.fillOval(x1 - 4, y1 - 4, 8, 8);
        g.fillOval(x2 - 4, y2 - 4, 8, 8);
        g.drawString("P1 (" + x1 + "," + y1 + ")", x1 - 15, y1 - 8);
        g.drawString("P2 (" + x2 + "," + y2 + ")", x2 + 8, y2 + 12);

        // 2. Triangle constructed from 3 non-collinear points: (300, 50), (450, 200), (350, 220)
        int tx1 = 300, ty1 = 50;
        int tx2 = 450, ty2 = 200;
        int tx3 = 350, ty3 = 220;

        // Non-collinearity check: Area * 2 = |x1(y2 - y3) + x2(y3 - y1) + x3(y1 - y2)| > 0
        int doubleArea = Math.abs(tx1 * (ty2 - ty3) + tx2 * (ty3 - ty1) + tx3 * (ty1 - ty2));
        boolean isNonCollinear = (doubleArea != 0);

        if (isNonCollinear) {
            int[] xPoints = {tx1, tx2, tx3};
            int[] yPoints = {ty1, ty2, ty3};

            g.setColor(new Color(40, 160, 80)); // Custom green
            if (showFill) {
                g.fillPolygon(xPoints, yPoints, 3);
            } else {
                g.drawPolygon(xPoints, yPoints, 3);
            }
        }
    }

    public static void main(String[] args) {
        // Top-level JFrame window container
        JFrame frame = new JFrame("Lecture 5: Geometric Constructions & Swing OOP");
        frame.setSize(520, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Custom JPanel drawing surface
        GeometricConstructions canvas = new GeometricConstructions();
        frame.add(canvas, BorderLayout.CENTER);

        // JButton embedded into frame using Anonymous Inner Class for event handling
        JButton toggleButton = new JButton("Toggle Fill / Outline Mode");
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.toggleFill();
            }
        });
        frame.add(toggleButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
