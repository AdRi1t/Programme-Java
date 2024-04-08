package sand;

import javax.swing.*;
import java.awt.*;

public class GravityControlPanel extends JPanel {
    transient ParticleGenerator sandGenerator;
    JLabel title;
    JSlider weightSlider;
    JSlider maxVelocitySlider;
    JSlider incrementTimeSlider;
    JSlider gravitySlider;
    JLabel weightLabel;
    JLabel maxVelocityLabel;
    JLabel incrementTimeLabel;
    JLabel gravityLabel;
    JLabel fpsLabel;
    JLabel particleCountLabel;
    JLabel collisionLabel;

    public GravityControlPanel(ParticleGenerator sandGenerator) {
        this.sandGenerator = sandGenerator;
        this.setBackground(Color.lightGray);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        setLayout(layout);

        title = new JLabel("Gravity Control");
        title.setBackground(Color.GRAY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        titlePanel.setBackground(Color.lightGray);
        titlePanel.add(title);

        weightSlider = createSlider(8, 50, 10);
        maxVelocitySlider = createSlider(0, 200, 10);
        incrementTimeSlider = createSlider(0, 100, 10);
        gravitySlider = createSlider(-20, 20, 0);

        weightLabel = createLabel();
        maxVelocityLabel = createLabel();
        incrementTimeLabel = createLabel();
        gravityLabel = createLabel();

        fpsLabel = createInfoLabel();
        particleCountLabel = createInfoLabel();
        collisionLabel = createInfoLabel();

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.setBackground(Color.lightGray);
        infoPanel.add(fpsLabel);
        infoPanel.add(collisionLabel);
        infoPanel.add(particleCountLabel);

        weightSlider.addChangeListener(e -> updateWeightValue());
        maxVelocitySlider.addChangeListener(e -> updateVelocityValue());
        incrementTimeSlider.addChangeListener(e -> updateIncrementTimeValue());
        gravitySlider.addChangeListener(e -> updateGravity());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        add(titlePanel, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.2;
        add(createSliderPanel(weightLabel, weightSlider), gbc);
        gbc.gridy = 2;
        add(createSliderPanel(maxVelocityLabel, maxVelocitySlider), gbc);
        gbc.gridy = 3;
        add(createSliderPanel(incrementTimeLabel, incrementTimeSlider), gbc);
        gbc.gridy = 4;
        add(createSliderPanel(gravityLabel, gravitySlider), gbc);
        gbc.gridy = 5;
        gbc.weighty = 0.1;
        add(infoPanel, gbc);

        updateWeightValue();
        updateVelocityValue();
        updateIncrementTimeValue();
        updateGravity();
        updateFPS(50);
        updateParticleCount();
    }

    private JSlider createSlider(int min, int max, int value) {
        JSlider slider = new JSlider(SwingConstants.HORIZONTAL, min, max, value);
        slider.setBackground(Color.lightGray);
        slider.setMajorTickSpacing((max - min) / 5);
        slider.setMinorTickSpacing((max - min) / 20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    private JLabel createLabel() {
        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JLabel createInfoLabel() {
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.lightGray);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(150, 30));
        return label;
    }

    private JPanel createSliderPanel(JLabel label, JSlider slider) {
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.add(label, BorderLayout.NORTH);
        sliderPanel.add(slider, BorderLayout.CENTER);
        sliderPanel.setBackground(Color.gray);
        sliderPanel.setPreferredSize(new Dimension(sliderPanel.getPreferredSize().width, 100));
        return sliderPanel;
    }

    private void updateWeightValue() {
        sandGenerator.setWeight(weightSlider.getValue());
        weightLabel.setText("Masse : " + weightSlider.getValue());
    }

    private void updateVelocityValue() {
        sandGenerator.setMaxRandomVelocity(maxVelocitySlider.getValue());
        maxVelocityLabel.setText("Vitesse Random maximum : " + maxVelocitySlider.getValue());
    }

    private void updateIncrementTimeValue() {
        sandGenerator.setIncrementTime((float) incrementTimeSlider.getValue() / 1000);
        incrementTimeLabel.setText(
                "Temps d'incrémentation : " + (float) incrementTimeSlider.getValue() / 1000 + "s");
    }

    public void updateFPS(int fps) {
        fpsLabel.setText("FPS: " + fps);
    }

    public void updateParticleCount() {
        particleCountLabel.setText("Particles: " + sandGenerator.getNbParticles());
    }

    public void updateCollision(float collision) {
        collisionLabel.setText("Collision: " + (int) collision + " per second");
    }

    public void updateGravity() {
        sandGenerator.setGravity((float) gravitySlider.getValue() / 10);
        gravityLabel.setText("Gravité : " + (float) gravitySlider.getValue() / 10);
    }
}