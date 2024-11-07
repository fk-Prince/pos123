package Frame.SwingComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MyTextField extends JPanel {

    private final JLabel label;
    private boolean isFocus;
    private final JTextField field;


    public MyTextField(String text, boolean bln) {
        setBorder(new EmptyBorder(5, 10, 5, 10));
        setLayout(new BorderLayout());

        if (bln) {
            label = new JLabel(text);
        } else {
            label = new JLabel(text.substring(4));
        }
        label.setVisible(false);
        label.setBorder(new EmptyBorder(0, 15, 0, 0));
        label.setForeground(Color.BLACK);
        add(label, BorderLayout.NORTH);

        field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                label.setVisible(isFocus || !getText().trim().isEmpty());


                if (!isFocus && getText().trim().isEmpty()) {
                    FontMetrics fm = g2d.getFontMetrics();
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(text, getInsets().left * 2, (getHeight() - fm.getHeight()) / 2 + fm.getAscent());
                }

                g2d.setColor(field.getText().isEmpty() ? Color.RED : Color.GREEN);
                g2d.drawLine(0, getHeight() - 3, getWidth(), getHeight() - 3);
                g2d.dispose();
            }
        };

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                isFocus = true;
                field.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                isFocus = !field.getText().trim().isEmpty();
                field.repaint();
            }
        });

        field.setBorder(new EmptyBorder(0, 15, 0, 15));
        field.setPreferredSize(new Dimension(250, 50));
        add(field, BorderLayout.SOUTH);

        setOpaque(false);

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(3, 4, getWidth() - 4, getHeight() - 4, 20, 20);


        g2d.dispose();

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 80);
    }

    public String getStringText() {
        return field.getText().trim();
    }

    public int getIntText() {
        try {
            return Integer.parseInt(field.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double getDoubleText() {
        try {
            return Double.parseDouble(field.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean isPriceValid() {
        try {
            double price = Double.parseDouble(field.getText().trim());
            if (price < 0) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isIntValid() {
        try {
            int i = Integer.parseInt(field.getText().trim());
            if (i < 0) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void setText(String text) {
        if (text.isEmpty()) {
            field.setText("");
        } else {
            field.setText(text);
        }
        isFocus = false;

    }

    public void setFocusable(boolean bln) {
        field.setFocusable(bln);
    }
}
