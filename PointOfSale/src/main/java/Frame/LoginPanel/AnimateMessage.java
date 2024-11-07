package Frame.LoginPanel;

import Frame.SwingComponents.MessageDialog;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class AnimateMessage {
    // private static double POSITION_MESSAGE = 0;
    private static Timer timer;
    private static JPanel currentMessagePanel;

    public static void showMessage(String message, boolean flag, MigLayout migLayout, JPanel parent, double X_POSITION, double Y) {
        if ( currentMessagePanel != null) {
            timer.stop();
            parent.remove(currentMessagePanel);
            parent.revalidate();
            parent.repaint();
            currentMessagePanel = null;
        }

        MessageDialog panel = new MessageDialog(message, flag);
        parent.add(panel, "pos " + X_POSITION + "% " + Y + "%");
        currentMessagePanel = panel;

        double[] Y_POSITION = {Y};
        timer = new Timer(0, e -> {
            Y_POSITION[0] += 0.2;
            migLayout.setComponentConstraints(panel, "pos " + X_POSITION + "% " + Y_POSITION[0] + "%");
            if (Y_POSITION[0] >= 2) {
                timer.stop();
                Y_POSITION[0] = 0;
            }
            parent.revalidate();
        });
        timer.start();
    }
}
