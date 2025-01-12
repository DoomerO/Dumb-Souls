package entities.AE;

import entities.enemies.Enemy;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import main.Game;
import world.Camera;

public class AE_Rupture extends AE_Attack_Entity {
	
	public int time, frames, index, maxFrames = 10, maxIndex = 3;
	public double dmg;
	
	public AE_Rupture(int x, int y, int width, int height, BufferedImage sprite, int time, int dmg) {
		super(x, y, width, height, sprite, time);
		setMask(0, 0, width, height);
		getAnimation(80, 128, 16, 16, 3);
		this.dmg = dmg;
		depth = 0;
	}
	
	public void tick() {
		time ++;
		if (frames <= 40) {
			frames++;
			if (frames == maxFrames) {
				index++;
			}
		}
		else {
			index = 2;
		}
		if (time <= 6 && time % 2 == 0) {
			collisionEnemy(false, 0, attackCollision);
		}
		if (time == life) {
			die();
		}
	}

	Function<Enemy, Void> attackCollision = (target) -> { 
		target.life -= dmg;
		target.receiveKnockback(Game.player);
		return null;
	};
	
	public void render() {
		Game.gameGraphics.drawImage(animation[index], getX() - Camera.getX(), getY() - Camera.getY(), width, height, null);
	}
}
