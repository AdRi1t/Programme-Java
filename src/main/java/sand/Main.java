package sand;

import java.awt.BorderLayout;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program started"); 
        ParticleGenerator sandGenerator = new ParticleGenerator(1000,800);
        MouseClickRecorder recorder = new MouseClickRecorder(sandGenerator);
        DrawingPanel drawingPanel = new DrawingPanel(sandGenerator);
        ParticleControlPanel controlPanel = new ParticleControlPanel(sandGenerator);
        GravityControlPanel gravityControlPanel = new GravityControlPanel(sandGenerator);
        ControlPanelSwitcher controlPanelSwitcher = new ControlPanelSwitcher(controlPanel, gravityControlPanel);
        drawingPanel.addMouseMotionListener(recorder);
        drawingPanel.addComponentListener(new WindowResizeListener(sandGenerator));
        
        Timer timer = new Timer(); 
        timer.scheduleAtFixedRate(new ParticleTimerTask(sandGenerator, drawingPanel, controlPanel), 10L, 20L);

        JFrame frame = new JFrame();
        frame.addComponentListener(new WindowMoveListener(sandGenerator));
        frame.setSize(1000, 800);
        frame.add(controlPanelSwitcher, BorderLayout.WEST);
        frame.add(drawingPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}