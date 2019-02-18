package kasperek.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Tomasz Kasperek
 * @version 0.1 02/18/2019
 * @see GameWindow
 * @since 0.1
 */

public class GamePane extends JPanel {
    private JButton nextStep;

    public GamePane() {
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);

        nextStep = new JButton("Next step");
        nextStep.setBounds(990, 10, 100, 40);

        this.add(nextStep);
        nextStep.setVisible(true);
    }
}