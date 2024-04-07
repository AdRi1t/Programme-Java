package sand;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanelSwitcher extends JPanel {
    private CardLayout cl;

    public ControlPanelSwitcher(ParticleControlPanel particleControlPanel, GravityControlPanel gravityControlPanel) {
        cl = new CardLayout();

        JPanel cards = new JPanel(cl);
        cards.add(particleControlPanel, "Particle Control");
        cards.add(gravityControlPanel, "Gravity Control");

        JButton btn = new JButton("Switch");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.next(cards);
            }
        });

        this.setLayout(new BorderLayout());
        this.add(cards, BorderLayout.CENTER);
        this.add(btn, BorderLayout.SOUTH);
    }
}