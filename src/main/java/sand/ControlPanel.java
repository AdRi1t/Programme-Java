package sand;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

public class ControlPanel extends JPanel {
    SandGenerator sandGenerator;
    JSlider weightSlider, vxSlider, vySlider;
    JLabel weightLabel, vxLabel, vyLabel;

    public ControlPanel(sand.SandGenerator sandGenerator) {
        this.sandGenerator = sandGenerator;
        setLayout(new GridLayout(0, 1));

        // Créer les sliders et les labels
        weightSlider = createSlider(10, 20, 10);
        vxSlider = createSlider(0, 100, 10);
        vySlider = createSlider(0, 100,10);

        weightLabel = createLabel();
        vxLabel = createLabel();
        vyLabel = createLabel();

        // Ajouter les ChangeListeners aux sliders
        weightSlider.addChangeListener(e -> updateWeightValue());
        vxSlider.addChangeListener(e -> updateVxValue());
        vySlider.addChangeListener(e -> updateVyValue());

        // Ajouter les sliders et les labels au panel
        add(createSliderPanel(weightLabel, weightSlider));
        add(createSliderPanel(vxLabel, vxSlider));
        add(createSliderPanel(vyLabel, vySlider));

        // Mettre à jour les valeurs initiales des labels
        updateWeightValue();
        updateVxValue();
        updateVyValue();
    }

    private JSlider createSlider(int min, int max, int value) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
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

    private void updateVxValue() {
        sandGenerator.setVx(vxSlider.getValue());
        vxLabel.setText("Vitesse X maximum : " + vxSlider.getValue());
    }

    private void updateVyValue() {
        sandGenerator.setVy(vySlider.getValue());
        vyLabel.setText("Vietsse Y maximum : " + vySlider.getValue());
    }
}