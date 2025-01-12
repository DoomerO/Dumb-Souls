package entities.AE;

import entities.enemies.Enemy;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public class AE_PunchRain extends AE_Attack_Entity {
	
	private double speed;
	private double dx, dy, damage;
	private int time;
	
	public AE_PunchRain(int x, int y, int height, int width, double spd, double dirx, double diry, int dmg, BufferedImage sprite, int time) {
		super(x, y, height, width, sprite, time);
		speed = spd;
		dx = dirx;
		dy = diry;
		damage = dmg;
		push = 0.5;
		getAnimation(176, 112, 16, 16, 1);
		setMask(2, 6, 3, 3);
		depth = 2;
	}
	
	public void tick() {
		x += dx * speed;
		y += dy * speed;
		time++;
		if (time == life) {
			die();
		}
		
		collisionEnemy(false, 0, attackCollision);
	}

	Function<Enemy, Void> attackCollision = (target) -> { 
		target.life -= damage;
		target.receiveKnockback(this);
		die();
		return null;
	};
}
