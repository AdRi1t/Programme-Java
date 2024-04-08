package sand;

import javax.swing.JPanel;

import sand.ParticleGenerator.Particle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.List;

public class DrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Color color = new Color(255, 255, 255);
	private transient ParticleGenerator sandGenerator;

	public DrawingPanel(ParticleGenerator sandGenerator) {
		this.sandGenerator = sandGenerator;
		this.setBackground(color);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		List<Particle> particlesCopy = Collections
				.synchronizedList(sandGenerator.getParticlesCopy());
		synchronized (particlesCopy) {
			for (Particle particle : particlesCopy) {
				int x = (int) particle.getX();
				int y = (int) particle.getY();
				int radius = Math.round(particle.getRadius());
				x -= radius / 2;
				y -= radius / 2;
				g2d.fillOval(x, y, radius, radius);
			}
		}
	}
}