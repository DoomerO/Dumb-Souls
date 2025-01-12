package entities.AE;

import entities.enemies.Enemy;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import main.Game;
import world.Camera;

public class AE_PoisonPool extends AE_Attack_Entity {
	
	public int frames, index, maxFrames = 20, maxIndex = 2;
	public double dmg;
	
	public AE_PoisonPool(int x, int y, int width, int height, BufferedImage sprite, int time, int dmg) {
		super(x, y, width, height, sprite, time);
		setMask(0, 0, width, height);
		getAnimation(128, 128, 16, 16, 2);
		this.dmg = dmg;
	}
	
	public void tick() {
		life--;
		frames++;
		if (frames == maxFrames) {
			index++;
			frames = 0;
			if (index == maxIndex) {
				index = 0;
			}
		}

		if (life % 12 == 0) {
			collisionEnemy(false, 0, attackCollision);
		}
		
		if (life == 0) {
			die();
		}
	}

	Function<Enemy, Void> attackCollision = (target) -> { 
		target.slowness = Math.max(target.slowness, 5);
		target.life -= dmg;
		return null;
	};
	
	public void render() {
		Game.gameGraphics.drawImage(animation[index], getX() - Camera.getX(), getY() - Camera.getY(), width, height, null);
	}
}
