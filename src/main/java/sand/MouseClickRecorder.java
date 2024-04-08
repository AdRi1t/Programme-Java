package sand;

import java.awt.event.*;

import javax.swing.SwingUtilities;

public class MouseClickRecorder extends MouseAdapter implements MouseMotionListener {
	private ParticleGenerator particleGenerator;

	public MouseClickRecorder(ParticleGenerator sandGenerator) {
		this.particleGenerator = sandGenerator;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		if (SwingUtilities.isLeftMouseButton(e)) {
			int mouseX = e.getX();
			int mouseY = e.getY();
			particleGenerator.addParticle(mouseX, mouseY);
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			particleGenerator.clearParticles();
		}
	}

	@Override
public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);
    if (SwingUtilities.isLeftMouseButton(e)) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        particleGenerator.addParticle(mouseX, mouseY);
    }
    if (SwingUtilities.isRightMouseButton(e)) {
        particleGenerator.clearParticles();
    }
}

}