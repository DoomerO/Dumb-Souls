package entities.AE;

import entities.enemies.Enemy;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public class AE_IceSpike extends AE_Attack_Entity {
	private int time;
	
	public AE_IceSpike(int x, int y, int width, int height, BufferedImage sprite, int time, int damage) {
		super(x, y, width, height, sprite, time);
		this.damage = damage;
		getAnimation(48, 128, 16, 16, 1);
		setMask(2, 4, 12, 12);
	}

	Function<Enemy, Void> attackCollision = (target) -> { 
		target.life -= damage;
		target.slowness = Math.max(target.slowness, damage);
		return null;
	};
	
	public void tick() {
		time++;
		if (time == life) {
			die();
		}
		collisionEnemy(true, 5, attackCollision);
		refreshTick();
	}
}
