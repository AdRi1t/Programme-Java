package sand;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParticleTimerTask {
    private ParticleGenerator sandGenerator;
    private DrawingPanel drawingPanel;
    private ControlPanelSwitcher controlPanel;
    private long startTime = System.nanoTime();
    private int frames = 0;
    private Timer timer;

    public ParticleTimerTask(ParticleGenerator sandGenerator, DrawingPanel drawingPanel,
                             ControlPanelSwitcher controlPanel, int delay) {
        this.controlPanel = controlPanel;
        this.sandGenerator = sandGenerator;
        this.drawingPanel = drawingPanel;
        this.timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });
        this.timer.start();
    }

    public void run() {
        drawingPanel.repaint();
        sandGenerator.updateParticles();
        controlPanel.updateParticleCount();
        controlPanel.updateCollision(sandGenerator.getCollisionCountPerSecond());
        frames++;
        long currentTime = System.nanoTime();
        double elapsedTime = (currentTime - startTime) / 1_000_000_000.0;
        if (elapsedTime >= 1) {
            controlPanel.updateFPS(frames);
            frames = 0;
            startTime = System.nanoTime();
        }
    }
}