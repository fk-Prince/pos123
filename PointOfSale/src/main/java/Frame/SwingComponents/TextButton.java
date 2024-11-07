package Frame.SwingComponents;

import javax.swing.*;
import java.awt.*;

public class TextButton extends JButton {
    private String text, highlight;

    public TextButton(String text, String highlight) {
        this.text = text;
        this.highlight = highlight;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        FontMetrics fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, 5, (getHeight() - fm.getAscent()) / 2 + fm.getAscent());


        g2d.setColor(getModel().isRollover() ? new Color(7, 7, 7) : new Color(182, 28, 91));
        g2d.drawString(highlight, 5 + fm.stringWidth(text), (getHeight() - fm.getAscent()) / 2 + fm.getAscent());


        g2d.setColor(!getModel().isRollover() ? new Color(7, 7, 7) : new Color(182, 28, 91));
        g2d.drawLine(5 + fm.stringWidth(text) + 3,
                (getHeight() - fm.getAscent()) / 2 + fm.getAscent() + 3,
                5 + fm.stringWidth(text) + fm.stringWidth(highlight),
                (getHeight() - fm.getAscent()) / 2 + fm.getAscent() + 3);
        g2d.dispose();

    }
}
