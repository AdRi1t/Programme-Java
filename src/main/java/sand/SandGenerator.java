package sand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SandGenerator {
	ArrayList<Particle> particles;
	private double gravity = 0;
	private float incrementTime = 0.02f;
	private float spawnWeight = 5;
	private float maxRandomVelocity = 10;
	private Random random;
	private int collisionCount = 0;
	private float collisionCountPerSecond = 0;
	private float lastTime = 0;
	int width = 0;
	int height = 0;

	public SandGenerator(int width, int height) {
		this.width = width;
		this.height = height;
		random = new Random();
		particles = new ArrayList<>();
	}

	public int getNbParticles() {
		return particles.size();
	}

	public void updateSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setWeight(int weight) {
		spawnWeight = weight;
	}

	public void setIncrementTime(float incrementTime) {
		this.incrementTime = incrementTime;
	}

	public void setMaxRandomVelocity(float maxRandomVelocity) {
		this.maxRandomVelocity = maxRandomVelocity;
	}

	public class Particle {
		private float weight = 1;
		private float x;
		private float y;
		private float vx;
		private float vy;

		public Particle(float x, float y) {
			this.x = x;
			this.y = y;
			this.vx = (random.nextFloat() * maxRandomVelocity) * (random.nextInt(2) == 0 ? -1 : 1);
			this.vy = (random.nextFloat() * maxRandomVelocity) * (random.nextInt(2) == 0 ? -1 : 1);
			this.weight = spawnWeight;
		}

		public float getX() {
			return x;
		}

		public float getY() {
			return y;
		}

		public float getWeight() {
			return weight;
		}
	}

	public void addParticle(int x, int y) {
		particles.add(new Particle((float) x, (float) y));
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public void clearParticles() {
		particles.clear();
	}

	public float getCollisionCountPerSecond() {
		return collisionCountPerSecond;
	}

	public void updateParticles() {
		for (Particle p : particles) {
			p.x += p.vx * incrementTime;
			p.y += p.vy * incrementTime;
			if (p.x < 0) {
				p.x = 0;
				p.vx = -p.vx;
				collisionCount++;
			}
			if (p.x > width - 4) {
				p.x = width - 4;
				p.vx = -p.vx;
				collisionCount++;
			}
			if (p.y < 0) {
				p.y = 0;
				p.vy = -p.vy;
				collisionCount++;
			}
			if (p.y > height - 4) {
				p.y = height - 4;
				p.vy = -p.vy;
				collisionCount++;
			}
		}
		handleCollisions();
		updateCollisionsPerSecond();
	}

	public void handleCollisions() {
		for (int i = 0; i < particles.size(); i++) {
			for (int j = i + 1; j < particles.size(); j++) {
				Particle p1 = particles.get(i);
				Particle p2 = particles.get(j);

				float dx = p1.x - p2.x;
				float dy = p1.y - p2.y;
				float distance = (float) Math.sqrt((dy * dy + dx * dx));

				float weightSum = p1.weight / 2 + p2.weight / 2;

				if (distance < weightSum) {
					float vx1 = p1.vx;
					float vy1 = p1.vy;
					float vx2 = p2.vx;
					float vy2 = p2.vy;
					float totalWeigt = p1.weight + p2.weight;

					p1.vx = vx1 * (p1.weight - p2.weight) / totalWeigt
							+ vx2 * 2 * p2.weight / totalWeigt;
					p1.vy = vy1 * (p1.weight - p2.weight) / totalWeigt
							+ vy2 * 2 * p2.weight / totalWeigt;
					p2.vx = vx2 * (p2.weight - p1.weight) / totalWeigt
							+ vx1 * 2 * p1.weight / totalWeigt;
					p2.vy = vy2 * (p2.weight - p1.weight) / totalWeigt
							+ vy1 * 2 * p1.weight / totalWeigt;

				}
			}
		}
	}

	public void updateCollisionsPerSecond() {
		lastTime += incrementTime;
		if (lastTime > 1) {
			collisionCountPerSecond = collisionCount / lastTime;
			collisionCount = 0;
			lastTime = 0;
		}
	}
}
