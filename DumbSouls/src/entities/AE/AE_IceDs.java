package entities.AE;

import entities.enemies.Enemy;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public class AE_IceDs extends AE_Attack_Entity {
	
	public int damage;
	private int time = 0;
	
	public AE_IceDs(int x, int y, int width, int height, BufferedImage sprite, int time) {
		super(x, y, width, height, sprite, time);
		setMask(0, 0, 16, 16);
		getAnimation(32, 112, 16, 16, 1);
		depth = 0;
	}

	Function<Enemy, Void> attackCollision = (target) -> { 
		target.slowness = Math.max(target.slowness, 4);
		return null;
	};
	
	public void tick() {
		time ++;
		if (time == life) {
			die();
		}
		collisionEnemy(true, 5, attackCollision);
		refreshTick();
	}
}
