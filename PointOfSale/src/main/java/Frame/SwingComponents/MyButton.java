package Frame.SwingComponents;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {

    private final String text;
    private boolean primary;

    public MyButton(String text, boolean primary) {
        this.primary = primary;
        this.text = text;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        if (primary) {
            g2d.setColor(getModel().isRollover() ? Color.WHITE : Color.DARK_GRAY);
            g2d.fillRoundRect(3, 4, getWidth() - 4, getHeight() - 5, 20, 20);
            g2d.setColor(getModel().isRollover() ? Color.DARK_GRAY : Color.WHITE);
            g2d.drawRoundRect(3, 4, getWidth() - 4, getHeight() - 5, 20, 20);
        } else {
            g2d.setColor(getModel().isRollover() ? Color.DARK_GRAY : Color.WHITE);
            g2d.fillRoundRect(3, 4, getWidth() - 4, getHeight() - 5, 20, 20);
            g2d.setColor(getModel().isRollover() ? Color.WHITE : Color.DARK_GRAY);
            g2d.drawRoundRect(3, 4, getWidth() - 4, getHeight() - 5, 20, 20);
        }

        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(text, (getWidth() - fm.stringWidth(text)) / 2, (getHeight() - fm.getDescent()) / 2 + fm.getAscent() - 5);

        g2d.dispose();
    }
}
