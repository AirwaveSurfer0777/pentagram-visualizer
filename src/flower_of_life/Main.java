package flower_of_life;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JPanel implements ActionListener {
   
	private static final long serialVersionUID = 1L;
	private double rotation = 0;
    private Timer timer;

    public Main() {
        // Setup timer for animation
        timer = new Timer(20, this); // 20ms delay between frames
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set anti-aliasing for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw pentagram with current rotation
        drawPentagram(g2d, 
            getWidth() / 2,     // centerX
            getHeight() / 2,    // centerY
            150,                // size
            rotation            // current rotation angle
        );
    }
    
    private void drawPentagram(Graphics2D g2d, int centerX, int centerY, int size, double rotationAngle) {
        // Calculate the five points of the pentagram
        double[][] points = new double[5][2];
        
        // Angle calculations for an inverted pentagram
        double goldenAngle = Math.PI * 2 / 5;
        double baseRotation = Math.PI / 2; // Rotated to point downwards
        
        // Calculate vertex points with additional rotation
        for (int i = 0; i < 5; i++) {
            double angle = i * goldenAngle + baseRotation + rotationAngle;
            points[i][0] = centerX + size * Math.cos(angle);
            points[i][1] = centerY + size * Math.sin(angle);
        }
        
        // Create pentagram path
        Path2D pentagram = new Path2D.Double();
        
        // Connect points to form the inverted star
        pentagram.moveTo(points[0][0], points[0][1]);
        pentagram.lineTo(points[2][0], points[2][1]);
        pentagram.lineTo(points[4][0], points[4][1]);
        pentagram.lineTo(points[1][0], points[1][1]);
        pentagram.lineTo(points[3][0], points[3][1]);
        pentagram.closePath();
        
        // Create circle path manually
        Path2D circle = new Path2D.Double();
        int numPoints = 100; // Smooth circle approximation
        for (int i = 0; i <= numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            double x = centerX + size * Math.cos(angle);
            double y = centerY + size * Math.sin(angle);
            
            if (i == 0) {
                circle.moveTo(x, y);
            } else {
                circle.lineTo(x, y);
            }
        }
        
        // Styling
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2f)); // Thicker line
        
        // Draw circle
        g2d.draw(circle);
        
        // Draw pentagram
        g2d.draw(pentagram);
        
        // Optional: fill with semi-transparent red
        g2d.setColor(new Color(139, 0, 0, 100)); // Dark red with transparency
        g2d.fill(pentagram);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Increment rotation
        rotation += 0.05; // Adjust speed of rotation
        
        // Repaint the panel
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rotating Pentagram");
            Main pentagramPanel = new Main();
            frame.add(pentagramPanel);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
        });
    }
}