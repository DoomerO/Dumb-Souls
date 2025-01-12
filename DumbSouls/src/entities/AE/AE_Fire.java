package entities.AE;

import entities.enemies.Enemy;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import main.Game;
import world.Camera;

public class AE_Fire extends AE_Attack_Entity {
	
	private int maxFrames = 20, frames = 0, index, maxIndex = 2, time;
	
	public AE_Fire(int x, int y, int width, int height, BufferedImage sprite, int time) {
		super(x, y, width, height, sprite, time);
		setMask(0, 8, 16, 8);
		getAnimation(0, 112, 16, 16, maxIndex);
		depth = 2;
	}

	Function<Enemy, Void> attackCollision = (target) -> { 
		target.life -= 2;
        return null;
	};
	
	public void tick() {
		frames ++;
		time ++;
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
	}
	
	public void render() {
		Game.gameGraphics.drawImage(animation[index], getX() - Camera.getX(), getY() - Camera.getY(), null);
	}
}
