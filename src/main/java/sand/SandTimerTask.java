package sand;

import java.util.TimerTask;

public class SandTimerTask extends TimerTask {
    private SandGenerator sandGenerator;
    private DrawingPanel drawingPanel;
    private ControlPanel controlPanel;
    private long startTime = System.nanoTime();
    private int frames = 0;

    public SandTimerTask(SandGenerator sandGenerator, DrawingPanel drawingPanel, ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
        this.sandGenerator = sandGenerator;
        this.drawingPanel = drawingPanel;
    }

    @Override
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
