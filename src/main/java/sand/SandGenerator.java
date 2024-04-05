package sand;

import java.util.ArrayList;
import java.util.List;

public class SandGenerator {
	ArrayList<Particle> particles;
	private double incrementTime = 0.01;
	private double gravity = 6.5;
	int width = 0;
	int height = 0;

	public SandGenerator(int width, int height) {
		this.width = width;
		this.height = height;
		particles = new ArrayList<>();
	}

	public int getNbParticles() {
		return particles.size();
	}

	public void updateSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public class Particle {
		double x;
		double y;
		double vx;
		double vy;

		public Particle(double x, double y) {
			this.x = x;
			this.y = y;
			this.vx = 0;
			this.vy = 0;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}
	}

	public void addParticle(int x, int y) {
		particles.add(new Particle((double) x, (double) y));
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public void clearParticles() {
		particles.clear();
	}

	public void updateParticles(double incrementTime) {
		for (Particle p : particles) {
			p.x += p.vx;
			p.y += p.vy;
			if (p.y >= height - 2) {
				p.y = height - 2;
				p.vy = -p.vy * 0.6; 
				p.vx = 0;
			} else {
				p.vy += gravity * incrementTime;
			}
		}
		handleCollisions();
	}

	public void handleCollisions() {
		for (int i = 0; i < particles.size(); i++) {
			for (int j = i + 1; j < particles.size(); j++) {
				Particle p1 = particles.get(i);
				Particle p2 = particles.get(j);

				double dx = p1.x - p2.x;
				double dy = p1.y - p2.y;
				double distance = Math.sqrt(dx * dx + dy * dy);

				if (distance < 5) {
					double vx1 = p1.vx;
					double vy1 = p1.vy;
					p1.vx = p2.vx;
					p1.vy = p2.vy;
					p2.vx = vx1;
					p2.vy = vy1;
				}
			}
		}
	}
}
