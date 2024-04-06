package sand;

import java.awt.BorderLayout;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program started"); 
        SandGenerator sandGenerator = new SandGenerator(1000,800);
        MouseClickRecorder recorder = new MouseClickRecorder(sandGenerator);
        DrawingPanel drawingPanel = new DrawingPanel(sandGenerator);
        ControlPanel controlPanel = new ControlPanel(sandGenerator);
        drawingPanel.addMouseMotionListener(recorder);
        drawingPanel.addComponentListener(new WindowResizeListener(sandGenerator));
        
        Timer timer = new Timer(); 
        timer.scheduleAtFixedRate(new SandTimerTask(sandGenerator, drawingPanel), 0L, 20L);

        JFrame frame = new JFrame();
        frame.setSize(1000, 800);
        frame.add(controlPanel, BorderLayout.WEST);
        frame.add(drawingPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}