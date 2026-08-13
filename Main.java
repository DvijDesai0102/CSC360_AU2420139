import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Main extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw a red line
        g.setColor(Color.RED);
        // (startX, startY, endX, endY)
        g.drawLine(50, 50, 300, 50);

        // Draw a blue outline rectangle
        g.setColor(Color.BLUE);
        // (x, y, width, height)
        g.drawRect(50, 80, 150, 80);

        // Draw a solid green circle
        g.setColor(Color.GREEN);
        // (x, y, width, height)
        g.fillOval(50, 180, 80, 80);

        // Draw an orange oval
        g.setColor(Color.ORANGE);
        g.drawOval(160, 180, 120, 80);
    }

    public static void main(String[] args) {
        // Create the window
        JFrame frame = new JFrame("Simple Java Graphics");

        // Add our drawing panel to the window
        Main panel = new Main();
        frame.add(panel);

        // Set window properties
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }
}
