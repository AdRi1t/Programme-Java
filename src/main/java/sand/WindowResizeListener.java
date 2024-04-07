package sand;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WindowResizeListener extends ComponentAdapter {
    private ParticleGenerator sandGenerator;

    public WindowResizeListener(ParticleGenerator sandGenerator) {
        this.sandGenerator = sandGenerator;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int newWidth = e.getComponent().getWidth();
        int newHeight = e.getComponent().getHeight();
        sandGenerator.updateSize(newWidth, newHeight);
    }
    
}