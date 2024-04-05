package sand;

import javax.swing.JPanel;

import sand.SandGenerator.Particle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Color color = new Color(255, 255, 255);
	private SandGenerator sandGenerator;

	public DrawingPanel(SandGenerator sandGenerator) {
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
		List<Particle> particlesCopy = sandGenerator.getParticles();
		for (Particle particle : particlesCopy) {
			int x = (int)particle.getX();
			int y = (int)particle.getY();
			int[] nx = {x-2, x+2, x+2, x-2};
			int[] ny = {y-2, y-2, y+2, y+2};
			Shape square = new Polygon(nx, ny, 4);
			g2d.fill(square);
		}
		g2d.dispose();
	}	
}