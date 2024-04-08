package sand;

import java.awt.BorderLayout;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program started"); 
        ParticleGenerator particleGenerator = new ParticleGenerator(1000,800);
        MouseClickRecorder recorder = new MouseClickRecorder(particleGenerator);
        DrawingPanel drawingPanel = new DrawingPanel(particleGenerator);
        ControlPanelSwitcher controlPanelSwitcher = new ControlPanelSwitcher(particleGenerator);
        drawingPanel.addMouseMotionListener(recorder);
        drawingPanel.addMouseListener(recorder);
        drawingPanel.addComponentListener(new WindowResizeListener(particleGenerator));
        
        ParticleTimerTask particleTimerTask = new ParticleTimerTask(particleGenerator, drawingPanel, controlPanelSwitcher, 20);

        JFrame frame = new JFrame();
        frame.addComponentListener(new WindowMoveListener(particleGenerator));
        frame.setSize(1000, 800);
        frame.add(controlPanelSwitcher, BorderLayout.WEST);
        frame.add(drawingPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}