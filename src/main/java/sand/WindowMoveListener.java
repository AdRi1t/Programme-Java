package sand;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WindowMoveListener extends ComponentAdapter {
    private float lastLocationX;
    private float lastLocationY;
    private ParticleGenerator sandGenerator;
    public WindowMoveListener(ParticleGenerator sandGenerator) {
        lastLocationX = 0;
        lastLocationY = 0;
        this.sandGenerator = sandGenerator;
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        float newLocationX = (float) e.getComponent().getLocation().getX();
        float newLocationY = (float) e.getComponent().getLocation().getY();
        float distanceX = newLocationX - lastLocationX;
        float distanceY = newLocationY - lastLocationY;
        sandGenerator.moveParticles(distanceX, distanceY);
        lastLocationX = newLocationX;
        lastLocationY = newLocationY;
    }
}