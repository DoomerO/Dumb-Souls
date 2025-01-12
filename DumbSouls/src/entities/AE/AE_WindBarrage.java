package entities.AE;

import entities.enemies.Enemy;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import main.Game;
import world.Camera; 

public class AE_WindBarrage extends AE_Attack_Entity{
	
	private double speed;
	private double dx, dy, damage;
	private int time;
	
	public AE_WindBarrage(int x, int y, int width, int height, double spd, double dx, double dy, double dmg, BufferedImage sprite, int time) {
		super(x, y, width, height, sprite, time);
		speed = spd;
		this.dx = dx;
		this.dy = dy;
		damage = dmg;
		push = -10;
		depth = 2;
		getAnimation(128, 112, 16, 16, 1);
		setMask(0, 0, 32, 32);
	}
	
	public void tick() {
		x += dx * speed;
		y += dy * speed;
		time++;
		if (time == life) {
			die();
		}
		
		collisionEnemy(true, 5, attackCollision);
		refreshTick();
	}

	Function<Enemy, Void> attackCollision = (target) -> { 
		target.life -= damage;
		target.receiveKnockback(this);
		return null;
	};
	
	public void render() {
		Game.gameGraphics.drawImage(animation[0], getX() - Camera.getX(), getY() - Camera.getY(), 32, 32, null);
	}
}
