package sand;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanelSwitcher extends JPanel {
    private CardLayout cl;
    private ParticleControlPanel particleControlPanel;
    private GravityControlPanel gravityControlPanel;
    private ParticleGenerator particleGenerator;

    public ControlPanelSwitcher(ParticleGenerator particleGenerator) {
        cl = new CardLayout();

        this.particleGenerator = particleGenerator;
        particleControlPanel = new ParticleControlPanel(particleGenerator);
        gravityControlPanel = new GravityControlPanel(particleGenerator);

        JPanel cards = new JPanel(cl);
        cards.add(particleControlPanel, "Particle Control");
        cards.add(gravityControlPanel, "Gravity Control");

        JButton btn = new JButton("Switch");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.next(cards);
                if (particleGenerator.isParticle()) {
                    particleGenerator.setGravityOn();
                } else {
                    particleGenerator.setGravityOff();
                }
            }
        });

        this.setLayout(new BorderLayout());
        this.add(cards, BorderLayout.CENTER);
        this.add(btn, BorderLayout.SOUTH);
    }

    public ParticleControlPanel getParticleControlPanel() {
        return particleControlPanel;
    }

    public GravityControlPanel getGravityControlPanel() {
        return gravityControlPanel;
    }

    public void updateFPS(int fps)
    {
        particleControlPanel.updateFPS(fps);
        gravityControlPanel.updateFPS(fps);
    }
    public void updateParticleCount()
    {
        particleControlPanel.updateParticleCount();
        gravityControlPanel.updateParticleCount();
    }
    public void updateCollision(float collision)
    {
        particleControlPanel.updateCollision(collision);
        gravityControlPanel.updateCollision(collision);
    }
}