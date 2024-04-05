package sand;

import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program started"); 
        SandGenerator sandGenerator = new SandGenerator(1000,800);
        MouseClickRecorder recorder = new MouseClickRecorder(sandGenerator);
        DrawingPanel drawingPanel = new DrawingPanel(sandGenerator);
        drawingPanel.addMouseMotionListener(recorder);

        Timer timer = new Timer(); 
        timer.scheduleAtFixedRate(new SandTimerTask(sandGenerator, drawingPanel), 0L, 25);

        JFrame frame = new JFrame();
        frame.addComponentListener(new WindowResizeListener(sandGenerator));
        frame.setSize(1000, 800);
        frame.add(drawingPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}