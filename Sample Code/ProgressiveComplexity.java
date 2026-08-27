import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.RenderingHints;

/**
 * Lecture 6 Demonstration:
 * Progressive Drawing Complexity & Event Dispatch Thread (EDT) Safety
 * 
 * Demonstrates the Three Tiers of Visual Complexity:
 * 1. Tier 1: Orthogonal Primitive Geometry (Centered Square bounding box arithmetic)
 * 2. Tier 2: Multi-Point Polygon Triangulation (Non-collinearity validation & Triangle rendering)
 * 3. Tier 3: Procedural & Recursive Graphics (Generative Fractal Tree via coordinate transformations)
 */
public class ProgressiveComplexity extends JPanel {

    private int maxRecursionDepth = 8;
    private double branchAngle = Math.PI / 6; // 30 degrees in radians
    private double scaleFactor = 0.75;

    public ProgressiveComplexity() {
        setBackground(new Color(245, 247, 250));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smooth rendering of recursive geometry
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // ---------------------------------------------------------------------
        // TIER 1: Orthogonal Primitive Geometry (Centered Square)
        // ---------------------------------------------------------------------
        int squareSide = 100;
        int squareX = (width / 4) - (squareSide / 2);
        int squareY = (height / 2) - (squareSide / 2);

        g2d.setColor(new Color(70, 130, 180)); // Steel Blue
        g2d.drawRect(squareX, squareY, squareSide, squareSide);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("Tier 1: Centered Square", squareX - 10, squareY + squareSide + 20);

        // ---------------------------------------------------------------------
        // TIER 2: Multi-Point Polygon Triangulation (Triangle & Non-Collinearity)
        // ---------------------------------------------------------------------
        int x1 = width / 2 - 60, y1 = height / 2 + 50;
        int x2 = width / 2 + 60, y2 = height / 2 + 50;
        int x3 = width / 2,      y3 = height / 2 - 50;

        // Vector determinant / cross-product check for non-collinearity: Area * 2 != 0
        int doubleArea = Math.abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
        boolean isNonCollinear = (doubleArea != 0);

        if (isNonCollinear) {
            int[] xPoints = {x1, x2, x3};
            int[] yPoints = {y1, y2, y3};

            g2d.setColor(new Color(220, 100, 50)); // Crimson / Orange
            g2d.drawPolygon(xPoints, yPoints, 3);
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("Tier 2: Verified Triangle", x1 - 10, y1 + 30);
        }

        // ---------------------------------------------------------------------
        // TIER 3: Procedural & Recursive Graphics (Generative Fractal Tree)
        // ---------------------------------------------------------------------
        int treeBaseX = (3 * width) / 4;
        int treeBaseY = height / 2 + 80;
        int initialBranchLength = 60;

        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("Tier 3: Recursive Fractal Tree", treeBaseX - 50, treeBaseY + 30);

        // Start recursive branching from the root stem
        drawFractalBranch(g2d, treeBaseX, treeBaseY, initialBranchLength, -Math.PI / 2, maxRecursionDepth);
    }

    /**
     * Recursive method to draw tree branches.
     * Applies geometric scaling factors and angular matrix transformations across depth levels.
     */
    private void drawFractalBranch(Graphics2D g2d, double x, double y, double length, double angle, int depth) {
        if (depth == 0 || length < 2) {
            return;
        }

        // Calculate branch endpoint using polar-to-Cartesian conversion
        double endX = x + length * Math.cos(angle);
        double endY = y + length * Math.sin(angle);

        // Dynamic color transition based on recursion depth (Trunk -> Leaves)
        float colorHue = 0.35f * (1.0f - (float) depth / maxRecursionDepth); // Brown to Green
        g2d.setColor(Color.getHSBColor(colorHue, 0.7f, 0.5f + 0.5f * (float) depth / maxRecursionDepth));

        // Draw current branch segment
        g2d.drawLine((int) Math.round(x), (int) Math.round(y), (int) Math.round(endX), (int) Math.round(endY));

        // Recursive call for sub-branches (Left and Right split)
        drawFractalBranch(g2d, endX, endY, length * scaleFactor, angle - branchAngle, depth - 1);
        drawFractalBranch(g2d, endX, endY, length * scaleFactor, angle + branchAngle, depth - 1);
    }

    public static void main(String[] args) {
        // Enforce Swing Event Dispatch Thread (EDT) safety using invokeLater
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Lecture 6: Progressive Drawing Complexity & EDT Safety");
                ProgressiveComplexity panel = new ProgressiveComplexity();

                frame.add(panel);
                frame.setSize(800, 450);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null); // Center on screen
                frame.setVisible(true);
            }
        });
    }
}
