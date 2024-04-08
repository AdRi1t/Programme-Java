package sand;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

public class ParticleGenerator {
	ArrayList<Particle> particles;
	private float gravity = 0;
	private float incrementTime = 0.02f;
	private float spawnWeight = 5;
	private float maxRandomVelocity = 10;
	private Random random;
	private int collisionCount = 0;
	private float collisionCountPerSecond = 0;
	private float lastTime = 0;
	boolean isParticle = true;
	int width = 0;
	int height = 0;

	UnaryOperator<Float> randomFloat = max -> random.nextFloat() * max
			* (random.nextInt(2) == 0 ? -1 : 1);

	public ParticleGenerator(int width, int height) {
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

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public boolean isParticle() {
		return isParticle;
	}

	public void setGravityOn() {
		isParticle = false;
	}

	public void setGravityOff() {
		isParticle = true;
	}

	public class Particle {
		private float radius;
		private float x;
		private float y;
		private float vx;
		private float vy;

		public Particle(float x, float y) {
			this.x = x + randomFloat.apply(radius * 5);
			this.y = y + randomFloat.apply(radius * 5);
			this.vx = randomFloat.apply(maxRandomVelocity);
			this.vy = randomFloat.apply(maxRandomVelocity);
			this.radius = spawnWeight;
		}

		public float getX() {
			return x;
		}

		public float getY() {
			return y;
		}

		public float getRadius() {
			return radius;
		}
	}

	public List<Particle> getParticlesCopy() {
		synchronized (particles) {
			return new ArrayList<>(particles);
		}
	}

	public void addParticle(int x, int y) {
		synchronized (particles) {
			particles.add(new Particle(x, y));
		}
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
		synchronized (particles) {
			if (isParticle) {
				updateParticleMovement();
				handleCollisions();
				handleParticleBoundaryCollision();
				updateCollisionsPerSecond();
			} else {
				updateParticleInteractions();
				updateParticleMovement();
				handleCollisionsWithGravity();
				handleParticleTooFar();
			}
		}
	}

	private void handleParticleTooFar() {
		Iterator<Particle> iterator = particles.iterator();
		while (iterator.hasNext()) {
			Particle p = iterator.next();
			float distance = (float) Math.sqrt((p.y * p.y + p.x * p.x));
			if (distance > 2000) {
				iterator.remove();
			}
		}
	}

	private void handleCollisionsWithGravity() {
		for (int i = 0; i < particles.size(); i++) {
			Particle p1 = particles.get(i);
			for (int j = i + 1; j < particles.size(); j++) {
				Particle p2 = particles.get(j);
				float dx = p1.x - p2.x;
				float dy = p1.y - p2.y;
				float distance = (float) Math.sqrt((dy * dy + dx * dx));

				float radiusSum = p1.radius / 2 + p2.radius / 2;

				if (distance < radiusSum) {
					collisionCount++;
					float vx1 = p1.vx;
					float vy1 = p1.vy;
					float vx2 = p2.vx;
					float vy2 = p2.vy;
					float p1Weight = 4 / 3 * (float) Math.PI * p1.radius * p1.radius * p1.radius;
					float p2Weight = 4 / 3 * (float) Math.PI * p2.radius * p2.radius * p2.radius;
					float totalWeigt = p1Weight + p2Weight;

					p1.vx = vx1 * p1Weight / totalWeigt + vx2 * p2Weight / totalWeigt;
					p1.vy = vy1 * p1Weight / totalWeigt + vy2 * p2Weight / totalWeigt;
					p2.vx = vx2 * p2Weight / totalWeigt + vx1 * p1Weight / totalWeigt;
					p2.vy = vy2 * p2Weight / totalWeigt + vy1 * p1Weight / totalWeigt;

					p1.vx = p1.vx * 0.9f;
					p1.vy = p1.vy * 0.9f;
					p2.vx = p2.vx * 0.9f;
					p2.vy = p2.vy * 0.9f;
				}
			}

		}
	}

	private void updateParticleMovement() {
		for (Particle p : particles) {
			p.x += p.vx * incrementTime;
			p.y += p.vy * incrementTime;
		}
	}

	private void handleParticleBoundaryCollision() {
		for (Particle p : particles) {
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
	}

	private void updateParticleInteractions() {
		for (Particle p : particles) {
			handleParticleInteraction(p);
		}
	}

	private void handleParticleInteraction(Particle p) {
		float totalAx = 0;
		float totalAy = 0;
		for (Particle p2 : particles) {
			if (p != p2) {
				float dx = p.x - p2.x;
				float dy = p.y - p2.y;
				float distanceSquared = (dx * dx + dy * dy);
				float weightP1 = (float) (4.0 / 3.0 * Math.PI * Math.pow(p.radius, 3)) * 10;
				float weightP2 = (float) (4.0 / 3.0 * Math.PI * Math.pow(p2.radius, 3)) * 10;
				float force = (-gravity * weightP1 * weightP2 / (distanceSquared + 10));
				float angle = (float) Math.atan2(dy, dx);
				float fx = force * (float) Math.cos(angle);
				float fy = force * (float) Math.sin(angle);
				float ax = fx / weightP1;
				float ay = fy / weightP1;

				totalAx += ax;
				totalAy += ay;
			}
		}

		p.vx += totalAx * incrementTime;
		p.vy += totalAy * incrementTime;
	}

	public void handleCollisions() {

		for (int i = 0; i < particles.size(); i++) {
			Particle p1 = particles.get(i);
			for (int j = i + 1; j < particles.size(); j++) {
				Particle p2 = particles.get(j);

				float dx = p1.x - p2.x;
				float dy = p1.y - p2.y;
				float distance = (float) Math.sqrt((dy * dy + dx * dx));

				float radiusSum = p1.radius / 2 + p2.radius / 2;

				if (distance < radiusSum) {
					float vx1 = p1.vx;
					float vy1 = p1.vy;
					float vx2 = p2.vx;
					float vy2 = p2.vy;
					float totalWeigt = p1.radius + p2.radius;

					p1.vx = vx1 * (p1.radius - p2.radius) / totalWeigt
							+ vx2 * 2 * p2.radius / totalWeigt;
					p1.vy = vy1 * (p1.radius - p2.radius) / totalWeigt
							+ vy2 * 2 * p2.radius / totalWeigt;
					p2.vx = vx2 * (p2.radius - p1.radius) / totalWeigt
							+ vx1 * 2 * p1.radius / totalWeigt;
					p2.vy = vy2 * (p2.radius - p1.radius) / totalWeigt
							+ vy1 * 2 * p1.radius / totalWeigt;

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

	public void moveParticles(float distanceX, float distanceY) {
		for (Particle p : particles) {
			p.x += 0.4 * distanceX;
			p.y += 0.4 * distanceY;
		}
	}
}
