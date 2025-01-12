package entities.AE;

import entities.enemies.Enemy;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import main.Game;
import world.Camera;

public class AE_HellFlame extends AE_Attack_Entity {
	
	private double speed;
	private double dx, dy, damage;
	private int time, spawntime;
	private int maxFrames = 20, frames = 0, index, maxIndex = 2;
	
	public AE_HellFlame(int x, int y, int width, int height, double spd, double dx, double dy, int dmg, BufferedImage sprite, int time) {
		super(x, y, height, width, sprite, time);
		speed = spd;
		this.dx = dx;
		this.dy = dy;
		damage = dmg;
		getAnimation(144, 112, 16, 16, 2);
		setMask(0, 0, 48, 48);
		depth = 3;
	}
	
	public void tick() {
		x += dx * speed;
		y += dy * speed;
		time++;
		frames ++;
		if (frames == maxFrames) {
			index++;
			frames = 0;
			if (index == maxIndex) {
				index = 0;
			}
		}
		if (time == life) {
			die();
		}
		
		collisionEnemy(true, 15, attackCollision);
		refreshTick();
		spawnFire();
	}

	Function<Enemy, Void> attackCollision = (target) -> { 
		target.life -= damage;
        return null;
	};
	
	private void spawnFire() {
		spawntime++;
		if (spawntime == 4) {
			spawntime = 0;
			Game.entities.add(new AE_Fire(getX(), getY() + 32, 16, 16, null, 40));
			Game.entities.add(new AE_Fire(getX() + 32, getY() + 32, 16, 16, null, 40));
		}
	}
	
	public void render() {
		Game.gameGraphics.drawImage(animation[index], getX() - Camera.getX(), getY() - Camera.getY(), 48, 48, null);
	}
}
