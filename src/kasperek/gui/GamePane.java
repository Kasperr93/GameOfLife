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
    private JButton allSteps;

    /**
     * Default pane constructor. It is responsibility for setter all needed parameters for window.
     */

    public GamePane() {
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        initializeNextStepButton();
        initializeAllStepButton();
    }


    private void initializeNextStepButton() {
        nextStep = new JButton("Next step");
        nextStep.setBounds(995, 10, 100, 40);

        this.add(nextStep);
        nextStep.setVisible(true);
    }

    private void initializeAllStepButton() {
        allSteps = new JButton("All steps");
        allSteps.setBounds(1095, 10, 100, 40);

        this.add(allSteps);
        allSteps.setVisible(true);
    }
}