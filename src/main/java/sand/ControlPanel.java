package sand;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    transient SandGenerator sandGenerator;
    JSlider weightSlider;
    JSlider maxVelocitySlider;
    JSlider incrementTimeSlider;
    JLabel weightLabel;
    JLabel maxVelocityLabel;
    JLabel incrementTimeLabel;
    JLabel fpsLabel;
    JLabel particleCountLabel;
    JLabel collisionLabel;

    public ControlPanel(sand.SandGenerator sandGenerator) {
        this.sandGenerator = sandGenerator;
        setLayout(new GridLayout(0, 1));

        weightSlider = createSlider(10, 20, 10);
        maxVelocitySlider = createSlider(0, 100, 10);
        incrementTimeSlider = createSlider(0, 100, 10);

        weightLabel = createLabel();
        maxVelocityLabel = createLabel();
        incrementTimeLabel = createLabel();

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

        add(createSliderPanel(weightLabel, weightSlider));
        add(createSliderPanel(maxVelocityLabel, maxVelocitySlider));
        add(createSliderPanel(incrementTimeLabel, incrementTimeSlider));  
        add(infoPanel);

        updateWeightValue();
        updateVelocityValue();
        updateIncrementTimeValue();
        updateFPS(50);
        updateParticleCount();
    }

    private JSlider createSlider(int min, int max, int value) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
        slider.setBackground(Color.lightGray);
        slider.setMajorTickSpacing((max - min) / 5);
        slider.setMinorTickSpacing((max - min) / 20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    private JLabel createLabel() {
        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
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
        weightLabel.setText("Poids : " + weightSlider.getValue());
    }

    private void updateVelocityValue() {
        sandGenerator.setMaxRandomVelocity(maxVelocitySlider.getValue());
        maxVelocityLabel.setText("Vitesse Random maximum : " + maxVelocitySlider.getValue());
    }

    private void updateIncrementTimeValue() {
        sandGenerator.setIncrementTime((float)incrementTimeSlider.getValue()/1000);
        incrementTimeLabel.setText("Temps d'incrémentation : " + (float)incrementTimeSlider.getValue()/1000 + "s");
    }
    
    public void updateFPS(int fps) {
        fpsLabel.setText(" FPS: " + fps);
    }

    public void updateParticleCount() {
        particleCountLabel.setText(" Particles: " + sandGenerator.getNbParticles());
    }

    public void updateCollision(float collision) {
        collisionLabel.setText(" Collision: " + (int)collision + " per second");
    }
}