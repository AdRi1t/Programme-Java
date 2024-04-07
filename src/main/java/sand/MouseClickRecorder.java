package sand;

import java.awt.event.*;

import javax.swing.SwingUtilities;

public class MouseClickRecorder extends MouseAdapter implements MouseMotionListener {
	private ParticleGenerator sandGenerator;

	public MouseClickRecorder(ParticleGenerator sandGenerator) {
		this.sandGenerator = sandGenerator;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		if (SwingUtilities.isLeftMouseButton(e)) {
			int mouseX = e.getX();
			int mouseY = e.getY();
			sandGenerator.addParticle(mouseX, mouseY);
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			sandGenerator.clearParticles();
		}
	}

}