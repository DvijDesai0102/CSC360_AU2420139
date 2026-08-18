import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;

public class Square extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Side length of the square
        int side = 150;

        // Calculate coordinates to center the square dynamically
        int x = (getWidth() - side) / 2;
        int y = (getHeight() - side) / 2;

        // Draw a clean square outline without any color
        g.drawRect(x, y, side, side);
    }

    public static void main(String[] args) {
        // Create the window
        JFrame frame = new JFrame("Square - Java Graphics");

        // Add our drawing panel to the window
        Square panel = new Square();
        frame.add(panel);

        // Set window properties
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }
}
