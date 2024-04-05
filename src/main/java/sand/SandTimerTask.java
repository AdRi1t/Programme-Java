package sand;
import java.util.TimerTask;


public class SandTimerTask extends TimerTask {
    private SandGenerator sandGenerator;
    private DrawingPanel drawingPanel;

    public SandTimerTask(SandGenerator sandGenerator, DrawingPanel drawingPanel) {
        this.sandGenerator = sandGenerator;
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void run() {
        drawingPanel.repaint();
		sandGenerator.updateParticles(0.025);
    }
}
